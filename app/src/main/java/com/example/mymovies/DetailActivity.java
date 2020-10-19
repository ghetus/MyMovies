package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.adapters.ReviewAdapter;
import com.example.mymovies.adapters.TrailerAdapter;
import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.data.Review;
import com.example.mymovies.data.Trailer;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewtitleOriginal;
    private TextView textViewraiting;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private static String lang;

    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private int movieId;
    private MainViewModel viewModel;
    private Movie movie;
    private FavouriteMovie favouriteMovie;
    private ImageView imageViewAddFavourite;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.itemMain:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                intent = new Intent(this, FavouriteActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        lang = Locale.getDefault().getLanguage();

        Intent getIntent = getIntent();
        if (getIntent != null && getIntent.hasExtra("id")) {
            movieId = getIntent.getIntExtra("id", 0);
        } else {
            finish();
        }

        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        imageViewAddFavourite = findViewById(R.id.imageViewAddFavourite);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewtitleOriginal = findViewById(R.id.textViewTitleOriginal);
        textViewraiting = findViewById(R.id.textViewRaiting);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewOverview = findViewById(R.id.textViewOverview);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if (!getIntent.getBooleanExtra("favourite", false)) {
            movie = viewModel.getMovieById(movieId);
        } else {
            movie = viewModel.getFavouriteMovieById(movieId);
        }
        setFavouriteMovie();
        Picasso.get().load(movie.getPosterBigPath()).placeholder(R.drawable.movie).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewtitleOriginal.setText(movie.getTitleOriginal());
        textViewraiting.setText(Double.toString(movie.getVoteAvg()));
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewOverview.setText(movie.getOverview());

        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        reviewAdapter = new ReviewAdapter();
        trailerAdapter = new TrailerAdapter();
        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentToTrailer);
            }
        });
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewTrailers.setAdapter(trailerAdapter);
        recyclerViewReviews.setAdapter(reviewAdapter);
        JSONObject jsonObjectTrailers = NetworkUtils.getJSONVideo(movieId, lang);
        JSONObject jsonObjectReviews = NetworkUtils.getJSONReview(movieId, lang);
        ArrayList<Trailer> trailers = JSONUtils.getTrailerFromJSON(jsonObjectTrailers);
        ArrayList<Review> reviews = JSONUtils.getReviewFromJSON(jsonObjectReviews);
        trailerAdapter.setTrailers(trailers);
        reviewAdapter.setReviews(reviews);
    }

    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null) {
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
        } else {
            viewModel.deleteFavouriteMovie(favouriteMovie);
        }
        setFavouriteMovie();
    }

    private void setFavouriteMovie() {
        favouriteMovie = viewModel.getFavouriteMovieById(movieId);
        if (favouriteMovie != null) {
            imageViewAddFavourite.setImageResource(R.drawable.add_favourite);
        } else {
            imageViewAddFavourite.setImageResource(R.drawable.del_favourite);
        }
    }
}
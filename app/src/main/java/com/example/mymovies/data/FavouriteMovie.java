package com.example.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity (tableName = "favourite_movies")
public class FavouriteMovie extends Movie {

    public FavouriteMovie(int uniqueId, int id, String title, String titleOriginal, String overview, String posterPath, String posterBigPath, String backPath, int voteCount, double voteAvg, String releaseDate) {
        super(uniqueId, id, title, titleOriginal, overview, posterPath, posterBigPath, backPath, voteCount, voteAvg, releaseDate);
    }

    @Ignore
    public FavouriteMovie (Movie movie) {
        super(movie.getUniqueId(), movie.getId(), movie.getTitle(), movie.getTitleOriginal(), movie.getOverview(), movie.getPosterPath(),
                movie.getPosterBigPath(), movie.getBackPath(), movie.getVoteCount(), movie.getVoteAvg(), movie.getReleaseDate());

    }
}

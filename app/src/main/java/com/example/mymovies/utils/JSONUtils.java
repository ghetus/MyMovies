package com.example.mymovies.utils;

import com.example.mymovies.data.Movie;
import com.example.mymovies.data.Review;
import com.example.mymovies.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {
    // general key
    private static final String KEY_RESULT = "results";
    // reviews key
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";
    // video key
    private static final String KEY_VIDEO = "key";
    private static final String KEY_VIDEO_NAME = "name";
    private static final String BASE_URL_YOUTUBE = "https://www.youtube.com/watch?v=%s";
    // movie key
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TITLE_ORIGINAL = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACK_PATH = "backdrop_path";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_VOTE_AVG = "vote_average";
    private static final String KEY_REALESE_DATE = "release_date";
    // poster key
    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                String title = objectMovie.getString(KEY_TITLE);
                String titleOriginal = objectMovie.getString(KEY_TITLE_ORIGINAL);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + '/' + objectMovie.getString(KEY_POSTER_PATH);
                String posterBigPath = BASE_POSTER_URL + BIG_POSTER_SIZE + '/' + objectMovie.getString(KEY_POSTER_PATH);
                String backPath = objectMovie.getString(KEY_BACK_PATH);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                double voteAvg = objectMovie.getDouble(KEY_VOTE_AVG);
                String releaseDate = objectMovie.getString(KEY_REALESE_DATE);
                result.add(new Movie(id, title, titleOriginal, overview, posterPath, posterBigPath, backPath, voteCount, voteAvg, releaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Review> getReviewFromJSON(JSONObject jsonObject) {
        ArrayList<Review> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonReview = jsonArray.getJSONObject(i);
                String author = jsonReview.getString(KEY_AUTHOR);
                String content = jsonReview.getString(KEY_CONTENT);
                result.add(new Review(author, content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Trailer> getTrailerFromJSON(JSONObject jsonObject) {
        ArrayList<Trailer> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonReview = jsonArray.getJSONObject(i);
                String key = String.format(BASE_URL_YOUTUBE, jsonReview.getString(KEY_VIDEO));
                String name = jsonReview.getString(KEY_VIDEO_NAME);
                result.add(new Trailer(key, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}

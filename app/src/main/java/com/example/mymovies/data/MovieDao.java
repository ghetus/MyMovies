package com.example.mymovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("Select * From movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("Select * From favourite_movies")
    LiveData<List<FavouriteMovie>> getAllFavouriteMovies();

    @Query("Select * From movies Where id == :movieId")
    Movie getMovieById(int movieId);

    @Query("Select * From favourite_movies Where id == :movieId")
    FavouriteMovie getFavouriteMovieById(int movieId);

    @Query("Delete From movies")
    void deleteAllMovies();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Insert
    void insertFavouriteMovie(FavouriteMovie movie);

    @Delete
    void deleteFavouriteMovie(FavouriteMovie movie);
}

package com.example.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey (autoGenerate = true)
    private int uniqueId;
    private int id;
    private String title;
    private String titleOriginal;
    private String overview;
    private String posterPath;
    private String posterBigPath;
    private String backPath;
    private int voteCount;
    private double voteAvg;
    private String releaseDate;

    public Movie(int uniqueId, int id, String title, String titleOriginal, String overview, String posterPath, String posterBigPath, String backPath, int voteCount, double voteAvg, String releaseDate) {
        this.uniqueId = uniqueId;
        this.id = id;
        this.title = title;
        this.titleOriginal = titleOriginal;
        this.overview = overview;
        this.posterPath = posterPath;
        this.posterBigPath = posterBigPath;
        this.backPath = backPath;
        this.voteCount = voteCount;
        this.voteAvg = voteAvg;
        this.releaseDate = releaseDate;
    }

    @Ignore
    public Movie(int id, String title, String titleOriginal, String overview, String posterPath, String posterBigPath, String backPath, int voteCount, double voteAvg, String releaseDate) {
        this.id = id;
        this.title = title;
        this.titleOriginal = titleOriginal;
        this.overview = overview;
        this.posterPath = posterPath;
        this.posterBigPath = posterBigPath;
        this.backPath = backPath;
        this.voteCount = voteCount;
        this.voteAvg = voteAvg;
        this.releaseDate = releaseDate;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleOriginal() {
        return titleOriginal;
    }

    public void setTitleOriginal(String titleOriginal) {
        this.titleOriginal = titleOriginal;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterBigPath() {
        return posterBigPath;
    }

    public void setPosterBigPath(String posterBigPath) {
        this.posterBigPath = posterBigPath;
    }

    public String getBackPath() {
        return backPath;
    }

    public void setBackPath(String backPath) {
        this.backPath = backPath;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

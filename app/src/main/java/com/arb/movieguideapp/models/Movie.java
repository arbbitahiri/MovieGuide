package com.arb.movieguideapp.models;

public class Movie {

    private String title;
    private String description;
    private String year_genre_time;
    private String ratings;
    private int thumbnail;
    private int coverImg;

    public Movie(String title, String description, String year_genre_time, String ratings, int thumbnail, int coverImg) {
        this.title = title;
        this.description = description;
        this.year_genre_time = year_genre_time;
        this.ratings = ratings;
        this.thumbnail = thumbnail;
        this.coverImg = coverImg;
    }

    public Movie(String title, int thumbnail, int coverImg) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.coverImg = coverImg;
    }

    public Movie(String title, String year_genre_time, String ratings, int thumbnail) {
        this.title = title;
        this.year_genre_time = year_genre_time;
        this.ratings = ratings;
        this.thumbnail = thumbnail;
    }

    public String getYear_genre_time() {
        return year_genre_time;
    }

    public void setYear_genre_time(String year_genre_time) {
        this.year_genre_time = year_genre_time;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(int coverImg) {
        this.coverImg = coverImg;
    }
}

package com.arb.movieguideapp.models;

import com.google.gson.annotations.SerializedName;

public class Slide {

    @SerializedName("title")
    private String title;
    private String genre;
    @SerializedName("backdrop_path")
    private String thumbnail;

    public Slide(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Slide(String title, String genre, String thumbnail) {
        this.title = title;
        this.genre = genre;
        this.thumbnail = thumbnail;
    }

    public String getRatings() {
        return genre;
    }

    public void setRatings(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return "https://image.tmdb.org/t/p/w780" + thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

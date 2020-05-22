package com.arb.movieguideapp.models;

public class Slide {

    private String title;
    private String ratings;
    private int thumbnail;

    public Slide(String title, int thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Slide(String title, String ratings, int thumbnail) {
        this.title = title;
        this.ratings = ratings;
        this.thumbnail = thumbnail;
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

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}

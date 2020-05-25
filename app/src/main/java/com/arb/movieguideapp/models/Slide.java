package com.arb.movieguideapp.models;

public class Slide {

    private String title;
    private String genre;
    private int thumbnail;

    public Slide(String title, int thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Slide(String title, String genre, int thumbnail) {
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

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}

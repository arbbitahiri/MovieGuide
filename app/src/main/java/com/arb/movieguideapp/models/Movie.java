package com.arb.movieguideapp.models;

public class Movie {

    private String title;
    private String description;
    private int thumbnail;
    private int coverImg;

    public Movie(String title, String description, int thumbnail, int coverImg) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.coverImg = coverImg;
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

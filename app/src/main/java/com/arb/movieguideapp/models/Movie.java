package com.arb.movieguideapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String description;
    @SerializedName("genre_ids")
    private List<Long> genre;
    private List<Category> categories;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("poster_path")
    private String thumbnail;
    @SerializedName("backdrop_path")
    private String coverImg;

    public Movie() { }

    public Movie(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Movie(int id, String title, String description, List<Long> genre,
                 String releaseDate, double voteAverage, String thumbnail, String coverImg) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.thumbnail = thumbnail;
        this.coverImg = coverImg;
        this.categories = new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getGenre() {
        return genre;
    }

    public void setGenre(List<Long> genre) {
        this.genre = genre;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getThumbnail() { return "https://image.tmdb.org/t/p/w342" + thumbnail; }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCoverImg() {
        return "https://image.tmdb.org/t/p/w1280" + coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void mapGenres(List<Category> categoryList) {
        if (this.categories == null)
            this.categories = new ArrayList<>();

        for (Long l : genre) {
            for (Category c : categoryList) {
                if (l.equals(c.getId()))
                    this.categories.add(c);
            }
        }
    }
}

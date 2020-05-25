package com.arb.movieguideapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String description;
    private String genre;
    private String time;
    @SerializedName("release_date")
    private String releaseDate;
    private String ratings;
    @SerializedName("poster_path")
    private String thumbnail;
    @SerializedName("backdrop_path")
    private String coverImg;
    private int _thumbnail;
    private int _coverImg;

    public Movie() { }

    public Movie(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        description = in.readString();
        genre = in.readString();
        ratings = in.readString();
        thumbnail = in.readString();
        coverImg = in.readString();
    }

    public Movie(String title, String description, String genre, String ratings, String thumbnail, String coverImg) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.ratings = ratings;
        this.thumbnail = thumbnail;
        this.coverImg = coverImg;
    }

    public Movie(String title, int _thumbnail, int _coverImg) {
        this.title = title;
        this._thumbnail = _thumbnail;
        this._coverImg = _coverImg;
    }

    public Movie(String title, String genre, String ratings, int _thumbnail) {
        this.title = title;
        this.genre = genre;
        this.ratings = ratings;
        this._thumbnail = _thumbnail;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getThumbnail() { return "https://image.tmdb.org/t/p/w342" + thumbnail; }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCoverImg() {
        return "https://image.tmdb.org/t/p/w780"+ coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int get_thumbnail() {
        return _thumbnail;
    }

    public void set_thumbnail(int _thumbnail) {
        this._thumbnail = _thumbnail;
    }

    public int get_coverImg() {
        return _coverImg;
    }

    public void set_coverImg(int _coverImg) {
        this._coverImg = _coverImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(genre);
        dest.writeString(time);
        dest.writeString(releaseDate);
        dest.writeString(ratings);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeString(coverImg);
    }
}

package com.arb.movieguideapp.clients;

import com.arb.movieguideapp.models.Movie;
import com.arb.movieguideapp.models.wrappers.MovieWrapper;
import com.arb.movieguideapp.models.wrappers.SlideWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetMovieDataService {

    @GET("movie/popular?api_key=81229d17288cd3c3a979724d4d5c9cae&language=en-US&page=1&region=US")
    Call<MovieWrapper> getPopular();

    @GET("movie/now_playing?api_key=81229d17288cd3c3a979724d4d5c9cae&language=en-US&page=1&region=us")
    Call<MovieWrapper> getNowPlaying();

    @GET("movie/top_rated?api_key=81229d17288cd3c3a979724d4d5c9cae&language=en-US&page=1&region=US")
    Call<MovieWrapper> getTopRated();

    @GET("movie/top_rated?api_key=81229d17288cd3c3a979724d4d5c9cae&language=en-US&page=1&region=US")
    Call<SlideWrapper> getTopRatedSlide();

    @GET("movie/upcoming?api_key=81229d17288cd3c3a979724d4d5c9cae&language=en-US&page=1&region=us")
    Call<MovieWrapper> getUpcoming();

    Call<Movie> searchByTitle(String title);
}
package com.arb.movieguideapp.clients;

import com.arb.movieguideapp.models.Movie;
import com.arb.movieguideapp.models.MovieWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDatService {

    @GET("movie/top_rated?api_key=81229d17288cd3c3a979724d4d5c9cae&language=en-US&page=1&region=US")
    Call<MovieWrapper> getTopRated();

    @GET("movie/latest?api_key=81229d17288cd3c3a979724d4d5c9cae&language=en-US")
    Call<MovieWrapper> getLatest();

    @GET("movie/popular?api_key=81229d17288cd3c3a979724d4d5c9cae&language=en-US&page=1&region=US")
    Call<MovieWrapper> getPopular();

    Call<Movie> searchByTitle(String title);
}

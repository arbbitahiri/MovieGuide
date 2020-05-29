package com.arb.movieguideapp.clients;

import com.arb.movieguideapp.models.wrappers.CategoryWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetCategoryDataService {
    @GET("genre/movie/list?api_key=81229d17288cd3c3a979724d4d5c9cae&language=en-US")
    Call<CategoryWrapper> getCategory();
}

package com.arb.movieguideapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.clients.GetGenreDataService;
import com.arb.movieguideapp.clients.GetMovieDataService;
import com.arb.movieguideapp.listeners.MovieClickListener;
import com.arb.movieguideapp.models.Genre;
import com.arb.movieguideapp.models.Movie;
import com.arb.movieguideapp.models.wrappers.GenreWrapper;
import com.arb.movieguideapp.models.wrappers.MovieWrapper;
import com.arb.movieguideapp.ui.activity.MovieDetailActivity;
import com.arb.movieguideapp.ui.adapters.SearchMovieAdapter;
import com.arb.movieguideapp.utils.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView searchRecyclerView;
    private SearchMovieAdapter movieAdapter;
    private List<Genre> genreList = new ArrayList<>();

    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.search_movie);
        searchView.setOnQueryTextListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Inflate the layout for this fragment
        setRetainInstance(true);
    }

    private void getMovieGenres() {
        try {
            GetGenreDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetGenreDataService.class);
            Call<GenreWrapper> call = service.getGenre();
            call.enqueue(new Callback<GenreWrapper>() {
                @Override
                public void onResponse(Call<GenreWrapper> call, Response<GenreWrapper> response) {
                    if (response.body() != null) {
                        genreList = response.body().getGenre();
                    } else
                        showError();
                }

                @Override
                public void onFailure(Call<GenreWrapper> call, Throwable t) {
                    Log.d("Error ", t.getMessage());
                    showError();
                }
            });
        }
        catch (Exception e) {
            Log.d("Error ", e.getMessage());
            showError();
        }
    }

    private void populateMovies(List<Movie> movieList){
        movieAdapter = new SearchMovieAdapter(movieList, new MovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movie);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getMovies() {
        searchRecyclerView = getActivity().findViewById(R.id.movie_search_recycler);
        searchRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getMovieGenres();
        String input_movie = searchView.getQuery().toString();
        GetMovieDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetMovieDataService.class);
        Call<MovieWrapper> call = service.getMovie(input_movie);

        call.enqueue(new Callback<MovieWrapper>() {
            @Override
            public void onResponse(Call<MovieWrapper> call, Response<MovieWrapper> response) {
                if (response.body() != null) {
                    List<Movie> movieList = response.body().getMovies();
                    populateMovies(movieList);

                    for (Movie m : movieList)
                        m.mapGenres(genreList);

                    searchRecyclerView.setAdapter(movieAdapter);
                    searchRecyclerView.smoothScrollToPosition(0);
                }

                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieWrapper> call, Throwable t) {
                showError();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showError() {
        Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        getMovies();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

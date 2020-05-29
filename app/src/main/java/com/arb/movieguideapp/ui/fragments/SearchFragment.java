package com.arb.movieguideapp.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.clients.GetGenreDataService;
import com.arb.movieguideapp.clients.GetMovieDataService;
import com.arb.movieguideapp.listeners.GenreClickListener;
import com.arb.movieguideapp.listeners.MovieClickListener;
import com.arb.movieguideapp.models.Genre;
import com.arb.movieguideapp.models.Movie;
import com.arb.movieguideapp.models.wrappers.GenreWrapper;
import com.arb.movieguideapp.models.wrappers.MovieWrapper;
import com.arb.movieguideapp.ui.activity.MovieDetailActivity;
import com.arb.movieguideapp.ui.adapters.CategoryAdapter;
import com.arb.movieguideapp.ui.adapters.MovieAdapter;
import com.arb.movieguideapp.utils.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private List<Genre> genreList;
    private Genre genre;

    private EditText editSearch;

    private AlertDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCategory = view.findViewById(R.id.rv_categories);
        editSearch = view.findViewById(R.id.editSearch);

        String inputMovie = editSearch.toString();

        if (!inputMovie.isEmpty()) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            rvCategory.setLayoutManager(new GridLayoutManager(getActivity(),2));
            rvCategory.setItemAnimator(new DefaultItemAnimator());
            if (isNetworkAvailable()) {
                getMovies(inputMovie);
                movieAdapter.notifyDataSetChanged();
            }
        } else {
            rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCategory.setItemAnimator(new DefaultItemAnimator());

            rvCategory.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            GetGenreDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetGenreDataService.class);
            getGenres(rvCategory, service.getGenre());
        }
    }

    private void populateMovies(List<Movie> movieList){
        movieAdapter = new MovieAdapter(movieList, new MovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movie);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvCategory.setAdapter(movieAdapter);
    }

    private void getMovies(String input_movie) {
        GetMovieDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetMovieDataService.class);
        Call<MovieWrapper> call = service.getMovie(input_movie);

        call.enqueue(new Callback<MovieWrapper>() {
            @Override
            public void onResponse(Call<MovieWrapper> call, Response<MovieWrapper> response) {
                if (response.body() != null) {
                    movieList = response.body().getMovies();
                    populateMovies(movieList);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieWrapper> call, Throwable t) {
                progressDialog.dismiss();
                showError();
            }
        });
    }

    private void getGenres(final RecyclerView recyclerView, Call<GenreWrapper> call) {
        try {
            call.enqueue(new Callback<GenreWrapper>() {
                @Override
                public void onResponse(Call<GenreWrapper> call, Response<GenreWrapper> response) {
                    if (response.body() != null) {
                        List<Genre> movieList = response.body().getGenre();
                        categoryAdapter = new CategoryAdapter(movieList, new GenreClickListener() {
                            @Override
                            public void onCategoryClick(Genre mGenre) {
                                getFragment(mGenre.getCategories());
                            }
                        });
                        recyclerView.setAdapter(categoryAdapter);
                        recyclerView.smoothScrollToPosition(0);

                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        showError();
                    }

                    categoryAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GenreWrapper> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("Error ", t.getMessage());
                    showError();
                }
            });
        }
        catch (Exception e) {
            progressDialog.dismiss();
            Log.d("Error ", e.getMessage());
            showError();
        }
    }

    private void getFragment(String categoryName) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.searchLayout, new MovieGenreFragment(categoryName))
                .addToBackStack(null)
                .commit();
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
}

package com.arb.movieguideapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.adapters.FavoriteMovieAdapter;
import com.arb.movieguideapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private List<Movie> movieList = new ArrayList<>();
    private FavoriteMovieAdapter favoriteMovieAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavorites = view.findViewById(R.id.favorite_recycler);
        favoriteMovieAdapter = new FavoriteMovieAdapter(getContext(), movieList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvFavorites.setLayoutManager(layoutManager);
        rvFavorites.setItemAnimator(new DefaultItemAnimator());

        rvFavorites.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        rvFavorites.setAdapter(favoriteMovieAdapter);

        populateMovieData();
    }

    private void populateMovieData() {
        Movie movie = new Movie("Avengers: Endgame", "2019 ‧ Action/Sci-fi ‧ 3h 2m",
                "8.4 IMDb | 94% Rotten Tomatoes", R.drawable.z_avengersendgame);
        movieList.add(movie);

        movie = new Movie("The Dark Knight", "2008 ‧ Action/Adventure ‧ 2h 32m",
                "9 IMDb | 94% Rotten Tomatoes", R.drawable.z_darkknight);
        movieList.add(movie);

        movie = new Movie("Inception", "2010 ‧ Thriller/Sci-fi ‧ 2h 28m",
                "8.8 IMDb | 87% Rotten Tomatoes", R.drawable.z_inception);
        movieList.add(movie);

        movie = new Movie("Interstellar", "2014 ‧ Sci-fi/Adventure ‧ 2h 49m",
                "8.6 IMDb | 72% Rotten Tomatoes", R.drawable.z_interstellar);
        movieList.add(movie);

        movie = new Movie("Once Upon a Time in Hollywood", "2019 ‧ Comedy/Drama ‧ 2h 40m",
                "7.7 IMDb | 85% Rotten Tomatoes", R.drawable.z_ouatih);
        movieList.add(movie);

        movie = new Movie("Dark Waters", "2019 ‧ Drama/Thriller ‧ 2h 6m",
                "7.6 IMDb | 90% Rotten Tomatoes", R.drawable.z_darkwaters);
        movieList.add(movie);

        movie = new Movie("The Departed", "2006 ‧ Thriller/Crime ‧ 2h 31m",
                "8.5 IMDb | 91% Rotten Tomatoes", R.drawable.z_thedeparted);
        movieList.add(movie);

        movie = new Movie("The Godfather", "1972 ‧ Crime/Crime ‧ 2h 58m",
                "9.2 IMDb | 98% Rotten Tomatoes", R.drawable.z_thegodfather);
        movieList.add(movie);

        movie = new Movie("The Irishman", "2019 ‧ Crime/Drama ‧ 3h 30m",
                "7.9 IMDb | 96% Rotten Tomatoes", R.drawable.z_theirishman);
        movieList.add(movie);

        movie = new Movie("Zodiac", "2007 ‧ Thriller/Mystery ‧ 2h 42m",
                "7.7 IMDb | 89% Rotten Tomatoes", R.drawable.z_zodiac);
        movieList.add(movie);

        favoriteMovieAdapter.notifyDataSetChanged();
    }
}

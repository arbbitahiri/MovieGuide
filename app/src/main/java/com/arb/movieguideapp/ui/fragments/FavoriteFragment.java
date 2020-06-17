package com.arb.movieguideapp.ui.fragments;

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
import com.arb.movieguideapp.ui.adapters.FavoriteMovieAdapter;
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
        favoriteMovieAdapter = new FavoriteMovieAdapter(movieList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvFavorites.setLayoutManager(layoutManager);
        rvFavorites.setItemAnimator(new DefaultItemAnimator());

        rvFavorites.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        rvFavorites.setAdapter(favoriteMovieAdapter);
    }
}

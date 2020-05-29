package com.arb.movieguideapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.models.Genre;
import com.arb.movieguideapp.ui.adapters.CategoryAdapter;

import java.util.List;

public class MovieGenreFragment extends Fragment {

    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;
    private List<Genre> genreList;

    private List<Integer> genre;

    private String categoryName;

    public MovieGenreFragment(List<Integer> genre) {
        this.genre = genre;
    }
    public MovieGenreFragment(String categoryName) {
        this.categoryName = categoryName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

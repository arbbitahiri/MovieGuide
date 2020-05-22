package com.arb.movieguideapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.adapters.MovieAdapter;
import com.arb.movieguideapp.adapters.SlideAdapter;
import com.arb.movieguideapp.models.Movie;
import com.arb.movieguideapp.models.Slide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Slide> lstSlides = new ArrayList<>();
    private List<Movie> lstMovies = new ArrayList<>();

    private ViewPager slidePager;
    private TabLayout indicator;

    private RecyclerView rvMovies, rvActionMovies;

    private MovieAdapter movieAdapter;
    private SlideAdapter slideAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovies = view.findViewById(R.id.rv_movies);
        rvActionMovies = view.findViewById(R.id.rv_action_movies);
        slidePager = view.findViewById(R.id.slider_pager);
        indicator = view.findViewById(R.id.indicator);

        populateSlideData();

        slideAdapter = new SlideAdapter(lstSlides);
        slidePager.setAdapter(slideAdapter);
        indicator.setupWithViewPager(slidePager,true);

        movieAdapter = new MovieAdapter(lstMovies);

        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMovies.setItemAnimator(new DefaultItemAnimator());
        rvMovies.setAdapter(movieAdapter);

        rvActionMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvActionMovies.setItemAnimator(new DefaultItemAnimator());
        rvActionMovies.setAdapter(movieAdapter);

        populateMovieData();
    }

    private void populateSlideData() {
        lstSlides.add(new Slide("Avengers: Endgame","8.8 IMDb | 87% Rotten Tomatoes", R.drawable.z_avengers123));
        lstSlides.add(new Slide("The Dark Knight","8.8 IMDb | 87% Rotten Tomatoes", R.drawable.z_darkknight123));
        lstSlides.add(new Slide("Inception","7.7 IMDb | 85% Rotten Tomatoes", R.drawable.z_inception123));
        lstSlides.add(new Slide("Zodiac","8.8 IMDb | 87% Rotten Tomatoes", R.drawable.z_zodiac123));
        lstSlides.add(new Slide("Once Upon a Time in Hollywood","8.8 IMDb | 87% Rotten Tomatoes", R.drawable.z_out123));
        lstSlides.add(new Slide("The Irishman","8.8 IMDb | 87% Rotten Tomatoes", R.drawable.z_irishman123));
    }

    private void populateMovieData() {
        lstMovies.add(new Movie("Avengers Endgame", R.drawable.z_avengersendgame, R.drawable.z_avengers123));
        lstMovies.add(new Movie("The Dark Knight", R.drawable.z_darkknight, R.drawable.z_darkknight123));
        lstMovies.add(new Movie("Interstellar", R.drawable.z_inception, R.drawable.z_inception123));
        lstMovies.add(new Movie("Zodiac", R.drawable.z_zodiac, R.drawable.z_zodiac123));
        lstMovies.add(new Movie("Interstellar", R.drawable.z_interstellar, R.drawable.z_interstellar123));
        lstMovies.add(new Movie("The Irishman", R.drawable.z_theirishman, R.drawable.z_irishman123));

        movieAdapter.notifyDataSetChanged();
    }
}

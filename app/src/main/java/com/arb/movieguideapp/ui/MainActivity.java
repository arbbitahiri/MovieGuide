package com.arb.movieguideapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.arb.movieguideapp.models.Movie;
import com.arb.movieguideapp.adapters.MovieAdapter;
import com.arb.movieguideapp.R;
import com.arb.movieguideapp.models.Slide;
import com.arb.movieguideapp.adapters.SlidePagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Slide> lstSlides;
    private ViewPager slidePager;
    private TabLayout indicator;
    private RecyclerView MoviesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidePager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.rv_movies);

        //lista e fotove
        lstSlides = new ArrayList<>();
        lstSlides.add(new Slide(R.drawable.z_inception321, "Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.z_out321, "Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.z_inception321, "Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.z_out321, "Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.z_inception321, "Slide Title \nmore text here"));

        SlidePagerAdapter adapter = new SlidePagerAdapter(this, lstSlides);
        slidePager.setAdapter(adapter);

        indicator.setupWithViewPager(slidePager, true);

        List<Movie> lstMovie = new ArrayList<>();
        lstMovie.add(new Movie("Avengers Endgame",R.drawable.z_avengersendgame));
        lstMovie.add(new Movie("The Dark Knight",R.drawable.z_darkknight));
        lstMovie.add(new Movie("Inception",R.drawable.z_inceptionas));
        lstMovie.add(new Movie("Zodiac",R.drawable.z_zodiacas));
        lstMovie.add(new Movie("Interstellar",R.drawable.z_interstellaras));
        lstMovie.add(new Movie("The Irishman",R.drawable.z_irishmanas));

        MovieAdapter movieAdapter = new MovieAdapter(this,lstMovie);
        MoviesRV.setAdapter(movieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
    }
}

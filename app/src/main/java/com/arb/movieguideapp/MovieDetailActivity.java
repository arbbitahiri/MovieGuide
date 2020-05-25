package com.arb.movieguideapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.arb.movieguideapp.fragments.HomeFragment;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, HomeFragment.newInstance())
                .commit();
    }
}

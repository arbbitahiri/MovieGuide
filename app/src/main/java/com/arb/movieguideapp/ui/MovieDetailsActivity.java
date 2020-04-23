package com.arb.movieguideapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.adapters.CastAdapter;
import com.arb.movieguideapp.models.Cast;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private RecyclerView RvCast;
    private CastAdapter castAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        //shfaq details

        //shfaq cast
        setupRvCast();

    }

    void setupRvCast(){

        List<Cast> mData = new ArrayList<>();
        mData.add(new Cast("name", R.drawable.z_z_matthew));
        mData.add(new Cast("name", R.drawable.z_z_matt));
        mData.add(new Cast("name", R.drawable.z_z_anna));
        mData.add(new Cast("name", R.drawable.z_z_jessica));
        mData.add(new Cast("name", R.drawable.z_z_michael));

        castAdapter = new CastAdapter(this, mData);
        RvCast.setAdapter(castAdapter);
        RvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}

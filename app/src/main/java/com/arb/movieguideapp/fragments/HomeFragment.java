package com.arb.movieguideapp.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.arb.movieguideapp.clients.GetDatService;
import com.arb.movieguideapp.listeners.RecyclerTouchListener;
import com.arb.movieguideapp.models.Movie;
import com.arb.movieguideapp.models.MovieWrapper;
import com.arb.movieguideapp.models.Slide;
import com.arb.movieguideapp.utils.RetrofitClientInstance;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Slide> lstSlides = new ArrayList<>();
    private List<Movie> lstMovies = new ArrayList<>();
    private List<Movie> lstMovies1 = new ArrayList<>();
    private List<Movie> lstMovies2 = new ArrayList<>();

    private RecyclerView rvMovies, rvTrending, rvHollywood, rvTopRated, rvBlockBuster, rvAwardWinning;
    private ViewPager slidePager;
    private TabLayout indicator;

    private MovieAdapter movieAdapter, movieAdapter1, movieAdapter2;
    private AlertDialog progressDialog;

    public static HomeFragment newInstance() {return new HomeFragment();}

    public HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        populateSlideData();
        SlideAdapter slideAdapter = new SlideAdapter(lstSlides);
        slidePager.setAdapter(slideAdapter);
        indicator.setupWithViewPager(slidePager,true);

        readDataFromExternalApi();
    }

    private void readDataFromExternalApi() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        GetDatService service = RetrofitClientInstance.getRetrofitInstance().create(GetDatService.class);

        Call<MovieWrapper> callTopRated = service.getTopRated();
        callTopRated.enqueue(new Callback<MovieWrapper>() {
            @Override
            public void onResponse(Call<MovieWrapper> call, Response<MovieWrapper> response) {
                if (response.body() != null) {
                    lstMovies = response.body().getMovies();
                    movieAdapter = new MovieAdapter(lstMovies);
                    initRecycleView(rvTopRated);
                } else {
                    Toast.makeText(getContext(), "onResponse - something wrong" + response.message(), Toast.LENGTH_LONG).show();
                }

                movieAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieWrapper> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "exception: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(@NonNull View view) {
        rvMovies = view.findViewById(R.id.rv_movies);
        rvTrending = view.findViewById(R.id.rv_trending);
        rvHollywood = view.findViewById(R.id.rv_hollywood);
        rvTopRated = view.findViewById(R.id.rv_top_rated);
        rvBlockBuster = view.findViewById(R.id.rv_blockbuster);
        rvAwardWinning = view.findViewById(R.id.rv_award_winning);
        slidePager = view.findViewById(R.id.slider_pager);
        indicator = view.findViewById(R.id.indicator);
    }

    private void initOnClick(RecyclerView rv, final String movie) {
        rv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(getContext(),  movie, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void initRecycleView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(movieAdapter);
    }

    private void populateSlideData() {
        lstSlides.add(new Slide("Avengers: Endgame","Action ‧ Sci-fi", R.drawable.z_avengers123));
        lstSlides.add(new Slide("The Dark Knight","Action ‧ Adventure ‧ Drama ", R.drawable.z_darkknight123));
        lstSlides.add(new Slide("Inception","Thriller ‧ Mystery & Suspense", R.drawable.z_inception123));
        lstSlides.add(new Slide("Zodiac","Mystery & Suspense", R.drawable.z_zodiac123));
        lstSlides.add(new Slide("Once Upon a Time in Hollywood","Comedy ‧ Drama", R.drawable.z_out123));
        lstSlides.add(new Slide("The Irishman","Drama ‧ Mystery & Suspense", R.drawable.z_theirishman123));
    }
}

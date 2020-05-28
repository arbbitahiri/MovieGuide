package com.arb.movieguideapp.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
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
import com.arb.movieguideapp.wrappers.MovieWrapper;
import com.arb.movieguideapp.models.Slide;
import com.arb.movieguideapp.utils.RetrofitClientInstance;
import com.arb.movieguideapp.wrappers.SlideWrapper;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class HomeFragment extends Fragment {

    private List<Slide> lstSlides;

    private RecyclerView rvPopular, rvTrending, rvNowPlaying, rvTopRated, rvUpcoming;
    private ViewPager slidePager;
    private TabLayout indicator;

    private MovieAdapter movieAdapter;
    private SlideAdapter slideAdapter;

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

        GetDatService service = RetrofitClientInstance.getRetrofitInstance().create(GetDatService.class);

        initViews(view, slidePager, indicator, service.getTopRatedSlide());

        initViews(view, rvPopular, R.id.rv_popular, service.getPopular());
        initViews(view, rvTrending, R.id.rv_trending, service.getLatest());
        initViews(view, rvNowPlaying, R.id.rv_now_playing, service.getNowPlaying());
        initViews(view, rvTopRated, R.id.rv_top_rated, service.getTopRated());
        initViews(view, rvUpcoming, R.id.rv_upcoming, service.getUpcoming());
    }

    private void initViews(@NonNull View view, RecyclerView recyclerView, int recycle, Call<MovieWrapper> call){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        recyclerView = view.findViewById(recycle);

        initRecycleView(recyclerView);

        readDataFromExternalApi(recyclerView, call);
    }

    private void initViews(@NonNull View view, ViewPager viewPager, TabLayout tabIndicator, Call<SlideWrapper> call){
        viewPager = view.findViewById(R.id.slider_pager);
        tabIndicator = view.findViewById(R.id.indicator);

        readDataFromExternalApi(viewPager, tabIndicator, call);
    }

    private void readDataFromExternalApi(final RecyclerView recyclerView, Call<MovieWrapper> call) {
        try {
            call.enqueue(new Callback<MovieWrapper>() {
                @Override
                public void onResponse(Call<MovieWrapper> call, Response<MovieWrapper> response) {
                    if (response.body() != null) {
                        List<Movie> movieList = response.body().getMovies();
                        movieAdapter = new MovieAdapter(movieList);
                        recyclerView.setAdapter(movieAdapter);
                        recyclerView.smoothScrollToPosition(0);
                    } else {
                        Toast.makeText(getContext(), "onResponse - something wrong" + response.message(), Toast.LENGTH_LONG).show();
                    }

                    movieAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieWrapper> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("Error ", t.getMessage());
                    Toast.makeText(getContext(), "Error fetching data!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e) {
            progressDialog.dismiss();
            Log.d("Error ", e.getMessage());
            Toast.makeText(getContext(), "Error fetching data! " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void readDataFromExternalApi(final ViewPager viewPager, final TabLayout tabIndicator, Call<SlideWrapper> call) {
        try {
            call.enqueue(new Callback<SlideWrapper>() {
                @Override
                public void onResponse(Call<SlideWrapper> call, Response<SlideWrapper> response) {
                    if (response.body() != null) {
                        List<Slide> slideList = response.body().getMovies();
                        slideAdapter = new SlideAdapter(slideList);
                        viewPager.setAdapter(slideAdapter);
                    } else {
                        Toast.makeText(getContext(), "onResponse - something wrong" + response.message(), Toast.LENGTH_LONG).show();
                    }

                    tabIndicator.setupWithViewPager(viewPager,true);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<SlideWrapper> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("Error ", t.getMessage());
                    Toast.makeText(getContext(), "Error fetching data!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e) {
            progressDialog.dismiss();
            Log.d("Error ", e.getMessage());
            Toast.makeText(getContext(), "Error fetching data! " + e.toString(), Toast.LENGTH_SHORT).show();
        }
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
//        rv.setAdapter(movieAdapter);
    }

//    private void populateSlideData() {
//        lstSlides.add(new Slide("Avengers: Endgame","Action ‧ Sci-fi", R.drawable.z_avengers123));
//        lstSlides.add(new Slide("The Dark Knight","Action ‧ Adventure ‧ Drama ", R.drawable.z_darkknight123));
//        lstSlides.add(new Slide("Inception","Thriller ‧ Mystery & Suspense", R.drawable.z_inception123));
//        lstSlides.add(new Slide("Zodiac","Mystery & Suspense", R.drawable.z_zodiac123));
//        lstSlides.add(new Slide("Once Upon a Time in Hollywood","Comedy ‧ Drama", R.drawable.z_out123));
//        lstSlides.add(new Slide("The Irishman","Drama ‧ Mystery & Suspense", R.drawable.z_theirishman123));
//    }
}

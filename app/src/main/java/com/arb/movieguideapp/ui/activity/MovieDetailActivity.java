package com.arb.movieguideapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.clients.GetCastDataService;
import com.arb.movieguideapp.clients.GetMovieTrailerService;
import com.arb.movieguideapp.listeners.TrailerClickListener;
import com.arb.movieguideapp.models.Cast;
import com.arb.movieguideapp.models.Genre;
import com.arb.movieguideapp.models.Movie;
import com.arb.movieguideapp.models.MovieTrailer;
import com.arb.movieguideapp.models.wrappers.CastWrapper;
import com.arb.movieguideapp.ui.adapters.CastAdapter;
import com.arb.movieguideapp.ui.adapters.CategoryAdapter;
import com.arb.movieguideapp.ui.adapters.TrailerAdapter;
import com.arb.movieguideapp.utils.RetrofitClientInstance;
import com.arb.movieguideapp.models.wrappers.MovieTrailerWrapper;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView txtTitle, txtRating, txtDate, txtDescription, txtTrailer, txtGenre;
    private ImageView imgPoster, imgCover;

    private CategoryAdapter categoryAdapter;
    private TrailerAdapter mTrailerAdapter;
    private CastAdapter mCastAdapter;

    private List<MovieTrailer> mMovieTrailers;
    private List<Cast> mCast;
    private List<Genre> mGenre;

    private RecyclerView mTrailerRecyclerView, mCastRecycleView, mWTWRecycleView;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mCastRecycleView = findViewById(R.id.rv_cast);
        mCastRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mWTWRecycleView = findViewById(R.id.rv_where_to_watch);
        mWTWRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mTrailerRecyclerView = findViewById(R.id.rv_trailer);
        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTrailerRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mTrailerRecyclerView.setNestedScrollingEnabled(false);

        txtTitle = findViewById(R.id.detail_movie_title);
        txtRating = findViewById(R.id.detail_movie_ratings);
        txtDate = findViewById(R.id.detail_movie_date);
        txtDescription = findViewById(R.id.detail_movie_description);
        imgPoster = findViewById(R.id.detail_movie_img);
        imgCover = findViewById(R.id.detail_movie_cover);
        txtGenre = findViewById(R.id.detail_genre);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                movie = (Movie) bundle.getSerializable("movie");
                if (movie != null) {
                    populateActivity(movie);
                    if(isNetworkAvailable()) {
                        getCasts(movie.getId());
                        getTrailer(movie.getId());
                    }
                }
            }
        } else {
            movie = (Movie) savedInstanceState.getSerializable("movie");
            if (movie != null) {
                populateActivity(movie);
                if(isNetworkAvailable()) {
                    mMovieTrailers = (List<MovieTrailer>) savedInstanceState.getSerializable("movie_trailers");
                    mCast = (List<Cast>) savedInstanceState.getSerializable("cast");
                    if (mMovieTrailers != null) {
                        populateCasts(mCast);
                        populateTrailers(mMovieTrailers);
                    }
                }
            }
        }
    }

    private void populateActivity(Movie mMovie) {
        Picasso.get().load(mMovie.getThumbnail()).into(imgPoster);
        Picasso.get().load(mMovie.getCoverImg()).into(imgCover);
        txtTitle.setText(mMovie.getTitle());
        txtDescription.setText(mMovie.getDescription());
        txtDate.setText(mMovie.getReleaseDate());

        List<String> genreString = new ArrayList<>();
        if (mMovie.getGenre() != null)  {
            for (Genre genre : movie.getCategories()) {
                genreString.add(genre.getCategories());
            }
        }
        txtGenre.setText(genreString.toString());

        String userRatingText = mMovie.getVoteAverage() + "/10";
        txtRating.setText(userRatingText);
    }

    private String getGenres(List<String> genreString) {
        if (movie.getGenre() != null)  {
            for (Genre genre : movie.getCategories()) {
                genreString.add(genre.getCategories());
            }
        }
        return TextUtils.join(" ‧ ", genreString);
    }

    private void populateCasts(List<Cast> mCast){
        mCastAdapter = new CastAdapter(mCast);
        mCastRecycleView.setAdapter(mCastAdapter);
    }

    private void getCasts(int movieId) {
        GetCastDataService castService = RetrofitClientInstance.getRetrofitInstance().create(GetCastDataService.class);
        Call<CastWrapper> call = castService.getCast(movieId);

        call.enqueue(new Callback<CastWrapper>() {
            @Override
            public void onResponse(@NonNull Call<CastWrapper> call, Response<CastWrapper> response) {
                if (response.body() != null) {
                    mCast = response.body().getCast();
                    populateCasts(mCast);
                }
            }

            @Override
            public void onFailure(Call<CastWrapper> call, Throwable t) {
                showError();
            }
        });
    }

    private void populateTrailers(List<MovieTrailer> mMovieTrailers){
        mTrailerAdapter = new TrailerAdapter(mMovieTrailers, new TrailerClickListener() {
            @Override
            public void onMovieTrailerClick(MovieTrailer mMovieTrailer) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + mMovieTrailer.getKey())));
            }
        });
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
    }

    private void getTrailer(int movieId) {
        GetMovieTrailerService movieTrailerService = RetrofitClientInstance.getRetrofitInstance().create(GetMovieTrailerService.class);
        Call<MovieTrailerWrapper> call = movieTrailerService.getTrailers(movieId);

        call.enqueue(new Callback<MovieTrailerWrapper>() {
            @Override
            public void onResponse(Call<MovieTrailerWrapper> call, Response<MovieTrailerWrapper> response) {
                if (response.body() != null) {
                    mMovieTrailers = response.body().getTrailerResult();
                    populateTrailers(mMovieTrailers);
                }
            }

            @Override
            public void onFailure(Call<MovieTrailerWrapper> call, Throwable t) {
                showError();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("movie", movie);
        if(isNetworkAvailable()) {
            outState.putSerializable("cast", (Serializable) mCast);
            outState.putSerializable("movie_trailers", (Serializable) mMovieTrailers);
        }
    }

    private void showError() {
        Toast.makeText(MovieDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}

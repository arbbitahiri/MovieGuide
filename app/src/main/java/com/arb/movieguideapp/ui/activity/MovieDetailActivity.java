package com.arb.movieguideapp.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.arb.movieguideapp.clients.GetCreditsDataService;
import com.arb.movieguideapp.clients.GetGenreDataService;
import com.arb.movieguideapp.clients.GetMovieDataService;
import com.arb.movieguideapp.clients.GetMovieTrailerService;
import com.arb.movieguideapp.listeners.MovieClickListener;
import com.arb.movieguideapp.listeners.TrailerClickListener;
import com.arb.movieguideapp.models.Cast;
import com.arb.movieguideapp.models.Crew;
import com.arb.movieguideapp.models.Genre;
import com.arb.movieguideapp.models.Movie;
import com.arb.movieguideapp.models.MovieTrailer;
import com.arb.movieguideapp.models.wrappers.CreditsWrapper;
import com.arb.movieguideapp.models.wrappers.GenreWrapper;
import com.arb.movieguideapp.models.wrappers.MovieWrapper;
import com.arb.movieguideapp.ui.adapters.CastAdapter;
import com.arb.movieguideapp.ui.adapters.CrewAdapter;
import com.arb.movieguideapp.ui.adapters.MovieAdapter;
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
    private TextView txtTitle, txtRating, txtDate, txtDescription, txtTrailer, txtGenre, txtCrew, txtSimilarMovies;
    private ImageView imgPoster, imgCover;

    private TrailerAdapter mTrailerAdapter;
    private CastAdapter mCastAdapter;
    private CrewAdapter mCrewAdapter;
    private MovieAdapter mMovieAdapter;

    private List<MovieTrailer> mMovieTrailers;
    private List<Cast> mCast;
    private List<Crew> mCrew;
    private List<Movie> mMovie;
    private List<Genre> genreList = new ArrayList<>();

    private RecyclerView mTrailerRecyclerView, mCastRecycleView, mSimilarMovieRecycleView, mCrewRecycleView;

    private Movie movie;

    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getMovieGenres();

//        initRecyclerView(mCastRecycleView, R.id.rv_cast);
//        initRecyclerView(mCrewRecycleView, R.id.rv_crew);
//        initRecyclerView(mWTWRecycleView, R.id.rv_where_to_watch);

        mCastRecycleView = findViewById(R.id.rv_cast);
        mCastRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mCrewRecycleView = findViewById(R.id.rv_crew);
        mCrewRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mSimilarMovieRecycleView = findViewById(R.id.rv_similar_movies);
        mSimilarMovieRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mTrailerRecyclerView = findViewById(R.id.rv_trailer);
        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTrailerRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mTrailerRecyclerView.setNestedScrollingEnabled(false);

//        initTextImageView();

        txtTitle = findViewById(R.id.detail_movie_title);
        txtRating = findViewById(R.id.detail_movie_ratings);
        txtDate = findViewById(R.id.detail_movie_date);
        txtDescription = findViewById(R.id.detail_movie_description);
        imgPoster = findViewById(R.id.detail_movie_img);
        imgCover = findViewById(R.id.detail_movie_cover);
        txtGenre = findViewById(R.id.detail_genre);
        txtTrailer = findViewById(R.id.detail_trailer);
        txtCrew = findViewById(R.id.detail_crew);
        txtSimilarMovies = findViewById(R.id.detail_similar_movies);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                movie = (Movie) bundle.getSerializable("movie");
                if (movie != null) {
                    populateActivity(movie);
                    if(isNetworkAvailable()) {
                        getCasts(movie.getId());
                        getCrew(movie.getId());
                        getTrailer(movie.getId());
                        getSimilarMovies(movie.getId());
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
                    mCrew = (List<Crew>) savedInstanceState.getSerializable("crew");
                    mMovie = (List<Movie>) savedInstanceState.getSerializable("movie");
                    if (mMovieTrailers != null) {
                        populateCasts(mCast);
                        populateCrew(mCrew);
                        populateTrailers(mMovieTrailers);
                        populateSimilarMovies(mMovie);
                    }
                }
            }
        }

        progressDialog.dismiss();
    }

//    private void initTextImageView() {
//        initTextView(txtTitle, R.id.detail_movie_title);
//        initTextView(txtRating, R.id.detail_movie_ratings);
//        initTextView(txtDate, R.id.detail_movie_date);
//        initTextView(txtDescription, R.id.detail_movie_description);
//        initTextView(txtGenre, R.id.detail_genre);
//        initTextView(txtTrailer, R.id.detail_trailer);
//        initTextView(txtCrew, R.id.detail_crew);
//        initTextView(txtSimilarMovies, R.id.detail_similar_movies);
//
//        initImageView(imgPoster, R.id.detail_movie_img);
//        initImageView(imgCover, R.id.detail_movie_cover);
//    }
//
//    private void initTextView(TextView textView, int id) {
//        textView = findViewById(id);
//    }
//
//    private void initImageView(ImageView imageView, int id) {
//        imageView = findViewById(id);
//    }

    private void initRecyclerView(RecyclerView recyclerView, int id) {
        recyclerView = findViewById(id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void populateActivity(Movie mMovie) {
        Picasso.get().load(mMovie.getThumbnail()).into(imgPoster);
        Picasso.get().load(mMovie.getCoverImg()).into(imgCover);
        txtTitle.setText(mMovie.getTitle());
        txtDescription.setText(mMovie.getDescription());
        txtDate.setText(mMovie.getReleaseDate());

        List<String> genreString = new ArrayList<>();
        Log.v("Tag", "Genres: " + mMovie.getGenre());
        if (mMovie.getGenre() != null)  {
            for (Genre genre : movie.getGenres()) {
                genreString.add(genre.getGenres());
            }
        }
        txtGenre.setText(genreString.toString());

        String userRatingText = mMovie.getVoteAverage() + "/10";
        txtRating.setText(userRatingText);
    }

//    private String getGenres(List<String> genreString) {
//        return TextUtils.join(" ‧ ", genreString);
//    }

    private void populateCasts(List<Cast> mCast){
        if (mCast.size() > 0) {
            mCastAdapter = new CastAdapter(mCast);
            mCastRecycleView.setAdapter(mCastAdapter);
        }
    }

    private void getCasts(int movieId) {
        GetCreditsDataService castService = RetrofitClientInstance.getRetrofitInstance().create(GetCreditsDataService.class);
        Call<CreditsWrapper> call = castService.getCast(movieId);

        call.enqueue(new Callback<CreditsWrapper>() {
            @Override
            public void onResponse(@NonNull Call<CreditsWrapper> call, Response<CreditsWrapper> response) {
                if (response.body() != null) {
                    mCast = response.body().getCast();
                    populateCasts(mCast);
                }
            }

            @Override
            public void onFailure(Call<CreditsWrapper> call, Throwable t) {
                showError();
            }
        });
    }

    private void populateCrew(List<Crew> mCrew){
        if (mCrew.size() > 0) {
            txtCrew.setVisibility(View.VISIBLE);
            mCrewRecycleView.setVisibility(View.VISIBLE);
            mCrewAdapter = new CrewAdapter(mCrew);
            mCrewRecycleView.setAdapter(mCrewAdapter);
        }
    }

    private void getCrew(int movieId) {
        GetCreditsDataService crewService = RetrofitClientInstance.getRetrofitInstance().create(GetCreditsDataService.class);
        Call<CreditsWrapper> call = crewService.getCrew(movieId);

        call.enqueue(new Callback<CreditsWrapper>() {
            @Override
            public void onResponse(@NonNull Call<CreditsWrapper> call, Response<CreditsWrapper> response) {
                if (response.body() != null) {
                    mCrew = response.body().getCrew();
                    populateCrew(mCrew);
                }
            }

            @Override
            public void onFailure(Call<CreditsWrapper> call, Throwable t) {
                showError();
            }
        });
    }

    private void getMovieGenres() {
        try {
            GetGenreDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetGenreDataService.class);
            Call<GenreWrapper> call = service.getGenre();
            call.enqueue(new Callback<GenreWrapper>() {
                @Override
                public void onResponse(Call<GenreWrapper> call, Response<GenreWrapper> response) {
                    if (response.body() != null) {
                        genreList = response.body().getGenre();
                    } else
                        showError();
                }

                @Override
                public void onFailure(Call<GenreWrapper> call, Throwable t) {
                    Log.d("Error ", t.getMessage());
                    showError();
                }
            });
        }
        catch (Exception e) {
            Log.d("Error ", e.getMessage());
            showError();
        }
    }

    private void populateSimilarMovies(List<Movie> mMovie){
        if (mMovie.size() > 0) {
            txtSimilarMovies.setVisibility(View.VISIBLE);
            mSimilarMovieRecycleView.setVisibility(View.VISIBLE);
            mMovieAdapter = new MovieAdapter(mMovie);
            onClickMovie(mMovie);
            mSimilarMovieRecycleView.setAdapter(mMovieAdapter);
        }
    }

    private void getSimilarMovies(int movieId) {
        try {
            GetMovieDataService movieService = RetrofitClientInstance.getRetrofitInstance().create(GetMovieDataService.class);
            Call<MovieWrapper> call = movieService.getSimilarMovie(movieId);

            call.enqueue(new Callback<MovieWrapper>() {
                @Override
                public void onResponse(Call<MovieWrapper> call, Response<MovieWrapper> response) {
                    if (response.body() != null) {
                        List<Movie> movieList = response.body().getMovies();

                        for (Movie m : movieList)
                            m.mapGenres(genreList);

                        populateSimilarMovies(movieList);
                    } else {
                        progressDialog.dismiss();
                        showError();
                    }

                    mMovieAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<MovieWrapper> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("Error ", t.getMessage());
                    showError();
                }
            });
        }
        catch (Exception e) {
            progressDialog.dismiss();
            Log.d("Error ", e.getMessage());
            showError();
        }
    }

    private void onClickMovie(List<Movie> movieList) {
        mMovieAdapter = new MovieAdapter(movieList, new MovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = new Intent(MovieDetailActivity.this, MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movie);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void populateTrailers(List<MovieTrailer> mMovieTrailers){
        if (mMovieTrailers.size() > 0) {
            txtTrailer.setVisibility(View.VISIBLE);
            mTrailerRecyclerView.setVisibility(View.VISIBLE);
            mTrailerAdapter = new TrailerAdapter(mMovieTrailers, new TrailerClickListener() {
                @Override
                public void onMovieTrailerClick(MovieTrailer mMovieTrailer) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + mMovieTrailer.getKey())));
                }
            });
            mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        }
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
            outState.putSerializable("crew", (Serializable) mCrew);
            outState.putSerializable("movie_trailers", (Serializable) mMovieTrailers);
        }
    }

    private void showError() {
        Toast.makeText(MovieDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}

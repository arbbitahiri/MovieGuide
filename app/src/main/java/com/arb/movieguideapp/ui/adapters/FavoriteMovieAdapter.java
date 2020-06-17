package com.arb.movieguideapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.listeners.MovieClickListener;
import com.arb.movieguideapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.MyViewHolder> {

    private List<Movie> movieList;
    private MovieClickListener movieClickListener;

    public FavoriteMovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public FavoriteMovieAdapter(List<Movie> movieList, MovieClickListener movieClickListener) {
        this.movieList = movieList;
        this.movieClickListener = movieClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Movie movie = movieList.get(i);

    }

    @Override
    public int getItemCount() {
        return null != movieList ? movieList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
//        private TextView txtGenre;
        private TextView txtRatings;
        private ImageView imgMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.movie_title);
//            txtGenre = itemView.findViewById(R.id.year_genre_time);
            txtRatings = itemView.findViewById(R.id.ratings);
            imgMovie = itemView.findViewById(R.id.item_movie_img_f);
        }

        public void bind(final Movie movie, final MovieClickListener movieClickListener) {
            txtTitle.setText(movie.getTitle());
            String userRatingText = movie.getVoteAverage() + "/10";
            txtRatings.setText(userRatingText);
            Picasso.get()
                    .load(movie.getThumbnail())
                    .into(imgMovie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieClickListener.onMovieClick(movie);
                }
            });
        }
    }
}

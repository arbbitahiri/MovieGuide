package com.arb.movieguideapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.models.Movie;

import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mMovies;

    public FavoriteMovieAdapter(List<Movie> mMovies) {
        this.mMovies = mMovies;
    }

    public FavoriteMovieAdapter(Context mContext, List<Movie> mMovies) {
        this.mContext = mContext;
        this.mMovies = mMovies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txtTitle.setText(mMovies.get(i).getTitle());
        myViewHolder.txtYearGenreTime.setText(mMovies.get(i).getYear_genre_time());
        myViewHolder.txtRatings.setText(mMovies.get(i).getRatings());
        myViewHolder.imgMovie.setImageResource(mMovies.get(i).getThumbnail());

//        Glide.with(mContext).load(mMovies.get(i).getThumbnail())
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtYearGenreTime;
        private TextView txtRatings;
        private ImageView imgMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.movie_title);
            txtYearGenreTime = itemView.findViewById(R.id.year_genre_time);
            txtRatings = itemView.findViewById(R.id.ratings);
            imgMovie = itemView.findViewById(R.id.item_movie_img_f);
        }
    }
}
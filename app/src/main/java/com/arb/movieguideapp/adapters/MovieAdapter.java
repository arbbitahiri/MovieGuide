package com.arb.movieguideapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> mMovies;

    public MovieAdapter(List<Movie> mMovies) {
        this.mMovies = mMovies;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.imgMovie.setImageResource(mMovies.get(i).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMovie = itemView.findViewById(R.id.item_more_img);
        }
    }
}

package com.arb.movieguideapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.listeners.GenreClickListener;
import com.arb.movieguideapp.models.Genre;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private GenreClickListener genreClickListener;
    private final List<Genre> mGenre;

    public CategoryAdapter(List<Genre> mGenre) {
        this.mGenre = mGenre;
    }

    public CategoryAdapter(List<Genre> mGenre, GenreClickListener genreClickListener) {
        this.genreClickListener = genreClickListener;
        this.mGenre = mGenre;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        Genre genre = mGenre.get(position);
        holder.bind(genre, genreClickListener);
    }

    @Override
    public int getItemCount() {
        return mGenre.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button btnCategory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btnCategory = itemView.findViewById(R.id.btnCategory);
        }

        public void bind(final Genre genre, final GenreClickListener genreClickListener) {
            btnCategory.setText(genre.getGenres());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    genreClickListener.onCategoryClick(genre);
                }
            });
        }
    }
}

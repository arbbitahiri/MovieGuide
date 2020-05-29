package com.arb.movieguideapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.listeners.CategoryClickListener;
import com.arb.movieguideapp.models.Cast;
import com.arb.movieguideapp.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final CategoryClickListener categoryClickListener;
    private final List<Category> mCategory;

    public CategoryAdapter(CategoryClickListener categoryClickListener, List<Category> mCategory) {
        this.categoryClickListener = categoryClickListener;
        this.mCategory = mCategory;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        Category category = mCategory.get(position);
        holder.bind(category, categoryClickListener);
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button btnCategory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btnCategory = itemView.findViewById(R.id.btnCategory);
        }

        public void bind(final Category category, final CategoryClickListener categoryClickListener) {
            btnCategory.setText(category.getCategories());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryClickListener.onCategoryClick(category);
                }
            });
        }
    }
}

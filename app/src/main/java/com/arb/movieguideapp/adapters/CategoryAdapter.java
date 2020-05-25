package com.arb.movieguideapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.models.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context mContext;
    List<Category> mCategory;

    public CategoryAdapter(Context mContext, List<Category> mCategory) {
        this.mContext = mContext;
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
        holder.btnCategory.setText(mCategory.get(position).getCategories());
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
    }
}

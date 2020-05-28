package com.arb.movieguideapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.models.WhereToWatch;

import java.util.List;

public class WhereToWatchAdapter extends RecyclerView.Adapter<WhereToWatchAdapter.MyViewHolder> {

    private List<WhereToWatch> mWhereToWatch;

    public WhereToWatchAdapter(List<WhereToWatch> mWhereToWatch) {
        this.mWhereToWatch = mWhereToWatch;
    }

    @NonNull
    @Override
    public WhereToWatchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_where_to_watch, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txtTitle.setText(mWhereToWatch.get(i).getTitle());
        myViewHolder.imgWTW.setImageResource(mWhereToWatch.get(i).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private ImageView imgWTW;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

package com.arb.movieguideapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.models.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {

    private List<Cast> mCast;

    public CastAdapter(List<Cast> mCast) {
        this.mCast = mCast;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cast, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.imgCast.setImageResource(mCast.get(i).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mCast.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCast;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCast = itemView.findViewById(R.id.img_cast);
        }
    }
}

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
import com.arb.movieguideapp.models.Cast;
import com.squareup.picasso.Picasso;

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
        Cast cast = mCast.get(i);
        myViewHolder.txtCast.setText(cast.getName());
        Picasso.get().load(cast.getThumbnail()).into(myViewHolder.imgCast);
    }

    @Override
    public int getItemCount() {
        return null != mCast ? mCast.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCast;
        private TextView txtCast;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCast = itemView.findViewById(R.id.txtCast);
            imgCast = itemView.findViewById(R.id.img_cast);
        }
    }
}

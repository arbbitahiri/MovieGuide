package com.arb.movieguideapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.models.Slide;

import java.util.List;

public class SlideAdapter extends PagerAdapter {

    private List<Slide> mSlide;

    public SlideAdapter(List<Slide> mSlide) {
        this.mSlide = mSlide;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_movie, null);

        ImageView imgSlide = slideLayout.findViewById(R.id.slide_img);
        TextView txtSlide = slideLayout.findViewById(R.id.slide_title);
        TextView txtRatings = slideLayout.findViewById(R.id.slide_rating);

        imgSlide.setImageResource(mSlide.get(position).getThumbnail());
        txtSlide.setText(mSlide.get(position).getTitle());
        txtRatings.setText(mSlide.get(position).getRatings());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return mSlide.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

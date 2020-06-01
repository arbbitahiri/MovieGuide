package com.arb.movieguideapp.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.models.Genre;
import com.arb.movieguideapp.models.Slide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

        Slide slide = mSlide.get(position);

        Picasso.get()
                .load(slide.getThumbnail())
                .into(imgSlide);
        txtSlide.setText(mSlide.get(position).getTitle());
        List<String> genreString = new ArrayList<>();
        Log.v("Tag", "Genres: " + slide.getGenre());
        if (slide.getGenre() != null)  {
            for (Genre genre : slide.getGenres()) {
                genreString.add(genre.getGenres());
            }
        }
        txtRatings.setText(genreString.toString());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return null != mSlide ? mSlide.size() : 0;
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

package com.arb.movieguideapp.listeners;

import com.arb.movieguideapp.models.Category;

import java.util.List;

public interface OnGetGenresCallback {
    void onSuccess(List<Category> genres);

    void onError();
}

package com.arb.movieguideapp.listeners;

import com.arb.movieguideapp.models.Genre;

import java.util.List;

public interface OnGetGenresCallback {
    void onSuccess(List<Genre> genres);

    void onError();
}

package com.arb.movieguideapp.models.wrappers;

import com.arb.movieguideapp.models.Cast;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CastWrapper {
    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private ArrayList<Cast> cast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }
}

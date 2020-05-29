package com.arb.movieguideapp.models.wrappers;

import com.arb.movieguideapp.models.Category;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryWrapper {
    @SerializedName("genres")
    private ArrayList<Category> category;

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }
}

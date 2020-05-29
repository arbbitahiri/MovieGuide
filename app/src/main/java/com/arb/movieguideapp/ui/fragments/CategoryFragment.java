package com.arb.movieguideapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.clients.GetCategoryDataService;
import com.arb.movieguideapp.listeners.CategoryClickListener;
import com.arb.movieguideapp.models.Category;
import com.arb.movieguideapp.models.wrappers.CategoryWrapper;
import com.arb.movieguideapp.ui.adapters.CategoryAdapter;
import com.arb.movieguideapp.utils.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;

    private List<Integer> genre;

    private String categoryName;

    public CategoryFragment(List<Integer> genre) {
        this.genre = genre;
    }
    public CategoryFragment(String categoryName) {
        this.categoryName = categoryName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

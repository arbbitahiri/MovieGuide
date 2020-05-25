package com.arb.movieguideapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.adapters.CategoryAdapter;
import com.arb.movieguideapp.listeners.RecyclerTouchListener;
import com.arb.movieguideapp.models.Category;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvCategory = view.findViewById(R.id.rv_categories);
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setItemAnimator(new DefaultItemAnimator());

        rvCategory.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        rvCategory.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rvCategory,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(getContext(), "ON CLICK", Toast.LENGTH_SHORT).show();
                    }
                }));

        rvCategory.setAdapter(categoryAdapter);

        populateCategory();
    }


    private void populateCategory() {
        Category category = new Category("Action");
        categoryList.add(category);

        category = new Category("Adventure");
        categoryList.add(category);

        category = new Category("Classics");
        categoryList.add(category);

        category = new Category("Comedies");
        categoryList.add(category);

        category = new Category("Documentaries");
        categoryList.add(category);

        category = new Category("Dramas");
        categoryList.add(category);

        category = new Category("History");
        categoryList.add(category);

        category = new Category("Horror");
        categoryList.add(category);

        category = new Category("Romance");
        categoryList.add(category);

        category = new Category("Sci-Fi & Fantasy");
        categoryList.add(category);

        category = new Category("Thriller");
        categoryList.add(category);

        categoryAdapter.notifyDataSetChanged();
    }
}

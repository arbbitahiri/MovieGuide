package com.arb.movieguideapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.ui.adapters.CategoryAdapter;
import com.arb.movieguideapp.ui.fragments.categories.ActionFragment;
import com.arb.movieguideapp.ui.fragments.categories.AdventureFragment;
import com.arb.movieguideapp.ui.fragments.categories.ClassicsFragment;
import com.arb.movieguideapp.ui.fragments.categories.ComedyFragment;
import com.arb.movieguideapp.ui.fragments.categories.CrimeFragment;
import com.arb.movieguideapp.ui.fragments.categories.DocumentaryFragment;
import com.arb.movieguideapp.ui.fragments.categories.DramaFragment;
import com.arb.movieguideapp.ui.fragments.categories.HistoryFragment;
import com.arb.movieguideapp.ui.fragments.categories.HorrorFragment;
import com.arb.movieguideapp.ui.fragments.categories.RomanceFragment;
import com.arb.movieguideapp.ui.fragments.categories.SciFiFantasyFragment;
import com.arb.movieguideapp.ui.fragments.categories.ThrillerFragment;
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

                        switch (position) {
                            case 0: getFragment(new ActionFragment()); break;
                            case 1: getFragment(new AdventureFragment()); break;
                            case 2: getFragment(new ClassicsFragment()); break;
                            case 3: getFragment(new ComedyFragment()); break;
                            case 4: getFragment(new CrimeFragment()); break;
                            case 5: getFragment(new DocumentaryFragment()); break;
                            case 6: getFragment(new DramaFragment()); break;
                            case 7: getFragment(new HistoryFragment()); break;
                            case 8: getFragment(new HorrorFragment()); break;
                            case 9: getFragment(new RomanceFragment()); break;
                            case 10: getFragment(new SciFiFantasyFragment()); break;
                            case 11: getFragment(new ThrillerFragment()); break;
                        }
                    }
                }));

        rvCategory.setAdapter(categoryAdapter);

        populateCategory();
    }

    private void getFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.searchLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void populateCategory() {
        addToCategory("Action");
        addToCategory("Adventure");
        addToCategory("Classics");
        addToCategory("Comedies");
        addToCategory("Crime");
        addToCategory("Documentaries");
        addToCategory("Dramas");
        addToCategory("History");
        addToCategory("Horror");
        addToCategory("Romance");
        addToCategory("Sci-Fi & Fantasy");
        addToCategory("Thriller");

        categoryAdapter.notifyDataSetChanged();
    }

    private void addToCategory(String cat) {
        Category category = new Category(cat);
        categoryList.add(category);
    }
}

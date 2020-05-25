package com.arb.movieguideapp.fragments;

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
import com.arb.movieguideapp.adapters.CategoryAdapter;
import com.arb.movieguideapp.fragments.categories.ActionFragment;
import com.arb.movieguideapp.fragments.categories.AdventureFragment;
import com.arb.movieguideapp.fragments.categories.ClassicsFragment;
import com.arb.movieguideapp.fragments.categories.ComedyFragment;
import com.arb.movieguideapp.fragments.categories.CrimeFragment;
import com.arb.movieguideapp.fragments.categories.DocumentaryFragment;
import com.arb.movieguideapp.fragments.categories.DramaFragment;
import com.arb.movieguideapp.fragments.categories.HistoryFragment;
import com.arb.movieguideapp.fragments.categories.HorrorFragment;
import com.arb.movieguideapp.fragments.categories.RomanceFragment;
import com.arb.movieguideapp.fragments.categories.SciFiFantasyFragment;
import com.arb.movieguideapp.fragments.categories.ThrillerFragment;
import com.arb.movieguideapp.listeners.RecyclerTouchListener;
import com.arb.movieguideapp.models.Category;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList = new ArrayList<>();

    private ActionFragment actionFragment = new ActionFragment();
    private AdventureFragment adventureFragment = new AdventureFragment();
    private ClassicsFragment classicsFragment = new ClassicsFragment();
    private ComedyFragment comedyFragment = new ComedyFragment();
    private CrimeFragment crimeFragment = new CrimeFragment();
    private DocumentaryFragment documentaryFragment = new DocumentaryFragment();
    private DramaFragment dramaFragment = new DramaFragment();
    private HistoryFragment historyFragment = new HistoryFragment();
    private HorrorFragment horrorFragment = new HorrorFragment();
    private RomanceFragment romanceFragment = new RomanceFragment();
    private SciFiFantasyFragment sciFiFantasyFragment = new SciFiFantasyFragment();
    private ThrillerFragment thrillerFragment = new ThrillerFragment();

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
                            case 0: getFragment(actionFragment); break;
                            case 1: getFragment(adventureFragment); break;
                            case 2: getFragment(classicsFragment); break;
                            case 3: getFragment(comedyFragment); break;
                            case 4: getFragment(crimeFragment); break;
                            case 5: getFragment(documentaryFragment); break;
                            case 6: getFragment(dramaFragment); break;
                            case 7: getFragment(historyFragment); break;
                            case 8: getFragment(horrorFragment); break;
                            case 9: getFragment(romanceFragment); break;
                            case 10: getFragment(sciFiFantasyFragment); break;
                            case 11: getFragment(thrillerFragment); break;
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

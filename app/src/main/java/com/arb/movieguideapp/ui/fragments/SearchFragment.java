package com.arb.movieguideapp.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.clients.GetCategoryDataService;
import com.arb.movieguideapp.listeners.CategoryClickListener;
import com.arb.movieguideapp.models.wrappers.CategoryWrapper;
import com.arb.movieguideapp.ui.adapters.CategoryAdapter;
import com.arb.movieguideapp.models.Category;
import com.arb.movieguideapp.utils.RetrofitClientInstance;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private Category category;

    private AlertDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCategory = view.findViewById(R.id.rv_categories);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setItemAnimator(new DefaultItemAnimator());

        rvCategory.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        GetCategoryDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetCategoryDataService.class);
        readDataFromExternalApi(rvCategory, service.getCategory());
    }

    private void readDataFromExternalApi(final RecyclerView recyclerView, Call<CategoryWrapper> call) {
        try {
            call.enqueue(new Callback<CategoryWrapper>() {
                @Override
                public void onResponse(Call<CategoryWrapper> call, Response<CategoryWrapper> response) {
                    if (response.body() != null) {
                        List<Category> movieList = response.body().getCategory();
                        categoryAdapter = new CategoryAdapter(movieList, new CategoryClickListener() {
                            @Override
                            public void onCategoryClick(Category mCategory) {
                                getFragment(mCategory.getCategories());
                            }
                        });
                        recyclerView.setAdapter(categoryAdapter);
                        recyclerView.smoothScrollToPosition(0);

                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "onResponse - something wrong" + response.message(), Toast.LENGTH_LONG).show();
                    }

                    categoryAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<CategoryWrapper> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("Error ", t.getMessage());
                    Toast.makeText(getContext(), "Error fetching data!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e) {
            progressDialog.dismiss();
            Log.d("Error ", e.getMessage());
            Toast.makeText(getContext(), "Error fetching data! " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getFragment(String categoryName) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.searchLayout, new CategoryFragment(categoryName))
                .addToBackStack(null)
                .commit();
    }
}

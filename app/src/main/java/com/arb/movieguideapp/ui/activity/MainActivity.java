package com.arb.movieguideapp.ui.activity;

import com.arb.movieguideapp.R;
import com.arb.movieguideapp.ui.fragments.FavoriteFragment;
import com.arb.movieguideapp.ui.fragments.HomeFragment;
import com.arb.movieguideapp.ui.fragments.MoreFragment;
import com.arb.movieguideapp.ui.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Fragment homeFragment = new HomeFragment();
    private Fragment searchFragment = new SearchFragment();
    private Fragment favoriteFragment = new FavoriteFragment();
    private Fragment moreFragment = new MoreFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment activeFragment = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().add(R.id.host_fragment, moreFragment, "4").hide(moreFragment).commit();
        fragmentManager.beginTransaction().add(R.id.host_fragment, favoriteFragment, "3").hide(favoriteFragment).commit();
        fragmentManager.beginTransaction().add(R.id.host_fragment, searchFragment, "2").hide(searchFragment).commit();
        fragmentManager.beginTransaction().add(R.id.host_fragment, homeFragment, "1").commit();

//        loadFragment(homeFragment);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_homeFragment, R.id.nav_searchFragment, R.id.nav_favoriteFragment, R.id.nav_settingsFragment)
//                .build();
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_homeFragment:
                fragmentManager
                        .beginTransaction()
                        .hide(activeFragment)
                        .show(homeFragment)
                        .commit();
                activeFragment = homeFragment;
                break;

            case R.id.nav_searchFragment:
                fragmentManager
                        .beginTransaction()
                        .hide(activeFragment)
                        .show(searchFragment)
                        .commit();
                activeFragment = searchFragment;
                break;

            case R.id.nav_favoriteFragment:
                fragmentManager
                        .beginTransaction()
                        .hide(activeFragment)
                        .show(favoriteFragment)
                        .commit();
                activeFragment = favoriteFragment;
                break;

            case R.id.nav_settingsFragment:
                fragmentManager
                        .beginTransaction()
                        .hide(activeFragment)
                        .show(moreFragment)
                        .commit();
                activeFragment = moreFragment;
                break;
        }

        return false;
    }

//    private boolean loadFragment(Fragment fragment) {
//        if (fragment != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.host_fragment, fragment)
//                    .commit();
//            return true;
//        }
//        return false;
//    }
}

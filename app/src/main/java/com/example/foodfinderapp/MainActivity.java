package com.example.foodfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 *   Main activity of the application
 */
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    // Called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup bottom navigation bar
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(SavedBusinessesFragment.newInstance("", ""));
    }

    // Opens a fragment from the bottom navigation bar
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Logic for bottom navigation bar
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                // Called when menu item is selected from bottom navigation bar
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Determine which menu item selected
                    switch (item.getItemId()) {
                        case R.id.navigation_saved_businesses:
                            openFragment(SavedBusinessesFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_nearby_businesses:
                            openFragment(NearbyBusinessesFragment.newInstance());
                            return true;
                        case R.id.navigation_profile:
                            openFragment(ProfileFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };

}
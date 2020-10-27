package com.thecats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.Fragments.ImagesFragment;
import com.Fragments.ListFragment;
import com.Fragments.VotesFragment;
import com.Util.Const;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ImagesFragment imagesFragment;
    private ListFragment listFragment;
    private VotesFragment votesFragment;
    private BottomNavigationView bottomNavigation;
    private int switchNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        imagesFragment = new ImagesFragment();
        listFragment = new ListFragment();
        votesFragment = new VotesFragment();

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        switchNavigationMenu = Const.NAV_LIST;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, listFragment).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments;
        switch (menuItem.getItemId()) {
            case R.id.navigation_List:
                if (switchNavigationMenu != Const.NAV_LIST) {
                    transaction.replace(R.id.frame_layout_main, listFragment);
                    fragments = getSupportFragmentManager().getFragments();
                    for (Fragment frament : fragments) {
                        getSupportFragmentManager().beginTransaction().remove(frament).commit();
                    }
                    transaction.commit();
                    switchNavigationMenu = Const.NAV_LIST;
                }
                return true;

            case R.id.navigation_Images:
                if (switchNavigationMenu != Const.NAV_IMAGES) {

                    transaction.replace(R.id.frame_layout_main, imagesFragment);
                    fragments = getSupportFragmentManager().getFragments();
                    for (Fragment frament : fragments) {
                        getSupportFragmentManager().beginTransaction().remove(frament).commit();
                    }
                    transaction.commit();
                    switchNavigationMenu = Const.NAV_IMAGES;


                }

                return true;

            case R.id.navigation_Votes:
                if (switchNavigationMenu != Const.NAV_VOTES) {
                    transaction.replace(R.id.frame_layout_main, votesFragment);
                    fragments = getSupportFragmentManager().getFragments();
                    for (Fragment frament : fragments) {
                        getSupportFragmentManager().beginTransaction().remove(frament).commit();
                    }
                    transaction.commit();
                    switchNavigationMenu = Const.NAV_VOTES;
                }
                return true;

        }
        return false;
    }
}
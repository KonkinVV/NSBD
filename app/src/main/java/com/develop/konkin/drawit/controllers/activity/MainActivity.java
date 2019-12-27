package com.develop.konkin.drawit.controllers.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.develop.konkin.drawit.R;
import com.develop.konkin.drawit.controllers.fragment.InfoFragment;
import com.develop.konkin.drawit.controllers.fragment.IntegralFragment;
import com.develop.konkin.drawit.controllers.fragment.graph.MainGraphFragment;
import com.develop.konkin.drawit.model.helper.FragmentHelper;

import static com.develop.konkin.drawit.model.helper.FragmentHelper.changeFragment;

public class MainActivity extends AppCompatActivity {
    public static final String FUNCTIONS_KEY = "functions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.activity_main__nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_info);
        changeFragment(InfoFragment.newInstance(), getSupportFragmentManager(), R.id.activity_main__container);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_graph:
                changeFragment(MainGraphFragment.newInstance(), getSupportFragmentManager(), R.id.activity_main__container);
                return true;
            case R.id.navigation_integral:
                changeFragment(IntegralFragment.newInstance(), getSupportFragmentManager(), R.id.activity_main__container);
                return true;
            case R.id.navigation_info:
                changeFragment(InfoFragment.newInstance(), getSupportFragmentManager(), R.id.activity_main__container);
                return true;
        }
        return false;
    };
}

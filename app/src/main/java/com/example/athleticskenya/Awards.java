package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;


import com.google.android.material.tabs.TabLayout;
import com.example.athleticskenya.fragments.TabsPagerAdapter;

public class Awards extends AppCompatActivity {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awards);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }else toolbar.setVisibility(View.GONE);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = findViewById(R.id.packagetablayout);
        createTabFragment();

    }

    private void createTabFragment(){
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), tabLayout);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}

package com.example.athleticskenya.viewPager;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public abstract class Presenter {

    protected View view;
    private AppCompatActivity activity;

    public Presenter(AppCompatActivity activity, View view){
        this.activity = activity;
        this.view = view;
        onCreateView();
    }

    public View getView(){
        return view;
    }

    public abstract void onCreateView();
}

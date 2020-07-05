package com.example.athleticskenya.viewPager;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athleticskenya.R;

public class Female extends Presenter {

    TextView textView;

    public Female(AppCompatActivity activity, View view){
        super(activity, view);
    }

    @Override
    public void onCreateView(){
        textView = view.findViewById(R.id.female);
    }
}

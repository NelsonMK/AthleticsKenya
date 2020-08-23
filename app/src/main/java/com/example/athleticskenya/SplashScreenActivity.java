package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashScreenActivity extends AppCompatActivity {

    Button signUp, logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        PrefManager prefManager = PrefManager.getInstance(SplashScreenActivity.this);
        int class1 = prefManager.userClass();
        if(prefManager.isLoggedIn()) {

            this.finish();
            switch (class1) {
                case 1:
                    Intent athlete = new Intent(SplashScreenActivity.this, AthleteActivity.class);
                    athlete.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(athlete);
                    finish();
                    break;
                case 2:
                    finish();
                    startActivity(new Intent(SplashScreenActivity.this, CoachActivity.class));
                    break;
            }
        }

        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreenActivity.this, SignupActivity.class));
                finish();
            }
        });

        logIn = findViewById(R.id.signIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
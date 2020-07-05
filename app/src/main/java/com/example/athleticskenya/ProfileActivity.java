package com.example.athleticskenya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.athleticskenya.Services.BrService;
import com.example.athleticskenya.database.DatabaseHandler;
import com.example.athleticskenya.getterClasses.User;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity{

    int id = PrefManager.getInstance(this).userId();

    private DatabaseHandler db = new DatabaseHandler(this);

    TextView first_name;
    TextView coach;
    TextView profile;
    TextView logout;
    TextView settings;
    ImageView profile_picture, imageViewSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

        /*
        start of setting up profile with data from shared preferences handled by UserManager class
         */
        profile();

        clicked();

    }

    void profile(){

        User user = db.getUser(id);

        first_name = findViewById(R.id.profile_email);
        first_name.setText(user.getEmail());

        profile_picture = findViewById(R.id.photo);
        String userImage = user.getImage();
        Glide.with(this)
                .load(userImage)
                .placeholder(R.drawable.person)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .apply(RequestOptions.circleCropTransform())
                .into(profile_picture);
    }

    void clicked(){
        profile = findViewById(R.id.editProfileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfile.class));
            }
        });
        coach = findViewById(R.id.textViewMyCoachOrAthlete);
        coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MyCoach.class));
            }
        });

        settings = findViewById(R.id.textViewSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "race" + user.getRace_type(), Toast.LENGTH_SHORT).show();
            }
        });
        imageViewSettings = findViewById(R.id.imageViewSettings);
        imageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startBroadcast();
            }
        });
        logout = findViewById(R.id.log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutDialog();
            }
        });
    }

    @SuppressLint("ShortAlarm")
    private void startBroadcast(){
       /* int time_input = 1;
        Intent intent = new Intent(this, BrService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (timeinput * 1000), pendingIntent);
        }*/

       int interval = 5000;
        Intent alarmIntent = new Intent(getApplicationContext(), BrService.class);
        // Pending Intent Object
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Alarm Manager Object
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        // Alarm Manager calls BroadCast for every Ten seconds (10 * 1000), BroadCase further calls service to check if new records are inserted in
        // Remote MySQL DB
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 5000, interval, pendingIntent);
        }
    }

   private void logOutDialog(){
       final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
       dialogBuilder.setTitle("Are you sure you want to log out?");
       dialogBuilder.setCancelable(false);


       dialogBuilder.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               PrefManager.getInstance(getApplicationContext()).logout();
           }
       });

       dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
       });

       final AlertDialog alertDialog = dialogBuilder.create();
       alertDialog.show();
   }
}

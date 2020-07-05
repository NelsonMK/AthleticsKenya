package com.example.athleticskenya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Random;

import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_ID;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_FIRSTNAME;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_LASTNAME;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_PHONE;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_EMAIL;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_IMAGE;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_CONTACT;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_HEIGHT;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_WEIGHT;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_LOCATION;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_D_O_B;
import static com.example.athleticskenya.adapters.AcceptedAdapter.EXTRA_RACE;

public class AcceptedProfile extends AppCompatActivity {

    TextView a_first_name, a_last_name, a_phone, a_email, a_height, a_weight, a_location, a_d_o_b, a_race;
    ImageView a_image;
    CardView cardView;

    Button button_diet, button_chat;

    public static final String EXTRA_ID_EXTRA = "id";
    public static final String EXTRA_CONTACT_EXTRA = "contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_profile);

        Intent intent = getIntent();
        final String id = intent.getStringExtra(EXTRA_ID);
        final String contact = intent.getStringExtra(EXTRA_CONTACT);

        profile();

        cardView = findViewById(R.id.card_head);
        cardView.setCardBackgroundColor(getRandomColor());

        button_diet = findViewById(R.id.diet_button);
        button_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcceptedProfile.this, RecommedDiet.class);
                intent.putExtra(EXTRA_ID_EXTRA, id);
                intent.putExtra(EXTRA_CONTACT_EXTRA, contact);
                startActivity(intent);
            }
        });

        button_chat = findViewById(R.id.chat_button);
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcceptedProfile.this, Chatroom.class);
                intent.putExtra(EXTRA_ID_EXTRA, id);
                intent.putExtra(EXTRA_CONTACT_EXTRA, contact);
                startActivity(intent);
            }
        });

    }

    void profile(){
                /*
        getting user data from the accepted adapter using put extras
         */

        Intent intent = getIntent();
        String first_name = intent.getStringExtra(EXTRA_FIRSTNAME);
        String last_name = intent.getStringExtra(EXTRA_LASTNAME);
        String phone = intent.getStringExtra(EXTRA_PHONE);
        String email = intent.getStringExtra(EXTRA_EMAIL);
        String d_o_b = intent.getStringExtra(EXTRA_D_O_B);
        String race = intent.getStringExtra(EXTRA_RACE);
        String image = intent.getStringExtra(EXTRA_IMAGE);
        String height1 = intent.getStringExtra(EXTRA_HEIGHT);
        String weight = intent.getStringExtra(EXTRA_WEIGHT);
        String location = intent.getStringExtra(EXTRA_LOCATION);

        a_first_name = findViewById(R.id.next_accepted_firstname);
        a_first_name.setText(first_name);
        a_last_name = findViewById(R.id.next_accepted_lastname);
        a_last_name.setText(last_name);
        a_phone = findViewById(R.id.next_accepted_phone);
        a_phone.setText(phone);
        a_email = findViewById(R.id.next_accepted_email);
        a_email.setText(email);
        a_d_o_b = findViewById(R.id.next_accepted_date);
        a_d_o_b.setText(d_o_b);
        a_race = findViewById(R.id.next_accepted_race);
        a_race.setText(race);
        a_location = findViewById(R.id.next_accepted_location);
        a_location.setText(location);
        a_height = findViewById(R.id.next_accepted_height);
        a_height.setText(height1);
        a_weight = findViewById(R.id.next_accepted_weight);
        a_weight.setText(weight);
        a_image = findViewById(R.id.accepted_pic);
        Glide.with(this)
                .load(image)
                .apply(RequestOptions.circleCropTransform())
                .into(a_image);
        /*
        end of setting up user profile
         */
    }

    public int getRandomColor(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }


}

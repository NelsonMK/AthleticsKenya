package com.example.athleticskenya;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import static com.example.athleticskenya.adapters.EventsAdapter.EXTRA_DETAILS;
import static com.example.athleticskenya.adapters.EventsAdapter.EXTRA_TITLES;
import static com.example.athleticskenya.adapters.EventsAdapter.EXTRA_IMAGE;

public class DetailActivity  extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String titles = intent.getStringExtra(EXTRA_TITLES);
        String detail = intent.getStringExtra(EXTRA_DETAILS);
        String image = intent.getStringExtra(EXTRA_IMAGE);

        TextView title = findViewById(R.id.text_view_title_detail);
        TextView details = findViewById(R.id.text_view_details);
        ImageView picture = findViewById(R.id.detail_imageview);

        title.setText(titles);
        Glide.with(this)
                .load(image)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(picture);
        details.setText(detail);

    }

}

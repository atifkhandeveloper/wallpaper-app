package com.digiclack.wallpapers.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.digiclack.wallpapers.APPUtility;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.TabActivity;

public class WelcomeActivity extends AppCompatActivity {

    Button wall , customwall , favourites , privacy;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        wall = findViewById(R.id.wall);
        customwall = findViewById(R.id.custom_wall);
        favourites = findViewById(R.id.favourites);
        privacy = findViewById(R.id.privacy);

        wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, TabActivity.class);
                startActivity(intent);
            }
        });

        customwall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent likequotesActivity = new Intent(WelcomeActivity.this, LikeAndDayQuotesListActivity.class);
                likequotesActivity.putExtra("wayTo", "Liked");
                startActivity(likequotesActivity);
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse(APPUtility.PrivacyPolicy));
                WelcomeActivity.this.startActivity(i);
            }
        });
    }


}
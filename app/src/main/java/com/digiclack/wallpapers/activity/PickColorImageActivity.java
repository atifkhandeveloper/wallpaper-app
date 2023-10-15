package com.digiclack.wallpapers.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.interfacelistner.GetColorListener;


public class PickColorImageActivity extends Activity {
    float initialX;
    float initialY;

    GetColorListener onGetColor;
    int pixel = -1;
    SharedPreferences preferences;
    String type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.dialog_color);
        this.onGetColor = AddTextQuotesActivity.c;
        this.type = getIntent().getStringExtra("type");
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ImageView img_base = (ImageView) findViewById(R.id.img_base);
        img_base.setImageBitmap(AddTextQuotesActivity.bitmapOriginal);
        ImageView img_done = (ImageView) findViewById(R.id.img_done);
        ImageView img_back = (ImageView) findViewById(R.id.img_back);
        final ImageView img_putcolor = (ImageView) findViewById(R.id.img_putcolor);
        img_putcolor.setBackgroundColor(this.pixel);
        img_base.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0 :
                        PickColorImageActivity.this.initialX = event.getX();
                        PickColorImageActivity.this.initialY = event.getY();
                        try {
                            PickColorImageActivity.this.pixel = AddTextQuotesActivity.bitmapOriginal.getPixel((int) PickColorImageActivity.this.initialX, (int) PickColorImageActivity.this.initialY);
                            img_putcolor.setBackgroundColor(PickColorImageActivity.this.pixel);
                            break;
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            break;
                        }
                    case 2 :
                        PickColorImageActivity.this.initialX = event.getX();
                        PickColorImageActivity.this.initialY = event.getY();
                        try {
                            PickColorImageActivity.this.pixel = AddTextQuotesActivity.bitmapOriginal.getPixel((int) PickColorImageActivity.this.initialX, (int) PickColorImageActivity.this.initialY);
                            img_putcolor.setBackgroundColor(PickColorImageActivity.this.pixel);
                            break;
                        } catch (IllegalArgumentException e2) {
                            e2.printStackTrace();
                            break;
                        }
                }
                return true;
            }
        });
        img_done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PickColorImageActivity.this.onGetColor.onColor(PickColorImageActivity.this.pixel, PickColorImageActivity.this.type);
                PickColorImageActivity.this.finish();
            }
        });
        img_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PickColorImageActivity.this.onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.onGetColor.onColor(0, this.type);
        finish();
    }

    protected void onResume() {
        super.onResume();
        if (this.preferences.getBoolean("isAdsDisabled", false)) {
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.preferences.getBoolean("isAdsDisabled", false)) {
        }
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.digiclack.wallpapers.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.adapter.StickerViewPagerAdapter;
import com.digiclack.wallpapers.interfacelistner.OnGetStickerReworded;
import com.digiclack.wallpapers.utility.Constants;


public class StickerList extends AppCompatActivity implements OnTabSelectedListener, OnGetStickerReworded {
    StickerViewPagerAdapter _adapter;
    ImageView back;
    int categoryPosition;
    TextView header_txt;
    RelativeLayout lay_MainTabbar;
    SharedPreferences preferences;
    String str1;
    String str2;
    String str3;
    TabLayout tabs;
    ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.sticker_list);
        getSupportActionBar().hide();
        init();
    }

    private void init() {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        this.categoryPosition = getIntent().getIntExtra("position", 0);
        this.lay_MainTabbar = (RelativeLayout) findViewById(R.id.lay_MainTabbar);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this._adapter = new StickerViewPagerAdapter(this, getFragmentManager(), this.categoryPosition);
        this.tabs = (TabLayout) findViewById(R.id.result_tabs);
        this.viewPager.setAdapter(this._adapter);
        this.tabs.setupWithViewPager(this.viewPager);
        this.viewPager.setCurrentItem(0);
        this.header_txt = (TextView) findViewById(R.id.txt_appname);
        this.back = (ImageView) findViewById(R.id.btn_bck);
        this.tabs.addOnTabSelectedListener(this);
        ((ImageView) findViewById(R.id.btn_bck)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StickerList.this.onBackPressed();
            }
        });
    }

    public void onTabSelected(Tab tab) {
    }

    public void onTabUnselected(Tab tab) {
    }

    public void onTabReselected(Tab tab) {
    }

    public void ongetStickerReworded(String type, String categoryBG, String imgType) {
        this.str1 = type;
        this.str2 = categoryBG;
        this.str3 = imgType;

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.failed_ads_dialog);
        dialog.setCancelable(false);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        btn_ok.setTypeface(Constants.getTextTypeface(this));
        btn_ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.viewPager != null && this.viewPager.getChildCount() != 0 && this._adapter.currentFragment(this.viewPager.getCurrentItem()) != null) {
            this._adapter.currentFragment(this.viewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void onResume() {
        if (!this.preferences.getBoolean("isAdsDisabled", false)) {
        }
        super.onResume();
    }

    protected void onPause() {
        if (!this.preferences.getBoolean("isAdsDisabled", false)) {
        }
        super.onPause();
    }

    public void onDestroy() {
        if (!this.preferences.getBoolean("isAdsDisabled", false)) {
        }
        super.onDestroy();
        try {
            this.lay_MainTabbar = null;
            this.header_txt = null;
            this.back = null;
            this._adapter = null;
            this.viewPager = null;
            this.tabs = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constants.freeMemory();
    }


}

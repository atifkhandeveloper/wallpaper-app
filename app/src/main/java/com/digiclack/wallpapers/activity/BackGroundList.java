package com.digiclack.wallpapers.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.adapter.BackgroundViewPagerAdapter;
import com.digiclack.wallpapers.interfacelistner.OnGetBackgroundReworded;
import com.digiclack.wallpapers.utility.Constants;


public class BackGroundList extends Activity implements OnGetBackgroundReworded {
    BackgroundViewPagerAdapter _adapter;
    ImageView back;
    OnClickListener backListener = new OnClickListener() {
        public void onClick(View v) {
            BackGroundList.this.finish();
        }
    };
    int categoryPosition;
    String categoryQuote;
    String hasAuthor;
    RelativeLayout lay_MainTabbar;
    SharedPreferences preferences;
    String quote;
    String str1;
    String str2;
    String str3;
    TabLayout tabs;
    ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_selected_category_list);
        init();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    }

    private void init() {
        this.quote = getIntent().getStringExtra("quote_edit");
        this.hasAuthor = getIntent().getStringExtra("hasAuthor");
        this.categoryQuote = getIntent().getStringExtra("categoryQuote");
        this.categoryPosition = getIntent().getIntExtra("categoryPos", 0);
        this.lay_MainTabbar = (RelativeLayout) findViewById(R.id.lay_MainTabbar);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this._adapter = new BackgroundViewPagerAdapter(this, getFragmentManager(), this.categoryPosition, this.quote, this.hasAuthor, this.categoryQuote);
        this.tabs = (TabLayout) findViewById(R.id.result_tabs);
        this.viewPager.setAdapter(this._adapter);
        this.tabs.setupWithViewPager(this.viewPager);
        this.viewPager.setCurrentItem(0);
        for (int i = 0; i < this.tabs.getTabCount(); i++) {
            this.tabs.getTabAt(i).setCustomView(this._adapter.getTabView(i));
        }
        this.back = (ImageView) findViewById(R.id.btn_bck);
        this.back.setOnClickListener(this.backListener);
        this.tabs.setOnTabSelectedListener(new OnTabSelectedListener() {
            public void onTabSelected(Tab tab) {
                tab.getCustomView().setBackgroundColor(BackGroundList.this.getResources().getColor(R.color.white));
            }

            public void onTabUnselected(Tab tab) {
                tab.getCustomView().setBackground(BackGroundList.this.getResources().getDrawable(R.drawable.gradient));
            }

            public void onTabReselected(Tab tab) {
            }
        });
    }

    public void ongetBackgroundReworded(String type, String categoryBG, String imgType) {
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



    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.viewPager != null && this.viewPager.getChildCount() != 0 && this._adapter.currentFragment(this.viewPager.getCurrentItem()) != null) {
            this._adapter.currentFragment(this.viewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void onResume() {
        super.onResume();
        if (!this.preferences.getBoolean("isAdsDisabled", false)) {

        }
    }

    protected void onPause() {
        super.onPause();
        if (!this.preferences.getBoolean("isAdsDisabled", false)) {

        }
    }

    public void onDestroy() {
        if (!this.preferences.getBoolean("isAdsDisabled", false)) {

        }
        super.onDestroy();
        try {
            this.lay_MainTabbar = null;
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

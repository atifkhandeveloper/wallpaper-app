package com.digiclack.wallpapers.activity;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digiclack.wallpapers.AdAdmob;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.adapter.RecyclerQuotesListAdapter;
import com.digiclack.wallpapers.db.DatabaseHandler;
import com.digiclack.wallpapers.utility.Constants;
import com.digiclack.wallpapers.utility.CustomTextView;
import com.digiclack.wallpapers.utility.QuotesInfo;

import java.util.ArrayList;

import yuku.ambilwarna.BuildConfig;

public class QuotesListActivity extends AppCompatActivity {
    public static String[] pathsOfFileUri = new String[1];
    int categoryId = -1;

    RecyclerView mRecyclerView;
    SharedPreferences preferences;
    ProgressBar progressBar;
    ArrayList<QuotesInfo> quotesInfoList = new ArrayList();
    LoadingQuotesListAsync quotesListAsync;
    RecyclerQuotesListAdapter quotesdapter;
    String searchString;

    public class LoadingQuotesListAsync extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            QuotesListActivity.this.progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            try {
                QuotesListActivity.this.quotesInfoList.clear();
                DatabaseHandler dh = DatabaseHandler.getDbHandler(QuotesListActivity.this);
                if (QuotesListActivity.this.searchString.equals(BuildConfig.FLAVOR)) {
                    QuotesListActivity.this.quotesInfoList = dh.getQuotesList(QuotesListActivity.this.categoryId, BuildConfig.FLAVOR);
                } else {
                    QuotesListActivity.this.quotesInfoList = dh.getQuotesList(QuotesListActivity.this.categoryId, QuotesListActivity.this.searchString);
                }
                dh.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return "yes";
        }

        protected void onPostExecute(String result) {
            QuotesListActivity.this.progressBar.setVisibility(View.GONE);
            if (QuotesListActivity.this.quotesInfoList.size() != 0) {
                QuotesListActivity.this.quotesdapter = new RecyclerQuotesListAdapter(QuotesListActivity.this, QuotesListActivity.this.quotesInfoList);
                QuotesListActivity.this.quotesdapter.setHasStableIds(true);
                QuotesListActivity.this.mRecyclerView.setAdapter(QuotesListActivity.this.quotesdapter);
                return;
            }
            ((CustomTextView) QuotesListActivity.this.findViewById(R.id.tvEmpty)).setVisibility(View.VISIBLE);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_quotes_list);
        getSupportActionBar().hide();
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            this.searchString = extra.getString("searchString");
            this.categoryId = extra.getInt("categoryId");
            String categoryName = extra.getString("categoryName");
            if (this.searchString.equals(BuildConfig.FLAVOR)) {
                ((CustomTextView) findViewById(R.id.headertext)).setText(categoryName);
            } else {
                ((CustomTextView) findViewById(R.id.headertext)).setText(this.searchString);
                if (this.searchString.length() > 26) {
                    ((CustomTextView) findViewById(R.id.txtDot)).setVisibility(View.VISIBLE);
                }
            }
        } else {
            finish();
        }
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.mRecyclerView.setHasFixedSize(true);
        pathsOfFileUri[0] = BuildConfig.FLAVOR;
        this.quotesListAsync = new LoadingQuotesListAsync();
        this.quotesListAsync.execute(new String[0]);

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);

    }

    public void viewLikedQuotes(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.preferences.getBoolean("isAdsDisabled", false)) {

            return;
        }

        if (!this.preferences.getBoolean("isAdsDisabled", false)) {

        }
    }

    protected void onPause() {
        super.onPause();
        if (!this.preferences.getBoolean("isAdsDisabled", false)) {
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (pathsOfFileUri.length > 0) {
            for (String deleteFile : pathsOfFileUri) {
                Constants.deleteFile(this, deleteFile);
            }
        }
    }


    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Glide.get(QuotesListActivity.this).clearDiskCache();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Glide.get(this).clearMemory();
            if (this.quotesListAsync != null) {
                if (this.quotesListAsync.getStatus() == Status.PENDING) {
                    this.quotesListAsync.cancel(true);
                }
                if (this.quotesListAsync.getStatus() == Status.RUNNING) {
                    this.quotesListAsync.cancel(true);
                }
            }
            this.mRecyclerView = null;
            this.quotesdapter = null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Constants.freeMemory();
    }
}

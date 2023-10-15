package com.digiclack.wallpapers.activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.adapter.RecyclerLikeQuotesAdapter;
import com.digiclack.wallpapers.db.DatabaseHandler;
import com.digiclack.wallpapers.utility.Constants;
import com.digiclack.wallpapers.utility.CustomTextView;
import com.digiclack.wallpapers.utility.QuotesInfo;
import com.digiclack.wallpapers.utility.UpdateQuotesListInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import yuku.ambilwarna.BuildConfig;

public class LikeAndDayQuotesListActivity extends AppCompatActivity implements UpdateQuotesListInterface {
    public static String[] pathsOfFileUri = new String[1];
    Editor editor;

    RecyclerView mRecyclerView;
    SharedPreferences preferences;
    SharedPreferences prefs;
    ProgressBar progressBar;
    ArrayList<QuotesInfo> quotesInfoList = new ArrayList();
    LoadingQuotesListAsync quotesListAsync;
    RecyclerLikeQuotesAdapter quotesdapter;
    String wayTo;

    public class LoadingQuotesListAsync extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            LikeAndDayQuotesListActivity.this.progressBar.setVisibility(View.VISIBLE);
    }

        protected String doInBackground(String... params) {
            try {
                LikeAndDayQuotesListActivity.this.quotesInfoList.clear();
                DatabaseHandler dh = DatabaseHandler.getDbHandler(LikeAndDayQuotesListActivity.this);
                if (params[0].equals("0")) {
                    LikeAndDayQuotesListActivity.this.quotesInfoList = dh.getLikedQuotesList(0);
                } else {
                    LikeAndDayQuotesListActivity.this.quotesInfoList = dh.getLikedQuotesList(Integer.parseInt(params[0]));
                }
                dh.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return "yes";
        }

        protected void onPostExecute(String result) {
            LikeAndDayQuotesListActivity.this.progressBar.setVisibility(View.GONE);
            if (LikeAndDayQuotesListActivity.this.quotesInfoList.size() != 0) {
                LikeAndDayQuotesListActivity.this.quotesdapter = new RecyclerLikeQuotesAdapter(LikeAndDayQuotesListActivity.this, LikeAndDayQuotesListActivity.this.quotesInfoList);
                LikeAndDayQuotesListActivity.this.quotesdapter.setHasStableIds(true);
                LikeAndDayQuotesListActivity.this.mRecyclerView.setAdapter(LikeAndDayQuotesListActivity.this.quotesdapter);
                return;
            }
            ((CustomTextView) LikeAndDayQuotesListActivity.this.findViewById(R.id.tvEmpty)).setVisibility(View.VISIBLE);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_quotes_list);
        getSupportActionBar().hide();
        this.prefs = getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor = getSharedPreferences("MY_PREFS_NAME", 0).edit();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.mRecyclerView.setHasFixedSize(true);
        pathsOfFileUri[0] = BuildConfig.FLAVOR;
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            this.wayTo = extra.getString("wayTo");
            if (this.wayTo.equals("Liked")) {
                ((CustomTextView) findViewById(R.id.headertext)).setText(getResources().getString(R.string.txt_liked_quotes));
                this.quotesListAsync = new LoadingQuotesListAsync();
                this.quotesListAsync.execute(new String[]{"0"});
                return;
            }
            int index;
            ((CustomTextView) findViewById(R.id.headertext)).setText(getResources().getString(R.string.txt_asQutesOfDay));
            String strDate = new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime());
            if (this.prefs.getString("Quote_Saved_Date", BuildConfig.FLAVOR).equalsIgnoreCase(strDate)) {
                index = this.prefs.getInt("Quote_ID", R.styleable.AppCompatTheme_seekBarStyle);
            } else {
                index = new Random().nextInt(6534);
                this.editor.putInt("Quote_ID", index);
                this.editor.putString("Quote_Saved_Date", strDate);
                this.editor.apply();
                this.editor.commit();
            }
            this.quotesListAsync = new LoadingQuotesListAsync();
            this.quotesListAsync.execute(new String[]{String.valueOf(index)});
        }
    }

    public void viewLikedQuotes(View view) {
        switch (view.getId()) {
            case R.id.btn_back :
                onBackPressed();
                return;
            default:
                return;
        }
    }

    public void updateAdapter() {
        if (this.wayTo.equals("Liked")) {
            this.quotesListAsync = new LoadingQuotesListAsync();
            this.quotesListAsync.execute(new String[]{"0"});
        }
    }

    protected void onPause() {
        super.onPause();
        if (!this.preferences.getBoolean("isAdsDisabled", false)) {

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

    public void onBackPressed() {
        super.onBackPressed();
        if (pathsOfFileUri.length > 0) {
            for (String deleteFile : pathsOfFileUri) {
                Constants.deleteFile(this, deleteFile);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Glide.get(LikeAndDayQuotesListActivity.this).clearDiskCache();
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


    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

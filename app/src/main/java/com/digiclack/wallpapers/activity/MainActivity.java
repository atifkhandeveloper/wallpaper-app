package com.digiclack.wallpapers.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digiclack.wallpapers.APPUtility;
import com.digiclack.wallpapers.AdAdmob;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.adapter.RecyclerCategoryAdapter;
import com.digiclack.wallpapers.db.DatabaseHandler;
import com.digiclack.wallpapers.db.Quotes;
import com.digiclack.wallpapers.db.QuotesSelect;
import com.digiclack.wallpapers.quotesedit.PictureConstant;
import com.digiclack.wallpapers.utility.CategoryInfo;
import com.digiclack.wallpapers.utility.Constants;

import java.util.ArrayList;

import yuku.ambilwarna.BuildConfig;

public class   MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST = 100;
    private static final int REQUEST_PERMISSION = 101;
    public static int height1;
    public static float ratio;
    public static int width;
    ImageButton btn_Search;
    ImageButton btn_clear;
    ImageButton btn_quoteofday;
    ArrayList<CategoryInfo> categoryInfoList = new ArrayList();
    EditText edittext;
    private boolean isOpenFisrtTime = false;

    RecyclerView mRecyclerView;
    boolean monthlyBoolean = false;

    SharedPreferences preferences;
    ProgressBar progressBar;
    LoadingCategoryAsync quotesListAsync;
    private BroadcastReceiver removewatermark_update = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            MainActivity.this.monthlyBoolean = false;
            MainActivity.this.yearlyBoolean = false;
        }
    };
    boolean yearlyBoolean = false;


    public class LoadingCategoryAsync extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            MainActivity.this.progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            try {
                MainActivity.this.categoryInfoList.clear();
                DatabaseHandler dh = DatabaseHandler.getDbHandler(MainActivity.this);
                MainActivity.this.callDB(dh);
                MainActivity.this.categoryInfoList = dh.getCategooryListDes();
                dh.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return "yes";
        }

        protected void onPostExecute(String result) {
            MainActivity.this.progressBar.setVisibility(View.GONE);
            if (MainActivity.this.categoryInfoList.size() != 0) {
                RecyclerCategoryAdapter categoryAdapter = new RecyclerCategoryAdapter(MainActivity.this, MainActivity.this.categoryInfoList);
                categoryAdapter.setHasStableIds(true);
                MainActivity.this.mRecyclerView.setAdapter(categoryAdapter);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        registerReceiver(this.removewatermark_update, new IntentFilter("Remove_Watermark"));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height1 = size.y;
        ratio = ((float) width) / ((float) height1);
        this.btn_quoteofday = (ImageButton) findViewById(R.id.btn_options);
        this.btn_clear = (ImageButton) findViewById(R.id.btn_clear);
        this.btn_Search = (ImageButton) findViewById(R.id.btn_Search);
        this.edittext = (EditText) findViewById(R.id.edittext);
        getWindow().setSoftInputMode(2);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        this.mRecyclerView.setHasFixedSize(true);
        if (VERSION.SDK_INT < 23 || (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)) {
            this.quotesListAsync = new LoadingCategoryAsync();
            this.quotesListAsync.execute(new String[0]);
        }else{
                permissionDialog();
            }

        this.edittext.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (MainActivity.this.edittext.length() > 0) {
                    MainActivity.this.btn_clear.setVisibility(View.VISIBLE);
                    MainActivity.this.btn_Search.setBackgroundResource(R.drawable.search);
                    return;
                }
                MainActivity.this.btn_clear.setVisibility(View.INVISIBLE);
                MainActivity.this.btn_Search.setBackgroundResource(R.drawable.search_light);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (MainActivity.this.edittext.length() > 0) {
                    MainActivity.this.btn_clear.setVisibility(View.VISIBLE);
                    MainActivity.this.btn_Search.setBackgroundResource(R.drawable.search);
                    return;
                }
                MainActivity.this.btn_clear.setVisibility(View.INVISIBLE);
                MainActivity.this.btn_Search.setBackgroundResource(R.drawable.search_light);
            }
        });

        AdAdmob adAdmob = new AdAdmob(MainActivity.this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), MainActivity.this);

    }

    public void viewLikedQuotes(View view) {
        switch (view.getId()) {
            case R.id.btn_like:
                Intent likequotesActivity = new Intent(this, LikeAndDayQuotesListActivity.class);
                likequotesActivity.putExtra("wayTo", "Liked");
                startActivity(likequotesActivity);
                return;
            case R.id.btn_options:
                PopupMenu popup = new PopupMenu(this, this.btn_quoteofday);
                popup.getMenuInflater().inflate(R.menu.poupup_menu_option, popup.getMenu());
                popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.textQuotesOfDay:
                                Intent likequotesActivity = new Intent(MainActivity.this, LikeAndDayQuotesListActivity.class);
                                likequotesActivity.putExtra("wayTo", "OfDay");
                                MainActivity.this.startActivity(likequotesActivity);
                                return true;
                            case R.id.imagePrivacy:
                                Intent i = new Intent("android.intent.action.VIEW");
                                i.setData(Uri.parse(APPUtility.PrivacyPolicy));
                                MainActivity.this.startActivity(i);
                                return true;
                            case R.id.imageMore:
                                String url1 = "https://play.google.com/store/apps/developer?id=" + APPUtility.Account_name;
                                Intent i1 = new Intent("android.intent.action.VIEW");
                                i1.setData(Uri.parse(url1));
                                MainActivity.this.startActivity(i1);
                                return true;
                            case R.id.imageRate:
                                String url2 = "https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName();
                                Intent i2 = new Intent("android.intent.action.VIEW");
                                i2.setData(Uri.parse(url2));
                                MainActivity.this.startActivity(i2);
                                return true;
                            case R.id.imageHelp:


                                try {
                                    Intent ii = new Intent(Intent.ACTION_SEND);
                                    ii.setType("text/plain");
                                    String sAux = "\n Photo frame\n\n";
                                    sAux = sAux
                                            + "https://play.google.com/store/apps/details?id="
                                            + getPackageName() + "\n\n";
                                    ii.putExtra(Intent.EXTRA_TEXT, sAux);
                                    startActivity(Intent.createChooser(ii, "choose one"));
                                } catch (Exception back5) {
                                }


                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
                return;

            case R.id.btn_Search:
                if (this.edittext.getText().toString().equals(BuildConfig.FLAVOR)) {
                    this.edittext.setError(getResources().getString(R.string.str_empaty));
                    return;
                }
                Intent searchquotesActivity = new Intent(this, QuotesListActivity.class);
                searchquotesActivity.putExtra("categoryId", 0);
                searchquotesActivity.putExtra("categoryName", BuildConfig.FLAVOR);
                searchquotesActivity.putExtra("searchString", this.edittext.getText().toString());
                startActivity(searchquotesActivity);
                return;
            case R.id.btn_clear:
                this.btn_clear.setVisibility(View.INVISIBLE);
                this.btn_Search.setBackgroundResource(R.drawable.search_light);
                this.edittext.setText(BuildConfig.FLAVOR);
                return;
            default:
                return;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_PERMISSION) {
            return;
        }
        if (VERSION.SDK_INT < 23 || (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)) {
            this.quotesListAsync = new LoadingCategoryAsync();
            this.quotesListAsync.execute(new String[0]);
            return;
        }  else if (VERSION.SDK_INT > 32 || (checkSelfPermission("android.permission.READ_MEDIA_IMAGES") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.READ_MEDIA_IMAGES") == PackageManager.PERMISSION_GRANTED)) {
            this.quotesListAsync = new LoadingCategoryAsync();
            this.quotesListAsync.execute(new String[0]);
            return;
        }
        permissionDialog();
    }

    public void permissionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.permissionsdialog);
        dialog.setTitle(getResources().getString(R.string.permission).toString());
        dialog.setCancelable(false);
        ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(View v) {
                if (VERSION.SDK_INT < 23) {
                    MainActivity.this.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, MainActivity.PERMISSIONS_REQUEST);
                    dialog.dismiss();
                } else if (VERSION.SDK_INT > 32) {
                    MainActivity.this.requestPermissions(new String[]{"android.permission.READ_MEDIA_IMAGES"}, MainActivity.PERMISSIONS_REQUEST);
                    dialog.dismiss();
                }
            }
        });
        if (this.isOpenFisrtTime) {
            Button setting = (Button) dialog.findViewById(R.id.settings);
            setting.setVisibility(View.VISIBLE);
            setting.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.this.startActivityForResult(intent, MainActivity.REQUEST_PERMISSION);
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != PERMISSIONS_REQUEST) {
            return;
        }
        if (grantResults[0] == 0) {
            if (VERSION.SDK_INT < 23 || (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)) {
                this.quotesListAsync = new LoadingCategoryAsync();
                this.quotesListAsync.execute(new String[0]);
                return;
            }
            this.isOpenFisrtTime = true;
            permissionDialog();
        } else if (VERSION.SDK_INT < 23 || (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)) {
            this.quotesListAsync = new LoadingCategoryAsync();
            this.quotesListAsync.execute(new String[0]);
        } else {
            this.isOpenFisrtTime = true;
            permissionDialog();
        }
    }

    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(this.removewatermark_update);
        try {

            System.gc();
            Runtime.getRuntime().gc();
        } catch (OutOfMemoryError e3) {
            e3.printStackTrace();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Glide.get(MainActivity.this).clearDiskCache();
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
        } catch (OutOfMemoryError e32) {
            e32.printStackTrace();
        } catch (Exception e42) {
            e42.printStackTrace();
        }
        Constants.freeMemory();
    }

    private void callDB(DatabaseHandler db) {
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", 0);
        Editor editor = prefs.edit();
        if (prefs.getString("name", "No name defined").equals("No name defined")) {
            String text = getResources().getString(R.string.tab_write);
            db.addQuotes(new Quotes("t1", 1, 53, String.valueOf(PictureConstant.getNewWidth(710.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(896.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 41.0f) + BuildConfig.FLAVOR, "-16514044", "windsong.ttf", "3", "3", "3", "-12303292", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t2", 2, 49, String.valueOf(PictureConstant.getNewWidth(630.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(1000.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 40.0f) + BuildConfig.FLAVOR, "-10081008", "Walkway_Bold.ttf", "1", "1", "1", "-1", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t3", 3, 49, String.valueOf(PictureConstant.getNewWidth(628.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(786.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 24.0f) + BuildConfig.FLAVOR, "-15064270", "Sofia-Regular.otf", "1", "1", "1", "-1", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t4", 4, 49, String.valueOf(PictureConstant.getNewWidth(559.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(833.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 36.0f) + BuildConfig.FLAVOR, "-13824", "Walkway_Bold.ttf", "4.5", "4.5", "4.5", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t5", 5, 49, String.valueOf(PictureConstant.getNewWidth(933.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(534.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 22.0f) + BuildConfig.FLAVOR, "-1", "Beyond Wonderland.ttf", "3", "3", "3", "-12303292", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t6", 6, 49, String.valueOf(PictureConstant.getNewWidth(929.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(516.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 31.0f) + BuildConfig.FLAVOR, "-3546350", "lesser concern.ttf", "3", "3", "3", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));


            db.addQuotes(new Quotes("t7", 7, 49, String.valueOf(PictureConstant.getNewWidth(770.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(774.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 37.0f) + BuildConfig.FLAVOR, "-12574976", "windsong.ttf", "1", "1", "1", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));

            db.addQuotes(new Quotes("t8", 8, 49, String.valueOf(PictureConstant.getNewWidth(890.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(504.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 24.0f) + BuildConfig.FLAVOR, "-2228371", "Advertising Script Bold Trial.ttf", "3.5", "3.5", "3.5", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t9", 9, 49, String.valueOf(PictureConstant.getNewWidth(816.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(736.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 34.0f) + BuildConfig.FLAVOR, "-1", "ROD.TTF", "5.5", "5.5", "5.5", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t10", 10, 49, String.valueOf(PictureConstant.getNewWidth(859.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(491.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 26.0f) + BuildConfig.FLAVOR, "-69405", "CalliGravity.ttf", "2.5", "2.5", "2.5", "-15647417", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t11", 11, 49, String.valueOf(PictureConstant.getNewWidth(947.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(427.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 26.0f) + BuildConfig.FLAVOR, "-16750266", "Walkway_Bold.ttf", "0", "0", "0", "0", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t12", 12, 49, String.valueOf(PictureConstant.getNewWidth(836.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(548.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 28.0f) + BuildConfig.FLAVOR, "-2381", "Beyond Wonderland.ttf", "3", "3", "3", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t13", 13, 49, String.valueOf(PictureConstant.getNewWidth(1040.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(565.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 27.0f) + BuildConfig.FLAVOR, "-5934779", "Cosmic Love.ttf", "1.5", "1.5", "1.5", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t14", 14, 49, String.valueOf(PictureConstant.getNewWidth(934.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(499.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 36.0f) + BuildConfig.FLAVOR, "-836536", "ARDECODE.ttf", "2", "2", "2", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t15", 15, 49, String.valueOf(PictureConstant.getNewWidth(919.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(513.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 19.0f) + BuildConfig.FLAVOR, "-1974091", "DejaVuSans_Bold.ttf", "3", "3", "3", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t16", 16, 49, String.valueOf(PictureConstant.getNewWidth(405.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(892.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 31.0f) + BuildConfig.FLAVOR, "-261", "Advertising Script Monoline Trial.ttf", "2", "2", "2", "-1", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t17", 17, 49, String.valueOf(PictureConstant.getNewWidth(460.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(843.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 31.0f) + BuildConfig.FLAVOR, "-1", "squealer.ttf", "2", "2", "2", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t18", 18, 53, String.valueOf(PictureConstant.getNewWidth(456.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(852.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 25.0f) + BuildConfig.FLAVOR, "-2816", "segoe.ttf", "3", "3", "3", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t19", 19, 53, String.valueOf(PictureConstant.getNewWidth(552.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(748.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 33.0f) + BuildConfig.FLAVOR, "-917886", "Aladin_Regular.ttf", "2.0", "2.0", "2.0", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t20", 20, 49, String.valueOf(PictureConstant.getNewWidth(486.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(850.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 32.0f) + BuildConfig.FLAVOR, "-2720513", "lesser concern.ttf", "3", "3", "3", "-16777216", "null", "no", "no", "no", "no", 250, 313, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t21", 21, 49, String.valueOf(PictureConstant.getNewWidth(902.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(569.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 27.0f) + BuildConfig.FLAVOR, "-7914994", "Advertising Script Monoline Trial.ttf", "0.0", "0.0", "0.0", "0", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t22", 22, 53, String.valueOf(PictureConstant.getNewWidth(652.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(748.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 29.0f) + BuildConfig.FLAVOR, "-256", "ROD.TTF", "2.5", "2.5", "2.5", "-10813440", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t23", 23, 49, String.valueOf(PictureConstant.getNewWidth(584.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(760.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 34.0f) + BuildConfig.FLAVOR, "-146432", "ARDECODE.ttf", "2", "2", "2", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t24", 24, 53, String.valueOf(PictureConstant.getNewWidth(445.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(707.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 30.0f) + BuildConfig.FLAVOR, "-1860755", "Walkway_Bold.ttf", "2.5", "2.5", "2.5", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t25", 25, 49, String.valueOf(PictureConstant.getNewWidth(442.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(741.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 32.0f) + BuildConfig.FLAVOR, "-8895187", "QUIGLEYW.TTF", "0", "0", "0", "06", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t26", 26, 49, String.valueOf(PictureConstant.getNewWidth(1007.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(446.0f)), text, PictureConstant.getNewSize(getApplicationContext(), 23.0f) + BuildConfig.FLAVOR, "-14143143", "Walkway_Bold.ttf", "2", "2", "2", "-16777216", "null", "no", "no", "no", "no", 0, 0, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            db.addQuotes(new Quotes("t99", 99, 49, String.valueOf(PictureConstant.getNewWidth(560.0f)) + "-" + String.valueOf(PictureConstant.getNewHeight(581.0f)), "Congratulations for always evolving and inspiring others", PictureConstant.getNewSize(getApplicationContext(), 18.0f) + BuildConfig.FLAVOR, "-16711936", "Advertising Script Bold Trial.ttf", "3.5", "3.5", "3.5", "-16777216", "null", "no", "no", "no", "no", 297, 313, 0, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "false", 0, BuildConfig.FLAVOR, 0, "false", 0));
            setSeletedData(db);
            editor.putString("name", "insertQuotes");
            editor.commit();
        }
    }

    public void setSeletedData(DatabaseHandler db) {
        db.addQuoteSelect(new QuotesSelect(1, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 41.0f) + BuildConfig.FLAVOR, "-7590912", "windsong.ttf", "2.0", "2.0", "2.0", "-9359358", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(2, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 41.0f) + BuildConfig.FLAVOR, "-11330816", "Capture_it.ttf", "3.0", "3.0", "3.0", "-1", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(3, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 25.0f) + BuildConfig.FLAVOR, "-1", "Sofia-Regular.otf", "3.0", "3.0", "3.0", "-12303292", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(4, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 36.0f) + BuildConfig.FLAVOR, "-1", "Beyond Wonderland.ttf", "4.5", "4.5", "4.5", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(5, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 23.0f) + BuildConfig.FLAVOR, "-256", "ufonts_com_ck_scratchy_box.ttf", "3.0", "3.0", "3.0", "-12303292", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(6, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 38.0f) + BuildConfig.FLAVOR, "-1", "lesser concern.ttf", "4.5", "4.5", "4.5", "-16777216", "null", "true", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(7, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 37.0f) + BuildConfig.FLAVOR, "-12574976", "windsong.ttf", BuildConfig.VERSION_NAME, BuildConfig.VERSION_NAME, BuildConfig.VERSION_NAME, "-16777216", "null", "false", "false", "true", "false"));
        db.addQuoteSelect(new QuotesSelect(8, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 24.0f) + BuildConfig.FLAVOR, "-2608", "Advertising Script Bold Trial.ttf", "3.0", "3.0", "3.0", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(9, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 35.0f) + BuildConfig.FLAVOR, "-1628", "Capture_it.ttf", "5.5", "5.5", "5.5", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(10, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 34.0f) + BuildConfig.FLAVOR, "-256", "CalliGravity.ttf", "2.5", "2.5", "2.5", "-15647417", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(11, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 26.0f) + BuildConfig.FLAVOR, "-16777216", "Aspergit.otf", "0.0", "0.0", "0.0", "0", "null", "true", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(12, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 28.0f) + BuildConfig.FLAVOR, "-1", "ufonts_com_ck_scratchy_box.ttf", "3.0", "3.0", "3.0", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(13, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 41.0f) + BuildConfig.FLAVOR, "-5934779", "lesser concern shadow.ttf", "1.5", "1.5", "1.5", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(14, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 37.0f) + BuildConfig.FLAVOR, "-9239546", "Adobe Caslon Pro Italic.ttf", "2.0", "2.0", "2.0", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(15, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 38.0f) + BuildConfig.FLAVOR, "-8114411", "majalla.ttf", "2.5", "2.5", "2.5", "-1", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(16, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 31.0f) + BuildConfig.FLAVOR, "-1850263", "Advertising Script Bold Trial.ttf", "0.0", "0.0", "0.0", "0", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(17, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 31.0f) + BuildConfig.FLAVOR, "-16316665", "squealer.ttf", "5.0", "5.0", "5.0", "-1", "null", "true", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(18, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 31.0f) + BuildConfig.FLAVOR, "-16711727", "Constantia Italic.ttf", "3.0", "3.0", "3.0", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(19, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 30.0f) + BuildConfig.FLAVOR, "-10223667", "Capture_it.ttf", "2.0", "2.0", "2.0", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(20, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 32.0f) + BuildConfig.FLAVOR, "-1", "lesser concern shadow.ttf", "3.0", "3.0", "3.0", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(21, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 39.0f) + BuildConfig.FLAVOR, "-11587554", "ARDECODE.ttf", "0.0", "0.0", "0.0", "0", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(22, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 30.0f) + BuildConfig.FLAVOR, "-16777216", "Capture_it.ttf", "0.0", "0.0", "0.0", "0", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(23, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 34.0f) + BuildConfig.FLAVOR, "-8109049", "aparaji.ttf", "2.0", "2.0", "2.0", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(24, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 30.0f) + BuildConfig.FLAVOR, "-16777216", "Walkway_Bold.ttf", "2.5", "2.5", "2.5", "-16777216", "null", "false", "false", "false", "true"));
        db.addQuoteSelect(new QuotesSelect(25, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 23.0f) + BuildConfig.FLAVOR, "-14744825", "squealer.ttf", "0.0", "0.0", "0.0", "0", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(26, 1, "15", "24", PictureConstant.getNewSize(getApplicationContext(), 23.0f) + BuildConfig.FLAVOR, "-15703697", "DejaVuSans_Bold.ttf", "0.0", "0.0", "0.0", "0", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(99, 1, "20", "26", PictureConstant.getNewSize(getApplicationContext(), 20.0f) + BuildConfig.FLAVOR, "-256", "Advertising Script Bold Trial.ttf", "3.0", "3.0", "3.0", "-16777216", "null", "false", "false", "false", "false"));
        db.addQuoteSelect(new QuotesSelect(99, 2, "9", "17", PictureConstant.getNewSize(getApplicationContext(), 20.0f) + BuildConfig.FLAVOR, "-16777216", "Adobe Caslon Pro Italic.ttf", "3.0", "3.0", "3.0", "-16777216", "null", "false", "false", "false", "false"));
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


    public void onBackPressed()      {
//        exitAlertDialog();
        super.onBackPressed();
    }

    public void exitAlertDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exitalert_dialog);
        dialog.setCancelable(true);

        Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
        ((Button) dialog.findViewById(R.id.btn_no)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
//                finishAffinity();
                dialog.dismiss();
            }
        });
        btn_yes.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.finish();
                System.exit(0);
                dialog.dismiss();
                moveTaskToBack(true);
            }
        });
        dialog.show();

    }


    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.digiclack.wallpapers.activity;

import static android.R.attr.x;
import static android.R.attr.y;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.digiclack.wallpapers.AdAdmob;
import com.digiclack.wallpapers.BuildConfig;
import com.digiclack.wallpapers.R;

import java.io.File;
import java.io.IOException;


public class SaveActivity extends AppCompatActivity {

    private ImageView mImageView;
    Uri myUri;
    Button fb, insta, what, share, save, back;
    RelativeLayout fbll, install, whatll, sharell;
    String path;
    ImageView shareapp , download;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(1024, 1024);

        setContentView(R.layout.save_activity);

        getSupportActionBar().hide();
//        askRatings();

        fb = (Button) findViewById(R.id.facebook);
        insta = (Button) findViewById(R.id.insta);
        what = (Button) findViewById(R.id.whatsup);
        share = (Button) findViewById(R.id.share);
        save = (Button) findViewById(R.id.done);
        back = (Button) findViewById(R.id.back);

        shareapp = findViewById(R.id.shareapp);
        download = findViewById(R.id.download);

        fbll = (RelativeLayout) findViewById(R.id.facebookll);
        install = (RelativeLayout) findViewById(R.id.install);
        whatll = (RelativeLayout) findViewById(R.id.whatsupll);
        sharell = (RelativeLayout) findViewById(R.id.sharell);
        mImageView = (ImageView) findViewById(R.id.mainImageView);
        Intent in = getIntent();
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inPreferredConfig = Bitmap.Config.ARGB_8888;
        path = in.getStringExtra("uri");
        Log.e("path", path);


        mImageView.setImageURI(Uri.parse(path));
        myUri = Uri.parse(path);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));

        download.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "touched down");

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "touched up");

                        saveImageBtnClicked();

                        break;
                }
                return false;
            }
        });
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "touched down");

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "touched up");

                        backk();

                        break;
                }
                return false;
            }
        });


        fb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "touched down");
                        fb.setBackgroundResource(R.drawable.apply);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "touched up");

                        try {
                            facebook();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        fb.setBackgroundResource(R.drawable.apply);
                        break;
                }
                return false;
            }
        });

        what.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "touched down");
                        what.setBackgroundResource(R.drawable.what1);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "touched up");
                        whatsup();
                        what.setBackgroundResource(R.drawable.what);
                        break;
                }
                return false;
            }
        });

        insta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "touched down");
                        insta.setBackgroundResource(R.drawable.insta1
                        );

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "touched up");
                        insta.setBackgroundResource(R.drawable.insta);
                        instagram();
                        break;
                }
                return false;
            }
        });

        shareapp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "touched down");
                        share.setBackgroundResource(R.drawable.sharehd);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "touched up");
                        share();
                        share.setBackgroundResource(R.drawable.sharehd);
                        break;
                }
                return false;
            }
        });


        AdAdmob adAdmob = new AdAdmob(SaveActivity.this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), SaveActivity.this);
        adAdmob.FullscreenAd(SaveActivity.this);
    }


    public void saveImageBtnClicked() {


        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        Toast.makeText(this, "save image", Toast.LENGTH_SHORT).show();
        finishAffinity();
        startActivity(new Intent(SaveActivity.this, MainActivity.class));
    }


    public void backk() {
        File fdelete = new File(myUri.getPath());
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + myUri.getPath());
            } else {
                System.out.println("file not Deleted :" + myUri.getPath());
            }
        }
        finish();

    }

    public void facebook() throws IOException {

        final Uri data = FileProvider.getUriForFile(SaveActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(path));
        SaveActivity.this.grantUriPermission(SaveActivity.this.getPackageName(), data, Intent.
                FLAG_GRANT_READ_URI_PERMISSION);

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , Uri.parse(String.valueOf(data)));
        Toast.makeText(this, "Please wait... Setting Wallpaper", Toast.LENGTH_SHORT).show();
        WallpaperManager wallpaperManager =
                WallpaperManager.getInstance(getApplicationContext());
        wallpaperManager.setBitmap(bitmap);
//        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        sendIntent.setType("image/*");
//        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Image");
//        sendIntent.setPackage("com.facebook.katana");
//        sendIntent.putExtra(Intent.EXTRA_STREAM, data);
//        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        sendIntent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.app_name));
//        SaveActivity.this.startActivity(Intent.createChooser(sendIntent, "Share Image:"));
    }

    public void instagram() {

        final Uri data = FileProvider.getUriForFile(SaveActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(path));
        SaveActivity.this.grantUriPermission(SaveActivity.this.getPackageName(), data, Intent.
                FLAG_GRANT_READ_URI_PERMISSION);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Image");
        sendIntent.setPackage("com.instagram.android");
        sendIntent.putExtra(Intent.EXTRA_STREAM, data);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.app_name));
        SaveActivity.this.startActivity(Intent.createChooser(sendIntent, "Share Image:"));

    }

    public void whatsup() {
        final Uri data = FileProvider.getUriForFile(SaveActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(path));
        SaveActivity.this.grantUriPermission(SaveActivity.this.getPackageName(), data, Intent.
                FLAG_GRANT_READ_URI_PERMISSION);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Image");
        sendIntent.setPackage("com.whatsapp");
        sendIntent.putExtra(Intent.EXTRA_STREAM, data);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.app_name));
        SaveActivity.this.startActivity(Intent.createChooser(sendIntent, "Share Image:"));
    }

    public void share() {

        final Uri data = FileProvider.getUriForFile(SaveActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(path));
        SaveActivity.this.grantUriPermission(SaveActivity.this.getPackageName(), data, Intent.
                FLAG_GRANT_READ_URI_PERMISSION);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Image");
        sendIntent.putExtra(Intent.EXTRA_STREAM, data);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.app_name));
        SaveActivity.this.startActivity(Intent.createChooser(sendIntent, "Share Image:"));


    }

//    void askRatings() {
//        ReviewManager manager = ReviewManagerFactory.create(this);
//        Task<ReviewInfo> request = manager.requestReviewFlow();
//        request.addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                // We can get the ReviewInfo object
//                ReviewInfo reviewInfo = task.getResult();
//                Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
//                flow.addOnCompleteListener(task2 -> {
//                    // The flow has finished. The API does not indicate whether the user
//                    // reviewed or not, or even whether the review dialog was shown. Thus, no
//                    // matter the result, we continue our app flow.
//                });
//            } else {
//                // There was some problem, continue regardless of the result.
//            }
//        });
//    }
}

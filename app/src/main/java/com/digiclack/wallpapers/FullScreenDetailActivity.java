package com.digiclack.wallpapers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digiclack.unsplash.Unsplash;
import com.digiclack.unsplash.models.Download;
import com.digiclack.unsplash.models.Photo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class FullScreenDetailActivity extends AppCompatActivity {

    private static String TAG = "FullScreenDetailActivity";
    private String CLIENT_ID;
    public ImageView imageView;
    ProgressBar progressBar;
    String photoUrl, splashUrl;
    TextView photoByTv;
    TextView onSplashTextView;
    boolean downloaded = false;
    private Unsplash unsplash;
    private String photoId;
    ImageView setwall , share , download;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        CLIENT_ID = getString(R.string.unsplash_access_key);;
        unsplash = new Unsplash(CLIENT_ID);
        photoId = getIntent().getStringExtra("PHOTO_ID");

        imageView = findViewById(R.id.imageView);
        photoByTv = findViewById(R.id.photo_by);
        onSplashTextView = findViewById(R.id.on_splash_tv);
        setwall = findViewById(R.id.setwall);
        download = findViewById(R.id.download);
        share = findViewById(R.id.share);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);



        setwall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FullScreenDetailActivity.this, "Please wait... Setting Wallpaper", Toast.LENGTH_SHORT).show();
                new SetAsWallpaperAsync(getApplicationContext(), photoUrl).execute();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Let me recommend you this application");
                    String shareMessage= "Dress up your phone with remarkable wallpapers to match your mood! Download this app\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }

            }
        });

        //InterstitialAd

        unsplash.getPhoto(photoId, new Unsplash.OnPhotoLoadedListener() {
            @Override
            public void onComplete(Photo photo) {

                photoUrl = photo.getUrls().getRegular();
                splashUrl = photo.getLinks().getHtml();
                if (photoUrl.length() > 1) {
                    Picasso.get().load(photoUrl).into(imageView);
                    if (progressBar.isShown()) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    photoByTv.setText("Photo by " + photo.getUser().getFirstName() + " " + photo.getUser().getLastName() + " ");

                    onSplashTextView.setText(Html.fromHtml("<p>on <u>Unsplash</u></p>"));
                    onSplashTextView.setMovementMethod(LinkMovementMethod.getInstance());
                    onSplashTextView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                            browserIntent.setData(Uri.parse(splashUrl));
                            startActivity(browserIntent);
                        }
                    });
                }

            }

            @Override
            public void onError(String error) {

            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setDrawingCacheEnabled(true);
                Bitmap bitmap = imageView.getDrawingCache();

                String root = Environment.getExternalStorageDirectory()
                        .toString();
                File newDir = new File(root + "/saved_picture");
                newDir.mkdirs();
                Random gen = new Random();
                int n = 10000;
                n = gen.nextInt(n);
                String fotoname = n + ".jpg";
                File file = new File(newDir, fotoname);
                String s = file.getAbsolutePath();
                Log.i("Path of saved image.", s);
                System.err.print("Path of saved image." + s);
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    Toast.makeText(getApplicationContext(), "Photo Saved",
                            Toast.LENGTH_SHORT).show();
                    out.close();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Photo Saved",
                            Toast.LENGTH_SHORT).show();
                    Log.e("Exception", e.toString());
                }




//                unsplash.getPhotoDownloadLink(photoId, new Unsplash.OnLinkLoadedListener() {
//                    @Override
//                    public void onComplete(Download downloadLink) {
//
//                        photoUrl = downloadLink.getUrl();
//                        isStoragePermissionGranted();
//
//                    }
//
//                    @Override
//                    public void onError(String error) {
//
//                    }
//                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_full_screen, menu);
        // Associate searchable configuration with the SearchView
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_set_wallpaper:

                //shows interstitial ad when "set as wallpaper" is clicked
                showAd();

                Toast.makeText(this, "Please wait... Setting Wallpaper", Toast.LENGTH_SHORT).show();
                new SetAsWallpaperAsync(getApplicationContext(), photoUrl).execute();

                return true;

            case R.id.download_wallpaper:

                //shows interstitial ad when "download" is clicked
                showAd();

                //triggering dowload -- api guideline recommendation
                unsplash.getPhotoDownloadLink(photoId, new Unsplash.OnLinkLoadedListener() {
                    @Override
                    public void onComplete(Download downloadLink) {

                        photoUrl = downloadLink.getUrl();
                        isStoragePermissionGranted();

                    }

                    @Override
                    public void onError(String error) {

                    }
                });

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAd() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");

    }

    private void downloadPhotoPicasso() {
        String storagePath = Environment.getExternalStorageDirectory().getPath() + "/Download/";
//Log.d("Strorgae in view",""+storagePath);
        File f = new File(storagePath);
        if (!f.exists()) {
            f.mkdirs();
        }
//storagePath.mkdirs();
        if (!f.exists()) {
            f.mkdirs();
        }
//Log.d("Storage ",""+pathname);
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(photoUrl);
        if (!downloaded) {
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir("/Download", uri.getLastPathSegment()+".jpg");
            Long referese = dm.enqueue(request);
            Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
            downloaded=true;
        } else {
            Toast.makeText(this, "Saved to gallery", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                downloadPhotoPicasso();

                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Toast.makeText(this, "Download Failed! Permission not granted.", Toast.LENGTH_SHORT).show();

                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            downloadPhotoPicasso();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    String getSaveImageFilePath() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Wallpapers");
        // Create a storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
//                Log.d(, "Failed to create directory");
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "IMG_" + timeStamp + ".jpg";

        String selectedOutputPath = mediaStorageDir.getPath() + File.separator + imageName;
//        Log.d(YOUR_FOLDER_NAME, "selected camera path " + selectedOutputPath);

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());

        int maxSize = 1080;

        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();

        if (bWidth > bHeight) {
            int imageHeight = (int) Math.abs(maxSize * ((float)bitmap.getWidth() / (float) bitmap.getHeight()));
            bitmap = Bitmap.createScaledBitmap(bitmap, maxSize, imageHeight, true);
        } else {
            int imageWidth = (int) Math.abs(maxSize * ((float)bitmap.getWidth() / (float) bitmap.getHeight()));
            bitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, maxSize, true);
        }
        imageView.setDrawingCacheEnabled(false);
        imageView.destroyDrawingCache();

        OutputStream fOut = null;
        try {
            File file = new File(selectedOutputPath);
            fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedOutputPath;
    }

}



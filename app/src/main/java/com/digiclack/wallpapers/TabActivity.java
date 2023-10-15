package com.digiclack.wallpapers;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.digiclack.wallpapers.activity.LikeAndDayQuotesListActivity;
import com.digiclack.wallpapers.activity.MainActivity;
import com.digiclack.wallpapers.activity.WelcomeActivity;
import com.google.android.material.navigation.NavigationView;


public class TabActivity extends AppCompatActivity {

    int jobId = 4343;


    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.open, R.string.close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
                        Intent intent = new Intent(TabActivity.this, TabActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.privacy:
                        Intent i = new Intent("android.intent.action.VIEW");
                        i.setData(Uri.parse(APPUtility.PrivacyPolicy));
                        TabActivity.this.startActivity(i);
                        break;
                    case R.id.categories:
                        Intent intent1 = new Intent(TabActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.favourites:
                        Intent likequotesActivity = new Intent(TabActivity.this, LikeAndDayQuotesListActivity.class);
                        likequotesActivity.putExtra("wayTo", "Liked");
                        startActivity(likequotesActivity);
                        ;break;
                    case R.id.rate:
                        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                        }
                        break;
                    case R.id.share:
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
                        break;

                    default:
                        return true;
                }


                return true;

            }
        });



        MainFragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment).commit();

            // Test if jobscheduler is running
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(isJobServiceOn()){
            Log.d("TabActivity: ", "JobScheduler running "+isJobServiceOn());

            //  Auto-wallpaper settings
            boolean autoWallpaperSwitch = SP.getBoolean("auto_wallpaper_switch", false);
            Log.d("TabActivity: ", "JobScheduler  -autowallpaper status "+autoWallpaperSwitch);

            int interval = Integer.parseInt(SP.getString("wallpaper_change_interval", "1440"));
            Log.d("TabActivity: ", "JobScheduler -interval "+interval);
        } else {

            // disabling auto-wallpaper service ended
            SP.edit().putBoolean("auto_wallpaper_switch", false).commit();
            Log.d("TabActivity: ", "JobScheduler NOT running "+isJobServiceOn());


        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_auto_wallpaper_activity) {

            Intent i = new Intent(this, AutoWallpaperSettingsActivity.class);
            startActivity(i);
            finish();

            return true;
        }
        if (id == R.id.menu_rate) {

            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }

            return true;
        }
        if (id == R.id.share_app) {

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

            return true;
        }


        if(t.onOptionsItemSelected(item))
            return true;


        return super.onOptionsItemSelected(item);
    }

    public boolean isJobServiceOn() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE ) ;

        boolean hasBeenScheduled = false ;

        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == jobId ) {
                hasBeenScheduled = true ;
                break ;
            }
        }

        return hasBeenScheduled ;
    }


}

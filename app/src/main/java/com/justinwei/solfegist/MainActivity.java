package com.justinwei.solfegist;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private Fragment cameraFragment;
    private Fragment learnFragment;
    private Fragment profileFragment;
    //solfege sound variables
    private MediaPlayer doSound, reSound, miSound, faSound, solSound, laSound, tiSound;
    public static boolean soundEnabled = false;

    //variables to update actionBar
    SpannableString sHome, sLearn, sProfile;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //display HomeFragment on startup
        if (findViewById(R.id.container) != null)
            if (null == savedInstanceState) {
                cameraFragment = Camera2BasicFragment.newInstance();
                learnFragment = new LearnFragment();
                profileFragment = new ProfileFragment();
                loadFragment(cameraFragment);
            }

        loadActionBarText();
        loadAudio();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = Camera2BasicFragment.newInstance();
                actionBar.setTitle(sHome);
                break;

            case R.id.navigation_learn:
                fragment = new LearnFragment();
                actionBar.setTitle(sLearn);

                break;

            case R.id.navigation_practice:
                fragment = new ProfileFragment();
                actionBar.setTitle(sProfile);
                break;

        }
        return loadFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.infoButton)
        {
            //show info screen
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadActionBarText() {
        //update action bar font
        sHome = new SpannableString("SOLFEGIST");
        sHome.setSpan(new TypefaceSpan(this, "Market_Deco.ttf"), 0, sHome.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar = getSupportActionBar();
        actionBar.setTitle(sHome);
        sLearn = new SpannableString("SOLFEGE HAND SIGNS");
        sLearn.setSpan(new TypefaceSpan(this, "Market_Deco.ttf"), 0, sLearn.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sProfile = new SpannableString("MY PROFILE");
        sProfile.setSpan(new TypefaceSpan(this, "Market_Deco.ttf"), 0, sProfile.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void loadAudio() {
        //handle sound
        doSound = MediaPlayer.create(this, R.raw.donote);
        reSound = MediaPlayer.create(this, R.raw.renote);
        miSound = MediaPlayer.create(this, R.raw.minote);
        faSound = MediaPlayer.create(this, R.raw.fanote);
        solSound = MediaPlayer.create(this, R.raw.solnote);
        laSound = MediaPlayer.create(this, R.raw.lanote);
        tiSound = MediaPlayer.create(this, R.raw.tinote);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                // update sound here
                if (soundEnabled)
                    switch ((String) Camera2BasicFragment.getToggleButton().getText()) {
                        case "do":
                            doSound.start();
                            break;
                        case "re":
                            reSound.start();
                            break;
                        case "mi":
                            miSound.start();
                            break;
                        case "fa":
                            faSound.start();
                            break;
                        case "sol":
                            solSound.start();
                            break;
                        case "la":
                            laSound.start();
                            break;
                        case "ti":
                            tiSound.start();
                            break;

                    }
                handler.postDelayed(this, 2000); // set time here to refresh textView

            }
        });
    }
}

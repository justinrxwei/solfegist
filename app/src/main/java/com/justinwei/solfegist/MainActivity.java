package com.justinwei.solfegist;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.pavelsikun.seekbarpreference.SeekBarPreference;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private Fragment cameraFragment;
    private Fragment learnFragment;
    private ProfileFragment profileFragment;

    private static boolean soundEnabled = false;

    //variables to update actionBar
    private SpannableString sHome, sLearn, sProfile;
    private ActionBar actionBar;

    MediaPlayer doSound, reSound, miSound, faSound, solSound, laSound, tiSound;
    private static int soundDelay;
    private static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //initialize fragments and display cameraFragment on startup
        if (findViewById(R.id.container) != null)
            if (null == savedInstanceState) {
                cameraFragment = Camera2BasicFragment.newInstance();
                learnFragment = new LearnFragment();
                profileFragment = new ProfileFragment();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, learnFragment)
                        .commit();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, profileFragment)
                        .commit();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, cameraFragment)
                        .commit();
            }

        loadActionBarText();
        loadAudio();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.navigation_home:
                displayCameraFragment();
                actionBar.setTitle(sHome);
                if (!ProfileFragment.getPrefCheckClassify().isChecked()) {
                    Camera2BasicFragment.getToggleButton().setChecked(false);
                }
                break;

            case R.id.navigation_learn:
                displayLearnFragment();
                actionBar.setTitle(sLearn);
                //turn off button when cameraFragment is hidden
                if (!ProfileFragment.getPrefCheckClassify().isChecked()) {
                    setSoundEnabled(false);
                    Camera2BasicFragment.getToggleButton().setChecked(false);
                }
                break;

            case R.id.navigation_practice:
                displayProfileFragment();
                actionBar.setTitle(sProfile);
                if (!ProfileFragment.getPrefCheckClassify().isChecked()) {
                    setSoundEnabled(false);
                    Camera2BasicFragment.getToggleButton().setChecked(false);
                }
                break;
        }
        return true;
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

    //https://stackoverflow.com/a/31197906/8309611
    //TODO: clean these methods up :(
    private void displayCameraFragment() {
        if (cameraFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(cameraFragment).commit();;
        } else {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, cameraFragment).commit();
        }
        if (learnFragment.isAdded()) getFragmentManager().beginTransaction().hide(learnFragment).commit();
        if (profileFragment.isAdded()) getFragmentManager().beginTransaction().hide(profileFragment).commit();
        //getFragmentManager().beginTransaction().commit();
    }

    private void displayLearnFragment() {
        if (learnFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(learnFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, learnFragment).commit();
        }
        if (cameraFragment.isAdded()) getFragmentManager().beginTransaction().hide(cameraFragment).commit();
        if (profileFragment.isAdded()) getFragmentManager().beginTransaction().hide(profileFragment).commit();

        //listeners for card buttons
        Button doButton = (Button) findViewById(R.id.doCardButton);
        Button reButton = (Button) findViewById(R.id.reCardButton);
        Button miButton = (Button) findViewById(R.id.miCardButton);
        Button faButton = (Button) findViewById(R.id.faCardButton);
        Button solButton = (Button) findViewById(R.id.solCardButton);
        Button laButton = (Button) findViewById(R.id.laCardButton);
        Button tiButton = (Button) findViewById(R.id.tiCardButton);

        doButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { doSound.start(); }
        });
        reButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { reSound.start(); }
        });
        miButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { miSound.start(); }
        });
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { faSound.start(); }
        });
        solButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { solSound.start(); }
        });
        laButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { laSound.start(); }
        });
        tiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { tiSound.start(); }
        });

    }

    private void displayProfileFragment() {
        if (profileFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(profileFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, profileFragment).commit();
        }
        if (learnFragment.isAdded()) getFragmentManager().beginTransaction().hide(learnFragment).commit();
        if (cameraFragment.isAdded()) getFragmentManager().beginTransaction().hide(cameraFragment).commit();

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

        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                // update sound here
                if (soundEnabled) {
                    soundDelay = ProfileFragment.getSoundDelayValue();
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
                }

                    handler.postDelayed(this, soundDelay*900 + 1500); // set time here to refresh textView
            }
        });


    }

    public static void setSoundEnabled(boolean soundEnabled) {
        MainActivity.soundEnabled = soundEnabled;
    }
}

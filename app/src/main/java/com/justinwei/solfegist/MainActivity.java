package com.justinwei.solfegist;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //display HomeFragment on startup
        if (findViewById(R.id.container) != null)
            if (null == savedInstanceState)
                loadFragment(Camera2BasicFragment.newInstance());


        //Update action bar font
        SpannableString s = new SpannableString("SOLFEGIST");
        s.setSpan(new TypefaceSpan(this, "Market_Deco.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);
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
                break;

            case R.id.navigation_learn:
                fragment = new LearnFragment();
                break;

            case R.id.navigation_practice:
                fragment = new PracticeFragment();
                break;

        }
        return loadFragment(fragment);
    }
}

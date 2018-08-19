package com.justinwei.solfegist;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import com.pavelsikun.seekbarpreference.SeekBarPreference;


public class ProfileFragment extends PreferenceFragment {

    private static SeekBarPreference prefSeekBar;
    private static CheckBoxPreference prefCheckClassify;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_content);

        prefSeekBar = (SeekBarPreference) findPreference("seekBarDelay");

        prefCheckClassify = (CheckBoxPreference) findPreference("checkbox_preference");
        prefCheckClassify.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean checked = Boolean.valueOf(newValue.toString());
                if (checked == false) MainActivity.setSoundEnabled(false);
                return true;
            }
        });

    }

    public static CheckBoxPreference getPrefCheckClassify() {
        return prefCheckClassify;
    }


    public static int getSoundDelayValue() {
        return prefSeekBar.getCurrentValue();
    }

}

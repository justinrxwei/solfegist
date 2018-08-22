package com.justinwei.solfegist;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pavelsikun.seekbarpreference.SeekBarPreference;


public class ProfileFragment extends PreferenceFragment {

    private static SeekBarPreference prefSeekBar;
    private static CheckBoxPreference prefCheckClassify;
    private static ListPreference prefSoundType;

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

        prefSoundType = (ListPreference) findPreference("sound_preference");
        prefSoundType.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                ((MainActivity)getActivity()).loadAudio((String) o);
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

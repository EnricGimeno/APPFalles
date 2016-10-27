package com.gimeno.enric.falles_designv2;

import android.os.Bundle;
import android.preference.PreferenceFragment;

// Clase para las preferencias
public class MyPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}

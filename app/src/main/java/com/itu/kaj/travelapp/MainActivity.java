package com.itu.kaj.travelapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;


public class MainActivity extends Activity implements CheckInFragment.OnFragmentInteractionListener, StationFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.mainContainer);

        if (fragment == null)
            fragment = new CheckInFragment();

        fm.beginTransaction().add(R.id.mainContainer, fragment).commit();

    }

    @Override
    public void onFragmentInteraction(String id) {
        // required implementation
    }



}

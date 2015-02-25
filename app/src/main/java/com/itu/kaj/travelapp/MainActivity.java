package com.itu.kaj.travelapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;


public class MainActivity extends Activity implements CheckInFragment.OnFragmentInteractionListener, StationFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.mainContainer);

        if (fragment == null)
            fragment = CheckInFragment.newInstance();

        fm.beginTransaction().add(R.id.mainContainer, fragment, CheckInFragment.TAG).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // required implementation
    }

    @Override
    public void recieveInput(String text) {
        CheckInFragment fragment = (CheckInFragment) getFragmentManager().findFragmentByTag(CheckInFragment.TAG);
        fragment.setStation(text);

    }


}

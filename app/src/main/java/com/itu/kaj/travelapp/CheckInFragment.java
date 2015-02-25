package com.itu.kaj.travelapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CheckInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CheckInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckInFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "check_in_fragmant";

    public final String LAST_START = "start";
    public final String LAST_DESTINATION = "destination";
    public final String CHECK_IN_BUTTON = "check_in_button";
    public final String CHECK_OUT_BUTTON = "check_out_button";
    public final String CHECK_IN_ENABLED = "check_in_enabled";
    public final String CHECK_OUT_ENABLED = "check_out_enabled";
    public final String CHECK_IN_TEXT = "check_in_text";
    public final String CHECK_OUT_TEXT = "check_out_text";
    public final String RECEIPT = "receipt";
    private String startStation = "";
    private String endStation = "";

    private String[] receipt = new String[2];

    private Button checkInButton, checkOutButton, selectInButton, selectOutButton;
    private EditText checkInText, checkOutText;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheckInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckInFragment newInstance() {
        CheckInFragment fragment = new CheckInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CheckInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_check_in, container, false);
        Log.i(TAG,"OnCreateView Called");
        return v;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Log.i(TAG,"OnViewCreated Called");


        checkInText = (EditText) v.findViewById(R.id.check_in_input);
        checkOutText = (EditText) v.findViewById(R.id.check_out_input);

        checkInButton = (Button) v.findViewById(R.id.check_in_button);
        checkInButton.setOnClickListener(this);

        checkOutButton = (Button) v.findViewById(R.id.check_out_button);
        checkOutButton.setOnClickListener(this);

        selectInButton = (Button) v.findViewById(R.id.selectInButton);
        selectInButton.setOnClickListener(this);

        selectOutButton = (Button) v.findViewById(R.id.selectOutButton);
        selectOutButton.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "on_pause called");
        Bundle outState = getFragmentManager().findFragmentByTag(TAG).getArguments();
        outState.putString(LAST_START, startStation);
        outState.putString(LAST_DESTINATION, endStation);
        outState.putStringArray(RECEIPT, receipt);
        outState.putBoolean(CHECK_IN_BUTTON, checkInButton.isEnabled());
        outState.putBoolean(CHECK_OUT_BUTTON, checkOutButton.isEnabled());

        outState.putBoolean(CHECK_IN_ENABLED, checkInText.isEnabled());
        outState.putString(CHECK_IN_TEXT, checkInText.getText().toString());

        outState.putBoolean(CHECK_OUT_ENABLED, checkOutText.isEnabled());
        outState.putString(CHECK_OUT_TEXT, checkOutText.getText().toString());

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.check_in_button:
                startStation = checkInText.getText().toString();
                if (startStation.equals("")) {
                    Toast.makeText(getActivity(), "Please enter input", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkInText.setEnabled(false);
                    checkInButton.setEnabled(false);
                    checkOutButton.setEnabled(true);
                    checkOutText.setEnabled(true);
                    selectInButton.setEnabled(false);
                    selectOutButton.setEnabled(true);
                }
                break;

            case R.id.check_out_button:
                endStation = checkOutText.getText().toString();
                if (endStation.equals("")) {
                    Toast.makeText(getActivity(), "Please enter input", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkOutText.setText("");
                    checkInText.setText("");
                    checkInButton.setEnabled(true);
                    checkOutButton.setEnabled(false);
                    checkInText.setEnabled(true);
                    checkOutText.setEnabled(false);
                    selectInButton.setEnabled(true);
                    selectOutButton.setEnabled(false);
                    receipt[0] = startStation;
                    receipt[1] = endStation;

                    Toast.makeText(getActivity(), "You are hopefully at your destination", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.selectInButton:

            case R.id.selectOutButton:
                Fragment fragment = new StationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (fragment == null)
                    fragment = StationFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putInt(StationFragment.INPUT_ID, v.getId());
                fragment.setArguments(bundle);

                //transaction.hide(getFragmentManager().findFragmentByTag(TAG));
                //transaction.show(fragment);
                transaction.replace(R.id.mainContainer, fragment);
                transaction.addToBackStack(null);

                transaction.commit();

                break;
        }
    }

    public void setStation(String station) {
        checkInText.setText(station);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop called");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"OnActivityCreated Called");
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            startStation = savedInstanceState.getString(LAST_START);
            endStation = savedInstanceState.getString(LAST_DESTINATION);
            receipt = savedInstanceState.getStringArray(RECEIPT);
            checkInButton.setEnabled(savedInstanceState.getBoolean(CHECK_IN_BUTTON));
            checkOutButton.setEnabled(savedInstanceState.getBoolean(CHECK_OUT_BUTTON));

            checkInText.setEnabled(savedInstanceState.getBoolean(CHECK_IN_ENABLED));
            checkInText.setText(savedInstanceState.getString(CHECK_IN_TEXT));

            checkOutText.setEnabled(savedInstanceState.getBoolean(CHECK_OUT_ENABLED));
            checkOutText.setText(savedInstanceState.getString(CHECK_OUT_TEXT));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "On Save Instance State called");
        super.onSaveInstanceState(outState);

        outState.putString(LAST_START, startStation);
        outState.putString(LAST_DESTINATION, endStation);
        outState.putStringArray(RECEIPT, receipt);
        outState.putBoolean(CHECK_IN_BUTTON, checkInButton.isEnabled());
        outState.putBoolean(CHECK_OUT_BUTTON, checkOutButton.isEnabled());

        outState.putBoolean(CHECK_IN_ENABLED, checkInText.isEnabled());
        outState.putString(CHECK_IN_TEXT, checkInText.getText().toString());

        outState.putBoolean(CHECK_OUT_ENABLED, checkOutText.isEnabled());
        outState.putString(CHECK_OUT_TEXT, checkOutText.getText().toString());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

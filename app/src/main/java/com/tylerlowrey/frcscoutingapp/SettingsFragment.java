package com.tylerlowrey.frcscoutingapp;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;

/**
 * This fragment allows the user to navigate between the PitScoutingFragment and MatchScoutingFragment
 */
public class SettingsFragment extends Fragment
{

    private static final String TAG = "SETTINGS_FRAGMENT";

    public SettingsFragment()
    {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of the MenuFragment class
     * @return SettingsFragment - A new instance of the PitScoutingFragment class
     */
    public static SettingsFragment newInstance()
    {
        SettingsFragment fragment = new SettingsFragment();

        return fragment;
    }

    /**
     * Inflates the fragment and makes sure the AppBar is shown to the user
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings_screen, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        return view;

    }


    /**
     * This function sets up all of the button click event handler functions and stores
     * references to view objects that are used in other functions in order to grab data
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Button logScoutingDataButton = getView().findViewById(R.id.log_scouting_data_button);
        logScoutingDataButton.setOnClickListener(onLogScoutingDataClick);
    }

    /**
     * Prints out all the un-uploaded scouting data in the SQLite database to the debug output stream (Logcat)
     */
    private View.OnClickListener onLogScoutingDataClick = view -> {
        JsonArray jsonArray = ScoutingDatabase.getInstance(getContext()).getScoutingDataAsJsonArray();
        Log.d(TAG, jsonArray.toString());
    };

    /**
     * Navigates the user to the Match Scouting Fragment
     *
     * @post The current fragment will be replaced with the PitScoutingFragment

    private View.OnClickListener onPitScoutingClick = view -> {
        NavigationManager navManager = NavigationManager.getInstance();
        navManager.navigateToFragment(PitScoutingFragment.newInstance());
    };
    */

}

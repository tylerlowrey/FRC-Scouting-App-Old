package com.tylerlowrey.frcscoutingapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MenuFragment extends Fragment
{

    public MenuFragment()
    {
        // Required empty public constructor
    }


    public static MenuFragment newInstance()
    {
        MenuFragment fragment = new MenuFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_menu_screen, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Button matchScoutingBtn = getView().findViewById(R.id.menu_match_scouting_button);
        matchScoutingBtn.setOnClickListener(onMatchScoutingClick);
        Button pitScoutingBtn = getView().findViewById(R.id.menu_pit_scouting_button);
        pitScoutingBtn.setOnClickListener(onPitScoutingClick);

        //TODO: Remove this
        /*
        TextView testText = getView().findViewById(R.id.test_shared_pref_username);
        SharedPreferences sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        testText.setText(sharedPrefs.getString(getString(R.string.shared_prefs_current_user), MainActivity.DEFAULT_USERNAME));
        */
    }

    private View.OnClickListener onMatchScoutingClick = view -> {
        NavigationManager navManager = NavigationManager.getInstance();
        navManager.navigateToFragment(MatchScoutingFragment.newInstance());
    };

    private View.OnClickListener onPitScoutingClick = view -> {
        NavigationManager navManager = NavigationManager.getInstance();
        navManager.navigateToFragment(PitScoutingFragment.newInstance());
    };

}

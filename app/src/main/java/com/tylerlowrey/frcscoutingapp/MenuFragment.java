package com.tylerlowrey.frcscoutingapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * This fragment allows the user to navigate between the PitScoutingFragment and MatchScoutingFragment
 */
public class MenuFragment extends Fragment
{

    public MenuFragment()
    {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of the MenuFragment class
     * @return MenuFragment - A new instance of the PitScoutingFragment class
     */
    public static MenuFragment newInstance()
    {
        MenuFragment fragment = new MenuFragment();

        return fragment;
    }

    /**
     * Inflates the fragment and makes sure the AppBar is shown to the user
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_menu_screen, container, false);
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
        Button matchScoutingBtn = getView().findViewById(R.id.menu_match_scouting_button);
        matchScoutingBtn.setOnClickListener(onMatchScoutingClick);
        Button pitScoutingBtn = getView().findViewById(R.id.menu_pit_scouting_button);
        pitScoutingBtn.setOnClickListener(onPitScoutingClick);

    }

    /**
     * Navigates the user to the Match Scouting Fragment
     *
     * @post The current fragment will be replaced with the MatchScoutingFragment
     */
    private View.OnClickListener onMatchScoutingClick = view -> {
        NavigationManager navManager = NavigationManager.getInstance();
        navManager.navigateToFragment(MatchScoutingFragment.newInstance());
    };

    /**
     * Navigates the user to the Match Scouting Fragment
     *
     * @post The current fragment will be replaced with the PitScoutingFragment
     */
    private View.OnClickListener onPitScoutingClick = view -> {
        NavigationManager navManager = NavigationManager.getInstance();
        navManager.navigateToFragment(PitScoutingFragment.newInstance());
    };

}

package com.tylerlowrey.frcscoutingapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Button matchScoutingBtn = getView().findViewById(R.id.menu_match_scouting_button);
        matchScoutingBtn.setOnClickListener(onMatchScoutingClick);
        Button pitScoutingBtn = getView().findViewById(R.id.menu_pit_scouting_button);
        pitScoutingBtn.setOnClickListener(onPitScoutingClick);

    }

    private View.OnClickListener onMatchScoutingClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            NavigationManager navManager = NavigationManager.getInstance();
            navManager.navigateToFragment(MatchScoutingFragment.newInstance());
        }
    };

    private View.OnClickListener onPitScoutingClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            NavigationManager navManager = NavigationManager.getInstance();
            navManager.navigateToFragment(PitScoutingFragment.newInstance());
        }
    };

}

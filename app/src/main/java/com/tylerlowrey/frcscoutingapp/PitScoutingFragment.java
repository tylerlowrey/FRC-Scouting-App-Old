package com.tylerlowrey.frcscoutingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class PitScoutingFragment extends Fragment
{


    public PitScoutingFragment()
    {
        // Required empty public constructor
    }

    public static PitScoutingFragment newInstance()
    {
        PitScoutingFragment fragment = new PitScoutingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pit_scouting, container, false);
    }


}

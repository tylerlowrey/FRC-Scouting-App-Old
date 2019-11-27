package com.tylerlowrey.frcscoutingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MatchScoutingFragment extends Fragment
{


    public MatchScoutingFragment()
    {
        // Required empty public constructor
    }

    public static MatchScoutingFragment newInstance()
    {
        MatchScoutingFragment fragment = new  MatchScoutingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_scouting, container, false);
    }


}

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Button submitFormBtn = view.findViewById(R.id.match_scouting_submit_button);
        submitFormBtn.setOnClickListener(onSubmitForm);

    }

    private View.OnClickListener onSubmitForm = (View view) -> {
        //
    };

}

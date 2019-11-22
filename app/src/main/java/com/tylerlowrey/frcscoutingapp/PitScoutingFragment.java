package com.tylerlowrey.frcscoutingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Button matchScoutingBtn = getView().findViewById(R.id.take_picture_button);
        matchScoutingBtn.setOnClickListener(onTakePictureClick);

    }

    private View.OnClickListener onTakePictureClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            //Attempt to open camera (if user has given permission)

            //Save picture to a folder on accessible phone storage
        }
    };


}

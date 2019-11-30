package com.tylerlowrey.frcscoutingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class PitScoutingFragment extends Fragment
{

    public static final String TAG = "PITSCOUTINGFRAGMENT";
    private LinearLayout formContainer;

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        return inflater.inflate(R.layout.fragment_pit_scouting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        formContainer = view.findViewById(R.id.pit_form_container);
        Button takePictureBtn = view.findViewById(R.id.take_picture_button);
        takePictureBtn.setOnClickListener(onTakePictureClick);
        Button submitFormBtn = view.findViewById(R.id.pit_scouting_submit_button);
        submitFormBtn.setOnClickListener(onSubmitForm);

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

    private View.OnClickListener onSubmitForm = (View view) -> {
        for(int i = 0; i < formContainer.getChildCount(); ++i)
        {
            View childView = formContainer.getChildAt(i);
            if(childView instanceof TextView)
            {
                TextView txtView = (TextView) childView;
                txtView.getText();
            }
        }
    };


}

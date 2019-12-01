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


public class AppSettingsFragment extends Fragment
{

    public AppSettingsFragment()
    {
        // Required empty public constructor
    }


    public static AppSettingsFragment newInstance()
    {
        AppSettingsFragment fragment = new AppSettingsFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings_screen , container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Button googleSignOutBtn = getView().findViewById(R.id.google_sign_out_button);
        googleSignOutBtn.setOnClickListener(onGoogleSignOutClick);
    }

    private View.OnClickListener onGoogleSignOutClick = (View view) -> {
        NavigationManager navManager = NavigationManager.getInstance();
        navManager.navigateToFragment(LoginScreenFragment.newInstance());
    };


}

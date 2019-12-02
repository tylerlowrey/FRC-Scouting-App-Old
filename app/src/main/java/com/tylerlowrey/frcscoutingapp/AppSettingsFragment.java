package com.tylerlowrey.frcscoutingapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;


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

        if(!FileUploader.getInstance().isSignedIntoDrive(getActivity().getApplicationContext()))
        {
            googleSignOutBtn.setText(R.string.settings_not_signed_in);
        }
        else
        {
            googleSignOutBtn.setText(R.string.settings_already_signed_in);
        }

        googleSignOutBtn.setOnClickListener(onGoogleAccountButtonClick);
    }

    private View.OnClickListener onGoogleAccountButtonClick = (View view) -> {

        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(getActivity().getApplicationContext());

        FileUploader fileUploader = FileUploader.getInstance();

        if(fileUploader.isSignedIntoDrive(getActivity().getApplicationContext()))
        {
            fileUploader.signOutOfDrive();
        }
        else
        {
            fileUploader.signIntoDrive(getActivity());
        }

        NavigationManager.getInstance().navigateToFragment(AppSettingsFragment.newInstance());

    };


}

package com.tylerlowrey.frcscoutingapp;


import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;

public class LoginScreenFragment extends Fragment
{

    public static final String TAG = "LOGIN_SCREEN_FRAGMENT";

    public LoginScreenFragment()
    {
        // Required empty public constructor
    }


    public static LoginScreenFragment newInstance()
    {
        LoginScreenFragment fragment = new LoginScreenFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login_screen, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Button nextFragBtn = getView().findViewById(R.id.login_submit_button);
        nextFragBtn.setOnClickListener(handleClick);
    }

    private View.OnClickListener handleClick = (View view) -> {
            String newUsername = getString(R.string.default_username);

            //Grab name from edit text
            EditText username = getView().findViewById(R.id.login_name_input);

            if (!username.getText().toString().equals(""))
            {
                newUsername = username.getText().toString();
                newUsername.replace(' ', '_');
            }

            NavigationManager navManager = NavigationManager.getInstance();
            SharedPreferences sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(getString(R.string.shared_prefs_current_user), newUsername);
            editor.apply();
            navManager.navigateToFragment(MenuFragment.newInstance());
    };

}

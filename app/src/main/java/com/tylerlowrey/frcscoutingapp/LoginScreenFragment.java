package com.tylerlowrey.frcscoutingapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginScreenFragment extends Fragment
{

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
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Button nextFragBtn = getView().findViewById(R.id.login_submit_button);
        nextFragBtn.setOnClickListener(handleClick);
    }

    private View.OnClickListener handleClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            NavigationManager navManager = NavigationManager.getInstance();
            navManager.navigateToFragment(MenuFragment.newInstance());
        }
    };

}

package com.tylerlowrey.frcscoutingapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * Provides logic and the Fragment implementation for the Login Screen
 * Adds event listeners to the buttons of the Login Screen Fragment
 */
public class LoginScreenFragment extends Fragment
{

    public static final String TAG = "LOGIN_SCREEN_FRAGMENT";

    public LoginScreenFragment()
    {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of the LoginScreenFragment class
     * @return LoginScreenFragment - A new instance of the LoginScreenFragmentclass
     */
    public static LoginScreenFragment newInstance()
    {
        LoginScreenFragment fragment = new LoginScreenFragment();

        return fragment;
    }

    /**
     * Inflates the fragment and makes sure the AppBar is hidden to the user
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login_screen, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return view;

    }

    /**
     * This function sets up all of the button click event handler functions and stores
     * references to view objects that are used in other functions in order to grab data
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Button nextFragBtn = getView().findViewById(R.id.login_submit_button);
        nextFragBtn.setOnClickListener(handleClick);
    }

    /**
     * Navigates to the MenuFragment and also sets the CURRENT_USER to the input given by the user
     *
     * @pre The login_name_input EditText should contain valid user input
     * @post The shared preference CURRENT_USER will be set to the contents of the login_name_input
     *       EditText
     */
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

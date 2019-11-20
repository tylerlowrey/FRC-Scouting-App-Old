package com.tylerlowrey.frcscoutingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationManager navManager = NavigationManager.getInstance();
        navManager.init(getSupportFragmentManager());

        navManager.navigateToFragment(LoginScreenFragment.newInstance());
    }

    @Override
    public void onBackPressed()
    {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1)
        {
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            this.finish();
        }
    }
}

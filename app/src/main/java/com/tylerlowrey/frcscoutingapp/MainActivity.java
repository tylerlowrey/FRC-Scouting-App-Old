package com.tylerlowrey.frcscoutingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

public class MainActivity extends AppCompatActivity
{

    public static final String TAG = "MAIN_ACTIVITY";
    public static final int REQUEST_CODE_SIGN_IN = 1;

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

    /**
     * Google Sign In stuff
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        switch(requestCode)
        {
            case REQUEST_CODE_SIGN_IN:
                if(resultCode == MainActivity.RESULT_OK && resultData != null)
                {
                    handleSignInResult(resultData);
                }
                break;
            default:
                Log.d(MainActivity.TAG, "onActivityResult: default case reached " + requestCode + ", " + resultCode);
                Log.d(MainActivity.TAG, "Result data: " + resultData.getExtras().toString());
        }

        super.onActivityResult(requestCode, resultCode, resultData);
    }

    private void handleSignInResult(Intent result)
    {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                    .addOnSuccessListener(googleAccount -> {
                        MainActivity.makeToast(getApplicationContext(), "Signed in as " + googleAccount.getEmail(), Toast.LENGTH_LONG);
                    });

    }

    public static void makeToast(Context context, String message, int duration)
    {
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}

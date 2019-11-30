package com.tylerlowrey.frcscoutingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{

    public static final String TAG = "MAIN_ACTIVITY";
    public static final int REQUEST_CODE_SIGN_IN = 1;
    public static final int REQUEST_CODE_UPLOAD_FILES = 2;

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
                    Log.d(MainActivity.TAG, "onActivityResult: REQUEST_CODE_SIGN_IN [" + requestCode + "], Result Code:" + resultCode);
                    Bundle bundle = resultData.getExtras();
                    for(String key: bundle.keySet())
                    {
                        Log.d(TAG, key + " = " + bundle.get(key));
                    }
                    handleSignInResult(resultData);
                }
            case REQUEST_CODE_UPLOAD_FILES:
                Log.d(MainActivity.TAG, "onActivityResult: REQUEST_CODE_SIGN_IN [" + requestCode + "], Result Code:" + resultCode);
                if(FileUploader.getInstance().hasFilePermissions(this))
                {
                    try
                    {
                        FileUploader.getInstance().uploadAllSavedFiles();
                        Log.d(TAG, "Files uploaded");
                        makeToast(getApplicationContext(), "Files uploaded.", Toast.LENGTH_LONG);
                    }
                    catch (IOException e)
                    {
                        Log.d(TAG, "Unable to upload files");
                        makeToast(getApplicationContext(), "Unable to upload files", Toast.LENGTH_LONG);
                    }

                }
                else
                {
                    Log.d(TAG, "Did not have permissions");
                }
                break;
            default:
                Log.d(MainActivity.TAG, "onActivityResult: default case reached " + requestCode + ", " + resultCode);
                Bundle bundle = resultData.getExtras();
                for(String key: bundle.keySet())
                {
                    Log.d(TAG, key + " = " + bundle.get(key));
                }

        }

        super.onActivityResult(requestCode, resultCode, resultData);
    }

    //Adapted from Zybooks 4.1.2
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;

    }

    //Adapted from Zybooks Figure 4.1.3
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_upload_files:
                makeToast(getApplicationContext(), "Files uploaded", Toast.LENGTH_LONG);
                return true;
            case R.id.action_change_user:
                NavigationManager.getInstance().navigateToFragment(LoginScreenFragment.newInstance());
                return true;
            case R.id.action_go_to_settings:
                NavigationManager.getInstance().navigateToFragment(PitScoutingFragment.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

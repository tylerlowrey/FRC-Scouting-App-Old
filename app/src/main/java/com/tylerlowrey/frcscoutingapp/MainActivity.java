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
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
{

    public static final String TAG = "MAIN_ACTIVITY";
    public static final int REQUEST_CODE_SIGN_IN = 1;
    public static final int REQUEST_CODE_UPLOAD_FILES = 2;
    public static final String DEFAULT_USERNAME = "Default_User";

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
           /* case REQUEST_CODE_UPLOAD_FILES:
                if(FileUploader.getInstance().hasFilePermissions(this))
                {
                    try
                    {
                        FileUploader.getInstance().uploadLocalFiles();
                        Log.d(TAG, "Files uploaded");
                        makeToast(getApplicationContext(), "Files uploaded.", Toast.LENGTH_LONG);
                    }
                    catch (IOException e)
                    {
                        Log.d(TAG, "Unable to upload files");
                        makeToast(getApplicationContext(), "Error: Unable to upload files", Toast.LENGTH_LONG);
                    }
                }
                else
                {
                    Log.d(TAG, "Did not have permissions");
                }
                break; */
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
            case R.id.action_main_menu:
                NavigationManager.getInstance().navigateToFragment(MenuFragment.newInstance());
                return true;
            case R.id.action_upload_files:
                try
                {
                    if(!FileUploader.getInstance().isSignedIntoDrive(getApplicationContext()))
                        FileUploader.getInstance().signIntoDrive(this);
                    else
                        FileUploader.getInstance().uploadLocalFiles();

                }
                catch (IOException e)
                {
                    Log.d(TAG, "Unable to upload files");
                    makeToast(getApplicationContext(), "Error: Unable to upload files", Toast.LENGTH_LONG);
                }
                return true;
            case R.id.action_change_user:
                NavigationManager.getInstance().navigateToFragment(LoginScreenFragment.newInstance());
                return true;
            case R.id.action_go_to_settings:
                NavigationManager.getInstance().navigateToFragment(AppSettingsFragment.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleSignInResult(Intent result)
    {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    Log.d(TAG, "Signed in as " + googleAccount.getEmail());

                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();

                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions.
                    FileUploader.getInstance().setDriveService(googleDriveService);
                })
                .addOnFailureListener(exception -> Log.e(TAG, "Unable to sign in.", exception));

    }

    public static void makeToast(Context context, String message, int duration)
    {
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}

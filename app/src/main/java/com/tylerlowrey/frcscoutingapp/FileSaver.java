package com.tylerlowrey.frcscoutingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A singleton class that handles the saving of files within the app.
 *
 * @pre external read and write permissions must be set up in the AndroidManifest file
 */
public class FileSaver
{
    private static final String TAG = "FILE_SAVER";
    public static final int REQUEST_READ_CODE = 0;
    public static final int REQUEST_WRITE_CODE = 1;
    private static FileSaver fileSaver;


    private FileSaver()
    {
    }

    /**
     * Returns the singleton instance of the FileUpload (or creates one if there is not one already)
     * @return FileSaver - The singleton FileSaver that is shared across the app
     */
    public static FileSaver getInstance()
    {
        if(fileSaver != null)
            return fileSaver;

        fileSaver = new FileSaver();
        return fileSaver;


    }

    /**
     * Checks to see if the user currently has given the app permission to access files and if not
     * will prompt the user for allowing permission
     *
     * @param activity - The instance of MainActivity that is calling this function
     * @return boolean - True if permission is granted, false otherwise.
     *
     * This code was adapted from ZyBooks 6.2.2
     */
    public boolean hasFilePermissions(Activity activity)
    {
        String writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readPermission = Manifest.permission.READ_EXTERNAL_STORAGE;

        Context context = activity.getApplicationContext();
        if(context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, readPermission))
            {
                Log.d(TAG, "shouldShowRequestPermissionRationale for WRITE was true");
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{ writePermission }, REQUEST_READ_CODE);
            }
            return false;
        }

        if(context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, readPermission))
            {
                Log.d(TAG, "shouldShowRequestPermissionRationale for READ was true");
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{ readPermission }, REQUEST_WRITE_CODE);
            }
            return false;
        }
        return true;

    }

    /**
     * Saves a file onto the device's external storage (Inside Documents/FRC Scouting App)
     *
     * @param filename - The name of the file that will be saved into storage
     * @param data - A String that contains the contents to be written to the file
     * @throws IOException
     *
     * @pre this.hasFilePermissions should be called directly prior to invoking this function so
     *      that the user is prompted to allow the app permission to store files externally
     * @post A new file named according to the filename parameter will be stored on the device
     */
    public void saveTextFileLocally(String filename, String data) throws IOException
    {

        File fileStorageRootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File appRootStorageDir = new File(fileStorageRootDir + "/FRC Scouting App");

        if(!appRootStorageDir.exists())
            appRootStorageDir.mkdirs();

        File appLocalStorageDir = new File(appRootStorageDir, "local");

        if(!appLocalStorageDir.exists())
            appLocalStorageDir.mkdirs();

        File fileToWrite = new File(appLocalStorageDir, filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite))) {
            writer.write(data);
        }

    }

}

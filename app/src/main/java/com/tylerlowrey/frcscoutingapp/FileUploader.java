package com.tylerlowrey.frcscoutingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//TODO: Indicate that this is adapted from gsuitedevs/android-samples (on GitHub)
public class FileUploader
{
    private static final String TAG = "FILE_UPLOADER";
    private final Executor threadExecutor = Executors.newSingleThreadExecutor();

    public static final int REQUEST_READ_CODE = 0;
    public static final int REQUEST_WRITE_CODE = 1;
    //private final Drive driveService;
    private static FileUploader fileUploader;


    private FileUploader()
    {
    }

    public static FileUploader getInstance()
    {
        if(fileUploader != null)
            return fileUploader;

        fileUploader = new FileUploader();
        return fileUploader;


    }

    //TODO: Indicated this was adapted from Zybooks 6.2.5
    public boolean uploadAllSavedFiles() throws IOException
    {
        /*
        File fileStorageRootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File fileStorageDir = new File(fileStorageRootDir + "/FRC Scouting App");

        if(!fileStorageDir.exists())
            fileStorageDir.mkdirs();

        //Read file
        File fileToRead = new File(fileStorageDir, "debug_test_file.txt");

        if(fileToRead.exists())
        {
            FileInputStream inputStream = new FileInputStream(fileToRead);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                String line;

                while ((line = reader.readLine()) != null) {
                    Log.d(TAG, line);
                }
            } finally {
                reader.close();
            }

            return true;
        }
        else
        {
            //Write file
            try
            {

                File fileToWrite = new File(fileStorageDir, "debug_test_file.txt");
                FileOutputStream outputStream = new FileOutputStream(fileToWrite);
                PrintWriter writer = new PrintWriter(outputStream);

                writer.println("Test line 1");
                writer.println("Test line 2");
                writer.println("Test line 3");
                writer.close();
            }
            catch (IOException e)
            {
                Log.d(TAG, "Unable to write to file. Error details: " + e.toString());
            }
        }

        */
        return false;


    }

    //TODO: Indicated this was adapted from Zybooks 6.2.2
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
                //displayPermissionsDialog();
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
                //displayPermissionsDialog();
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{ readPermission }, REQUEST_WRITE_CODE);
            }
            return false;
        }
        return true;

    }

    public void saveFileLocally(File file)
    {
        try
        {
            File fileStorageRootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File fileStorageDir = new File(fileStorageRootDir + "/FRC Scouting App");

            if(!fileStorageDir.exists())
                fileStorageDir.mkdirs();

            File fileToWrite = new File(fileStorageDir, "debug_test_file.txt");
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            PrintWriter writer = new PrintWriter(outputStream);

        }
        catch (IOException e)
        {
            Log.d(TAG, "Error while trying to save file to external storage. Error details: " + e.toString());
        }

    }

    /*
    public Task<String> createFile(String filename)
    {
        return Tasks.call(threadExecutor, () -> {
            File metadata = new File()
                    .setParents(Collections.singletonList("root"))
                    .setMimeType("text/plain")
                    .setName(filename);

            File googleFile = driveService.files().create(metadata).execute();

            //TODO: display error to user using a toast and log the error

            return googleFile.getId();
        });
    }

    public Task<Void> saveFile(String fileID, String filename, String content)
    {

        return Tasks.call(threadExecutor, () -> {
            File metadata = new File().setName(filename);

            ByteArrayContent contentStream = ByteArrayContent.fromString("text/plain", content);

            driveService.files().update(fileID, metadata, contentStream).execute();
            return null;
        });
    }

    public Task<FileList> getFileList()
    {
        return Tasks.call(threadExecutor, () -> driveService.files().list().setSpaces("drive").execute());
    }

    public void logFileList()
    {
        FileList fileList = getFileList().getResult();
        Log.d(FileUploader.TAG, fileList.toString());
    }


     */


}

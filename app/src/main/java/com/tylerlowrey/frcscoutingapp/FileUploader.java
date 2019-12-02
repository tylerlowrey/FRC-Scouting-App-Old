package com.tylerlowrey.frcscoutingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.tylerlowrey.frcscoutingapp.MainActivity.REQUEST_CODE_SIGN_IN;

//TODO: Indicate that this is adapted from gsuitedevs/android-samples (on GitHub)
public class FileUploader
{
    private static final String TAG = "FILE_UPLOADER";
    private final Executor threadExecutor = Executors.newSingleThreadExecutor();

    public static final int REQUEST_READ_CODE = 0;
    public static final int REQUEST_WRITE_CODE = 1;
    private Drive googleDriveService;
    private GoogleSignInClient googleSignInClient;
    private static FileUploader fileUploader;


    private FileUploader()
    {
    }

    /**
     * Returns the singleton instance of the FileUpload (or creates one if there is not one already)
     * @return FileUploader - The singleton FileUploader that is shared across the app
     */
    public static FileUploader getInstance()
    {
        if(fileUploader != null)
            return fileUploader;

        fileUploader = new FileUploader();
        return fileUploader;


    }

    public boolean isSignedIntoDrive(Context context)
    {
        return !(GoogleSignIn.getLastSignedInAccount(context) == null);
    }

    public void signIntoDrive(Activity activity)
    {
        Log.d(TAG, "Requesting sign-in");

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .build();
        googleSignInClient = GoogleSignIn.getClient(activity, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        activity.startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    public void signOutOfDrive()
    {
        //Sign out of Google Account
        googleSignInClient.signOut();
        //Get rid of drive service
        googleDriveService = null;
    }

    //TODO: Indicated this was adapted from Zybooks 6.2.5
    public void uploadLocalFiles() throws IOException
    {
        Tasks.call(threadExecutor, () -> {

            java.io.File fileStorageRootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            java.io.File appStorageRootDir = new java.io.File(fileStorageRootDir, "FRC Scouting App");
            java.io.File appLocalDir = new java.io.File(appStorageRootDir, "local");

            java.io.File[] filesToUpload = appLocalDir.listFiles();

            for(java.io.File file : filesToUpload)
            {
                Task<String> fileID = createFile(file.getName());
                saveFile(fileID.getResult(), file.getName(), file.toString());
            }

            String localDirPath = appLocalDir.getCanonicalPath();
            String uploadedDirPath = fileStorageRootDir.getCanonicalPath();
            Path moveFiles = Files.move(Paths.get(localDirPath), Paths.get(uploadedDirPath));

            if (moveFiles != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        });
    }

    //TODO: Indicate this was adapted from Zybooks 6.2.2
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

        java.io.File fileStorageRootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        java.io.File appRootStorageDir = new java.io.File(fileStorageRootDir + "/FRC Scouting App");

        if(!appRootStorageDir.exists())
            appRootStorageDir.mkdirs();

        java.io.File appLocalStorageDir = new java.io.File(appRootStorageDir, "local");

        if(!appLocalStorageDir.exists())
            appLocalStorageDir.mkdirs();

        java.io.File fileToWrite = new java.io.File(appLocalStorageDir, filename);

        FileOutputStream outputStream = new FileOutputStream(fileToWrite);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite))) {
            writer.write(data);
        }

    }


    public Task<String> createFile(String filename)
    {
        return Tasks.call(threadExecutor, () -> {
            File metadata = new File()
                    .setParents(Collections.singletonList("root"))
                    .setMimeType("text/plain")
                    .setName(filename);

            File googleFile = googleDriveService.files().create(metadata).execute();

            return googleFile.getId();
        });
    }

    public Task<Void> saveFile(String fileID, String filename, String content)
    {
        return Tasks.call(threadExecutor, () -> {
            File metadata = new File().setName(filename);

            ByteArrayContent contentStream = ByteArrayContent.fromString("text/plain", content);

            googleDriveService.files().update(fileID, metadata, contentStream).execute();
            return null;
        });
    }

    public Task<FileList> getFileList()
    {
        return Tasks.call(threadExecutor, () -> googleDriveService.files().list().setSpaces("drive").execute());
    }

    public void logFileList()
    {
        FileList fileList = getFileList().getResult();
        Log.d(FileUploader.TAG, fileList.toString());
    }


    public void setDriveService(Drive googleDriveService)
    {
        this.googleDriveService = googleDriveService;
    }
}

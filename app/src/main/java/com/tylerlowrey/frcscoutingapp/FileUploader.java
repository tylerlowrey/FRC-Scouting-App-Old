package com.tylerlowrey.frcscoutingapp;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//TODO: Indicate that this is adapted from gsuitedevs/android-samples (on GitHub)
public class FileUploader
{
    private static final String TAG = "FILE_UPLOADER";
    private final Executor threadExecutor = Executors.newSingleThreadExecutor();
    private final Drive driveService;


    public FileUploader(Drive driveService)
    {
        this.driveService = driveService;
    }

    public void uploadFiles(String path)
    {

    }

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


}

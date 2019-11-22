package com.tylerlowrey.frcscoutingapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager
{
    /*
    private static final String LOGGING_TAG = "ConfigManager";
    //Adapted from https://stackoverflow.com/questions/5140539/android-config-file
    //TODO: Cite this code
    @Nullable
    public static String getConfigValue(Context context, String tag)
    {
        Resources resources = context.getResources();

        try
        {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(tag);
        }
        catch (Resources.NotFoundException e)
        {
            Log.e(LOGGING_TAG, "Unable to find the config file: " + e.getMessage());
        }
        catch (IOException e)
        {
            Log.e(LOGGING_TAG, "Failed to open config file.");
        }

        return null;
    }
    */
}

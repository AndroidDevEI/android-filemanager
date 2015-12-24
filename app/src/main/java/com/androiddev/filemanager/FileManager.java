package com.androiddev.filemanager;

import android.util.Log;

/**
 * Created by Emil Ivanov on 12/24/2015.
 */
public class FileManager {
    private static final String TAG = FileManager.class.getName();

    public static void writeFileExternalStorage(){
        Log.d(TAG, "This will write file in external storage");
    }

    public static void writeFileFromWebExternalStorage(){
        Log.d(TAG, "This will write file in external storage from web");
    }

    public static void readFileExternalStorage(){
        Log.d(TAG, "This will read file from internal storage");
    }
}

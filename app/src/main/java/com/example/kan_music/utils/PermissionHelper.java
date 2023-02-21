package com.example.kan_music.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {


    public static boolean checkPermission(Activity context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED;
    }

    public static void onProgressPermission(Activity context) throws Exception{
        if (checkPermission(context)){
            ActivityCompat.requestPermissions(context,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},999);
        }else {
            throw new SecurityException("No permission for app ");
        }
    }

}

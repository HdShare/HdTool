package com.hd.hdtool.utils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

public class PermissionUtils {

    public static void checkPermission(AppCompatActivity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            activity.startActivity(intent);
        }

        String[] permissions = {
                Manifest.permission.QUERY_ALL_PACKAGES,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        };
        for (String str : permissions) {
            if (activity.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(permissions, 101);
            }
        }

    }

}

package com.hd.hdtool.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.hd.hdtool.bean.AppInfo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppListUtils {

    private static ArrayList<AppInfo> appList;

    public static ArrayList<AppInfo> initAppList(Context context, ProgressDialog progressDialog) {
        appList = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(0);
        progressDialog.setMax(list.size() - 1);
        for (int i = 0; i < list.size(); i++) {
            progressDialog.setProgress(i + 1);
            PackageInfo packageInfo = list.get(i);
            AppInfo bean = new AppInfo();
            //bean.setUid(packageInfo.applicationInfo.uid);
            int flags = packageInfo.applicationInfo.flags;
            int flag_system = ApplicationInfo.FLAG_SYSTEM;
            if ((flags & flag_system) == 0) {
                bean.setAppSystem(false);
            } else if ((flags & flag_system) == 1) {
                bean.setAppSystem(true);
            }
            //bean.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
            bean.setAppIcon(packageManager.getDefaultActivityIcon());
            bean.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            bean.setAppPackageName(packageInfo.packageName);
            bean.setAppLastUpdateTime(packageInfo.lastUpdateTime);
            //bean.setAppApkDir(packageInfo.applicationInfo.sourceDir);//
            //bean.setAppDataDir(packageInfo.applicationInfo.dataDir);//
            bean.setAppVersionName(packageInfo.versionName);
            appList.add(bean);
        }
        CollatorAppName(appList);
        return appList;
    }

    public static ArrayList<AppInfo> getAppList() {
        return appList;
    }

    public static void CollatorAppName(ArrayList<AppInfo> List) {
        Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(List, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo appInfo1, AppInfo appInfo2) {
                String appName1 = appInfo1.getAppName();
                String appName2 = appInfo2.getAppName();
                return cmp.compare(appName1, appName2);
            }
        });
    }

    public static void CollatorApp_Hooking_And_Name(ArrayList<AppInfo> List) {
        Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(List, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo appInfo1, AppInfo appInfo2) {
                String appName1 = appInfo1.getAppName();
                String appName2 = appInfo2.getAppName();
                boolean appHooking1 = appInfo1.isAppHooking();
                boolean appHooking2 = appInfo2.isAppHooking();
                if (appHooking1 == appHooking2) {
                    return cmp.compare(appName1, appName2);
                } else {
                    return appHooking1 ? -1 : 1;
                }
            }
        });
    }

    public static ArrayList<AppInfo> getAppList(ArrayList<AppInfo> appList, boolean isSystemApp) {
        ArrayList<AppInfo> tempList = new ArrayList<>();
        for (int i = 0; i < appList.size(); i++) {
            if (appList.get(i).isAppSystem() == isSystemApp) {
                tempList.add(appList.get(i));
            }
        }
        return tempList;
    }

}

package com.hd.hdtool.bean;

import android.graphics.drawable.Drawable;

public class AppInfo {

    public int uid;
    public boolean appSystem;
    public Drawable appIcon;
    public String appName;
    public String appPackageName;
    public boolean appHooking;
    public long appLastUpdateTime;
    public String appApkDir;
    public String appDataDir;
    public String appVersionName;

    public AppInfo() {
        uid = 0;
        appSystem = false;
        appIcon = null;
        appName = "AppName";
        appPackageName = "AppPackageName";
        appHooking = false;
        appLastUpdateTime = 0;
        appApkDir = "/data/app/~/~/base.apk";
        appDataDir = "/data/user/0/~";
        appVersionName = "1.0";
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public boolean isAppSystem() {
        return appSystem;
    }

    public void setAppSystem(boolean appSystem) {
        this.appSystem = appSystem;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public boolean isAppHooking() {
        return appHooking;
    }

    public void setAppHooking(boolean appHooking) {
        this.appHooking = appHooking;
    }

    public long getAppLastUpdateTime() {
        return appLastUpdateTime;
    }

    public void setAppLastUpdateTime(long appLastUpdateTime) {
        this.appLastUpdateTime = appLastUpdateTime;
    }

    public String getAppApkDir() {
        return appApkDir;
    }

    public void setAppApkDir(String appApkDir) {
        this.appApkDir = appApkDir;
    }

    public String getAppDataDir() {
        return appDataDir;
    }

    public void setAppDataDir(String appDataDir) {
        this.appDataDir = appDataDir;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

}
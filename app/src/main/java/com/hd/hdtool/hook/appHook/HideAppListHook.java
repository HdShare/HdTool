package com.hd.hdtool.hook.appHook;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import com.hd.hdtool.utils.HdUtils;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HideAppListHook {

    public static void hideApp(String pkg) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetHideAppList");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    "android.app.ApplicationPackageManager",
                    ClassLoader.getSystemClassLoader(),
                    "getPackageInfo",
                    String.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String packageName = (String) param.args[0];
                            if (packageName.contains(pkg)) {
                                param.args[0] = "hd";
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_InstallApp>> " + packageName);
                                }
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    "android.app.ApplicationPackageManager",
                    ClassLoader.getSystemClassLoader(),
                    "getApplicationInfo",
                    String.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String packageName = (String) param.args[0];
                            if (packageName.contains(pkg)) {
                                param.args[0] = "hd";
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_InstallApp>> " + packageName);
                                }
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    "android.app.ApplicationPackageManager",
                    ClassLoader.getSystemClassLoader(),
                    "getInstalledPackages",
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            List list = (List) param.getResult();
                            ArrayList arrayList = new ArrayList();
                            for (int i = 0; i < list.size(); i++) {
                                PackageInfo packageInfo = (PackageInfo) list.get(i);
                                if (!packageInfo.packageName.contains(pkg)) {
                                    arrayList.add(packageInfo);
                                } else {
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end_Installed_Packages>> " + packageInfo.packageName);
                                    }
                                }
                            }
                            param.setResult(arrayList);
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    "android.app.ApplicationPackageManager",
                    ClassLoader.getSystemClassLoader(),
                    "getInstalledApplications",
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            List list = (List) param.getResult();
                            ArrayList arrayList = new ArrayList();
                            for (int i = 0; i < list.size(); i++) {
                                ApplicationInfo applicationInfo = (ApplicationInfo) list.get(i);
                                if (!applicationInfo.packageName.contains(pkg)) {
                                    arrayList.add(applicationInfo);
                                } else {
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end_Installed_Applications>> " + applicationInfo.packageName);
                                    }
                                }
                            }
                            param.setResult(arrayList);
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

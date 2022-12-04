package com.hd.hdtool.hook.versionHook;

import android.content.pm.PackageInfo;
import android.text.TextUtils;

import com.hd.hdtool.utils.FileJsonUtils;
import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class VersionHook {

    public static void hook(XC_LoadPackage.LoadPackageParam loadPackageParam, String setFileName) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetAnalogVersion");
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
                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String packageName = (String) param.args[0];
                            if (packageName.equals(loadPackageParam.packageName)) {
                                PackageInfo packageInfo = (PackageInfo) param.getResult();

                                String analogVersionCode = FileJsonUtils.readJson(setFileName, "analogVersionCode");
                                String analogVersionName = FileJsonUtils.readJson(setFileName, "analogVersionName");

                                if (FileJsonUtils.readJson(setFileName, "swAppSetAnalogVersionCode").equals("true") && !TextUtils.isEmpty(analogVersionCode)) {
                                    packageInfo.versionCode = Integer.parseInt(analogVersionCode);
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetAnalogVersionCode" + " | " + analogVersionCode);
                                    }
                                }
                                if (FileJsonUtils.readJson(setFileName, "swAppSetAnalogVersionName").equals("true") && !TextUtils.isEmpty(analogVersionName)) {
                                    packageInfo.versionName = analogVersionName;
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetAnalogVersionName" + " | " + analogVersionName);
                                    }
                                }
                                param.setResult(packageInfo);

                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

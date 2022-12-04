package com.hd.hdtool.hook.moreAppHook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class XamarinHook {

    public static void standardMode1(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetXamarinStandardMode1");
        }
        try {
            Class<?> hookclass = classLoader.loadClass("armadillo.c6");
            XposedHelpers.findAndHookMethod(
                    hookclass,
                    "a",
                    Activity.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetXamarinStandardMode1");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void standardMode2(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetXamarinStandardMode2");
        }
        try {
            Class<?> hookclass = classLoader.loadClass("armadillo.Global");
            XposedHelpers.findAndHookMethod(
                    hookclass,
                    "a",
                    Activity.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetXamarinStandardMode2");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void standardMode3(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetXamarinStandardMode3");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("armadillo.Model.AppInfo$data"),
                    "getAuthmode",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            param.setResult(0);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetXamarinStandardMode3_getAuthmode");
                            }
                        }
                    }
            );
            Class<?> hookclass = classLoader.loadClass("armadillo.VerifyActivity");
            XposedHelpers.findAndHookMethod(
                    hookclass,
                    "onActivityCreated",
                    Activity.class,
                    Bundle.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetXamarinStandardMode3_onActivityCreated");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void qurikMode(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetXamarinQurikMode");
        }
        try {
            Class<?> hookclass = classLoader.loadClass("armadillo.CloudApp");
            XposedHelpers.findAndHookMethod(
                    hookclass,
                    "a",
                    Activity.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetXamarinQurikMode");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

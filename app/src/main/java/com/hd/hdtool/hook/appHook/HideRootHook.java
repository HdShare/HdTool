package com.hd.hdtool.hook.appHook;

import static de.robv.android.xposed.XposedHelpers.findConstructorExact;

import android.content.ContentResolver;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;

import com.hd.hdtool.utils.HdUtils;

import java.io.File;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XCallback;

public class HideRootHook {

    public static void hook() {
        XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetHideRoot");
        try {
            XposedBridge.hookAllMethods(
                    System.class,
                    "getProperty",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String property = (String) param.args[0];
                            if (property != null && (property.equals("ro.secure") || property.equals("ro.debuggable"))) {
                                param.setResult("1");
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_getProperty>> " + property);
                                }
                            }
                        }
                    }
            );
            XposedBridge.hookMethod(
                    findConstructorExact(File.class, String.class),
                    new XC_MethodHook(XCallback.PRIORITY_HIGHEST) {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            String result = (String) param.args[0];
                            if (result != null) {
                                boolean contains_su = result.toLowerCase().endsWith("/su");
                                boolean error_path = (!result.contains("/data/user/0/") && !result.contains("/data/app/"));
                                if (contains_su && error_path) {
                                    param.args[0] = result.toLowerCase().replaceAll("su", "hd");
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end_su_exists>> " + result);
                                    }
                                }
                            }
                        }
                    }
            );
            XposedBridge.hookMethod(
                    findConstructorExact(java.io.File.class, String.class),
                    new XC_MethodHook(XCallback.PRIORITY_HIGHEST) {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            String result = (String) param.args[0];
                            if (result != null) {
                                boolean contains_magisk = result.toLowerCase().contains("/busybox");
                                if (contains_magisk) {
                                    param.args[0] = result.toLowerCase().replaceAll("busybox", "hd");
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end_busybox_exists>> " + result);
                                    }
                                }
                            }
                        }
                    }
            );
            XposedBridge.hookMethod(
                    findConstructorExact(java.io.File.class, String.class),
                    new XC_MethodHook(XCallback.PRIORITY_HIGHEST) {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            String result = (String) param.args[0];
                            if (result != null) {
                                boolean contains_magisk = result.toLowerCase().contains("magisk");
                                if (contains_magisk) {
                                    param.args[0] = result.toLowerCase().replaceAll("magisk", "hd");
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end_magisk_exists>> " + result);
                                    }
                                }
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    Runtime.class,
                    "exec",
                    String[].class,
                    String[].class,
                    File.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String[] cmdarray = (String[]) param.args[0];
                            StringBuilder sb = new StringBuilder();
                            for (String str : cmdarray) {
                                sb.append(str + "");
                            }
                            String cmd = sb.toString();
                            if (cmd.contains("su") || cmd.contains("busybox")) {
                                String _cmd = cmd.replaceAll("su", "hd").replaceAll("busybox", "hd");
                                String[] tempArr = new String[]{_cmd};
                                param.args[0] = tempArr;
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_shell_su>> " + cmd);
                                }
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    Debug.class,
                    "isDebuggerConnected",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            param.setResult(false);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end_isDebuggerConnected>> " + "false");
                            }
                        }
                    }
            );
            if (!Build.TAGS.equals("release-keys")) {
                XposedHelpers.setStaticObjectField(android.os.Build.class, "TAGS", "release-keys");
                if (HdUtils.isDebug()) {
                    XposedBridge.log(HdUtils.获取TAG() + " >>end_TAGS>> " + "release-keys");
                }
            }
            XposedHelpers.findAndHookMethod(
                    "android.os.SystemProperties",
                    ClassLoader.getSystemClassLoader(),
                    "get",
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            if (((String) param.args[0]).equals("ro.build.selinux")) {
                                param.setResult("1");
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_SELinux>> " + "1");
                                }
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    Settings.Global.class,
                    "getInt",
                    ContentResolver.class,
                    String.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            String setting = (String) param.args[1];
                            if (setting != null && Settings.Global.ADB_ENABLED.equals(setting)) {
                                param.setResult(0);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_ADB>> " + "0");
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

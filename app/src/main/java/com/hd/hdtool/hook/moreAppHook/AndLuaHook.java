package com.hd.hdtool.hook.moreAppHook;

import android.app.AlertDialog;
import android.content.Context;

import com.hd.hdtool.utils.FileJsonUtils;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;

import java.lang.reflect.Array;
import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class AndLuaHook {

    private static String result = "";

    public static void callMethodHook(Context context, String setFolderPath, String fileName) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetAndluaCallMethod");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("com.luajava.LuaJavaAPI"),
                    "callMethod",
                    long.class,
                    Object.class,
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Object 方法参数 = param.args[1];
                            String 方法类 = param.args[2].toString();
                            if (方法类.contains("AlertDialog")) {

                                if (FileJsonUtils.readJson(fileName, "swAppSetAndluaAlertDialog").equals("true")) {
                                    if (方法参数 instanceof AlertDialog) {
                                        AlertDialog alertDialog = (AlertDialog) 方法参数;
                                        alertDialog.setCanceledOnTouchOutside(true);
                                    } else if (方法参数 instanceof AlertDialog.Builder) {
                                        AlertDialog.Builder builder = (AlertDialog.Builder) 方法参数;
                                        builder.setCancelable(true);
                                    }
                                }

                            } else if (方法类.contains("File")) {

                            } else if (方法类.contains("Button")) {

                            } else if (方法类.contains("androlua")) {

                            }
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_CallMethod.lua", " -> " + 方法类);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetAndluaCallMethod");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void httpTaskHook(Context context, String setFolderPath) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetAndluaHttpTask");
        }
        try {
            XposedHelpers.findAndHookConstructor(
                    classLoader.loadClass("com.androlua.Http$HttpTask"),
                    String.class,
                    String.class,
                    String.class,
                    String.class,
                    HashMap.class,
                    XposedHelpers.findClass("com.luajava.LuaObject", classLoader),
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            result = " -> " + " url " + " -> " + param.args[0] + "\n";
                            result += " -> " + " method " + " -> " + param.args[1] + "\n";
                            result += " -> " + " cookie " + " -> " + param.args[2] + "\n";
                            result += " -> " + " charset " + " -> " + param.args[3] + "\n";
                            result += " -> " + " header " + " -> " + param.args[4] + "\n";
                            result += " -> " + " callback " + " -> " + param.args[5].getClass().getName() + "\n";
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_HttpTask.lua", result);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetAndluaHttpTask");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void runFuncHook(Context context, String setFolderPath, String functionName) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetAndluaRunFunc");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("com.androlua.LuaActivity"),
                    "runFunc",
                    String.class,
                    Array.newInstance(Object.class, 2).getClass(),
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String 方法名 = param.args[0].toString();
                            result = " -> " + 方法名;
                            if (方法名.contains(functionName)) {
                                param.setResult(null);
                                result += " -> 已拦截";
                            }
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_RunFunc.lua", result);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetAndluaRunFunc");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

package com.hd.hdtool.hook.moreAppHook;

import android.content.Context;

import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;

import java.util.regex.Pattern;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class GameGuardianHook {

    public static void moreFunction(Context context, String setFolderPath) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetGameGuardianMoreFunction");
        }
        try {
            XposedHelpers.findAndHookConstructor(
                    classLoader.loadClass("android.ext.Script$ApiFunction$1"),
                    XposedHelpers.findClass("android.ext.Script$ApiFunction", classLoader),
                    XposedHelpers.findClass("luaj.ap", classLoader),
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Object ApiFunction = param.args[0];
                            Object varargs = param.args[1];
                            String data = "==========================================\n";
                            data += ApiFunction.toString() + "\n";
                            if ((boolean) XposedHelpers.callMethod(varargs, "i", 1)) {
                                data += varargs.toString() + "\n";
                            }
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_GG_moreFunction.lua", data);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetGameGuardianMoreFunction");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void httpFunction(Context context, String setFolderPath) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetGameGuardianHttpFunction");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("android.ext.Script$makeRequest"),
                    "b",
                    XposedHelpers.findClass("luaj.ap", classLoader),
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Object varargs = param.args[0];
                            String data = "";
                            data += "gg.makeRequest(" + XposedHelpers.callMethod(varargs, "r", 1) + ")";
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_GG_httpFunction.lua", data);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetGameGuardianHttpFunction");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void mainFunction(Context context, String setFolderPath) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetGameGuardianMainFunction");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("android.ext.Script$setRanges"),
                    "d",
                    XposedHelpers.findClass("luaj.ap", classLoader),
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Object varargs = param.args[0];
                            String data = "";
                            data += "gg.setRanges(" + XposedHelpers.callMethod(varargs, "o", 1) + ")";
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_GG_mainFunction.lua", data);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "Script$setRanges");
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("android.ext.Script$searchNumber"),
                    "d",
                    XposedHelpers.findClass("luaj.ap", classLoader),
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Object varargs = param.args[0];
                            String data = "";
                            if ((boolean) XposedHelpers.callMethod(varargs, "i", 1)) {
                                String str_number = (String) XposedHelpers.callMethod(varargs, "r", 1);
                                String str_type = (String) XposedHelpers.callMethod(varargs, "r", 2);
                                if (Pattern.matches("^[a-zA-Z0-9.:;~-]*$", str_number)) {
                                    data += "gg.searchNumber(\"" + str_number;
                                    data += "\"," + str_type + ")";
                                } else {
                                    data += "跳过一处异常搜索";
                                }
                            }
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_GG_mainFunction.lua", data);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "Script$searchNumber");
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("android.ext.Script$editAll"),
                    "b",
                    XposedHelpers.findClass("luaj.ap", classLoader),
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Object varargs = param.args[0];
                            String data = "";
                            if ((boolean) XposedHelpers.callMethod(varargs, "i", 1)) {
                                String str_number = (String) XposedHelpers.callMethod(varargs, "r", 1);
                                String str_type = (String) XposedHelpers.callMethod(varargs, "o", 2);
                                if (Pattern.matches("^[a-zA-Z0-9.:;~-]*$", str_number)) {
                                    data += "gg.editAll(\"" + str_number;
                                    data += "\"," + str_type + ")";
                                } else {
                                    data += "跳过一处异常修改";
                                }
                            }
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_GG_mainFunction.lua", data);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "Script$editAll");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

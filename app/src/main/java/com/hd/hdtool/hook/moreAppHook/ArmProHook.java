package com.hd.hdtool.hook.moreAppHook;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.hd.hdtool.utils.FileJsonUtils;
import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class ArmProHook {

    private static String tempSelfClass = "";

    public static void findClass(Context context, String setFileName) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetArmProFindClass");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    ClassLoader.class,
                    "loadClass",
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            if (param.hasThrowable()) return;
                            if (param.args.length != 1) return;
                            Class<?> loadClazz = (Class<?>) param.getResult();
                            String name = loadClazz.getName();
                            if (name.endsWith(".e")) {
                                tempSelfClass = name.substring(0, name.indexOf(".e"));
                            }
                            if (!TextUtils.isEmpty(tempSelfClass)) {
                                FileJsonUtils.writeJson(setFileName, "ArmProFindClass", tempSelfClass);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetArmProFindClass" + " " + "findClass -> " + tempSelfClass);
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void globalMode1(Context context, String setFileName) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetArmProGlobalMode1");
        }
        try {
            String ArmProFindClass = FileJsonUtils.readJson(setFileName, "ArmProFindClass");
            Class<?> hookclass = classLoader.loadClass(ArmProFindClass + ".rF");
            XposedHelpers.findAndHookMethod(
                    hookclass,
                    "b",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetArmProGlobalMode1");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void globalMode2(Context context, String setFileName) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetArmProGlobalMode2");
        }
        try {
            String ArmProFindClass = FileJsonUtils.readJson(setFileName, "ArmProFindClass");
            Class<?> hookclass = classLoader.loadClass(ArmProFindClass + ".f");
            XposedHelpers.findAndHookMethod(
                    hookclass,
                    "Call",
                    Activity.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetArmProGlobalMode2");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

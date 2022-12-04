package com.hd.hdtool.hook.windowHook;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class CancelWindowHook {

    public static void hook() {
        XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetCancelWindow");
        try {
            XposedBridge.hookAllMethods(
                    Activity.class,
                    "setFinishOnTouchOutside",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.args[0] = true;
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "setFinishOnTouchOutside");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    android.app.AlertDialog.Builder.class,
                    "setCancelable",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.args[0] = true;
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "setCancelable");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    androidx.appcompat.app.AlertDialog.Builder.class,
                    "setCancelable",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.args[0] = true;
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "setCancelable");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    Dialog.class,
                    "setCancelable",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.args[0] = true;
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "setCancelable");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    Dialog.class,
                    "setCanceledOnTouchOutside",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.args[0] = true;
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "setCanceledOnTouchOutside");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    Window.class,
                    "setCloseOnTouchOutside",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.args[0] = true;
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "setCloseOnTouchOutside");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    Window.class,
                    "setCloseOnTouchOutsideIfNotSet",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.args[0] = true;
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "setCloseOnTouchOutsideIfNotSet");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

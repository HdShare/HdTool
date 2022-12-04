package com.hd.hdtool.hook;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.hd.hdtool.hook.adsHook.AdsHook;
import com.hd.hdtool.hook.moreAppHook.AndLuaHook;
import com.hd.hdtool.hook.moreAppHook.ArmProHook;
import com.hd.hdtool.hook.moreAppHook.CloudInjectHook;
import com.hd.hdtool.hook.moreAppHook.GameGuardianHook;
import com.hd.hdtool.hook.moreAppHook.XamarinHook;
import com.hd.hdtool.utils.FileJsonUtils;
import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class ProtectHook {

    public static void protectHook(Context context, ClassLoader classLoader, String setFolderPath, String setFileName) {

        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>loading==100%>> " + "get context and classLoader");
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetHdDebug").equals("true")) {
            TestHook.hook(classLoader, setFileName);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetKillADsModeByteDance").equals("true")) {
            AdsHook.hookByteDance(context);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetKillADsModeTencent").equals("true")) {
            AdsHook.hookTencent(context);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetKillADsModeMimo").equals("true")) {
            AdsHook.hookMimo(context);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetKillSharePage").equals("true")) {
            if (HdUtils.isDebug()) {
                XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetKillSharePage");
            }
            try {
                Class<?> hookclass = classLoader.loadClass("com.tencent.a.SetupInfoActivity");
                XposedHelpers.findAndHookMethod(
                        hookclass,
                        "onCreate",
                        Bundle.class,
                        new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                XposedHelpers.callMethod(param.thisObject, "gotoNext");
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetKillSharePage");
                                }
                            }
                        }
                );
            } catch (Exception e) {
            }
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetKillTpACE").equals("true")) {
            if (HdUtils.isDebug()) {
                XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetKillTpACE");
            }
            try {
                Class<?> hookclass = classLoader.loadClass("com.tencent.tp.TssJavaMethod");
                XposedHelpers.findAndHookMethod(
                        hookclass,
                        "sendCmdEx",
                        String.class,
                        new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                param.setResult(-1);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetKillTpACE");
                                }
                            }
                        }
                );
            } catch (Exception e) {
            }
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetCloudInject").equals("true")) {
            CloudInjectHook.hook(context);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetArmProFindClass").equals("true")) {
            ArmProHook.findClass(context, setFileName);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetArmProGlobalMode1").equals("true")) {
            ArmProHook.globalMode1(context, setFileName);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetArmProGlobalMode2").equals("true")) {
            ArmProHook.globalMode2(context, setFileName);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetXamarinStandardMode1").equals("true")) {
            XamarinHook.standardMode1(context);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetXamarinStandardMode2").equals("true")) {
            XamarinHook.standardMode2(context);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetXamarinStandardMode3").equals("true")) {
            XamarinHook.standardMode3(context);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetXamarinQurikMode").equals("true")) {
            XamarinHook.qurikMode(context);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetAndluaCallMethod").equals("true")) {
            AndLuaHook.callMethodHook(context, setFolderPath, setFileName);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetAndluaHttpTask").equals("true")) {
            AndLuaHook.httpTaskHook(context, setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetAndluaRunFunc").equals("true")) {
            AndLuaHook.runFuncHook(context, setFolderPath, "检测");
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetGameGuardianMoreFunction").equals("true")) {
            GameGuardianHook.moreFunction(context, setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetGameGuardianHttpFunction").equals("true")) {
            GameGuardianHook.httpFunction(context, setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetGameGuardianMainFunction").equals("true")) {
            GameGuardianHook.mainFunction(context, setFolderPath);
        }

    }

}

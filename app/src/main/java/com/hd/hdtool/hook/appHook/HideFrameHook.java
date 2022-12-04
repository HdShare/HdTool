package com.hd.hdtool.hook.appHook;

import static de.robv.android.xposed.XposedHelpers.findConstructorExact;

import com.hd.hdtool.utils.HdUtils;

import java.io.BufferedReader;
import java.io.File;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XCallback;

public class HideFrameHook {

    public static void hook() {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetHideFrame");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    BufferedReader.class,
                    "readLine",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String line = (String) param.getResult();
                            if (line != null && (line.contains("frida_server") || line.contains("hijack") || line.contains("libstrmon.so"))) {
                                if (!line.contains(HdUtils.获取TAG())) {//不加这一句会与XposedBridge.log无限循环冲突，排除包含（Hd_Tool）的日志
                                    param.setResult("");
                                    new File("").lastModified();
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end_readLine>> " + line);
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
                            if (result != null && (result.contains("frida_server") || result.contains("hijack") || result.contains("libstrmon.so"))) {
                                param.args[0] = result.replaceAll("frida_server", "hd")
                                        .replaceAll("hijack", "hd")
                                        .replaceAll("libstrmon.so", "hd");
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_file>> " + result);
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

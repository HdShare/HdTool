package com.hd.hdtool.hook.appHook;

import com.hd.hdtool.utils.HdUtils;

import java.io.BufferedReader;
import java.io.File;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HideCydiaSubstrateHook {

    public static void hook() {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetHideCydiaSubstrateHook");
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
                            if (line != null && line.contains("com.saurik.substrate")) {
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
            XposedHelpers.findAndHookMethod(
                    StackTraceElement.class,
                    "getClassName",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String className = (String) param.getResult();
                            //XposedBridge.log(folderName + " >>>>>>>>>>>stack_getClass>> " + className);
                            if (className != null && (className.contains("substrate") || className.contains("ZygoteInit"))) {
                                param.setResult("");
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_stack_getClass>> " + className);
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

package com.hd.hdtool.hook.appHook;

import android.content.ComponentName;

import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HideMagiskHook {

    public static void hook() {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetHideMagisk");
        }
        try {
            XposedHelpers.findAndHookConstructor(
                    ComponentName.class,
                    String.class,
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String pkg = (String) param.args[0];
                            String cls = (String) param.args[1];
                            if (pkg.contains("com.topjohnwu.magisk")) {
                                param.args[0] = "hd";
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_Intent>> " + pkg + " | " + cls);
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

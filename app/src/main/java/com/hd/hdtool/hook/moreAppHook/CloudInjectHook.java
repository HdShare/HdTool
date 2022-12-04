package com.hd.hdtool.hook.moreAppHook;

import android.content.Context;

import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class CloudInjectHook {

    public static void hook(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetCloudInject");
        }
        try {
            Class<?> hookclass = classLoader.loadClass("rxc.internal.operators.OperatorObserveOn$ObserveOnSubscriber");
            XposedHelpers.findAndHookMethod(
                    hookclass,
                    "call",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetCloudInject");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

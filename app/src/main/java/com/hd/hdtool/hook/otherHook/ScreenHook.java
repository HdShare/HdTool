package com.hd.hdtool.hook.otherHook;

import android.view.Window;

import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class ScreenHook {

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetScreen");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    Window.class,
                    "setFlags",
                    int.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            int flag = (int) param.args[0];
                            int setFlag = flag & (-8193);
                            param.args[0] = setFlag;
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetScreen" + " | " + flag + " | " + setFlag);
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

package com.hd.hdtool.hook.networkHook;

import com.hd.hdtool.utils.HdUtils;

import java.net.NetworkInterface;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HideVpnHook {

    public static void hook() {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetHideVpn");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    NetworkInterface.class,
                    "getName",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult("rmnet_data1");
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetHideVpn");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

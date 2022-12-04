package com.hd.hdtool.hook.networkHook;

import com.hd.hdtool.utils.HdUtils;

import java.net.Proxy;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;

public class HideWifiProxyHook {

    public static void hook() {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetHideWifiProxy");
        }
        try {
            XposedBridge.hookAllMethods(
                    System.class,
                    "getProperty",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String property = (String) param.args[0];
                            if (property.equals("http.proxyHost") || property.equals("http.proxyPort")) {
                                param.setResult(property.equals("http.proxyHost") ? "" : -1);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetHideWifiProxy");
                                }
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(Proxy.class, "getHost", new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                            return "";
                        }
                    }
            );
            XposedBridge.hookAllMethods(Proxy.class, "getPort", new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                            return -1;
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

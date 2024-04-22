package com.hd.hdtool.hook.algorithmHook;

import android.util.Base64;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.StringUtils;
import com.hd.hdtool.utils.TimeUtils;

import java.security.Key;

import javax.crypto.Mac;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HmacHook {

    private static String keylog = "";
    private static String log = "";

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetAlgorithmHmac");
        }
        try {
            XposedBridge.hookAllMethods(
                    Mac.class,
                    "init",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);

                            Key key = (Key) param.args[0];
                            keylog = "加密密钥 " + "\n" + new String(key.getEncoded()) + "\n";
                            keylog += "加密密钥 (hex) " + "\n" + StringUtils.toHexString(key.getEncoded(), false, false) + "\n";
                            keylog += "加密密钥 (base64) " + "\n" + Base64.encodeToString(key.getEncoded(), 0) + "\n";

                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    Mac.class,
                    "update",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);

                            byte[] params = (byte[]) param.args[0];
                            int offset = 0;
                            int len = params.length;

                            if (param.args.length == 3) {
                                offset = ((Integer) param.args[1]).intValue();
                                len = ((Integer) param.args[2]).intValue();
                            }

                            byte[] keybate = new byte[len];
                            System.arraycopy(params, offset, keybate, 0, len);

                            log = "加密数据 " + "\n" + new String(keybate) + "\n";
                            log += "加密数据 (hex) " + "\n" + StringUtils.toHexString(keybate, false, false) + "\n";
                            log += "加密数据 (base64) " + "\n" + Base64.encodeToString(keybate, 0) + "\n";

                        }
                    }
            );

            XposedBridge.hookAllMethods(
                    Mac.class,
                    "doFinal",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "\n";
                            data += "加密类型  " + ((Mac) param.thisObject).getAlgorithm() + "\n";
                            data += "\n";
                            data += keylog;

                            if (param.args.length >= 1) {
                                byte[] params = (byte[]) param.args[0];
                                data += "加密数据 " + "\n" + new String(params) + "\n";
                                data += "加密数据 (hex) " + "\n" + StringUtils.toHexString(params, false, false) + "\n";
                                data += "加密数据 (base64) " + "\n" + Base64.encodeToString(params, 0) + "\n";
                            } else {
                                data += log;
                            }

                            byte[] res = (byte[]) param.getResult();
                            data += "加密结果 " + "\n" + new String(res) + "\n";
                            data += "加密结果 (hex重点) " + "\n" + StringUtils.toHexString(res, false, false) + "\n";
                            data += "加密结果 (base64) " + "\n" + Base64.encodeToString(res, 0) + "\n";
                            data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";

                            if (!ToolHook.printStackTrace().contains("xposed") && !ToolHook.printStackTrace().contains(HdUtils.getPackageName())) {
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_Hmac.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetAlgorithmHmac");
                                }
                            }

                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

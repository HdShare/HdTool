package com.hd.hdtool.hook.algorithmHook;

import android.util.Base64;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.StringUtils;
import com.hd.hdtool.utils.TimeUtils;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class AesDesRsaHook {

    private static String mode = "";
    private static String keylog = "";
    private static String log = "";

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetAlgorithmAesDesRsa");
        }
        try {
            XposedBridge.hookAllMethods(
                    Cipher.class,
                    "init",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);

                            if (param.args.length == 3) {
                                int opmode = (int) param.args[0];
                                if (opmode == Cipher.ENCRYPT_MODE) {
                                    mode = "加密";
                                } else if (opmode == Cipher.DECRYPT_MODE) {
                                    mode = "解密";
                                } else {
                                    mode = "未知";
                                }

                                Key key = (Key) param.args[1];
                                keylog = mode + "密钥 " + "\n" + new String(key.getEncoded()) + "\n";
                                keylog += mode + "密钥 (hex) " + "\n" + StringUtils.toHexString(key.getEncoded(), false, false) + "\n";
                                keylog += mode + "密钥 (base64) " + "\n" + Base64.encodeToString(key.getEncoded(), 0) + "\n";

                                if (param.args[2] instanceof AlgorithmParameterSpec) {
                                    AlgorithmParameterSpec parameterSpec = (AlgorithmParameterSpec) param.args[2];
                                    byte[] bytes_IV = (byte[]) XposedHelpers.callMethod(parameterSpec, "getIV", new Object[0]);
                                    keylog += mode + "向量 " + "\n" + new String(bytes_IV) + "\n";
                                    keylog += mode + "向量 (hex) " + "\n" + StringUtils.toHexString(bytes_IV, false, false) + "\n";
                                    keylog += mode + "向量 (base64) " + "\n" + Base64.encodeToString(bytes_IV, 0) + "\n";
                                }
                            }

                        }

                    }
            );
            XposedBridge.hookAllMethods(
                    Cipher.class,
                    "update",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            byte[] params = (byte[]) param.args[0];
                            int offset = 0;
                            int len = params.length;

                            if (param.args.length == 3) {
                                offset = ((Integer) param.args[1]).intValue();
                                len = ((Integer) param.args[2]).intValue();
                            }

                            byte[] keybate = new byte[len];
                            System.arraycopy(params, offset, keybate, 0, len);

                            log += mode + "数据 " + "\n" + new String(keybate) + "\n";
                            log += mode + "数据 (hex) " + "\n" + StringUtils.toHexString(keybate, false, false) + "\n";
                            log += mode + "数据 (base64) " + "\n" + Base64.encodeToString(keybate, 0) + "\n";

                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    Cipher.class,
                    "doFinal",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "\n";
                            data += mode + "类型  " + ((Cipher) param.thisObject).getAlgorithm() + "\n";
                            data += "\n";
                            data += keylog;

                            byte[] params = (byte[]) param.args[0];
                            int offset = 0;
                            int len = 0;

                            if (param.args.length == 3) {
                                offset = ((Integer) param.args[1]).intValue();
                                len = ((Integer) param.args[2]).intValue();
                            } else if (param.args.length == 1) {
                                offset = 0;
                                len = params.length;
                            }

                            byte[] keybate = new byte[len];
                            System.arraycopy(params, offset, keybate, 0, len);

                            if (len > 0) {
                                data += mode + "数据 " + "\n" + new String(keybate) + "\n";
                                data += mode + "数据 (hex) " + "\n" + StringUtils.toHexString(keybate, false, false) + "\n";
                                data += mode + "数据 (base64) " + "\n" + Base64.encodeToString(keybate, 0) + "\n";
                            } else {
                                data += log;
                            }

                            if (param.getResult() instanceof Integer) {
                                Integer res = (Integer) param.getResult();
                                data += "Test" + mode + "长度 " + "\n" + res + "\n";
                            } else {
                                byte[] res = (byte[]) param.getResult();
                                data += mode + "结果 " + "\n" + new String(res) + "\n";
                                data += mode + "结果 (hex) " + "\n" + StringUtils.toHexString(res, false, false) + "\n";
                                data += mode + "结果 (base64) " + "\n" + Base64.encodeToString(res, 0) + "\n";
                            }
                            data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";

                            if (!ToolHook.printStackTrace().contains("xposed") && !ToolHook.printStackTrace().contains(HdUtils.getPackageName())) {
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_AES_DES_RSA.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetAlgorithmAesDesRsa");
                                }
                            }

                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

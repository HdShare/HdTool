package com.hd.hdtool.hook.algorithmHook;

import android.util.Base64;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.StringUtils;
import com.hd.hdtool.utils.TimeUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class Base64Hook {

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetAlgorithmBase64");
        }
        try {
            XposedBridge.hookAllMethods(
                    Base64.class,
                    "encode",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "\n";
                            data += "加密类型  " + "Base64" + "\n";
                            data += "\n";

                            byte[] params = (byte[]) param.args[0];
                            int offset = 0;
                            int len = params.length;

                            if (param.args.length == 4) {
                                offset = ((Integer) param.args[1]).intValue();
                                len = ((Integer) param.args[2]).intValue();

                                byte[] keybate = new byte[len];
                                System.arraycopy(params, offset, keybate, 0, len);

                                data += "加密数据 " + "\n" + new String(keybate) + "\n";
                                data += "加密数据 (hex) " + "\n" + StringUtils.toHexString(keybate, false, false) + "\n";
                                data += "\n";

                                byte[] res = (byte[]) param.getResult();
                                data += "加密结果 " + "\n" + new String(res) + "\n";
                                data += "加密结果 (hex) " + "\n" + StringUtils.toHexString(res, false, false) + "\n";
                                data += "\n";

                                //data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";//如果开启后打不开被Hook程序，请注释掉堆栈这部分代码

                                //if (!ToolHook.printStackTrace().contains("xposed") && !ToolHook.printStackTrace().contains(HdUtils.getPackageName())) {
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_Base64_encode.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetAlgorithmBase64");
                                }
                                //}

                            }

                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    Base64.class,
                    "decode",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "\n";
                            data += "解密类型  " + "Base64" + "\n";
                            data += "\n";

                            if (param.args.length == 4) {

                                byte[] params = (byte[]) param.args[0];

                                if (params.length == 0) return;

                                int offset = ((Integer) param.args[1]).intValue();
                                int len = ((Integer) param.args[2]).intValue();

                                byte[] keybate = new byte[len];
                                System.arraycopy(params, offset, keybate, 0, len);

                                data += "解密数据 " + "\n" + new String(keybate) + "\n";
                                data += "解密数据 (hex) " + "\n" + StringUtils.toHexString(keybate, false, false) + "\n";
                                data += "\n";

                                byte[] res = (byte[]) param.getResult();
                                data += "解密结果 " + "\n" + new String(res) + "\n";
                                data += "解密结果 (hex) " + "\n" + StringUtils.toHexString(res, false, false) + "\n";
                                data += "\n";

                                //data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";//如果开启后打不开被Hook程序，请注释掉堆栈这部分代码

                                //if (!ToolHook.printStackTrace().contains("xposed") && !ToolHook.printStackTrace().contains(HdUtils.getPackageName())) {
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_Base64_decode.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetAlgorithmBase64");
                                }
                                //}

                            }

                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

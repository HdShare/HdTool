package com.hd.hdtool.hook.fileHook;

import android.content.res.AssetManager;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class FileHook {

    public static void readHook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetFileRead");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    FileInputStream.class,
                    "read",
                    byte[].class,
                    int.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String path = (String) XposedHelpers.getObjectField(param.thisObject, "path");
                            if (path != null) {
                                byte[] b = (byte[]) param.args[0];
                                int off = (int) param.args[1];
                                int len = (int) param.args[2];

                                byte[] bytes = new byte[2048];
                                String str = "";
                                if (len > 2048) {
                                    System.arraycopy(b, off, bytes, 0, 2048);
                                    str = new String(bytes) + "(最多记录2kb数据...)";
                                } else {
                                    System.arraycopy(b, off, bytes, 0, len);
                                    str = new String(bytes);
                                }

                                if (!path.equals("") && !path.contains(HdUtils.获取存储文件夹())) {
                                    String data = "==========================================\n";
                                    data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                                    data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                                    data += "文件操作 " + "读取文件" + "\n";
                                    data += "文件路径 " + path + "\n";
                                    data += "文件内容 " + str + "\n";
                                    data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                                    FileUtils.writeAdd(setFolderPath + "/" + "Hook_FileRead.lua", data);
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetFileRead");
                                    }
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void writeHook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetFileWrite");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    FileOutputStream.class,
                    "write",
                    byte[].class,
                    int.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String path = (String) XposedHelpers.getObjectField(param.thisObject, "path");
                            if (path != null) {
                                byte[] b = (byte[]) param.args[0];
                                int off = (int) param.args[1];
                                int len = (int) param.args[2];

                                byte[] bytes = new byte[2048];
                                String str = "";
                                if (len > 2048) {
                                    System.arraycopy(b, off, bytes, 0, 2048);
                                    str = new String(bytes) + "(最多记录2kb数据...)";
                                } else {
                                    System.arraycopy(b, off, bytes, 0, len);
                                    str = new String(bytes);
                                }

                                if (!path.equals("") && !path.contains(HdUtils.获取存储文件夹())) {
                                    String data = "==========================================\n";
                                    data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                                    data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                                    data += "文件操作 " + "写入文件" + "\n";
                                    data += "文件路径 " + path + "\n";
                                    data += "文件内容 " + str + "\n";
                                    data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                                    FileUtils.writeAdd(setFolderPath + "/" + "Hook_FileWrite.lua", data);
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetFileWrite");
                                    }
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void deleteHook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetFileDelete");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    File.class,
                    "delete",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            File file = (File) param.thisObject;
                            if (file != null && !file.getAbsolutePath().contains(HdUtils.获取存储文件夹())) {
                                String data = "==========================================\n";
                                data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                                data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                                data += "文件操作 " + "删除文件" + "\n";
                                data += "文件路径 " + file.getAbsolutePath() + "\n";
                                data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_FileDelete.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetFileDelete");
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void assetsHook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetFileAssets");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    AssetManager.class,
                    "open",
                    String.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String filePath = (String) param.args[0];
                            if (filePath != null) {
                                String data = "==========================================\n";
                                data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                                data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                                data += "文件操作 " + "资源文件" + "\n";
                                data += "文件路径 " + filePath + "\n";
                                data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_FileAssets.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetFileAssets");
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

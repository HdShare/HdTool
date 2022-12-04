package com.hd.hdtool.hook.jsonHook;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import org.json.JSONObject;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class JsonObjectHook {

    public static void put(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorJsonObjectPut");
        }
        try {
            XposedBridge.hookAllMethods(
                    JSONObject.class,
                    "put",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            if (!ToolHook.printStackTrace().contains(HdUtils.getPackageName())) {
                                String data = "==========================================\n";
                                data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                                data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                                data += "数据操作 " + "添加数据" + "\n";
                                data += "数据内容 " + "\n" + "name : " + param.args[0] + "\n" + "value : " + param.args[1] + "\n";
                                data += "数据结果 " + param.getResult() + "\n";
                                data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_JsonObject_put.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetMonitorJsonObjectPut");
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void get(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorJsonObjectGet");
        }
        try {
            XposedBridge.hookAllMethods(
                    JSONObject.class,
                    "get",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            if (!ToolHook.printStackTrace().contains(HdUtils.getPackageName())) {
                                String data = "==========================================\n";
                                data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                                data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                                data += "数据操作 " + "获取数据" + "\n";
                                data += "数据内容 " + "\n" + "name : " + param.args[0] + "\n";
                                data += "数据结果 " + param.getResult() + "\n";
                                data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_JsonObject_get.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetMonitorJsonObjectGet");
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

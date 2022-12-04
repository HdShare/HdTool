package com.hd.hdtool.hook.windowHook;

import android.app.Dialog;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileJsonUtils;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class DialogHook {

    public static void hook(String setFolderPath, String setFileName) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorDialog");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    Dialog.class,
                    "show",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Dialog dialog = (Dialog) param.thisObject;
                            String windowType = "Dialog弹窗";
                            String windowText = ToolHook.getViewText(dialog.getWindow().getDecorView());
                            String windowKeyword = "(已拦截)";

                            boolean isKill = false;
                            JSONArray windowKeywords = FileJsonUtils.readJsonArray(setFileName, "windowKeywords");
                            for (int i = 0; i < windowKeywords.length(); i++) {
                                try {
                                    if (windowText.contains(windowKeywords.getString(i))) {
                                        isKill = true;
                                        windowKeyword += "[" + windowKeywords.getString(i) + "]";
                                        if (HdUtils.isDebug()) {
                                            XposedBridge.log(HdUtils.获取TAG() + " >>Kill_Dialog>> " + windowKeywords.getString(i) + " | " + isKill);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "控件类名 " + dialog.getClass().getName() + "\n";
                            data += "控件类型 " + windowType + "\n";
                            data += "控件内容 " + windowText + "\n";
                            data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                            if (FileJsonUtils.readJson(setFileName, "swAppSetWindowKeywords").equals("true") && isKill) {
                                dialog.dismiss();
                                data = data.replaceAll(windowType, windowType + windowKeyword);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_kill>> " + windowType + windowKeyword);
                                }
                            }
                            if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorDialog").equals("true")) {
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_Dialog.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + windowType + "(已监听)");
                                }
                            }
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetMonitorDialog");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

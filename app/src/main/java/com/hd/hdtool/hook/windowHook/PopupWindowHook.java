package com.hd.hdtool.hook.windowHook;

import android.widget.PopupWindow;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileJsonUtils;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class PopupWindowHook {

    public static void hook(String setFolderPath, String setFileName) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorPopupWindow");
        }
        try {
            XposedBridge.hookAllMethods(
                    PopupWindow.class,
                    "showAsDropDown",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            PopupWindow popupWindow = (PopupWindow) param.thisObject;
                            String windowType = "PopupWindow弹窗";
                            String windowText = ToolHook.getViewText(popupWindow.getContentView());
                            String windowKeyword = "(已拦截)";

                            boolean isKill = false;
                            JSONArray windowKeywords = FileJsonUtils.readJsonArray(setFileName, "windowKeywords");
                            for (int i = 0; i < windowKeywords.length(); i++) {
                                try {
                                    if (windowText.contains(windowKeywords.getString(i))) {
                                        isKill = true;
                                        windowKeyword += "[" + windowKeywords.getString(i) + "]";
                                        if (HdUtils.isDebug()) {
                                            XposedBridge.log(HdUtils.获取TAG() + " >>Kill_PopupWindow>> " + windowKeywords.getString(i) + " | " + isKill);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "控件类名 " + popupWindow.getClass().getName() + "\n";
                            data += "控件类型 " + windowType + "\n";
                            data += "控件内容 " + windowText + "\n";
                            data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                            if (FileJsonUtils.readJson(setFileName, "swAppSetWindowKeywords").equals("true") && isKill) {
                                popupWindow.dismiss();
                                data = data.replaceAll(windowType, windowType + windowKeyword);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_kill>> " + windowType + windowKeyword);
                                }
                            }
                            if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorPopupWindow").equals("true")) {
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_PopupWindow.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + windowType + "(已监听)");
                                }
                            }
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "showAsDropDown");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    PopupWindow.class,
                    "showAtLocation",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            PopupWindow popupWindow = (PopupWindow) param.thisObject;
                            String windowType = "PopupWindow弹窗";
                            String windowText = ToolHook.getViewText(popupWindow.getContentView());
                            String windowKeyword = "(已拦截)";

                            boolean isKill = false;
                            JSONArray windowKeywords = FileJsonUtils.readJsonArray(setFileName, "windowKeywords");
                            for (int i = 0; i < windowKeywords.length(); i++) {
                                try {
                                    if (windowText.contains(windowKeywords.getString(i))) {
                                        isKill = true;
                                        windowKeyword += "[" + windowKeywords.getString(i) + "]";
                                        if (HdUtils.isDebug()) {
                                            XposedBridge.log(HdUtils.获取TAG() + " >>Kill_Toast>> " + windowKeywords.getString(i) + " | " + isKill);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "控件类名 " + popupWindow.getClass().getName() + "\n";
                            data += "控件类型 " + windowType + "\n";
                            data += "控件内容 " + windowText + "\n";
                            data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                            if (FileJsonUtils.readJson(setFileName, "swAppSetWindowKeyWords").equals("true") && isKill) {
                                popupWindow.dismiss();
                                data = data.replaceAll(windowType, windowType + windowKeyword);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + windowType + windowKeyword);
                                }
                            }
                            if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorPopupWindow").equals("true")) {
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_PopupWindow.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + windowType + "(已监听)");
                                }
                            }
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "showAtLocation");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

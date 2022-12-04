package com.hd.hdtool.hook.monitorHook;

import android.view.View;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class OnClickHook {

    private static View tempView = null;

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorOnClick");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    View.class,
                    "performClick",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            View view = (View) param.thisObject;
                            View.OnClickListener onClickListener = (View.OnClickListener) XposedHelpers.getObjectField(XposedHelpers.getObjectField(view, "mListenerInfo"), "mOnClickListener");
                            if (tempView != view && onClickListener != null) {
                                String data = "==========================================\n";
                                data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                                data += "控件类名 " + view.getClass().getName() + "\n";
                                data += "控件ID " + Integer.toHexString(view.getId()) + "\n";
                                data += "控件内容 " + ToolHook.getViewText(view) + "\n";
                                data += "控件回调 " + onClickListener.getClass().getName() + "\n";
                                data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_OnClick.lua", data);
                                tempView = view;
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetMonitorOnClick");
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

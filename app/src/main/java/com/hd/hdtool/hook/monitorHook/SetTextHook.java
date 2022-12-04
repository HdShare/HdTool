package com.hd.hdtool.hook.monitorHook;

import android.widget.TextView;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class SetTextHook {

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorSetText");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    TextView.class,
                    "setText",
                    CharSequence.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            TextView textView = (TextView) param.thisObject;
                            if (param.args[0] != null) {
                                String data = "==========================================\n";
                                data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                                data += "控件类名 " + textView.getClass().getName() + "\n";
                                data += "控件ID " + Integer.toHexString(textView.getId()) + "\n";
                                data += "控件内容 " + param.args[0] + "\n";
                                data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                                FileUtils.writeAdd(setFolderPath + "/" + "Hook_SetText.lua", data);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetMonitorSetText");
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

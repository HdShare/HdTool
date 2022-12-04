package com.hd.hdtool.hook.httpHook;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import java.net.URL;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HttpHook {

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorHttp");
        }
        try {
            XposedHelpers.findAndHookConstructor(
                    URL.class,
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                            data += "监听内容 " + param.args[0] + "\n";
                            data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_Http.lua", data);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetMonitorHttp");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

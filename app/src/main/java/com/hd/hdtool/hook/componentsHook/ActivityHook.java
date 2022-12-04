package com.hd.hdtool.hook.componentsHook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class ActivityHook {

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorActivity");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    Activity.class,
                    "startActivityForResult",
                    Intent.class,
                    int.class,
                    Bundle.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Intent intent = (Intent) param.args[0];
                            int requestCode = (int) param.args[1];
                            Bundle bundle = intent.getExtras();
                            ComponentName mComponent = (ComponentName) XposedHelpers.getObjectField(intent, "mComponent");
                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                            data += "自身包名 " + mComponent.getPackageName() + "\n";
                            data += "跳转类名 " + mComponent.getClassName() + "\n";
                            if (bundle != null) {
                                data += "额外数据 " + bundle + "\n";
                            }
                            data += "请求代码 " + requestCode + "\n";
                            data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_Activity.lua", data);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetMonitorActivity");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

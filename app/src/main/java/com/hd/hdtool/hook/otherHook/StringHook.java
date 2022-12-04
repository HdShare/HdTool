package com.hd.hdtool.hook.otherHook;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class StringHook {

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetStringEqual");
        }
        XposedHelpers.findAndHookMethod(
                "java.lang.String",
                ClassLoader.getSystemClassLoader(),
                "equals",
                Object.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        String edtStr = param.thisObject.toString();
                        if (edtStr.contains("密码")) {
                            String password = param.args[0].toString();
                            String data = "==========================================\n";
                            data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                            data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                            data += "执行操作 " + "解锁密码监听" + "\n";
                            data += "解锁密码 " + password + "\n";
                            data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                            FileUtils.writeAdd(setFolderPath + "/" + "Hook_StringEqual.lua", data);
                            param.setResult(true);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetStringEqual");
                            }
                        }
                    }
                }
        );
    }

}

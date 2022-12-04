package com.hd.hdtool.hook.monitorHook;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TimeUtils;

import java.io.File;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class ShellHook {

    public static void hook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorShell");
        }
        XposedHelpers.findAndHookMethod(
                Runtime.class,
                "exec",
                String[].class,
                String[].class,
                File.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        String[] cmdarray = (String[]) param.args[0];
                        StringBuilder sb = new StringBuilder();
                        for (String str : cmdarray) {
                            sb.append(str + "");
                        }

                        Process mProcess = (Process) param.getResult();
                        String data = "==========================================\n";
                        data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                        data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                        data += "执行操作 " + "命令行监听" + "\n";
                        data += "执行内容 " + sb.toString() + "\n";
                        data += "执行结果 " + mProcess.toString() + "\n";
                        data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";
                        FileUtils.writeAdd(setFolderPath + "/" + "Hook_Shell.lua", data);
                        if (HdUtils.isDebug()) {
                            XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetMonitorShell");
                        }
                    }
                }
        );
    }

}

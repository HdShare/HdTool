package com.hd.hdtool.hook;

import android.text.InputFilter;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class TestHook {

    public static void hook(ClassLoader classLoader, String setFileName) {

//        XposedHelpers.findAndHookMethod(
//                ClassLoader.class,
//                "loadClass",
//                String.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//
//                        if (!param.hasThrowable()) {
//                            try {
//                                String strClazz = param.args[0].toString();
//                                if (!strClazz.startsWith("android.")) {//排除android控件类
//                                    Class<?> clazz = (Class<?>) param.getResult();
//                                    synchronized (this.getClass()) {
//                                        if (strClazz.contains("com.hd.hdtool")) {//hook类名
//                                            Log.d("LSPosed", "类名: " + strClazz);
//                                            Method[] methods = clazz.getDeclaredMethods();
//                                            for (Method method : methods) {
//                                                if (!method.getName().equals("methodName")) {//hook方法名
//                                                    Log.d("LSPosed", "方法名: " + method.getName());//打印方法名称
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            } catch (Exception e) {
//                            }
//                        }
//
//                    }
//                }
//        );
    }

}

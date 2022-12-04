package com.hd.hdtool.hook.appHook;

import static de.robv.android.xposed.XposedHelpers.findConstructorExact;

import com.hd.hdtool.utils.HdUtils;

import java.io.BufferedReader;
import java.io.File;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XCallback;

public class HideXposedHook {

    public static void hook() {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetHideXposed");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    Class.class,
                    "forName",
                    String.class,
                    boolean.class,
                    ClassLoader.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String result = (String) param.args[0];
                            if (param.args != null && result != null && result.contains("xposed")) {
                                param.setThrowable(new ClassNotFoundException(" not found " + result));
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_forName>> " + result);
                                }
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    StackTraceElement.class,
                    "getClassName",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String className = (String) param.getResult();
                            //XposedBridge.log(folderName + " >>>>>>>>>>>stack_getClass>> " + className);
                            if (className != null && (className.contains("xposed") || (className.contains("EdHooker")) || className.contains("ZygoteInit"))) {
                                param.setResult("");
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_stack_getClass>> " + className);
                                }
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    BufferedReader.class,
                    "readLine",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            String line = (String) param.getResult();
                            if (line != null && (line.contains("xposed") || line.contains("XposedBridge") || line.contains("lsposed"))) {
                                if (!line.contains(HdUtils.获取TAG())) {//不加这一句会与XposedBridge.log无限循环冲突，排除包含（Hd_Tool）的日志
                                    param.setResult("");
                                    new File("").lastModified();
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end_readLine_jar>> " + line);
                                    }
                                }
                            }
                        }
                    }
            );
            XposedBridge.hookMethod(
                    findConstructorExact(java.io.File.class, String.class),
                    new XC_MethodHook(XCallback.PRIORITY_HIGHEST) {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            String result = (String) param.args[0];
                            if (result != null && result.contains("XposedBridge")) {
                                param.args[0] = result.replaceAll("XposedBridge", "hd");
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_file_jar>> " + result);
                                }
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    Modifier.class,
                    "isNative",
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            param.setResult(false);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end_isNative>> " + "false");
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    System.class,
                    "getProperty",
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            if (param.args[0] != null && param.args[0].equals("vxp")) {
                                param.setResult(null);
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_getProperty>> " + "vxp");
                                }
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    System.class,
                    "getenv",
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            String property = (String) param.args[0];
                            if (property.contains("CLASSPATH")) {
                                param.setResult("FAKE.CLASSPATH");
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end_getenv_CLASSPATH>> " + property);
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

package com.hd.hdtool.hook;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hd.hdtool.hook.algorithmHook.AesDesRsaHook;
import com.hd.hdtool.hook.algorithmHook.Base64Hook;
import com.hd.hdtool.hook.algorithmHook.HmacHook;
import com.hd.hdtool.hook.appHook.HideCydiaSubstrateHook;
import com.hd.hdtool.hook.appHook.HideTaiChiEpicHook;
import com.hd.hdtool.hook.appHook.HideFrameHook;
import com.hd.hdtool.hook.appHook.HideMagiskHook;
import com.hd.hdtool.hook.componentsHook.ActivityHook;
import com.hd.hdtool.hook.fileHook.FileHook;
import com.hd.hdtool.hook.httpHook.HttpHook;
import com.hd.hdtool.hook.jsonHook.JsonArrayHook;
import com.hd.hdtool.hook.jsonHook.JsonObjectHook;
import com.hd.hdtool.hook.monitorHook.ShellHook;
import com.hd.hdtool.hook.appHook.HideRootHook;
import com.hd.hdtool.hook.networkHook.HideVpnHook;
import com.hd.hdtool.hook.networkHook.HideWifiProxyHook;
import com.hd.hdtool.hook.appHook.HideXposedHook;
import com.hd.hdtool.hook.monitorHook.ExitHook;
import com.hd.hdtool.hook.monitorHook.OnClickHook;
import com.hd.hdtool.hook.monitorHook.OnLongClickHook;
import com.hd.hdtool.hook.monitorHook.SetTextHook;
import com.hd.hdtool.hook.algorithmHook.Md5ShaHook;
import com.hd.hdtool.hook.otherHook.ScreenHook;
import com.hd.hdtool.hook.otherHook.StringHook;
import com.hd.hdtool.hook.signaturesHook.SignaturesHook;
import com.hd.hdtool.hook.versionHook.VersionHook;
import com.hd.hdtool.hook.windowHook.CancelWindowHook;
import com.hd.hdtool.hook.windowHook.DialogHook;
import com.hd.hdtool.hook.windowHook.PopupWindowHook;
import com.hd.hdtool.hook.windowHook.ToastHook;
import com.hd.hdtool.utils.AppInfoUtils;
import com.hd.hdtool.utils.FileJsonUtils;
import com.hd.hdtool.utils.HdUtils;

import org.json.JSONArray;
import org.json.JSONException;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage, IXposedHookInitPackageResources {

    private String setFolderPath;
    private String setFileName;
    private boolean isMoreDex = false;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        setXpStatus(loadPackageParam);
        initHook(loadPackageParam);
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam initPackageResourcesParam) throws Throwable {
        if (initPackageResourcesParam.packageName.equals("com.zjwh.android_wh_physicalfitness")) {
            initPackageResourcesParam.res.hookLayout(0x7f0d004f,
                    new XC_LayoutInflated() {
                        @Override
                        public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                            int signature_et_id = initPackageResourcesParam.res.getIdentifier("signature_et", "id", "com.zjwh.android_wh_physicalfitness");
                            EditText editText = (EditText) liparam.view.findViewById(signature_et_id);
                            editText.setTextColor(Color.RED);
                            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(999)});
                        }
                    }
            );
        }
    }

    private void setXpStatus(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (loadPackageParam.packageName.equals("com.hd.hdtool")) {
            XposedHelpers.findAndHookMethod(
                    "com.hd.hdtool.MainActivity",
                    loadPackageParam.classLoader,
                    "getXpStatus",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            boolean result = true;
                            param.setResult(result);
                        }
                    }
            );
        }
    }

    private void initHook(XC_LoadPackage.LoadPackageParam loadPackageParam) throws JSONException {
        JSONArray jsonArray = FileJsonUtils.readJsonArray(HdUtils.获取存储文件(), "packageNameList");
        JSONArray temp_jsonArray = FileJsonUtils.readJsonArray(HdUtils.获取内部存储文件(loadPackageParam.packageName), "packageNameList");
        if (jsonArray.length() == 0 && temp_jsonArray.length() == 0) {
            XposedBridge.log(HdUtils.获取TAG() + "被Hook程序" + "[" + loadPackageParam.packageName + "]" + "读取配置信息出现异常");
        } else {
            for (int i = 0; i < (temp_jsonArray.length() != 0 ? temp_jsonArray.length() : jsonArray.length()); i++) {
                if ((temp_jsonArray.length() != 0 ? temp_jsonArray : jsonArray).getString(i).equals(loadPackageParam.packageName)) {
                    if (temp_jsonArray.length() != 0) {
                        XposedBridge.log(HdUtils.获取TAG() + "内部存储" + HdUtils.获取内部设置文件(loadPackageParam.packageName));
                        setFolderPath = HdUtils.获取内部设置文件夹(loadPackageParam.packageName);
                        setFileName = HdUtils.获取内部设置文件(loadPackageParam.packageName);
                    } else {
                        XposedBridge.log(HdUtils.获取TAG() + "外部存储" + HdUtils.获取设置文件(loadPackageParam.packageName));
                        setFolderPath = HdUtils.获取设置文件夹(loadPackageParam.packageName);
                        setFileName = HdUtils.获取设置文件(loadPackageParam.packageName);
                    }
                    startHook(loadPackageParam);
                }
            }
        }
    }

    private void startHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        if (FileJsonUtils.readJson(setFileName, "swAppSetHideXposed").equals("true")) {
            HideXposedHook.hook();
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetHideRoot").equals("true")) {
            HideRootHook.hook();
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetHideMagisk").equals("true")) {
            HideMagiskHook.hook();
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetHideTaiChiEpic").equals("true")) {
            HideTaiChiEpicHook.hook();
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetHideCydiaSubstrate").equals("true")) {
            HideCydiaSubstrateHook.hook();
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetHideFrame").equals("true")) {
            HideFrameHook.hook();
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetHideVpn").equals("true")) {
            HideVpnHook.hook();
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetHideWifiProxy").equals("true")) {
            HideWifiProxyHook.hook();
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorHttp").equals("true")) {
            HttpHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetAnalogVersionCode").equals("true") || FileJsonUtils.readJson(setFileName, "swAppSetAnalogVersionName").equals("true")) {
            VersionHook.hook(loadPackageParam, setFileName);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetCancelWindow").equals("true")) {
            CancelWindowHook.hook();
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetWindowKeywords").equals("true") || FileJsonUtils.readJson(setFileName, "swAppSetMonitorDialog").equals("true")) {
            DialogHook.hook(setFolderPath, setFileName);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetWindowKeywords").equals("true") || FileJsonUtils.readJson(setFileName, "swAppSetMonitorToast").equals("true")) {
            ToastHook.hook(setFolderPath, setFileName);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetWindowKeywords").equals("true") || FileJsonUtils.readJson(setFileName, "swAppSetMonitorPopupWindow").equals("true")) {
            PopupWindowHook.hook(setFolderPath, setFileName);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorOnClick").equals("true")) {
            OnClickHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorOnLongClick").equals("true")) {
            OnLongClickHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorSetText").equals("true")) {
            SetTextHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorExit").equals("true")) {
            ExitHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetAlgorithmBase64").equals("true")) {
            Base64Hook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetAlgorithmAesDesRsa").equals("true")) {
            AesDesRsaHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetAlgorithmMd5Sha").equals("true")) {
            Md5ShaHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetAlgorithmHmac").equals("true")) {
            HmacHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorJsonObjectPut").equals("true")) {
            JsonObjectHook.put(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorJsonObjectGet").equals("true")) {
            JsonObjectHook.get(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorJsonArrayPut").equals("true")) {
            JsonArrayHook.put(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorJsonArrayGet").equals("true")) {
            JsonArrayHook.get(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorActivity").equals("true")) {
            ActivityHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetFileRead").equals("true")) {
            FileHook.readHook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetFileWrite").equals("true")) {
            FileHook.writeHook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetFileDelete").equals("true")) {
            FileHook.deleteHook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetFileAssets").equals("true")) {
            FileHook.assetsHook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorSignatures").equals("true")) {
            SignaturesHook.monitorHook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetMonitorShell").equals("true")) {
            ShellHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetScreen").equals("true")) {
            ScreenHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetStringEqual").equals("true")) {
            StringHook.hook(setFolderPath);
        }

        if (FileJsonUtils.readJson(setFileName, "swAppSetFindContextBase").equals("true")) {
            if (HdUtils.isDebug()) {
                XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "FindContext-通用加固方案");
            }
            try {
                XposedHelpers.findAndHookMethod(
                        ContextWrapper.class,
                        "attachBaseContext",
                        Context.class,
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Context context = (Context) param.args[0];
                                ClassLoader classLoader = context.getClassLoader();
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "FindContext-通用加固方案");
                                }
                                ProtectHook.protectHook(context, classLoader, setFolderPath, setFileName);
                            }
                        }
                );
            } catch (Exception e) {
                if (HdUtils.isDebug()) {
                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "No-FindContext-通用加固方案");
                }
            }
        } else if (FileJsonUtils.readJson(setFileName, "swAppSetFindContextQihoo").equals("true")) {
            if (HdUtils.isDebug()) {
                XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "FindContext-360加固");
            }
            try {
                XposedHelpers.findAndHookMethod(
                        "com.stub.StubApp",
                        loadPackageParam.classLoader,
                        "attachBaseContext",
                        Context.class,
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Context context = (Context) param.args[0];
                                ClassLoader classLoader = context.getClassLoader();
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "FindContext-360加固");
                                }
                                ProtectHook.protectHook(context, classLoader, setFolderPath, setFileName);
                            }
                        }
                );
            } catch (Exception e) {
                if (HdUtils.isDebug()) {
                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "No-FindContext-360加固");
                }
            }
        } else if (FileJsonUtils.readJson(setFileName, "swAppSetFindContextTencent").equals("true")) {
            if (HdUtils.isDebug()) {
                XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "FindContext-腾讯御安全");
            }
            try {
                XposedHelpers.findAndHookMethod(
                        "com.wrapper.proxyapplication.WrapperProxyApplication",
                        loadPackageParam.classLoader,
                        "attachBaseContext",
                        Context.class,
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Context context = (Context) param.args[0];
                                ClassLoader classLoader = context.getClassLoader();
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "FindContext-腾讯御安全");
                                }
                                ProtectHook.protectHook(context, classLoader, setFolderPath, setFileName);
                            }
                        }
                );
            } catch (Exception e) {
                if (HdUtils.isDebug()) {
                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "No-FindContext-腾讯御安全");
                }
            }
        } else if (FileJsonUtils.readJson(setFileName, "swAppSetFindContextBangcle").equals("true")) {
            if (HdUtils.isDebug()) {
                XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "FindContext-梆梆加固企业版");
            }
            try {
                XposedHelpers.findAndHookMethod(
                        "com.secneo.apkwrapper.AW",
                        loadPackageParam.classLoader,
                        "attachBaseContext",
                        Context.class,
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Context context = (Context) param.args[0];
                                ClassLoader classLoader = context.getClassLoader();
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "FindContext-梆梆加固企业版");
                                }
                                ProtectHook.protectHook(context, classLoader, setFolderPath, setFileName);
                            }
                        }
                );
            } catch (Exception e) {
                if (HdUtils.isDebug()) {
                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "No-FindContext-梆梆加固企业版");
                }
            }
        } else {
            if (HdUtils.isDebug()) {
                XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "FindContext-默认方案");
            }
            try {
                XposedHelpers.findAndHookMethod(
                        Application.class,
                        "attach",
                        Context.class,
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Context context = (Context) param.args[0];
                                ClassLoader classLoader = context.getClassLoader();
                                if (HdUtils.isDebug()) {
                                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "FindContext-默认多dex方案");
                                }
                                isMoreDex = true;
                                ProtectHook.protectHook(context, classLoader, setFolderPath, setFileName);
                            }
                        }
                );
                if (isMoreDex == false) {
                    Context context = (Context) AndroidAppHelper.currentApplication();
                    if (HdUtils.isDebug()) {
                        XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "FindContext-默认单dex方案");
                    }
                    ProtectHook.protectHook(context, loadPackageParam.classLoader, setFolderPath, setFileName);
                }
            } catch (Exception e) {
                if (HdUtils.isDebug()) {
                    XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "No-FindContext-普通模式");
                }
            }
        }

    }

}




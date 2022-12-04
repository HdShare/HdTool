package com.hd.hdtool.hook.adsHook;

import android.content.Context;

import com.hd.hdtool.utils.HdUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class AdsHook {

    public static void hookByteDance(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetKillADsModeByteDance");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("com.bytedance.sdk.openadsdk.TTAdSdk"),
                    "getAdManager",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "穿山甲广告");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void hookTencent(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetKillADsModeTencent");
        }
        try {
            XposedBridge.hookAllConstructors(
                    classLoader.loadClass("com.qq.e.ads.splash.SplashAD"),
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "腾讯-优量汇 | 开屏SplashAD广告");
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("com.qq.e.ads.rewardvideo.RewardVideoAD"),
                    "loadAD",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "腾讯-优量汇 | 激励RewardVideoAD广告");
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("com.qq.e.ads.interstitial2.UnifiedInterstitialAD"),
                    "loadAD",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "腾讯-优量汇 | 插屏UnifiedInterstitialAD广告");
                            }
                        }
                    }
            );
            XposedHelpers.findAndHookMethod(
                    classLoader.loadClass("com.qq.e.ads.banner2.UnifiedBannerView"),
                    "loadAD",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "腾讯-优量汇 | 横幅UnifiedBannerView广告");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    classLoader.loadClass("com.qq.e.ads.nativ.NativeExpressAD"),
                    "loadAD",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "腾讯-优量汇 | 信息流NativeExpressAD广告");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    classLoader.loadClass("com.qq.e.ads.nativ.NativeUnifiedAD"),
                    "loadData",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "腾讯-优量汇 | 开发者自渲染NativeUnifiedADData");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

    public static void hookMimo(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetKillADsModeMimo");
        }
        try {
            XposedBridge.hookAllMethods(
                    classLoader.loadClass("com.miui.zeus.mimo.sdk.BannerAd"),
                    "showAd",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "小米-米盟 | 横幅BannerAd广告");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    classLoader.loadClass("com.miui.zeus.mimo.sdk.InterstitialAd"),
                    "show",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "小米-米盟 | 插屏InterstitialAd广告");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    classLoader.loadClass("com.miui.zeus.mimo.sdk.RewardVideoAd"),
                    "showAd",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "小米-米盟 | 激励RewardVideoAd广告");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    classLoader.loadClass("com.miui.zeus.mimo.sdk.TemplateAd"),
                    "show",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "小米-米盟 | 信息流TemplateAd广告");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    classLoader.loadClass("com.miui.zeus.mimo.sdk.SplashAd"),
                    "loadAndShow",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "小米-米盟 | 开屏SplashAd广告");
                            }
                        }
                    }
            );
            XposedBridge.hookAllMethods(
                    classLoader.loadClass("com.miui.zeus.mimo.sdk.NativeAd"),
                    "load",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(null);
                            if (HdUtils.isDebug()) {
                                XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "小米-米盟 | 信息流NativeAd广告");
                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

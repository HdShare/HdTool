package com.hd.hdtool.hook.signaturesHook;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;

import com.hd.hdtool.hook.ToolHook;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.StringUtils;
import com.hd.hdtool.utils.TimeUtils;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class SignaturesHook {

    public static void monitorHook(String setFolderPath) {
        if (HdUtils.isDebug()) {
            XposedBridge.log(HdUtils.获取TAG() + " >>start>> " + "swAppSetMonitorSignatures");
        }
        try {
            XposedHelpers.findAndHookMethod(
                    "android.app.ApplicationPackageManager",
                    ClassLoader.getSystemClassLoader(),
                    "getPackageInfo",
                    String.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            PackageInfo packageInfo = (PackageInfo) param.getResult();
                            String packagename = (String) param.args[0];
                            int value = (int) param.args[1];
                            if (value == PackageManager.GET_SIGNATURES) {
                                byte[] signatures_ByteArray = packageInfo.signatures[0].toByteArray();
                                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(signatures_ByteArray);
                                X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(byteArrayInputStream);

                                boolean addColon = false;
                                boolean isUpperCase = false;

                                String encode_md5 = StringUtils.toHexString(MessageDigest.getInstance("MD5").digest(x509Certificate.getEncoded()), addColon, isUpperCase);
                                String encode_sha1 = StringUtils.toHexString(MessageDigest.getInstance("SHA1").digest(x509Certificate.getEncoded()), addColon, isUpperCase);
                                String encode_sha256 = StringUtils.toHexString(MessageDigest.getInstance("SHA256").digest(x509Certificate.getEncoded()), addColon, isUpperCase);
                                String encode_base64 = Base64.encodeToString(signatures_ByteArray, 0);

                                String data = "==========================================\n";
                                data += "当前时间 " + TimeUtils.getNowTime() + "\n";
                                data += "控件类名 " + param.thisObject.getClass().getName() + "\n";
                                data += "检测包名 " + packagename + "\n";
                                data += "签名结果 (md5) " + "\n" + encode_md5 + "\n";
                                data += "签名结果 (sha1) " + "\n" + encode_sha1 + "\n";
                                data += "签名结果 (sha256) " + "\n" + encode_sha256 + "\n";
                                data += "签名结果 (base64) " + "\n" + encode_base64 + "\n";
                                data += "调用堆栈 " + ToolHook.printStackTrace() + "\n";

                                if (!ToolHook.printStackTrace().contains("com.qualcomm.qti.Performance")) {
                                    FileUtils.writeAdd(setFolderPath + "/" + "Hook_Signatures.lua", data);
                                    if (HdUtils.isDebug()) {
                                        XposedBridge.log(HdUtils.获取TAG() + " >>end>> " + "swAppSetMonitorSignatures");
                                    }
                                }

                            }
                        }
                    }
            );
        } catch (Exception e) {
        }
    }

}

package com.hd.hdtool.utils;

public class HdUtils {

    public static String 获取TAG() {
        return "Hd_Tool --> ";
    }

    public static boolean isDebug() {
        return true;
    }

    public static String getPackageName() {
        return "com.hd.hdtool";
    }

    public static String 获取文件夹名() {
        return "Hd_Tool";
    }

    public static String 获取文件名() {
        return "Hd_Tool.json";
    }

    public static String 获取存储文件夹() {
        return FileUtils.getPath(true, 获取文件夹名());
    }

    public static String 获取存储文件() {
        return FileUtils.getPath(false, 获取存储文件夹() + "/" + 获取文件名());
    }

    public static String 获取设置文件夹(String 包名) {
        return FileUtils.getPath(false, 获取存储文件夹() + "/" + 包名);
    }

    public static String 获取设置文件(String 包名) {
        return FileUtils.getPath(false, 获取存储文件夹() + "/" + 包名 + "/" + 包名 + ".json");
    }

    public static String 获取内部存储文件夹(String 包名) {
        return FileUtils.getPath(true, "Android/data/" + 包名 + "/files");
    }

    public static String 获取内部存储文件(String 包名) {
        return FileUtils.getPath(false, 获取内部存储文件夹(包名) + "/" + 获取文件名());
    }

    public static String 获取内部设置文件夹(String 包名) {
        return FileUtils.getPath(false, 获取内部存储文件夹(包名) + "/" + 包名);
    }

    public static String 获取内部设置文件(String 包名) {
        return FileUtils.getPath(false, 获取内部存储文件夹(包名) + "/" + 包名 + "/" + 包名 + ".json");
    }

    public static String 获取XP_已激活颜色() {
        return "#ffffff";
    }

    public static String 获取XP_未激活颜色() {
        return "#ff6774";
    }

    public static String 获取XP_已Hook颜色() {
        return "#ff6774";
    }

    public static String 获取XP_未Hook颜色() {
        return "#008278";
    }

    public static String 获取应用_已选择下划线颜色() {
        return "#3dbfea";
    }

    public static String 获取应用_未选择下划线颜色() {
        return "#4385f5";
    }

}

package com.hd.hdtool.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileJsonUtils {

    public static boolean hasPackageName(JSONArray jsonArray, String keyName) {
        boolean has = false;
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getString(i).equals(keyName)) {
                    has = true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return has;
    }

    public static void deletePackageName(String fileName, String keyName, JSONArray jsonArray, String packageName) {
        JSONArray tempJsonArray = jsonArray;
        try {
            for (int i = 0; i < tempJsonArray.length(); i++) {
                if (tempJsonArray.getString(i).equals(packageName)) {
                    tempJsonArray.remove(i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        writeJsonArray(fileName, keyName, tempJsonArray);
    }

    public static void writeJson(String fileName, String keyName, String value) {
        try {
            JSONObject jsonObject = new JSONObject(FileUtils.read(fileName));
            jsonObject.put(keyName, value);
            FileUtils.write(fileName, jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void writeJsonArray(String fileName, String keyName, JSONArray jsonArray) {
        try {
            JSONObject jsonObject = new JSONObject(FileUtils.read(fileName));
            jsonObject.put(keyName, jsonArray);
            FileUtils.write(fileName, jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String readJson(String fileName, String keyName) {
        String str = "";
        if (!TextUtils.isEmpty(FileUtils.read(fileName))) {
            try {
                JSONObject jsonObject = new JSONObject(FileUtils.read(fileName));
                if (jsonObject.has(keyName)) {
                    str = jsonObject.getString(keyName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static JSONArray readJsonArray(String fileName, String keyName) {
        JSONArray jsonArray = new JSONArray();
        if (!TextUtils.isEmpty(FileUtils.read(fileName))) {
            try {
                JSONObject jsonObject = new JSONObject(FileUtils.read(fileName));
                jsonArray = jsonObject.getJSONArray(keyName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

}

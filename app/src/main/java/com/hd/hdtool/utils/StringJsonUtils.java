package com.hd.hdtool.utils;

import org.json.JSONObject;

public class StringJsonUtils {

    public static boolean json_has(String str, String key) {
        boolean isHas = str.indexOf(key) == -1 ? false : true;
        return isHas;
    }

    public static Object json_get(String str, String key) {
        Object value = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            value = jsonObject.get(key);
            if (value instanceof Integer) {
                int beginIndex = str.indexOf(key) + key.length() + ("\":").length();
                int endIndex = str.indexOf(",", beginIndex);
                value = str.substring(beginIndex, endIndex);
            }
        } catch (Exception e) {
        }
        return value;
    }

    public static String json_set(String str, String key, Object value) {
        String result = "";
        try {
            Object temp = json_get(str, key);
            String temp_str = "\"" + key + "\":" + temp;
            String temp_value = "\"" + key + "\":" + value;
            result = str.replaceAll(temp_str, temp_value);
        } catch (Exception e) {
        }
        return result;
    }

}

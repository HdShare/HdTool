package com.hd.hdtool.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class AssetsUtils {

    public static String read(Context context, String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public static void copy(Context context, String assetsFileName, String filePosition) {
        try {
            JSONObject jsonObject = new JSONObject(read(context, assetsFileName));
            FileUtils.write(filePosition, jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

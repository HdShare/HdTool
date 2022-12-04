package com.hd.hdtool.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        int width = metric.widthPixels;
        return width;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        int height = metric.heightPixels;
        return height;
    }

}

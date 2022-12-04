package com.hd.hdtool.utils;

import android.content.Context;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.hd.hdtool.R;

public class TitleBarUtils {

    public static void setColor(AppCompatActivity activity) {
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        window.setStatusBarColor(activity.getResources().getColor(R.color.themes));
    }

    public static int getHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}

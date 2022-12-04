package com.hd.hdtool.hook;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ToolHook {

    public static String getViewText(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            String str = "\n";
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                String trim = getViewText(viewGroup.getChildAt(i)).trim();
                if (!TextUtils.isEmpty(trim)) {
                    str = str + trim + "\n\n";
                }
            }
            return str;
        } else if (view instanceof TextView) {
            String charSequence = ((TextView) view).getText().toString();
            return !TextUtils.isEmpty(charSequence) ? charSequence : "";
        } else {
            return "";
        }
    }

    public static String printStackTrace() {
        String str = "\n";
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        for (int i = 3; i < stackElements.length; i++) {
            StackTraceElement element = stackElements[i];
            str += "at " + element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ")" + "\n";
        }
        return str;
    }

}

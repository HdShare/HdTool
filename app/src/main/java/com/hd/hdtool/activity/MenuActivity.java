package com.hd.hdtool.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hd.hdtool.R;
import com.hd.hdtool.utils.TitleBarUtils;

public class MenuActivity {

    public static void addMenuView(Context context,LinearLayout linearLayout){
        View llHomeMember = LayoutInflater.from(context).inflate(R.layout.layout_menu, null);
        linearLayout.addView(llHomeMember);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llHomeMember.getLayoutParams();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        params.topMargin = TitleBarUtils.getHeight(context);
        llHomeMember.setLayoutParams(params);
    }

}

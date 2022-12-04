package com.hd.hdtool.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hd.hdtool.R;

public class SetTitle extends LinearLayout {

    private TextView setTitle_title;

    String Title;

    public SetTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.widget_settitle, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.setTitle);
        Title = typedArray.getString(R.styleable.setTitle_setTitle_Title);
        Title = (!TextUtils.isEmpty(Title) ? Title : "Title");

        setTitle_title = findViewById(R.id.setTitle_title);
        setTitle_title.setText(Title);

    }

}

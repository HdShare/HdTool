package com.hd.hdtool.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hd.hdtool.R;

public class SetSwitch extends LinearLayout {

    private LinearLayout setSwitch;
    private TextView setSwitch_title;
    private TextView setSwitch_help;
    private Switch setSwitch_switch;

    String Title;
    String Help;
    String IdName;

    public SetSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.widget_setswitch, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.setSwitch);
        Title = typedArray.getString(R.styleable.setSwitch_setSwitch_Title);
        Title = (!TextUtils.isEmpty(Title) ? Title : "Title");
        Help = typedArray.getString(R.styleable.setSwitch_setSwitch_Help);
        Help = (!TextUtils.isEmpty(Help) ? Help : "Help");

        int Id = this.getId();
        if (Id == -1) {
            IdName = "IdName";
        } else {
            IdName = context.getResources().getResourceEntryName(Id);
        }

        setSwitch = (LinearLayout) findViewById(R.id.setSwitch);
        setSwitch_title = findViewById(R.id.setSwitch_title);
        setSwitch_title.setText(Title);
        setSwitch_help = findViewById(R.id.setSwitch_help);
        setSwitch_help.setText(Help);
        setSwitch_switch = (Switch) findViewById(R.id.setSwitch_switch);

    }

    public void onSetSwitchChecked() {
        if (!setSwitch_switch.isChecked()) {
            setSwitch_switch.setChecked(true);
        } else {
            setSwitch_switch.setChecked(false);
        }
    }

    public void setHelpText(CharSequence text) {
        setSwitch_help.setText(text);
    }

    public void setSwitchEnabled(boolean enabled) {
        setSwitch_switch.setEnabled(enabled);
    }

    public void setSwitchChecked(boolean checked) {
        setSwitch_switch.setChecked(checked);
    }

    public interface OnSetSwitchListener {
        void onSetSwitchCheckedChanged(String IdName, boolean b);

        void onSetSwitchClick(View view);

        void initSetting(String IdName);
    }

    private OnSetSwitchListener onSetSwitchListener;

    public void setOnSetSwitchListener(OnSetSwitchListener setSwitchListener) {
        this.onSetSwitchListener = setSwitchListener;
        if (onSetSwitchListener != null) {
            setSwitch_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    onSetSwitchListener.onSetSwitchCheckedChanged(IdName, b);
                }
            });
            setSwitch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSetSwitchListener.onSetSwitchClick(view);
                }
            });
            onSetSwitchListener.initSetting(IdName);
        }
    }

}

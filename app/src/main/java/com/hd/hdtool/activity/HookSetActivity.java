package com.hd.hdtool.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hd.hdtool.R;
import com.hd.hdtool.utils.AssetsUtils;
import com.hd.hdtool.utils.FileJsonUtils;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.TitleBarUtils;
import com.hd.hdtool.widget.SetSwitch;

import org.json.JSONArray;
import org.json.JSONException;

public class HookSetActivity extends AppCompatActivity implements View.OnClickListener {

    private String appName;
    private String packageName;

    private LinearLayout btnAppSetBackLayout;
    private TextView tvAppSetAppName;
    private Switch swAppSetOpen;
    private SetSwitch swAppSetHdDebug;
    private SetSwitch swAppSetHideXposed;
    private SetSwitch swAppSetHideRoot;
    private SetSwitch swAppSetHideMagisk;
    private SetSwitch swAppSetHideTaiChiEpic;
    private SetSwitch swAppSetHideCydiaSubstrate;
    private SetSwitch swAppSetHideFrame;
    private SetSwitch swAppSetHideVpn;
    private SetSwitch swAppSetHideWifiProxy;
    private SetSwitch swAppSetMonitorHttp;
    private SetSwitch swAppSetAnalogVersionCode;
    private SetSwitch swAppSetAnalogVersionName;
    private SetSwitch swAppSetCancelWindow;
    private SetSwitch swAppSetWindowKeywords;
    private SetSwitch swAppSetMonitorDialog;
    private SetSwitch swAppSetMonitorToast;
    private SetSwitch swAppSetMonitorPopupWindow;
    private SetSwitch swAppSetMonitorOnClick;
    private SetSwitch swAppSetMonitorOnLongClick;
    private SetSwitch swAppSetMonitorSetText;
    private SetSwitch swAppSetMonitorExit;
    private SetSwitch swAppSetAlgorithmBase64;
    private SetSwitch swAppSetAlgorithmAesDesRsa;
    private SetSwitch swAppSetAlgorithmMd5Sha;
    private SetSwitch swAppSetAlgorithmHmac;
    private SetSwitch swAppSetMonitorJsonObjectPut;
    private SetSwitch swAppSetMonitorJsonObjectGet;
    private SetSwitch swAppSetMonitorJsonArrayPut;
    private SetSwitch swAppSetMonitorJsonArrayGet;
    private SetSwitch swAppSetMonitorActivity;
    private SetSwitch swAppSetFileRead;
    private SetSwitch swAppSetFileWrite;
    private SetSwitch swAppSetFileDelete;
    private SetSwitch swAppSetFileAssets;
    private SetSwitch swAppSetMonitorSignatures;
    private SetSwitch swAppSetMonitorShell;
    private SetSwitch swAppSetScreen;
    private SetSwitch swAppSetStringEqual;
    private SetSwitch swAppSetFindContextBase;
    private SetSwitch swAppSetFindContextQihoo;
    private SetSwitch swAppSetFindContextTencent;
    private SetSwitch swAppSetFindContextBangcle;
    private SetSwitch swAppSetKillADsModeByteDance;
    private SetSwitch swAppSetKillADsModeTencent;
    private SetSwitch swAppSetKillADsModeMimo;
    private SetSwitch swAppSetKillSharePage;
    private SetSwitch swAppSetKillTpACE;
    private SetSwitch swAppSetCloudInject;
    private SetSwitch swAppSetArmProFindClass;
    private SetSwitch swAppSetArmProGlobalMode1;
    private SetSwitch swAppSetArmProGlobalMode2;
    private SetSwitch swAppSetXamarinStandardMode1;
    private SetSwitch swAppSetXamarinStandardMode2;
    private SetSwitch swAppSetXamarinStandardMode3;
    private SetSwitch swAppSetXamarinQurikMode;
    private SetSwitch swAppSetAndluaCallMethod;
    private SetSwitch swAppSetAndluaHttpTask;
    private SetSwitch swAppSetAndluaRunFunc;
    private SetSwitch swAppSetAndluaAlertDialog;
    private SetSwitch swAppSetGameGuardianMoreFunction;
    private SetSwitch swAppSetGameGuardianHttpFunction;
    private SetSwitch swAppSetGameGuardianMainFunction;

    private String ArmProFindClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_set);

        TitleBarUtils.setColor(HookSetActivity.this);

        Intent intent = getIntent();
        appName = intent.getStringExtra("appName");
        packageName = intent.getStringExtra("packageName");

        initView();
        initListener();

        initSetting();

    }

    @Override
    protected void onResume() {
        super.onResume();
        JSONArray HookAppList = FileJsonUtils.readJsonArray(HdUtils.获取存储文件(), "packageNameList");
        if (FileJsonUtils.hasPackageName(HookAppList, packageName)) {
            ArmProFindClass = FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), "ArmProFindClass");
            if (!TextUtils.isEmpty(ArmProFindClass)) {
                swAppSetArmProFindClass.setHelpText("TestFind -> " + ArmProFindClass);
                swAppSetArmProGlobalMode1.setSwitchEnabled(true);
                swAppSetArmProGlobalMode2.setSwitchEnabled(true);
            } else {
                swAppSetArmProGlobalMode1.setSwitchEnabled(false);
                swAppSetArmProGlobalMode2.setSwitchEnabled(false);
            }
        }
    }

    private void initView() {
        btnAppSetBackLayout = (LinearLayout) findViewById(R.id.btnAppSetBackLayout);
        tvAppSetAppName = (TextView) findViewById(R.id.tvAppSetAppName);
        swAppSetOpen = (Switch) findViewById(R.id.swAppSetOpen);
        swAppSetHdDebug = (SetSwitch) findViewById(R.id.swAppSetHdDebug);
        swAppSetHideXposed = (SetSwitch) findViewById(R.id.swAppSetHideXposed);
        swAppSetHideRoot = (SetSwitch) findViewById(R.id.swAppSetHideRoot);
        swAppSetHideMagisk = (SetSwitch) findViewById(R.id.swAppSetHideMagisk);
        swAppSetHideTaiChiEpic = (SetSwitch) findViewById(R.id.swAppSetHideTaiChiEpic);
        swAppSetHideCydiaSubstrate = (SetSwitch) findViewById(R.id.swAppSetHideCydiaSubstrate);
        swAppSetHideFrame = (SetSwitch) findViewById(R.id.swAppSetHideFrame);
        swAppSetHideVpn = (SetSwitch) findViewById(R.id.swAppSetHideVpn);
        swAppSetHideWifiProxy = (SetSwitch) findViewById(R.id.swAppSetHideWifiProxy);
        swAppSetMonitorHttp = (SetSwitch) findViewById(R.id.swAppSetMonitorHttp);
        swAppSetAnalogVersionCode = (SetSwitch) findViewById(R.id.swAppSetAnalogVersionCode);
        swAppSetAnalogVersionName = (SetSwitch) findViewById(R.id.swAppSetAnalogVersionName);
        swAppSetCancelWindow = (SetSwitch) findViewById(R.id.swAppSetCancelWindow);
        swAppSetWindowKeywords = (SetSwitch) findViewById(R.id.swAppSetWindowKeywords);
        swAppSetMonitorDialog = (SetSwitch) findViewById(R.id.swAppSetMonitorDialog);
        swAppSetMonitorToast = (SetSwitch) findViewById(R.id.swAppSetMonitorToast);
        swAppSetMonitorPopupWindow = (SetSwitch) findViewById(R.id.swAppSetMonitorPopupWindow);
        swAppSetMonitorOnClick = (SetSwitch) findViewById(R.id.swAppSetMonitorOnClick);
        swAppSetMonitorOnLongClick = (SetSwitch) findViewById(R.id.swAppSetMonitorOnLongClick);
        swAppSetMonitorSetText = (SetSwitch) findViewById(R.id.swAppSetMonitorSetText);
        swAppSetMonitorExit = (SetSwitch) findViewById(R.id.swAppSetMonitorExit);
        swAppSetAlgorithmBase64 = (SetSwitch) findViewById(R.id.swAppSetAlgorithmBase64);
        swAppSetAlgorithmAesDesRsa = (SetSwitch) findViewById(R.id.swAppSetAlgorithmAesDesRsa);
        swAppSetAlgorithmMd5Sha = (SetSwitch) findViewById(R.id.swAppSetAlgorithmMd5Sha);
        swAppSetAlgorithmHmac = (SetSwitch) findViewById(R.id.swAppSetAlgorithmHmac);
        swAppSetMonitorJsonObjectPut = (SetSwitch) findViewById(R.id.swAppSetMonitorJsonObjectPut);
        swAppSetMonitorJsonObjectGet = (SetSwitch) findViewById(R.id.swAppSetMonitorJsonObjectGet);
        swAppSetMonitorJsonArrayPut = (SetSwitch) findViewById(R.id.swAppSetMonitorJsonArrayPut);
        swAppSetMonitorJsonArrayGet = (SetSwitch) findViewById(R.id.swAppSetMonitorJsonArrayGet);
        swAppSetMonitorActivity = (SetSwitch) findViewById(R.id.swAppSetMonitorActivity);
        swAppSetFileRead = (SetSwitch) findViewById(R.id.swAppSetFileRead);
        swAppSetFileWrite = (SetSwitch) findViewById(R.id.swAppSetFileWrite);
        swAppSetFileDelete = (SetSwitch) findViewById(R.id.swAppSetFileDelete);
        swAppSetFileAssets = (SetSwitch) findViewById(R.id.swAppSetFileAssets);
        swAppSetMonitorSignatures = (SetSwitch) findViewById(R.id.swAppSetMonitorSignatures);
        swAppSetMonitorShell = (SetSwitch) findViewById(R.id.swAppSetMonitorShell);
        swAppSetScreen = (SetSwitch) findViewById(R.id.swAppSetScreen);
        swAppSetStringEqual = (SetSwitch) findViewById(R.id.swAppSetStringEqual);
        swAppSetFindContextBase = (SetSwitch) findViewById(R.id.swAppSetFindContextBase);
        swAppSetFindContextQihoo = (SetSwitch) findViewById(R.id.swAppSetFindContextQihoo);
        swAppSetFindContextTencent = (SetSwitch) findViewById(R.id.swAppSetFindContextTencent);
        swAppSetFindContextBangcle = (SetSwitch) findViewById(R.id.swAppSetFindContextBangcle);
        swAppSetKillADsModeByteDance = (SetSwitch) findViewById(R.id.swAppSetKillADsModeByteDance);
        swAppSetKillADsModeTencent = (SetSwitch) findViewById(R.id.swAppSetKillADsModeTencent);
        swAppSetKillADsModeMimo = (SetSwitch) findViewById(R.id.swAppSetKillADsModeMimo);
        swAppSetKillSharePage = (SetSwitch) findViewById(R.id.swAppSetKillSharePage);
        swAppSetKillTpACE = (SetSwitch) findViewById(R.id.swAppSetKillTpACE);
        swAppSetCloudInject = (SetSwitch) findViewById(R.id.swAppSetCloudInject);
        swAppSetArmProFindClass = (SetSwitch) findViewById(R.id.swAppSetArmProFindClass);
        swAppSetArmProGlobalMode1 = (SetSwitch) findViewById(R.id.swAppSetArmProGlobalMode1);
        swAppSetArmProGlobalMode2 = (SetSwitch) findViewById(R.id.swAppSetArmProGlobalMode2);
        swAppSetXamarinStandardMode1 = (SetSwitch) findViewById(R.id.swAppSetXamarinStandardMode1);
        swAppSetXamarinStandardMode2 = (SetSwitch) findViewById(R.id.swAppSetXamarinStandardMode2);
        swAppSetXamarinStandardMode3 = (SetSwitch) findViewById(R.id.swAppSetXamarinStandardMode3);
        swAppSetXamarinQurikMode = (SetSwitch) findViewById(R.id.swAppSetXamarinQurikMode);
        swAppSetAndluaCallMethod = (SetSwitch) findViewById(R.id.swAppSetAndluaCallMethod);
        swAppSetAndluaHttpTask = (SetSwitch) findViewById(R.id.swAppSetAndluaHttpTask);
        swAppSetAndluaRunFunc = (SetSwitch) findViewById(R.id.swAppSetAndluaRunFunc);
        swAppSetAndluaAlertDialog = (SetSwitch) findViewById(R.id.swAppSetAndluaAlertDialog);
        swAppSetGameGuardianMoreFunction = (SetSwitch) findViewById(R.id.swAppSetGameGuardianMoreFunction);
        swAppSetGameGuardianHttpFunction = (SetSwitch) findViewById(R.id.swAppSetGameGuardianHttpFunction);
        swAppSetGameGuardianMainFunction = (SetSwitch) findViewById(R.id.swAppSetGameGuardianMainFunction);
    }

    private void initListener() {
        btnAppSetBackLayout.setOnClickListener(this);
        tvAppSetAppName.setText(appName);
        swAppSetOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                JSONArray HookAppList = FileJsonUtils.readJsonArray(HdUtils.获取存储文件(), "packageNameList");
                if (b) {
                    if (!FileJsonUtils.hasPackageName(HookAppList, packageName)) {
                        HookAppList.put(packageName);
                        FileJsonUtils.writeJsonArray(HdUtils.获取存储文件(), "packageNameList", HookAppList);
                    }
                    String str_default = FileUtils.read(HdUtils.获取设置文件(packageName));
                    if (TextUtils.isEmpty(str_default)) {
                        FileUtils.createFolder(HdUtils.获取设置文件夹(packageName));
                        AssetsUtils.copy(HookSetActivity.this, "setting.json", HdUtils.获取设置文件(packageName));
                        FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), "ArmProFindClass", "");
                    }
                } else {
                    FileJsonUtils.deletePackageName(HdUtils.获取存储文件(), "packageNameList", HookAppList, packageName);
                    FileUtils.deleteFolder(HdUtils.获取设置文件夹(packageName));
                }
            }
        });
        swAppSetHdDebug.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetHdDebug.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetHdDebug.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }

        });
        swAppSetHideXposed.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetHideXposed.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetHideXposed.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }

        });
        swAppSetHideRoot.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetHideRoot.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetHideRoot.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetHideMagisk.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetHideMagisk.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetHideMagisk.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetHideTaiChiEpic.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetHideTaiChiEpic.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetHideTaiChiEpic.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetHideCydiaSubstrate.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetHideCydiaSubstrate.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetHideCydiaSubstrate.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetHideFrame.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetHideFrame.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetHideFrame.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetHideVpn.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetHideVpn.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetHideVpn.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetHideWifiProxy.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetHideWifiProxy.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetHideWifiProxy.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorHttp.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorHttp.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorHttp.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAnalogVersionCode.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                final EditText inputServer = new EditText(HookSetActivity.this);
                inputServer.setText(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), "analogVersionCode"));
                AlertDialog builder = new AlertDialog.Builder(HookSetActivity.this)
                        .setTitle("设置版本号")
                        .setMessage("清空则使用真实版本号\n限制[0~2147483647]区间的整数")
                        .setView(inputServer)
                        .setNegativeButton("取消", null)
                        .setNeutralButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                swAppSetAnalogVersionCode.setSwitchChecked(false);
                                String analogVersionCode = inputServer.getText().toString();
                                FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), "analogVersionCode", analogVersionCode);
                            }
                        })
                        .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                swAppSetAnalogVersionCode.setSwitchChecked(true);
                                String analogVersionCode = inputServer.getText().toString();
                                FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), "analogVersionCode", analogVersionCode);
                            }
                        })
                        .show();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAnalogVersionCode.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAnalogVersionName.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                final EditText inputServer = new EditText(HookSetActivity.this);
                inputServer.setText(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), "analogVersionName"));
                AlertDialog builder = new AlertDialog.Builder(HookSetActivity.this)
                        .setTitle("设置版本名")
                        .setMessage("清空则使用真实版本名")
                        .setView(inputServer)
                        .setNegativeButton("取消", null)
                        .setNeutralButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                swAppSetAnalogVersionName.setSwitchChecked(false);
                                String analogVersionName = inputServer.getText().toString();
                                FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), "analogVersionName", analogVersionName);
                            }
                        })
                        .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                swAppSetAnalogVersionName.setSwitchChecked(true);
                                String analogVersionName = inputServer.getText().toString();
                                FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), "analogVersionName", analogVersionName);
                            }
                        })
                        .show();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAnalogVersionName.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetCancelWindow.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetCancelWindow.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetCancelWindow.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetWindowKeywords.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                final EditText inputServer = new EditText(HookSetActivity.this);
                JSONArray windowKeywords = FileJsonUtils.readJsonArray(HdUtils.获取设置文件(packageName), "windowKeywords");
                inputServer.setText(windowKeywords.toString());
                AlertDialog builder = new AlertDialog.Builder(HookSetActivity.this)
                        .setTitle("拦截关键词")
                        .setMessage("关键词请转换为JSON数组\n参考格式:[\"1\",\"2\",\"3\"]")
                        .setView(inputServer)
                        .setNegativeButton("取消", null)
                        .setNeutralButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                swAppSetWindowKeywords.setSwitchChecked(false);
                            }
                        })
                        .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String windowKeyword = inputServer.getText().toString();
                                if (!windowKeyword.isEmpty() && !windowKeyword.equals("[]")) {
                                    swAppSetWindowKeywords.setSwitchChecked(true);
                                    try {
                                        JSONArray new_windowKeywords = new JSONArray(windowKeyword);
                                        FileJsonUtils.writeJsonArray(HdUtils.获取设置文件(packageName), "windowKeywords", new_windowKeywords);
                                    } catch (JSONException e) {
                                        Toast.makeText(HookSetActivity.this, "数据格式出错", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(HookSetActivity.this, "关键词不能为空", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .show();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetWindowKeywords.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorDialog.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorDialog.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorDialog.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorToast.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorToast.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorToast.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorPopupWindow.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorPopupWindow.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorPopupWindow.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorOnClick.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorOnClick.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorOnClick.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorOnLongClick.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorOnLongClick.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorOnLongClick.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorSetText.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorSetText.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorSetText.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorExit.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorExit.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorExit.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAlgorithmBase64.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetAlgorithmBase64.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAlgorithmBase64.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAlgorithmAesDesRsa.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetAlgorithmAesDesRsa.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAlgorithmAesDesRsa.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAlgorithmMd5Sha.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetAlgorithmMd5Sha.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAlgorithmMd5Sha.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAlgorithmHmac.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetAlgorithmHmac.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAlgorithmHmac.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorJsonObjectPut.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorJsonObjectPut.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorJsonObjectPut.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorJsonObjectGet.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorJsonObjectGet.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorJsonObjectGet.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorJsonArrayPut.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorJsonArrayPut.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorJsonArrayPut.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorJsonArrayGet.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorJsonArrayGet.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorJsonArrayGet.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorActivity.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorActivity.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorActivity.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetFileRead.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetFileRead.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetFileRead.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetFileWrite.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetFileWrite.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetFileWrite.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetFileDelete.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetFileDelete.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetFileDelete.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetFileAssets.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetFileAssets.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetFileAssets.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorSignatures.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorSignatures.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorSignatures.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetMonitorShell.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetMonitorShell.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetMonitorShell.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetScreen.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetScreen.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetScreen.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetStringEqual.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetStringEqual.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetStringEqual.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetFindContextBase.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetFindContextBase.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetFindContextBase.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetFindContextQihoo.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetFindContextQihoo.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetFindContextQihoo.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetFindContextTencent.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetFindContextTencent.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetFindContextTencent.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetFindContextBangcle.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetFindContextBangcle.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetFindContextBangcle.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetKillADsModeByteDance.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetKillADsModeByteDance.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetKillADsModeByteDance.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetKillADsModeTencent.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetKillADsModeTencent.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetKillADsModeTencent.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetKillADsModeMimo.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetKillADsModeMimo.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetKillADsModeMimo.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetKillSharePage.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetKillSharePage.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetKillSharePage.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetKillTpACE.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetKillTpACE.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetKillTpACE.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetCloudInject.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetCloudInject.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetCloudInject.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetArmProFindClass.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetArmProFindClass.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetArmProFindClass.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetArmProGlobalMode1.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                ArmProFindClass = FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), "ArmProFindClass");
                if (!TextUtils.isEmpty(ArmProFindClass)) {
                    swAppSetArmProGlobalMode1.setSwitchEnabled(true);
                    swAppSetArmProGlobalMode1.onSetSwitchChecked();
                } else {
                    Toast.makeText(HookSetActivity.this, "自定义类名不存在，无法开启", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetArmProGlobalMode1.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetArmProGlobalMode2.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                ArmProFindClass = FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), "ArmProFindClass");
                if (!TextUtils.isEmpty(ArmProFindClass)) {
                    swAppSetArmProGlobalMode2.setSwitchEnabled(true);
                    swAppSetArmProGlobalMode2.onSetSwitchChecked();
                } else {
                    Toast.makeText(HookSetActivity.this, "自定义类名不存在，无法开启", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetArmProGlobalMode2.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetXamarinStandardMode1.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetXamarinStandardMode1.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetXamarinStandardMode1.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetXamarinStandardMode2.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetXamarinStandardMode2.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetXamarinStandardMode2.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetXamarinStandardMode3.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetXamarinStandardMode3.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetXamarinStandardMode3.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetXamarinQurikMode.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetXamarinQurikMode.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetXamarinQurikMode.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAndluaCallMethod.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetAndluaCallMethod.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAndluaCallMethod.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAndluaHttpTask.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetAndluaHttpTask.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAndluaHttpTask.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAndluaRunFunc.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetAndluaRunFunc.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAndluaRunFunc.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetAndluaAlertDialog.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetAndluaAlertDialog.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetAndluaAlertDialog.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));

            }
        });
        swAppSetGameGuardianMoreFunction.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetGameGuardianMoreFunction.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetGameGuardianMoreFunction.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetGameGuardianHttpFunction.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetGameGuardianHttpFunction.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetGameGuardianHttpFunction.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
        swAppSetGameGuardianMainFunction.setOnSetSwitchListener(new SetSwitch.OnSetSwitchListener() {
            @Override
            public void onSetSwitchCheckedChanged(String IdName, boolean b) {
                if (b != Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName))) {
                    FileJsonUtils.writeJson(HdUtils.获取设置文件(packageName), IdName, String.valueOf(b));
                }
            }

            @Override
            public void onSetSwitchClick(View view) {
                swAppSetGameGuardianMainFunction.onSetSwitchChecked();
            }

            @Override
            public void initSetting(String IdName) {
                swAppSetGameGuardianMainFunction.setSwitchChecked(Boolean.valueOf(FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), IdName)));
            }
        });
    }

    private void initSetting() {
        String str_default = FileUtils.read(HdUtils.获取设置文件(packageName));
        if (!TextUtils.isEmpty(str_default)) {
            swAppSetOpen.setChecked(true);
        }
        JSONArray HookAppList = FileJsonUtils.readJsonArray(HdUtils.获取存储文件(), "packageNameList");
        if (FileJsonUtils.hasPackageName(HookAppList, packageName)) {
            ArmProFindClass = FileJsonUtils.readJson(HdUtils.获取设置文件(packageName), "ArmProFindClass");
            if (!TextUtils.isEmpty(ArmProFindClass)) {
                swAppSetArmProFindClass.setHelpText("TestFind -> " + ArmProFindClass);
                swAppSetArmProGlobalMode1.setSwitchEnabled(true);
                swAppSetArmProGlobalMode2.setSwitchEnabled(true);
            } else {
                swAppSetArmProGlobalMode1.setSwitchEnabled(false);
                swAppSetArmProGlobalMode2.setSwitchEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAppSetBackLayout:
                finish();
                break;
        }
    }

}
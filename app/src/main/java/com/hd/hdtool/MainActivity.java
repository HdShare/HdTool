package com.hd.hdtool;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.hdtool.activity.MenuActivity;
import com.hd.hdtool.adapter.AppListAdapter;
import com.hd.hdtool.bean.AppInfo;
import com.hd.hdtool.utils.AppListUtils;
import com.hd.hdtool.utils.AppInfoUtils;
import com.hd.hdtool.utils.AssetsUtils;
import com.hd.hdtool.utils.FileUtils;
import com.hd.hdtool.utils.HdUtils;
import com.hd.hdtool.utils.InputMethodUtils;
import com.hd.hdtool.utils.PermissionUtils;
import com.hd.hdtool.utils.ProgressDialogUtils;
import com.hd.hdtool.utils.FileJsonUtils;
import com.hd.hdtool.utils.TitleBarUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout dlMainLayout;
    private LinearLayout llMainFindLayout;
    private LinearLayout btnMainBackLayout;
    private EditText edtMainKey;
    private LinearLayout btnMainClear;
    private LinearLayout llMainTitleLayout;
    private LinearLayout btnMainMenu;
    private TextView tvMainXpStatus;
    private LinearLayout btnMainFind;
    private LinearLayout btnMainUserAppList;
    private LinearLayout btnMainSystemAppList;
    private TextView tvMainUserApp;
    private TextView tvMainSystemApp;
    private View viewMainUserAppline;
    private View viewMainSystemAppline;
    private RecyclerView rvMainAppList;
    private LinearLayout llMainMenuLayout;

    private boolean ApplistType = false;
    private ArrayList<AppInfo> listAllApp = new ArrayList<>();
    private ArrayList<AppInfo> listApp = new ArrayList<>();
    private AppListAdapter adapter;

    private String findApp_KeyCode = "";

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                listAllApp = AppListUtils.getAppList();
                getAppList(ApplistType);
            }
        }
    };

    private boolean getXpStatus() {
        return false;
    }

    private void getAppList(boolean isSystemApp) {
        listApp = AppListUtils.getAppList(listAllApp, isSystemApp);
        rvMainAppList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        getHookAppList();
        adapter = new AppListAdapter(listApp);
        rvMainAppList.setAdapter(adapter);
    }

    private void getHookAppList() {
        JSONArray jsonArray = FileJsonUtils.readJsonArray(HdUtils.获取存储文件(), "packageNameList");
        for (int j = 0; j < listApp.size(); j++) {
            listApp.get(j).setAppHooking(false);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    if (listApp.get(j).getAppPackageName().equals(jsonArray.getString(i))) {
                        listApp.get(j).setAppHooking(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        AppListUtils.CollatorApp_Hooking_And_Name(listApp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionUtils.checkPermission(MainActivity.this);
        TitleBarUtils.setColor(MainActivity.this);
        initSetEnvironment();

        initView();
        initListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            getHookAppList();
            adapter.notifyDataSetChanged();
        }
    }

    private void initSetEnvironment() {
        FileUtils.createFolder(HdUtils.获取存储文件夹());
        String str_default = FileUtils.read(HdUtils.获取存储文件());
        if (TextUtils.isEmpty(str_default)) {
            AssetsUtils.copy(this, "setting.json", HdUtils.获取存储文件());
            FileJsonUtils.writeJsonArray(HdUtils.获取存储文件(), "packageNameList", new JSONArray());
        }
    }

    private void initView() {
        dlMainLayout = (DrawerLayout) findViewById(R.id.dlMainLayout);
        llMainFindLayout = (LinearLayout) findViewById(R.id.llMainFindLayout);
        btnMainBackLayout = (LinearLayout) findViewById(R.id.btnMainBackLayout);
        edtMainKey = (EditText) findViewById(R.id.edtMainKey);
        btnMainClear = (LinearLayout) findViewById(R.id.btnMainClear);
        llMainTitleLayout = (LinearLayout) findViewById(R.id.llMainTitleLayout);
        btnMainMenu = (LinearLayout) findViewById(R.id.btnMainMenu);
        tvMainXpStatus = (TextView) findViewById(R.id.tvMainXpStatus);
        btnMainFind = (LinearLayout) findViewById(R.id.btnMainFind);
        btnMainUserAppList = (LinearLayout) findViewById(R.id.btnMainUserAppList);
        btnMainSystemAppList = (LinearLayout) findViewById(R.id.btnMainSystemAppList);
        tvMainUserApp = (TextView) findViewById(R.id.tvMainUserApp);
        tvMainSystemApp = (TextView) findViewById(R.id.tvMainSystemApp);
        viewMainUserAppline = (View) findViewById(R.id.viewMainUserAppline);
        viewMainSystemAppline = (View) findViewById(R.id.viewMainSystemAppline);
        rvMainAppList = (RecyclerView) findViewById(R.id.rvMainAppList);
        llMainMenuLayout = (LinearLayout) findViewById(R.id.llMainMenuLayout);

        MenuActivity.addMenuView(MainActivity.this, llMainMenuLayout);

    }

    private void initListener() {
        btnMainBackLayout.setOnClickListener(this);
        edtMainKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                findApp_KeyCode = editable.toString();
                findApp(findApp_KeyCode, ApplistType);
            }
        });
        btnMainClear.setOnClickListener(this);
        if (getXpStatus()) {
            tvMainXpStatus.setText(getResources().getText(R.string.app_name) + " " + AppInfoUtils.getVersionName(MainActivity.this) + " [已激活] ");
            tvMainXpStatus.setTextColor(Color.parseColor(HdUtils.获取XP_已激活颜色()));
            ProgressDialogUtils progressDialogUtils = new ProgressDialogUtils(MainActivity.this, handler);
            progressDialogUtils.execute((Void) null);
        } else {
            tvMainXpStatus.setText(getResources().getText(R.string.app_name) + " " + AppInfoUtils.getVersionName(MainActivity.this) + " [未激活] ");
            tvMainXpStatus.setTextColor(Color.parseColor(HdUtils.获取XP_未激活颜色()));
        }
        btnMainMenu.setOnClickListener(this);
        btnMainFind.setOnClickListener(this);
        btnMainUserAppList.setOnClickListener(this);
        btnMainSystemAppList.setOnClickListener(this);
        viewMainUserAppline.setBackgroundColor(Color.parseColor(HdUtils.获取应用_已选择下划线颜色()));
        viewMainSystemAppline.setBackgroundColor(Color.parseColor(HdUtils.获取应用_未选择下划线颜色()));
    }

    private void findApp(String key, boolean isSystemApp) {
        listApp = AppListUtils.getAppList(listAllApp, isSystemApp);
        if (adapter != null) {
            ArrayList<AppInfo> tempList = new ArrayList<>();
            for (int i = 0; i < listApp.size(); i++) {
                if (listApp.get(i).getAppName().toLowerCase().contains(key.toLowerCase()) || listApp.get(i).getAppPackageName().toLowerCase().contains(key.toLowerCase())) {
                    tempList.add(listApp.get(i));
                }
            }
            AppListUtils.CollatorApp_Hooking_And_Name(tempList);
            adapter = new AppListAdapter(tempList);
            rvMainAppList.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMainBackLayout:
                llMainFindLayout.setVisibility(View.GONE);
                llMainTitleLayout.setVisibility(View.VISIBLE);
                edtMainKey.setText("");
                InputMethodUtils.hideKeyboard(edtMainKey);
                break;
            case R.id.btnMainClear:
                edtMainKey.setText("");
                break;
            case R.id.btnMainMenu:
                if (dlMainLayout.isDrawerOpen(llMainMenuLayout)) {
                    dlMainLayout.closeDrawer(llMainMenuLayout);
                } else {
                    dlMainLayout.openDrawer(llMainMenuLayout);
                }
                break;
            case R.id.btnMainFind:
                llMainFindLayout.setVisibility(View.VISIBLE);
                llMainTitleLayout.setVisibility(View.GONE);
                edtMainKey.requestFocus();
                edtMainKey.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodUtils.showKeyboard(edtMainKey);
                    }
                });
                break;
            case R.id.btnMainUserAppList:
                tvMainUserApp.setTextColor(Color.parseColor("#ffffff"));
                tvMainSystemApp.setTextColor(Color.parseColor("#bbbbbb"));
                viewMainUserAppline.setBackgroundColor(Color.parseColor(HdUtils.获取应用_已选择下划线颜色()));
                viewMainSystemAppline.setBackgroundColor(Color.parseColor(HdUtils.获取应用_未选择下划线颜色()));
                ApplistType = false;
                listApp = AppListUtils.getAppList(listAllApp, ApplistType);
                findApp(findApp_KeyCode, ApplistType);
                break;
            case R.id.btnMainSystemAppList:
                tvMainUserApp.setTextColor(Color.parseColor("#bbbbbb"));
                tvMainSystemApp.setTextColor(Color.parseColor("#ffffff"));
                viewMainUserAppline.setBackgroundColor(Color.parseColor(HdUtils.获取应用_未选择下划线颜色()));
                viewMainSystemAppline.setBackgroundColor(Color.parseColor(HdUtils.获取应用_已选择下划线颜色()));
                ApplistType = true;
                listApp = AppListUtils.getAppList(listAllApp, ApplistType);
                findApp(findApp_KeyCode, ApplistType);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (llMainFindLayout.getVisibility() == View.VISIBLE) {
                llMainFindLayout.setVisibility(View.GONE);
                llMainTitleLayout.setVisibility(View.VISIBLE);
                edtMainKey.setText("");
            }
            if (dlMainLayout.isDrawerOpen(llMainMenuLayout)) {
                dlMainLayout.closeDrawer(llMainMenuLayout);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
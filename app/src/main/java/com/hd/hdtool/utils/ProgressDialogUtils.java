package com.hd.hdtool.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressDialogUtils extends AsyncTask<Void, Integer, Integer> {

    private AppCompatActivity activity;
    private Handler handler;
    private ProgressDialog progressDialog;

    public ProgressDialogUtils(AppCompatActivity activity, Handler handler) {
        this.activity = activity;
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("正在加载应用列表...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        AppListUtils.initAppList(activity, progressDialog);
        return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
        progressDialog.dismiss();
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }

}
package com.hd.hdtool.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.hdtool.R;
import com.hd.hdtool.activity.HookSetActivity;
import com.hd.hdtool.bean.AppInfo;
import com.hd.hdtool.utils.HdUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    List<AppInfo> list;

    public AppListAdapter(List<AppInfo> list) {
        this.list = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAppIcon;
        TextView tvAppName, tvVersionName, tvPackageName, tvInstallTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAppIcon = (ImageView) itemView.findViewById(R.id.imgItemAppIcon);
            tvAppName = (TextView) itemView.findViewById(R.id.tvItemAppName);
            tvVersionName = (TextView) itemView.findViewById(R.id.tvItemVersionName);
            tvPackageName = (TextView) itemView.findViewById(R.id.tvItemPackageName);
            tvInstallTime = (TextView) itemView.findViewById(R.id.tvItemInstallTime);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main_appinfo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfo appInfo = list.get(position);
        holder.imgAppIcon.setImageDrawable(appInfo.getAppIcon());
        holder.tvAppName.setText(appInfo.getAppName());
        holder.tvVersionName.setText(appInfo.getAppVersionName());
        holder.tvPackageName.setText(appInfo.getAppPackageName());
        holder.tvPackageName.setTextColor(appInfo.isAppHooking() ? Color.parseColor(HdUtils.获取XP_已Hook颜色()) : Color.parseColor(HdUtils.获取XP_未Hook颜色()));
        holder.tvInstallTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(appInfo.getAppLastUpdateTime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HookSetActivity.class);
                intent.putExtra("appName", appInfo.getAppName());
                intent.putExtra("packageName", appInfo.getAppPackageName());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

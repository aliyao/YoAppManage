package com.yoyo.yoappmanage.module.installed.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseAdapter;
import com.yoyo.yoappmanage.base.OnBaseRecyclerViewListener;
import com.yoyo.yoappmanage.entity.InstalledInfoEntity;
import com.yoyo.yoappmanage.module.installed.adapter.holder.InstalledViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/20 17:27
 * 修改人：yoyo
 * 修改时间：2016/6/20 17:27
 * 修改备注：
 */
public class InstalledAdapter extends BaseAdapter<InstalledInfoEntity, InstalledViewHolder> {
    List<String>  unInstallPackageNameList;

    public InstalledAdapter(@NonNull List<InstalledInfoEntity> mData, OnBaseRecyclerViewListener onBaseRecyclerViewListener) {
        super(mData,onBaseRecyclerViewListener);
    }

    @Override
    public InstalledViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.installed_listview_item, null);
        //view.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        InstalledViewHolder viewHolder = new InstalledViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InstalledViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        InstalledInfoEntity installedInfoEntity = getItem(position);
        holder.iv_icon.setImageDrawable(installedInfoEntity.getIcon());
        holder.tv_name.setText(installedInfoEntity.getName());
        holder.tv_packagename.setText(installedInfoEntity.getPackageName());
        holder.tv_version_name.setText(holder.version_tip+" "+installedInfoEntity.getVersionName());
        if(unInstallPackageNameList!=null&&unInstallPackageNameList.contains(installedInfoEntity.getPackageName())){
            holder.itemView.setEnabled(false);
            holder.list_item_click.setEnabled(false);
            holder.progress_small.setVisibility(View.VISIBLE);
        }else{
            holder.itemView.setEnabled(true);
            holder.list_item_click.setEnabled(true);
            holder.progress_small.setVisibility(View.GONE);
        }
       // holder.tv_label.setVisibility(View.GONE);
    }

    public void add(String packageName){
        if(TextUtils.isEmpty(packageName)){
            return;
        }
        if(unInstallPackageNameList==null){
            unInstallPackageNameList=new ArrayList<>();
        }
        if(unInstallPackageNameList.contains(packageName)){//已存在
            return;
        }
        unInstallPackageNameList.add(packageName);
    }

    public void remove(String packageName){
        if(TextUtils.isEmpty(packageName)){
            return;
        }
        if(unInstallPackageNameList==null){
            return;
        }
        if(unInstallPackageNameList.contains(packageName)){//已存在
            unInstallPackageNameList.remove(packageName);
        }
    }
}
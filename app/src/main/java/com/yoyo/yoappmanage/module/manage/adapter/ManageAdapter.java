package com.yoyo.yoappmanage.module.manage.adapter;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseAdapter;
import com.yoyo.yoappmanage.entity.ManageInfoEntity;
import com.yoyo.yoappmanage.module.manage.adapter.holder.ManageViewHolder;

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
public class ManageAdapter extends BaseAdapter<ManageInfoEntity, ManageViewHolder> {

    public ManageAdapter(@NonNull List<ManageInfoEntity> mData) {
        super(mData);
    }

    @Override
    public ManageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_listview_item, null);
        //view.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ManageViewHolder viewHolder = new ManageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ManageViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ManageInfoEntity manageInfo = getItem(position);
        //holder.iv_icon;
        holder.tv_name.setText(manageInfo.getName());
        holder.tv_packagename.setText(manageInfo.getPackageName());
        holder.tv_label.setVisibility(View.GONE);
    }
}
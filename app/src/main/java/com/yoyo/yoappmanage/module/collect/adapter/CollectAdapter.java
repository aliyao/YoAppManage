package com.yoyo.yoappmanage.module.collect.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseAdapter;
import com.yoyo.yoappmanage.base.OnBaseRecyclerViewListener;
import com.yoyo.yoappmanage.entity.CollectInfoEntity;
import com.yoyo.yoappmanage.module.collect.adapter.holder.CollectViewHolder;

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
public class CollectAdapter extends BaseAdapter<CollectInfoEntity, CollectViewHolder> {

    public CollectAdapter(@NonNull List<CollectInfoEntity> mData, OnBaseRecyclerViewListener onBaseRecyclerViewListener) {
        super(mData,onBaseRecyclerViewListener);
    }

    @Override
    public CollectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_listview_item, null);
        //view.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        CollectViewHolder viewHolder = new CollectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CollectViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CollectInfoEntity collectInfoEntity = getItem(position);
        //holder.iv_icon.setImageBitmap();
        holder.tv_name.setText(collectInfoEntity.getName());
        holder.tv_packagename.setText(collectInfoEntity.getPackageName());
        holder.tv_version_name.setText(holder.version_tip+" "+collectInfoEntity.getVersionName());
       // holder.tv_label.setVisibility(View.GONE);
    }
}
package com.yoyo.yoappmanage.module.manage.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseRecyclerViewViewHolder;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：YoPassword
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/5/18 17:45
 * 修改人：yoyo
 * 修改时间：2016/5/18 17:45
 * 修改备注：
 */
public class ManageViewHolder extends BaseRecyclerViewViewHolder {
    @BindView(R.id.iv_icon)
    public ImageView iv_icon;
    @BindView(R.id.tv_name)
    public TextView tv_name;
    @BindView(R.id.tv_packagename)
    public TextView tv_packagename;
   /* @BindView(R.id.tv_label)
    public TextView tv_label;*/
    @BindView(R.id.tv_version_name)
    public TextView tv_version_name;
    @BindString(R.string.version_tip)
    public String version_tip;

    public ManageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

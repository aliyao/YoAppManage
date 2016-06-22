package com.yoyo.yoappmanage.module.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.module.installed.fragment.InstalledFragment;
import com.yoyo.yoappmanage.module.collect.fragment.CollectFragment;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/20 15:50
 * 修改人：yoyo
 * 修改时间：2016/6/20 15:50
 * 修改备注：
 */
public class MainSectionsPagerAdapter  extends FragmentPagerAdapter {

    Context context;
    public MainSectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 1:
                fragment= CollectFragment.newInstance(position + 1);
                break;
            default:
                fragment = InstalledFragment.newInstance(position + 1);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return context.getResources().getString(R.string.action_collect);
            default:
                return context.getResources().getString(R.string.action_installed);
        }
    }
}

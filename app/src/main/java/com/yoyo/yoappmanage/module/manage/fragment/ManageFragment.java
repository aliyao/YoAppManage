package com.yoyo.yoappmanage.module.manage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.module.BaseFragment;

/**
 * 项目名称：YoAppManage
 * 类描述：管理
 * 创建人：yoyo
 * 创建时间：2016/6/20 15:39
 * 修改人：yoyo
 * 修改时间：2016/6/20 15:39
 * 修改备注：
 */
public class ManageFragment extends BaseFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ManageFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ManageFragment newInstance(int sectionNumber) {
        ManageFragment fragment = new ManageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage, container, false);
       // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
       // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}
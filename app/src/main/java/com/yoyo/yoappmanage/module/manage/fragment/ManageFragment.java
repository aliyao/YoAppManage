package com.yoyo.yoappmanage.module.manage.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoyo.common.view.OnToDoItemClickListener;
import com.yoyo.common.view.RefreshLayout;
import com.yoyo.common.view.YoSnackbar;
import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseFragment;
import com.yoyo.yoappmanage.base.OnBaseRecyclerViewListener;
import com.yoyo.yoappmanage.common.util.AlertDialogUtils;
import com.yoyo.yoappmanage.common.util.ApkUtil;
import com.yoyo.yoappmanage.common.util.FileUtil;
import com.yoyo.yoappmanage.common.util.X3DBUtils;
import com.yoyo.yoappmanage.config.AppConfig;
import com.yoyo.yoappmanage.entity.ManageInfoEntity;
import com.yoyo.yoappmanage.module.manage.adapter.ManageAdapter;
import com.yoyo.yoappmanage.module.manage.utils.AppInfoProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 项目名称：YoAppManage
 * 类描述：管理
 * 创建人：yoyo
 * 创建时间：2016/6/20 15:39
 * 修改人：yoyo
 * 修改时间：2016/6/20 15:39
 * 修改备注：
 */
public class ManageFragment extends BaseFragment implements OnBaseRecyclerViewListener {
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ManageAdapter manageAdapter;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public ManageFragment() {
    }

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
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }


    @Override
    protected void init() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshAdapter();
                    }
                }, AppConfig.RefreshViewTime);
            }
        });

        recyclerView.setHasFixedSize(true);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        List<ManageInfoEntity> list = getNotSystemAppInfo();
        manageAdapter = new ManageAdapter(list,this);
        //passwordAdapter.setOnRecyclerViewListener(this);
        //设置adapter
        recyclerView.setAdapter(manageAdapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
         /*   recyclerViewPassword.addItemDecoration(new DividerItemDecoration(
                    getActivity(), DividerItemDecoration.HORIZONTAL_LIST));*/

        // int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space_item_decoration);
        //recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
    }

    public void refreshAdapter() {
        //刷新PagerAdapter
        Observable.create(new Observable.OnSubscribe<List<ManageInfoEntity>>() {
            @Override
            public void call(Subscriber<? super List<ManageInfoEntity>> subscriber) {
                List<ManageInfoEntity> listResult = getNotSystemAppInfo();
                subscriber.onNext(listResult);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ManageInfoEntity>>() {
                    @Override
                    public void call(List<ManageInfoEntity> list) {
                        manageAdapter.setmData(list);
                        manageAdapter.notifyDataSetChanged();
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                });
    }

    /**
     * 获取非系统APP信息
     *
     * @return
     */
    private List<ManageInfoEntity> getNotSystemAppInfo() {
        AppInfoProvider appInfoProvider = new AppInfoProvider(getContext());
        List<ManageInfoEntity> listAllApps = appInfoProvider.getAllApps();
        List<ManageInfoEntity> listResult = new ArrayList<>();
        String myAppPackageName = this.getActivity().getPackageName();
        for (ManageInfoEntity manageInfoEntity :
                listAllApps) {
            if (!manageInfoEntity.isSystemApp() && !manageInfoEntity.getPackageName().equals(myAppPackageName)) {
                listResult.add(manageInfoEntity);
            }
        }
        return listResult;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemLongClick(final int position) {
        String[] toDo = getContext().getResources().getStringArray(R.array.alert_dialog_list_todo_manage_item_long_click);
        AlertDialogUtils.showAlertDialogList(getContext(), toDo, new OnToDoItemClickListener() {

            @Override
            public void onItemClick(DialogInterface dialog, int which) {
                ManageInfoEntity manageInfoEntity=manageAdapter.getItem(position);
                switch (which) {
                    case 0:
                        install(manageInfoEntity);
                        break;
                    case 1:
                        unInstall(manageInfoEntity);
                        break;
                    case 2:
                        delectInstall(manageInfoEntity);
                        break;
                }

            }
        });
        return false;
    }

    private void install(ManageInfoEntity manageInfoEntity){
        try {
            String apkSystemPath=manageInfoEntity.getApkSystemPath();
            if(new File(apkSystemPath).exists()){
                YoSnackbar.showSnackbar(ManageFragment.this.getView(),R.string.to_do_installed);
                refreshAdapter();
                return;
            }
            String apkPath=manageInfoEntity.getApkPath();
            if(!new File(apkPath).exists()){
                YoSnackbar.showSnackbar(ManageFragment.this.getView(),R.string.to_do_error);
                refreshAdapter();
                return;
            }
            ApkUtil.install(ManageFragment.this.getActivity(),apkPath);
            refreshAdapter();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void unInstall(ManageInfoEntity saveManageInfoEntity){
        try {
            String apkSystemPath=saveManageInfoEntity.getApkSystemPath();
            File apkSystemFile=new File(apkSystemPath);
            String apkSystemFileName=apkSystemFile.getName();
            if(!apkSystemFileName.endsWith(".apk") ){//是否为apk文件
                YoSnackbar.showSnackbar(ManageFragment.this.getView(),R.string.manage_item_not_found);
                return;
            }
            if(!apkSystemFile.exists()){
                //X3DBUtils.delectById(ManageInfoEntity.class, manageAdapter.getItem(position).getPackageName());
                //refreshAdapter();
                YoSnackbar.showSnackbar(ManageFragment.this.getView(),R.string.manage_item_not_found);
                return;
            }
            String apkFile=FileUtil.getDiskCacheDirApkPath(getActivity());
            boolean isCopySuccess= FileUtil.copyFile(apkSystemPath ,apkFile,apkSystemFileName);

            if(!isCopySuccess){
                YoSnackbar.showSnackbar(ManageFragment.this.getView(),R.string.to_do_error);
                return;
            }
            if(!new File(apkFile).exists()){
                YoSnackbar.showSnackbar(ManageFragment.this.getView(),R.string.to_do_error);
                return;
            }
            saveManageInfoEntity.setApkPath(apkFile);
            boolean isSaveSuccess=X3DBUtils.save(saveManageInfoEntity);
            if(!isSaveSuccess){
                YoSnackbar.showSnackbar(ManageFragment.this.getView(),R.string.to_do_error);
                return;
            }
            ApkUtil.unInstall(ManageFragment.this.getActivity(),saveManageInfoEntity.getPackageName());
            refreshAdapter();
        }catch (Exception e){
            e.printStackTrace();
            YoSnackbar.showSnackbar(ManageFragment.this.getView(),R.string.to_do_error);
            return;
        }

    }

    private void delectInstall(final ManageInfoEntity manageInfoEntity){
        AlertDialogUtils.showAlertDialog(getContext(), R.string.manage_item_delect_todo, new OnToDoItemClickListener() {
            @Override
            public void onPositiveClick(DialogInterface dialog, int which) {
                super.onPositiveClick(dialog, which);
                boolean isSaveSuccess=X3DBUtils.delectById(ManageInfoEntity.class,manageInfoEntity.getPackageName());
                if(!isSaveSuccess){
                    YoSnackbar.showSnackbar(ManageFragment.this.getView(),R.string.to_do_error);
                    return;
                }
                refreshAdapter();
            }
        });
    }

    @Override
    public void onResume() {
        refreshAdapter();
        super.onResume();
    }
}
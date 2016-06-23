package com.yoyo.yoappmanage.module.installed.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoyo.common.view.OnToDoItemClickListener;
import com.yoyo.common.view.RefreshLayout;
import com.yoyo.common.view.YoSnackbar;
import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseFragment;
import com.yoyo.yoappmanage.base.OnBaseRecyclerViewListener;
import com.yoyo.yoappmanage.common.tool.InstallCollectTools;
import com.yoyo.yoappmanage.common.util.AlertDialogUtils;
import com.yoyo.yoappmanage.common.util.ApkUtil;
import com.yoyo.yoappmanage.common.util.BitampUtils;
import com.yoyo.yoappmanage.common.util.FileUtil;
import com.yoyo.yoappmanage.common.util.X3DBUtils;
import com.yoyo.yoappmanage.config.AppConfig;
import com.yoyo.yoappmanage.entity.CollectInfoEntity;
import com.yoyo.yoappmanage.entity.InstalledInfoEntity;
import com.yoyo.yoappmanage.entity.RxJavaTodoEntity;
import com.yoyo.yoappmanage.module.installed.adapter.InstalledAdapter;
import com.yoyo.yoappmanage.module.installed.utils.AppInfoProvider;

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
 * 类描述：已安装
 * 创建人：yoyo
 * 创建时间：2016/6/20 15:39
 * 修改人：yoyo
 * 修改时间：2016/6/20 15:39
 * 修改备注：
 */
public class InstalledFragment extends BaseFragment implements OnBaseRecyclerViewListener {
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    InstalledAdapter installedAdapter;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public InstalledFragment() {
    }

    public static InstalledFragment newInstance(int sectionNumber) {
        InstalledFragment fragment = new InstalledFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_installed, container, false);
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
        List<InstalledInfoEntity> list = getNotSystemAppInfo();
        installedAdapter = new InstalledAdapter(list, this);
        //passwordAdapter.setOnRecyclerViewListener(this);
        //设置adapter
        recyclerView.setAdapter(installedAdapter);
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
        Observable.create(new Observable.OnSubscribe<List<InstalledInfoEntity>>() {
            @Override
            public void call(Subscriber<? super List<InstalledInfoEntity>> subscriber) {
                List<InstalledInfoEntity> listResult = getNotSystemAppInfo();
                subscriber.onNext(listResult);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<InstalledInfoEntity>>() {
                    @Override
                    public void call(List<InstalledInfoEntity> list) {
                        installedAdapter.setmData(list);
                        installedAdapter.notifyDataSetChanged();
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
    private List<InstalledInfoEntity> getNotSystemAppInfo() {
        AppInfoProvider appInfoProvider = new AppInfoProvider(getContext());
        List<InstalledInfoEntity> listAllApps = appInfoProvider.getAllApps();
        List<InstalledInfoEntity> listResult = new ArrayList<>();
        String myAppPackageName = this.getActivity().getPackageName();
        for (InstalledInfoEntity installedInfoEntity :
                listAllApps) {
            if (!installedInfoEntity.isSystemApp() && !installedInfoEntity.getPackageName().equals(myAppPackageName)) {
                listResult.add(installedInfoEntity);
            }
        }
        return listResult;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemLongClick(final int position) {
        String[] toDo = getContext().getResources().getStringArray(R.array.alert_dialog_list_todo_installed_item_long_click);
        AlertDialogUtils.showAlertDialogList(getContext(), toDo, new OnToDoItemClickListener() {

                    @Override
                    public void onItemClick(DialogInterface dialog, final int which) {
                        switch (which) {
                            case 1:
                                unInstall(installedAdapter.getItem(position));
                                break;
                            case 0:
                                installedAdapter.add(installedAdapter.getItem(position).getPackageName());
                                installedAdapter.notifyDataSetChanged();
                                Observable.create(new Observable.OnSubscribe<RxJavaTodoEntity>() {
                                    @Override
                                    public void call(Subscriber<? super RxJavaTodoEntity> subscriber) {
                                        InstalledInfoEntity installedInfoEntity=installedAdapter.getItem(position);
                                        Bitmap iconBitmap= BitampUtils.drawableToBitamp(installedInfoEntity.getIcon());
                                        String iconSavePath=FileUtil.getDiskCacheDirIconImgPath(getContext());
                                        String saveIconBitmapFilePath=BitampUtils.saveBitmapIcon(iconBitmap,iconSavePath);
                                        CollectInfoEntity collectInfoEntity=InstallCollectTools.InstallToCollectInfo(installedInfoEntity,saveIconBitmapFilePath);
                                        RxJavaTodoEntity  rxJavaTodoEntity = unInstallDelInfo(collectInfoEntity);
                                        subscriber.onNext(rxJavaTodoEntity);
                                        subscriber.onCompleted();
                                    }
                                })
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<RxJavaTodoEntity>() {
                                            @Override
                                            public void call(RxJavaTodoEntity rxJavaTodoEntity) {
                                                installedAdapter.remove(installedAdapter.getItem(position).getPackageName());
                                                installedAdapter.notifyDataSetChanged();
                                                if (rxJavaTodoEntity == null) {
                                                    return;
                                                }
                                                if (rxJavaTodoEntity.isSuccess()) {
                                                    refreshAdapter();
                                                }
                                                YoSnackbar.showSnackbar(InstalledFragment.this.getView(), rxJavaTodoEntity.getrTipText());


                                            }
                                        });
                                break;
                        }

                    }
                }

        );
        return false;
    }

    private void unInstall(InstalledInfoEntity installedInfoEntity) {
        if (installedInfoEntity == null || TextUtils.isEmpty(installedInfoEntity.getPackageName())) {
            return;
        }
        ApkUtil.unInstall(InstalledFragment.this.getActivity(), installedInfoEntity.getPackageName());
    }

    private RxJavaTodoEntity unInstallDelInfo(CollectInfoEntity collectInfoEntity) {
        RxJavaTodoEntity rxJavaTodoEntity = new RxJavaTodoEntity(false, R.string.to_do_error);
        try {
            String apkSystemPath = collectInfoEntity.getApkSystemPath();
            File apkSystemFile = new File(apkSystemPath);
            String apkSystemFileName = apkSystemFile.getName();
            if (!apkSystemFileName.endsWith(".apk")) {//是否为apk文件
                rxJavaTodoEntity.setrTipText(R.string.item_not_found);
                rxJavaTodoEntity.setSuccess(false);
                return rxJavaTodoEntity;
            }
            if (!apkSystemFile.exists()) {
                //X3DBUtils.delectById(InstalledInfoEntity.class, installedAdapter.getItem(position).getPackageName());
                //refreshAdapter();
                rxJavaTodoEntity.setrTipText(R.string.item_not_found);
                rxJavaTodoEntity.setSuccess(false);
                return rxJavaTodoEntity;
            }
            String apkFilePath = FileUtil.getDiskCacheDirApkPath(getActivity());
            boolean isCopySuccess = FileUtil.copyFile(apkSystemPath, apkFilePath, apkSystemFileName);
            String apkFile = FileUtil.getDiskCacheDirApkPath(getActivity())+apkSystemFileName;
            if (!isCopySuccess) {
                return rxJavaTodoEntity;
            }
            if (!new File(apkFile).exists()) {
                return rxJavaTodoEntity;
            }
            collectInfoEntity.setApkPath(apkFile);
            boolean isSaveSuccess = X3DBUtils.save(collectInfoEntity);
            if (!isSaveSuccess) {
                return rxJavaTodoEntity;
            }
            ApkUtil.unInstall(InstalledFragment.this.getActivity(), collectInfoEntity.getPackageName());
            rxJavaTodoEntity.setrTipText(0);
            rxJavaTodoEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rxJavaTodoEntity;
    }

    @Override
    public void onResume() {
        refreshAdapter();
        super.onResume();
    }
}
package com.yoyo.yoappmanage.module.collect.fragment;

import android.content.DialogInterface;
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
import com.yoyo.yoappmanage.common.util.AlertDialogUtils;
import com.yoyo.yoappmanage.common.util.ApkUtil;
import com.yoyo.yoappmanage.common.util.X3DBUtils;
import com.yoyo.yoappmanage.config.AppConfig;
import com.yoyo.yoappmanage.entity.CollectInfoEntity;
import com.yoyo.yoappmanage.entity.RxJavaTodoEntity;
import com.yoyo.yoappmanage.module.collect.adapter.CollectAdapter;

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
 * 类描述：收藏
 * 创建人：yoyo
 * 创建时间：2016/6/20 15:42
 * 修改人：yoyo
 * 修改时间：2016/6/20 15:42
 * 修改备注：
 */
public class CollectFragment extends BaseFragment implements OnBaseRecyclerViewListener {
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    CollectAdapter collectAdapter;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public CollectFragment() {
    }

    public static CollectFragment newInstance(int sectionNumber) {
        CollectFragment fragment = new CollectFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collect, container, false);
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
        List<CollectInfoEntity> list = getAppInfo();
        collectAdapter = new CollectAdapter(list, this);
        //设置adapter
        recyclerView.setAdapter(collectAdapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void refreshAdapter() {
        //刷新PagerAdapter
        Observable.create(new Observable.OnSubscribe<List<CollectInfoEntity>>() {
            @Override
            public void call(Subscriber<? super List<CollectInfoEntity>> subscriber) {
                List<CollectInfoEntity> listResult = getAppInfo();
                subscriber.onNext(listResult);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CollectInfoEntity>>() {
                    @Override
                    public void call(List<CollectInfoEntity> list) {
                        collectAdapter.setmData(list);
                        collectAdapter.notifyDataSetChanged();
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
    private List<CollectInfoEntity> getAppInfo() {
        List<CollectInfoEntity> listAllApps = X3DBUtils.findAll(CollectInfoEntity.class);
        if(listAllApps==null){
            return listAllApps;
        }
        List<CollectInfoEntity> listResult = new ArrayList<>();
        String myAppPackageName = this.getActivity().getPackageName();
        for (CollectInfoEntity collectInfoEntity :
                listAllApps) {
            if (!collectInfoEntity.getPackageName().equals(myAppPackageName)) {
                listResult.add(collectInfoEntity);
            }
        }
        return listResult;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemLongClick(final int position) {
        String[] toDo = getContext().getResources().getStringArray(R.array.alert_dialog_list_todo_collect_item_long_click);
        AlertDialogUtils.showAlertDialogList(getContext(), toDo, new OnToDoItemClickListener() {

                    @Override
                    public void onItemClick(DialogInterface dialog, final int which) {
                        switch (which) {
                            case 0:
                                Observable.create(new Observable.OnSubscribe<RxJavaTodoEntity>() {
                                    @Override
                                    public void call(Subscriber<? super RxJavaTodoEntity> subscriber) {
                                        RxJavaTodoEntity rxJavaTodoEntity = install(collectAdapter.getItem(position));
                                        subscriber.onNext(rxJavaTodoEntity);
                                        subscriber.onCompleted();
                                    }
                                })
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<RxJavaTodoEntity>() {
                                            @Override
                                            public void call(RxJavaTodoEntity rxJavaTodoEntity) {
                                                if (rxJavaTodoEntity == null) {
                                                    return;
                                                }
                                                if (rxJavaTodoEntity.isSuccess()) {
                                                    refreshAdapter();
                                                }
                                                YoSnackbar.showSnackbar(CollectFragment.this.getView(), rxJavaTodoEntity.getrTipText());
                                            }
                                        });
                                break;
                            case 1:
                                delectInstall(collectAdapter.getItem(position));
                                break;
                        }
                    }
                }
        );
        return false;
    }

    private RxJavaTodoEntity install(CollectInfoEntity collectInfoEntity) {
        RxJavaTodoEntity rxJavaTodoEntity = new RxJavaTodoEntity(false, R.string.to_do_error);
        try {
            String apkSystemPath = collectInfoEntity.getApkSystemPath();
            if (new File(apkSystemPath).exists()) {
                rxJavaTodoEntity.setrTipText(R.string.to_do_installed);
                rxJavaTodoEntity.setSuccess(true);
                return rxJavaTodoEntity;
            }
            String apkPath = collectInfoEntity.getApkPath();
            if (!new File(apkPath).exists()) {
                return rxJavaTodoEntity;
            }
            ApkUtil.install(CollectFragment.this.getActivity(), apkPath);
            rxJavaTodoEntity.setrTipText(0);
            rxJavaTodoEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rxJavaTodoEntity;
    }

    private void delectInstall(final CollectInfoEntity collectInfoEntity) {
        AlertDialogUtils.showAlertDialog(getContext(),R.string.item_delect_todo,new OnToDoItemClickListener(){
            @Override
            public void onPositiveClick(DialogInterface dialog, int which) {
                super.onPositiveClick(dialog, which);
                Observable.create(new Observable.OnSubscribe<RxJavaTodoEntity>() {
                    @Override
                    public void call(Subscriber<? super RxJavaTodoEntity> subscriber) {

                        RxJavaTodoEntity rxJavaTodoEntity = new RxJavaTodoEntity(false, R.string.to_do_error);
                        boolean isSaveSuccess = X3DBUtils.delectById(CollectInfoEntity.class, collectInfoEntity.getPackageName());
                        if (isSaveSuccess) {
                            rxJavaTodoEntity.setSuccess(true);
                            rxJavaTodoEntity.setrTipText(0);
                            String apkPath = collectInfoEntity.getApkPath();
                            if (!TextUtils.isEmpty(apkPath)) {
                                File delApkPathFile = new File(apkPath);
                                if (delApkPathFile.exists()) {
                                    delApkPathFile.delete();
                                }
                            }

                            String iconImg = collectInfoEntity.getIconPath();
                            if (!TextUtils.isEmpty(iconImg)) {
                                File delIconImgFile = new File(iconImg);
                                if (delIconImgFile.exists()) {
                                    delIconImgFile.delete();
                                }
                            }
                        }

                        subscriber.onNext(rxJavaTodoEntity);
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RxJavaTodoEntity>() {
                            @Override
                            public void call(RxJavaTodoEntity rxJavaTodoEntity) {
                                if (rxJavaTodoEntity == null) {
                                    return;
                                }
                                if (rxJavaTodoEntity.isSuccess()) {
                                    refreshAdapter();
                                }
                                YoSnackbar.showSnackbar(CollectFragment.this.getView(), rxJavaTodoEntity.getrTipText());
                            }
                        });
            }
        });


    }

    @Override
    public void onResume() {
        refreshAdapter();
        super.onResume();
    }
}
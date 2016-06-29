package com.yoyo.yoappmanage.module.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.yoyo.common.view.OnToDoItemClickListener;
import com.yoyo.common.view.YoSnackbar;
import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseActivity;
import com.yoyo.yoappmanage.common.util.AlertDialogUtils;
import com.yoyo.yoappmanage.common.util.AppInfoUtil;
import com.yoyo.yoappmanage.common.util.BmobUtils;
import com.yoyo.yoappmanage.common.util.MyCloudCodeListener;
import com.yoyo.yoappmanage.config.AppConfig;
import com.yoyo.yoappmanage.entity.AppUpdateEntity;
import com.yoyo.yoappmanage.entity.JsonEntity;
import com.yoyo.yoappmanage.entity.MenuItemSelectEntity;
import com.yoyo.yoappmanage.module.about.AboutActivity;
import com.yoyo.yoappmanage.module.adapter.MainSectionsPagerAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {
    private MainSectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    List<MenuItemSelectEntity> menuItemSelectEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new MainSectionsPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        menuItemSelectEntityList = new ArrayList<>();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setSelectedMenuItems();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        final ViewTreeObserver viewTreeObserver = getWindow().getDecorView().getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View menuCollectView = findViewById(R.id.action_collect);
                View menuInstalledView = findViewById(R.id.action_installed);
                if (menuInstalledView != null && menuCollectView != null) {
                    menuItemSelectEntityList.add(new MenuItemSelectEntity(menuInstalledView));
                    menuItemSelectEntityList.add(new MenuItemSelectEntity(menuCollectView));
                    setSelectedMenuItems();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            }
        });
        initBmob();
    }

    private void initBmob() {
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(this, AppConfig.MY_BMOB_APPLICATION_ID);

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);

        //查找Person表里面id为6b6c11c537的数据
        /*BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.getObject(this, "6b6c11c537", new GetListener<Person>() {
            @Override
            public void onSuccess(Person object) {
                toast("查询成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                toast("查询失败：" + msg);
            }
        });*/

        updateAppCheck();
    }

    /**
     * app检查更新
     */
    private void updateAppCheck(){
        try {
            JSONObject params = new JSONObject();
            int versionCode = AppInfoUtil.getVersionCode(this);
            if(versionCode<=0){
                return;
            }
            params.put("versionCode", versionCode);
            BmobUtils.callEndpoint(this, AppConfig.MY_BMOB_UPDATE_APP_CLOUDCODENAME, params, new MyCloudCodeListener<JsonEntity<AppUpdateEntity>>() {
                @Override
                public void onMySuccess(boolean isSuccess,final JsonEntity<AppUpdateEntity> result) {
                    //super.onMySuccess(isSuccess, result);
                    if(isSuccess){
                        AlertDialogUtils.showAlertDialog( MainActivity.this,0,R.string.update_text, R.string.btn_ok_web_update, R.string.btn_cancle, 0,null,false,false,new OnToDoItemClickListener(){
                            @Override
                            public void onPositiveClick(DialogInterface dialog, int which) {
                                super.onPositiveClick(dialog, which);
                                if(!TextUtils.isEmpty(result.getResult().getUpdateUrl())){
                                    Uri uri = Uri.parse(result.getResult().getUpdateUrl());
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }

                @Override
                public void onMyFailure(int i, String s) {
                   // super.onMyFailure(i, s);
                    YoSnackbar.showSnackbar(mViewPager,R.string.net_error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void setSelectedMenuItems() {
        if (menuItemSelectEntityList == null || menuItemSelectEntityList.size() <= 0) {
            return;
        }
        int currentPage = mViewPager.getCurrentItem();
        for (int i = 0; i < menuItemSelectEntityList.size(); i++) {
            if (currentPage != i) {
                menuItemSelectEntityList.get(i).setBackground();
            } else {
                menuItemSelectEntityList.get(i).setBackground(R.drawable.shape_menu_item_selected_bg);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
            case R.id.action_installed:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.action_collect:
                mViewPager.setCurrentItem(1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}

package com.yoyo.yoappmanage.module.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yoyo.common.view.YoSnackbar;
import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseActivity;
import com.yoyo.yoappmanage.common.util.AppInfoUtil;

public class AboutActivity extends BaseActivity {
    //  Button bt_update;
    TextView app_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        init();
    }

    private void init() {
        app_version = (TextView) findViewById(R.id.app_version);
        // bt_update = (Button) findViewById(R.id.bt_update);
        String versionName = "版本 " + AppInfoUtil.getVersionName(AboutActivity.this);
        if (TextUtils.isEmpty(versionName)) {
            app_version.setVisibility(View.GONE);
        } else {
            app_version.setText(versionName);
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            /*case R.id.action_update:
                openApplicationMarket();
                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     */
  /*  private void openApplicationMarket() {
        String packageName = this.getPackageName();
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
            YoSnackbar.showSnackbar(app_version,R.string.open_market_error);

            // 调用系统浏览器进入商城
            String url = "https://www.baidu.com/s?wd="+getResources().getString(R.string.app_name);
            openLinkBySystem(url);
        }
    }*/

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
  /*  private void openLinkBySystem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }*/
}

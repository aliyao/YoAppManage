package com.yoyo.yoappmanage.module.about;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseActivity;
import com.yoyo.yoappmanage.common.util.AppInfoUtil;

public class AboutActivity extends BaseActivity {
    Button bt_update;
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
        TextView app_version = (TextView) findViewById(R.id.app_version);
        bt_update = (Button) findViewById(R.id.bt_update);
        String versionName = "版本 "+AppInfoUtil.getVersionName(AboutActivity.this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}

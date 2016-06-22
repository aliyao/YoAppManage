package com.yoyo.yoappmanage.module.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.yoyo.yoappmanage.R;
import com.yoyo.yoappmanage.base.BaseActivity;
import com.yoyo.yoappmanage.entity.MenuItemSelectEntity;
import com.yoyo.yoappmanage.module.adapter.MainSectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

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
        menuItemSelectEntityList=new ArrayList<>();
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
    }

    /**
     *
     */
    private void setSelectedMenuItems() {
        if (menuItemSelectEntityList == null || menuItemSelectEntityList.size() <=0) {
            return;
        }
        int currentPage = mViewPager.getCurrentItem();
        for (int i = 0; i < menuItemSelectEntityList.size(); i++) {
            if(currentPage!=i){
               menuItemSelectEntityList.get(i).setBackground();
            }else {
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

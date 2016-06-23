package com.yoyo.yoappmanage;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yoyo.base.BaseApplication;

import org.xutils.x;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/20 14:49
 * 修改人：yoyo
 * 修改时间：2016/6/20 14:49
 * 修改备注：
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initUtils();
    }

    public void initUtils(){
        //Xutils数据库
        x.Ext.init(this);
        x.Ext.setDebug(android.support.design.BuildConfig.DEBUG);
        //Facebook 图片加载的框架
        Fresco.initialize(this);
    }
}

package com.yoyo.yoappmanage.config;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/20 14:59
 * 修改人：yoyo
 * 修改时间：2016/6/20 14:59
 * 修改备注：
 */
public class AppConfig {
    public final static boolean isDebug=true;
    public final static String logTag="YoAppManage";
    //上下拉刷新延迟时间
    public final static long RefreshViewTime = 500;//毫秒
    public final static int DBVersion = 1;//数据库版本
    public final static String DBName = "YoAppManageDB";

    public final static boolean Is_SSOSetting=false;

    public final static String APP_AES_KEY="M5LN2zu8p1WEadVn";
    public final static String APP_MD5_USER_INFO_KEY="DnXZgyZo2QKeZMoQ";
    public final static String APP_MD5_USER_ID_KEY="jdxOuv211KjVXwcz";
}

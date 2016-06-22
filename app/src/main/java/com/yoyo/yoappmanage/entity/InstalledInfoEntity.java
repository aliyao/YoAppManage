package com.yoyo.yoappmanage.entity;

import android.graphics.drawable.Drawable;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/20 17:30
 * 修改人：yoyo
 * 修改时间：2016/6/20 17:30
 * 修改备注：
 */
public class InstalledInfoEntity {
    public Drawable icon ;//获得该资源图片在 R 文件中的值 (对应于 android:icon 属性)
   /* @Column(name="labelRes")
    public int labelRes;// 获得该 label 在 R 文件中的值 (对应于 android:label 属性)*/
    public String name;// 获得该节点的 name 值 (对应于 android:name 属性)
    public String packageName;// 获得该应用程序的包名 (对应于 android：packagename 属性)
    private boolean isSystemApp;//是否为系统app
    String versionName;//版本名字
    long versionCode;//版本号
    String apkSystemPath;
    String apkPath;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

   /* public int getLabelRes() {
        return labelRes;
    }

    public void setLabelRes(int labelRes) {
        this.labelRes = labelRes;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(boolean systemApp) {
        isSystemApp = systemApp;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkSystemPath() {
        return apkSystemPath;
    }

    public void setApkSystemPath(String apkSystemPath) {
        this.apkSystemPath = apkSystemPath;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }
}

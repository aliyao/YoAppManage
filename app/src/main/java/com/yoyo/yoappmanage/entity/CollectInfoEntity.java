package com.yoyo.yoappmanage.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/20 17:30
 * 修改人：yoyo
 * 修改时间：2016/6/20 17:30
 * 修改备注：
 */
@Table(name = "CollectInfoEntity")
public class CollectInfoEntity {
    @Column(name="icon")
    public int icon ;//获得该资源图片在 R 文件中的值 (对应于 android:icon 属性)
   /* @Column(name="labelRes")
    public int labelRes;// 获得该 label 在 R 文件中的值 (对应于 android:label 属性)*/
    @Column(name="name")
    public String name;// 获得该节点的 name 值 (对应于 android:name 属性)
    @Column(name="packageName",isId = true)
    public String packageName;// 获得该应用程序的包名 (对应于 android：packagename 属性)
    @Column(name="versionName")
    String versionName;//版本名字
    @Column(name="versionCode")
    long versionCode;//版本号
    @Column(name="apkSystemPath")
    String apkSystemPath;
    @Column(name="apkPath")
    String apkPath;

    public CollectInfoEntity(){

    }

    public CollectInfoEntity(int icon,String name,String packageName, String versionName,long versionCode,String apkSystemPath, String apkPath){
        this.icon=icon;
        this.name=name;
        this.packageName=packageName;
        this.versionName=versionName;
        this.versionCode=versionCode;
        this.apkSystemPath=apkSystemPath;
        this.apkPath=apkPath;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
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

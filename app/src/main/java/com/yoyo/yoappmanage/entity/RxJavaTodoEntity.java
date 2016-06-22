package com.yoyo.yoappmanage.entity;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/22 15:27
 * 修改人：yoyo
 * 修改时间：2016/6/22 15:27
 * 修改备注：
 */
public class RxJavaTodoEntity {
    boolean isSuccess;
    int rTipText;

    public RxJavaTodoEntity( boolean isSuccess,int rTipText){
       this.isSuccess=isSuccess;
        this.rTipText=rTipText;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getrTipText() {
        return rTipText;
    }

    public void setrTipText(int rTipText) {
        this.rTipText = rTipText;
    }
}

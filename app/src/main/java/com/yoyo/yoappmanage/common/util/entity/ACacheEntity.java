package com.yoyo.yoappmanage.common.util.entity;

/**
 * 项目名称：YoPassword
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/12 17:09
 * 修改人：yoyo
 * 修改时间：2016/6/12 17:09
 * 修改备注：
 */
public class ACacheEntity {
    String openId;//userid
    int loginStatus;//1登录
    int firstOpen;//1第一次打开
    String checkPassword;
    String randomCode;
    String rootPath;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getFirstOpen() {
        return firstOpen;
    }

    public void setFirstOpen(int firstOpen) {
        this.firstOpen = firstOpen;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}

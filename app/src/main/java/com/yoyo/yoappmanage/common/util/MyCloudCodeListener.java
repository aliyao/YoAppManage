package com.yoyo.yoappmanage.common.util;

import com.google.gson.Gson;
import com.yoyo.yoappmanage.entity.JsonEntity;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.bmob.v3.listener.CloudCodeListener;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/28 12:26
 * 修改人：yoyo
 * 修改时间：2016/6/28 12:26
 * 修改备注：
 */
public class MyCloudCodeListener<B> implements CloudCodeListener {
    @Override
    public void onSuccess(Object object) {
        boolean isSuccess = false;
        B jsonEntity = null;
        try {
            if (object != null) {
                JSONObject mJSONObject = new JSONObject(object.toString());
                String mJSONObjectText = object.toString();
                if (mJSONObject.has("results")) {
                    mJSONObjectText = mJSONObject.get("results").toString();
                }
                Gson gson = new Gson();
                //JsonEntity jsonEntity = gson.fromJson(mJSONObjectText, JsonEntity.class);
                // if(jsonEntity!=null&&jsonEntity.getResult()!=null){
                //String result=jsonEntity.getResult().toString();
                //if(!TextUtils.isEmpty(result)){
                Type mySuperClass = MyCloudCodeListener.this.getClass().getGenericSuperclass();
                Type type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
                jsonEntity = gson.fromJson(mJSONObjectText, type);
                if (jsonEntity != null && ((JsonEntity)jsonEntity).getState() < 1000) {
                    isSuccess = true;
                }
                //  }
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        onMySuccess(isSuccess, jsonEntity);
    }

    @Override
    public void onFailure(int i, String s) {
        onMyFailure(i, s);
    }

    public void onMySuccess(boolean isSuccess, B result) {
        YoLogUtils.i(result.toString());
    }

    public void onMyFailure(int i, String s) {

    }
}

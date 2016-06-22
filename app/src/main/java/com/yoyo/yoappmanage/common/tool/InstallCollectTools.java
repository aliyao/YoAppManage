package com.yoyo.yoappmanage.common.tool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

import com.yoyo.yoappmanage.entity.CollectInfoEntity;
import com.yoyo.yoappmanage.entity.InstalledInfoEntity;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/22 17:45
 * 修改人：yoyo
 * 修改时间：2016/6/22 17:45
 * 修改备注：
 */
public class InstallCollectTools {
    public static CollectInfoEntity InstallToCollectInfo(InstalledInfoEntity installedInfoEntity,String iconSavePath) {

        CollectInfoEntity collectInfoEntity = new CollectInfoEntity(iconSavePath, installedInfoEntity.getName(), installedInfoEntity.getPackageName(), installedInfoEntity.getVersionName(),
                installedInfoEntity.getVersionCode(), installedInfoEntity.getApkSystemPath(), installedInfoEntity.getApkPath());
        return collectInfoEntity;
    }

}

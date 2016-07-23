package com.yoyo.yoappmanage.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.yoyo.yoappmanage.config.AppConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/21 20:34
 * 修改人：yoyo
 * 修改时间：2016/6/21 20:34
 * 修改备注：
 */
public class FileUtil {
    final static String resPath = AppConfig.appName + "res" + File.separator;
    final static String apkPath = resPath + "apk" + File.separator;
    final static String iconImgPath = apkPath + "iconImg"+ File.separator;

    /**
     * 复制单个文件
     *
     * @param oldFilePath String 原文件路径和名字 如：c:/fqf.txt
     * @param newPath     String 复制后路径 如：f:/
     * @param newFileName String 复制后路径名字 如：fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldFilePath, String newPath, String newFileName) {
        boolean isCopySuccess = false;
        String newCacheFileName = newFileName.substring(0, newFileName.lastIndexOf("."));
        try {
            //int bytesum = 0;
            File oldfile = new File(oldFilePath);//原文件
            if (!oldfile.exists()) { // 文件不存在时 复制失败
                return isCopySuccess;
            }
            String newfilePath = newPath + newFileName;//复制后路径
            File newfile = new File(newfilePath);//复制文件
            /*if (newfile.exists()) {// 文件存在就不复制  返回成功
                return true;
            }*/
            if (newfile.exists()) {// 文件存在就删除
                newfile.delete();
            }
            File newCachePath = new File(newPath);//缓存文件目录
            if (!newCachePath.exists()) {
                newCachePath.mkdirs();
            }
            File newCacheFile = new File(newPath, newCacheFileName);//缓存文件
            if (newCacheFile.exists()) {// 文件存在就删除
                newCacheFile.delete();
            }
            int byteread;
            InputStream inStream = new FileInputStream(oldFilePath); // 读入原文件
            FileOutputStream fs = new FileOutputStream(newCacheFile);
            byte[] buffer = new byte[1444];
            while ((byteread = inStream.read(buffer)) != -1) {
                // bytesum += byteread; // 字节数 文件大小
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            //下载完成  进行改名
            newCacheFile.renameTo(newfile);
            isCopySuccess = true;
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
        File newCacheFile = new File(newPath, newCacheFileName);//缓存文件
        if (newCacheFile.exists()) {// 文件存在就删除
            newCacheFile.delete();
        }
        return isCopySuccess;
    }

    public static String getDiskCacheDirApkPath(Context context) {
        String rootParh = getDiskCacheDir(context) + apkPath;
        return rootParh;
    }

    public static String getDiskCacheDirIconImgPath(Context context) {
        String rootParh = getDiskCacheDir(context) + iconImgPath;
        return rootParh;
    }

    public static String getDiskCacheDir(Context context) {
        // Check if media is mounted or storage is built-in, if so, try and use
        // external cache dir
        // otherwise use internal cache dir
        String rootParh = ACacheUtils.getRootPath(context);
        if (!TextUtils.isEmpty(rootParh)) {
            return rootParh;
        }

        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) || !isExternalStorageRemovable() ? getExternalApkDir(
                context).getPath()
                : context.getCacheDir().getPath();

        return cachePath + File.separator;
    }

    public static boolean isExternalStorageRemovable() {
        if (Utils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public static File getExternalApkDir(Context context) {
        if (Utils.hasFroyo()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
      /*  final String cacheDir = "/Android/data/" + context.getPackageName()
                + "/cache/";*/
        final String apkDir =  context.getPackageName()
                + "/apk/";
        return new File(Environment.getExternalStorageDirectory().getPath()
                + apkDir);
    }
}

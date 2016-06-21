package com.yoyo.yoappmanage.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 项目名称：YoAppManage
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2016/6/21 18:37
 * 修改人：yoyo
 * 修改时间：2016/6/21 18:37
 * 修改备注：
 */
public class ApkUtil {
    /**
     * install slient
     * 静默安装
     *
     * @param context
     * @param filePath
     * @return 0 means normal, 1 means file not exist, 2 means other exception error
     */
  /*  public static int installSlient(Context context, String filePath) {
        File file = new File(filePath);
        if (filePath == null || filePath.length() == 0 || (file = new File(filePath)) == null || file.length() <= 0
                || !file.exists() || !file.isFile()) {
            return 1;
        }

        String[] args = {"pm", "install", "-r", filePath};
        ProcessBuilder processBuilder = new ProcessBuilder(args);

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        int result;
        try {
            process = processBuilder.start();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;

            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }

            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = 2;
        } catch (Exception e) {
            e.printStackTrace();
            result = 2;
        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }

        // TODO should add memory is not enough here
        if (successMsg.toString().contains("Success") || successMsg.toString().contains("success")) {
            result = 0;
        } else {
            result = 2;
        }
        Log.d("installSlient", "successMsg:" + successMsg + ", ErrorMsg:" + errorMsg);
        return result;
    }
*/
    /**
     * 普通模式安装，调用系统 Intent
     *
     * @param context
     * @param filePath
     */
    public static void install(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void unInstall(Context context, String packageName) {
        //通过程序的包名创建URI
        Uri packageURI = Uri.parse("package:" + packageName);
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        //执行卸载程序
        context.startActivity(intent);
    }
}

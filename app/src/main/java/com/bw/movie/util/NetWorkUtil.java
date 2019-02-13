package com.bw.movie.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 *
 * @描述 设置网络的工具类
 *
 * @创建日期 2019/1/25 0:41
 *
 */
public class NetWorkUtil {
    /*
     * 判断网络连接是否已开
     * 2012-08-20
     *true 已打开  false 未打开
     * */


    public static boolean isConn(Context context){
        boolean bisConnFlag=false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if(network!=null){
            bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }
    /*
     * 打开设置网络界面
     * */

    public static void setNetworkMethod(final Context context){
        //提示对话框

        DiaLogUtil.getInstance(context);

    }
}

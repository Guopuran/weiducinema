package com.bw.movie;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.view.WindowManager;


public class MyApplication extends Application {
    public static Context instance;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        instance=getApplicationContext();

        // android 7.0系统解决拍照的问题//
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


    }

    public static Context getContext(){
        return instance;
    }
}
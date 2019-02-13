package com.bw.movie.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bw.movie.presenter.IpresenterImpl;
import com.bw.movie.util.CircularLoading;
import com.bw.movie.util.NetWorkUtil;
import com.bw.movie.view.IView;

import java.util.Map;

public abstract class BaseActivity extends AppCompatActivity implements IView {
    IpresenterImpl mIpresenterImpl;
    Dialog mCircularLoading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (getSupportActionBar()!=null) getSupportActionBar().hide();
        if (getActionBar()!=null) getActionBar().hide();
        //no status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(getLayoutResId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        mIpresenterImpl=new IpresenterImpl(this);
        initView(savedInstanceState);
        initData();
    }


    protected abstract void initData();

    protected abstract void success(Object object);

    protected abstract void failed(String error);

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getLayoutResId();

    @Override
    public void successData(Object object) {
        //关闭
        CircularLoading.closeDialog(mCircularLoading);
        success(object);

    }

    @Override
    public void failedData(String error) {
        //关闭
        CircularLoading.closeDialog(mCircularLoading);
        failed(error);

    }

    //get请求
    protected void getRequest(String url,Class clazz){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(this, "加载中...", true);

        mIpresenterImpl.getRequestIpresenter(url,clazz);


    }

    //post请求
    protected void postRequest(String url, Map<String,String> params, Class clazz){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(this, "加载中...", true);

        mIpresenterImpl.postRequestIpresenter(url,params,clazz);
        //显示
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mIpresenterImpl != null){
            mIpresenterImpl.onDeatch();
        }
    }
}
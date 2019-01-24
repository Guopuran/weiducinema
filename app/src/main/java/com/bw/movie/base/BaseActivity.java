package com.bw.movie.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bw.movie.presenter.IpresenterImpl;
import com.bw.movie.view.IView;

import java.util.Map;

public abstract class BaseActivity extends AppCompatActivity implements IView {
    IpresenterImpl mIpresenterImpl;
    Dialog mCircularLoading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIpresenterImpl=new IpresenterImpl(this);

        setContentView(getLayoutResId());
        initView(savedInstanceState);
        initData();
    }

    protected abstract int getLayoutResId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void success(Object object);

    protected abstract void failed(String error);

    @Override
    public void successData(Object object) {
        success(object);

    }

    @Override
    public void failedData(String error) {
        failed(error);

    }

    //post请求
    protected void getRequest(String url,Class clazz){
        mIpresenterImpl.getRequestIpresenter(url,clazz);


    }

    //get请求
    protected void postRequest(String url, Map<String,String> params, Class clazz){
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
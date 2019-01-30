package com.bw.movie.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.presenter.IpresenterImpl;
import com.bw.movie.util.CircularLoading;
import com.bw.movie.util.NetWorkUtil;
import com.bw.movie.view.IView;

import java.util.Map;

public abstract class BaseFragment extends Fragment implements IView {
    IpresenterImpl mIpresenterImpl;
    Dialog mCircularLoading;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIpresenterImpl=new IpresenterImpl(this);
        initView(view);
        initData();
    }



    protected abstract void initData();

    protected abstract void success(Object object);

    protected abstract void failed(String error);

    protected abstract void initView(View view);

    protected abstract int getLayoutResId();

    @Override
    public void successData(Object object) {
        //关闭
        //CircularLoading.closeDialog(mCircularLoading);
        success(object);
    }

    @Override
    public void failedData(String error) {
        //关闭
        //CircularLoading.closeDialog(mCircularLoading);
        failed(error);
    }

    //post请求
    protected void getRequest(String url,Class clazz){
        if (!(NetWorkUtil.isConn(getActivity()))){
            NetWorkUtil.setNetworkMethod(getActivity());
            return ;
        }
        //显示
        //mCircularLoading = CircularLoading.showLoadDialog(getActivity(), "加载中...", true);

        mIpresenterImpl.getRequestIpresenter(url,clazz);
    }

    //get请求
    protected void postRequest(String url, Map<String,String> params, Class clazz){
        if (!(NetWorkUtil.isConn(getActivity()))){
            NetWorkUtil.setNetworkMethod(getActivity());
            return ;
        }
        //显示
        //mCircularLoading = CircularLoading.showLoadDialog(getActivity(), "加载中...", true);

        mIpresenterImpl.postRequestIpresenter(url,params,clazz);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mIpresenterImpl != null){
            mIpresenterImpl.onDeatch();
        }
    }
}


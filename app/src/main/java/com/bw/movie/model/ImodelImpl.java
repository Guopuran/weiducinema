package com.bw.movie.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bw.movie.MyApplication;
import com.bw.movie.util.RetrofitUtil;
import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

public class ImodelImpl implements Imodel{

    //get请求
    @Override
    public void getRequestModel(String url, final Class clazz, final ModelCallBack callBack) {
        if (!isNetWork()){
            callBack.failData("网络状态不可用");
            return;
        }
        RetrofitUtil.getInstance().get(url, new RetrofitUtil.ICallBack() {
            @Override
            public void successData(String result) {
                Object object = getGson(result, clazz);
                callBack.successData(object);
            }

            @Override
            public void failureData(String error) {
                callBack.failData("无法连接");
            }

        });
    }

    //post请求
    @Override
    public void postRequestModel(String url, Map<String, String> params, final Class clazz, final ModelCallBack callBack) {
        if (!isNetWork()){
            callBack.failData("网络状态不可用");
            return;
        }
        RetrofitUtil.getInstance().post(url, params, new RetrofitUtil.ICallBack() {
            @Override
            public void successData(String result) {
                Object object = getGson(result, clazz);
                callBack.successData(object);
            }

            @Override
            public void failureData(String error) {
                callBack.failData("无法连接");
            }


        });
    }

    @Override
    public void deleteRequestModel(String url, final Class clazz, final ModelCallBack callBack) {
        if (!isNetWork()){
            callBack.failData("网络状态不可用");
            return;
        }
        RetrofitUtil.getInstance().delete(url, new RetrofitUtil.ICallBack() {
            @Override
            public void successData(String result) {
                Object object = getGson(result, clazz);
                callBack.successData(object);
            }

            @Override
            public void failureData(String error) {
                callBack.failData("无法连接");
            }

        });

    }
    //put请求
    @Override
    public void putRequestModel(String url, Map<String, String> params, final Class clazz, final ModelCallBack callBack) {
        if (!isNetWork()){
            callBack.failData("网络状态不可用");
            return;
        }
        RetrofitUtil.getInstance().put(url, params, new RetrofitUtil.ICallBack() {
            @Override
            public void successData(String result) {
                Object object = getGson(result, clazz);
                callBack.successData(object);
            }

            @Override
            public void failureData(String error) {
                callBack.failData("无法连接");
            }

        });
    }


    @Override
    public void postImageRequestModel(String url, File file, final Class clazz, final ModelCallBack callBack) {
        if (!isNetWork()){
            callBack.failData("网络状态不可用");
            return;
        }
        RetrofitUtil.getInstance().postImage(url, file, new RetrofitUtil.ICallBack() {
            @Override
            public void successData(String result) {
                Object object = getGson(result, clazz);
                callBack.successData(object);
            }

            @Override
            public void failureData(String error) {
                callBack.failData("无法连接");
            }

        });
    }



    //gson解析
    private Object getGson(String result, Class clazz) {
        Object o = new Gson().fromJson(result, clazz);
        return o;
    }

    //判断网络状态
    public static boolean isNetWork(){
        ConnectivityManager cm = (ConnectivityManager) MyApplication.instance.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isAvailable();
    }
}

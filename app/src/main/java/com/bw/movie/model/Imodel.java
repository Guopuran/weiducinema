package com.bw.movie.model;

import java.io.File;
import java.util.Map;

/**
 *
 * @详情 M层
 *
 * @创建日期 2018/12/29 15:00
 *
 */
public interface Imodel {

    //get请求
    void getRequestModel(String url, Class clazz, ModelCallBack callBack);

    //post请求
    void postRequestModel(String url, Map<String, String> params, Class clazz, ModelCallBack callBack);
    //delete请求
    void deleteRequestModel(String url, Class clazz, ModelCallBack callBack);
    //put请求
    void putRequestModel(String url, Map<String, String> params, Class clazz, ModelCallBack callBack);
    //上传图片
    void postImageRequestModel(String url, File file, Class clazz, ModelCallBack callBack);



}
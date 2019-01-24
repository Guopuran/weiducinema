package com.bw.movie.presenter;

import java.io.File;
import java.util.Map;

/**
 *
 * @详情 P层
 *
 * @创建日期 2018/12/29 15:01
 *
 */
public interface Ipresenter {

    //get请求
    void getRequestIpresenter(String url, Class clazz);
    //post请求
    void postRequestIpresenter(String url, Map<String, String> params, Class clazz);
    //get请求
    void deleteRequestIpresenter(String url, Class clazz);
    //put请求
    void putRequestIpresenter(String url, Map<String, String> params, Class clazz);
    //上传图片
    void postImageRequestIpresenter(String url, File file, Class clazz);

}

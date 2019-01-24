package com.bw.movie.view;

/**
 *
 * @描述 V层接口
 *
 * @创建日期 2019/1/24 9:55
 *
 */
public interface IView {

    //成功
    void successData(Object object);
    //失败
    void failedData(String error);

}

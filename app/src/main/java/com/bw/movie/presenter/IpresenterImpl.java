package com.bw.movie.presenter;

import com.bw.movie.model.ImodelImpl;
import com.bw.movie.model.ModelCallBack;
import com.bw.movie.view.IView;

import java.io.File;
import java.util.Map;

/**
 *
 * @描述 P层实现类
 *
 * @创建日期 2019/1/24 16:37
 *
 */
public class IpresenterImpl implements Ipresenter{

    private ImodelImpl mImodelImpl;
    private IView mIView;

    public IpresenterImpl(IView mIView) {
        this.mIView = mIView;
        //实例化
        mImodelImpl=new ImodelImpl();
    }

    //解绑
    public void  onDeatch(){
        //解绑M层
        if (mImodelImpl!=null){
            mImodelImpl=null;
        }

        //解绑V层
        if (mIView!=null){
            mIView=null;
        }
    }

    //get请求
    @Override
    public void getRequestIpresenter(String url, Class clazz) {
        mImodelImpl.getRequestModel(url , clazz , new ModelCallBack() {

            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }
        });
    }

    //post请求
    @Override
    public void postRequestIpresenter(String url, Map<String, String> params, Class clazz) {
        mImodelImpl.postRequestModel(url , params , clazz, new ModelCallBack() {

            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }
        });
    }
    //delete请求
    @Override
    public void deleteRequestIpresenter(String url, Class clazz) {
        mImodelImpl.deleteRequestModel(url , clazz , new ModelCallBack() {

            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }

        });
    }
    //post请求
    @Override
    public void putRequestIpresenter(String url, Map<String, String> params, Class clazz) {
        mImodelImpl.putRequestModel(url , params , clazz, new ModelCallBack() {

            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }
        });
    }

    @Override
    public void postImageRequestIpresenter(String url, File file, Class clazz) {
        mImodelImpl.postImageRequestModel(url, file, clazz, new ModelCallBack() {
            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }
        });
    }

}
package com.bw.movie.main.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.main.movie.adpter.MovieBannerAdpter;
import com.bw.movie.main.movie.bean.MovieBannerBean;
import com.bw.movie.util.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;


public class MovieFragment extends BaseFragment {
    @BindView(R.id.movie_banner_recycle)
    RecyclerCoverFlow recyclerCoverFlow;
     MovieBannerAdpter movieBannerAdpter;
    @Override
    protected void initData() {
        //轮播请求数据
        getBannerData();
        //设置轮播
        setBannerRecycle();
    }
    //设置轮播
    public void setBannerRecycle()
    {
        movieBannerAdpter  = new MovieBannerAdpter(getActivity());
        recyclerCoverFlow.setAdapter(movieBannerAdpter);

    }
      //轮播请求数据
    public void getBannerData()
    {
        getRequest(String.format(Apis.MOVIEBAANNER_URL,1,10),MovieBannerBean.class);
    }
    @Override
    protected void success(Object object) {
         if (object instanceof MovieBannerBean){
             MovieBannerBean movieBannerBean = (MovieBannerBean) object;
             movieBannerAdpter.setmList(movieBannerBean.getResult());
         }
    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movie;
    }
}

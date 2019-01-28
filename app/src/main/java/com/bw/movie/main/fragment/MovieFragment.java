package com.bw.movie.main.fragment;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.details.DetailsActivity;
import com.bw.movie.main.movie.activity.MovieMroeActivity;
import com.bw.movie.main.movie.adpter.MoreMovieAdpter;
import com.bw.movie.main.movie.adpter.MovieBannerAdpter;
import com.bw.movie.main.movie.adpter.MovieHotAdpter;
import com.bw.movie.main.movie.adpter.MovieNowHotAdpter;
import com.bw.movie.main.movie.adpter.MovieWillAdpter;
import com.bw.movie.main.movie.bean.MovieBannerBean;
import com.bw.movie.main.movie.bean.MovieHotBean;
import com.bw.movie.main.movie.bean.MovieNowHotBean;
import com.bw.movie.main.movie.bean.MovieWillBean;
import com.bw.movie.util.Apis;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;


public class MovieFragment extends BaseFragment {
    @BindView(R.id.movie_banner_recycle)
    RecyclerCoverFlow recyclerCoverFlow;
    @BindView(R.id.movie_hot_recycle)
    RecyclerView hot_recycle;
    @BindView(R.id.movie_nowhot_recycle)
    RecyclerView nowhot_recycle;
    @BindView(R.id.movie_will_recycle)
    RecyclerView will_recycle;
    @BindView(R.id.movie_search_image)
    ImageView search_image;
    @BindView(R.id.movie_search_text)
    TextView search_text;
    @BindView(R.id.movie_search_relative)
    RelativeLayout search_relative;
    @BindView(R.id.movie_hot_movie_image)
    ImageView hot_more;
    @BindView(R.id.movie_nowhot_movie_image)
    ImageView nowhot_more;
    @BindView(R.id.movie_will_movie_image)
    ImageView will_more;

     MovieBannerAdpter movieBannerAdpter;
     MovieHotAdpter movieHotAdpter;
     MovieNowHotAdpter movieNowHotAdpter;
     MovieWillAdpter movieWillAdpter;
     int REQUEST=101;
    @Override
    protected void initData()
    {
        //轮播请求数据
        getBannerData();
        //设置轮播
        setBannerRecycle();
        //热门的展示
        initHotLayout();
        //正在热映的展示
        initNowHotLayout();
        //即将上映的展示
        initWillLayout();
        //热门的跳转
        hotOnClick();
        //正在热映的跳转
        nowHotOnClick();
        //即将热映的跳转
        willOnClick();
        //banner的跳转
        bannerOnClick();
    }
    //热门的跳转
    public void hotOnClick(){
        movieHotAdpter.setOnHotItemClickLisenter(new MovieHotAdpter.onHotClick() {
            @Override
            public void onHotClickItem(int id) {
                Intent intent = new Intent(getContext(),DetailsActivity.class);
                intent.putExtra("flag",id);;
                startActivityForResult(intent,REQUEST);
            }
        });
    }
    //正在热映的跳转
    public void nowHotOnClick(){
       movieNowHotAdpter.setOnNewHotItemClickLisenter(new MovieNowHotAdpter.onNewHotClick() {
           @Override
           public void onNewHotClickItem(int id) {
               Intent intent = new Intent(getContext(),DetailsActivity.class);
               intent.putExtra("flag",id);;
               startActivityForResult(intent,REQUEST);
           }
       });
    }
    //banner的跳转
    public void bannerOnClick(){
        movieBannerAdpter.setOnItembannerOnClick(new MovieBannerAdpter.bannerOnClick() {
            @Override
            public void bannerItemOnClickLisenter(int id) {
                Intent intent = new Intent(getContext(),DetailsActivity.class);
                intent.putExtra("flag",id);;
                startActivityForResult(intent,REQUEST);
            }
        });
    }
    //即将热映的跳转
    public void  willOnClick(){
        movieWillAdpter.setOnWillItemClickLisenter(new MovieWillAdpter.onWillClick() {
            @Override
            public void onWillClickItem(int id) {
                Intent intent = new Intent(getContext(),DetailsActivity.class);
                intent.putExtra("flag",id);;
                startActivityForResult(intent,REQUEST);
            }
        });
    }
    @OnClick(R.id.movie_hot_movie_image)
    public void hotMoreOnClick()
    {
        Intent intent = new Intent(getContext(),MovieMroeActivity.class);
        intent.putExtra("flag",0);
        startActivity(intent);
    }
    @OnClick(R.id.movie_nowhot_movie_image)
    public void nowHotMoreOnClick(){
        Intent intent = new Intent(getContext(),MovieMroeActivity.class);
        intent.putExtra("flag",1);
        startActivity(intent);
    }
    @OnClick(R.id.movie_will_movie_image)
    public void willMoreOnClick(){
        Intent intent = new Intent(getContext(),MovieMroeActivity.class);
        intent.putExtra("flag",2);
        startActivity(intent);
    }
    //弹出的动画
    @OnClick(R.id.movie_search_image)
    public void outAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(search_relative, "translationX",  -400f);
        animator.setDuration(1000);
        animator.start();
    }
    //收回的动画
    @OnClick(R.id.movie_search_text)
    public void inAnimation()
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(search_relative, "translationX",  10f);
        animator.setDuration(500);
        animator.start();
    }
    //设置轮播
    public void setBannerRecycle()
    {
        movieBannerAdpter  = new MovieBannerAdpter(getActivity());
        recyclerCoverFlow.setAdapter(movieBannerAdpter);
    }
    //热门的展示
    public void initHotLayout()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        movieHotAdpter = new MovieHotAdpter(getContext());
        hot_recycle.setAdapter(movieHotAdpter);
        hot_recycle.setLayoutManager(linearLayoutManager);
         getHotData();
    }
    //正在热映的展示
    public void initNowHotLayout()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        movieNowHotAdpter = new MovieNowHotAdpter(getContext());
        nowhot_recycle.setAdapter(movieNowHotAdpter);
        nowhot_recycle.setLayoutManager(linearLayoutManager);
        getNowHotData();
    }
    //即将上映的展示
    public void initWillLayout()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        movieWillAdpter = new MovieWillAdpter(getContext());
        will_recycle.setAdapter(movieWillAdpter);
        will_recycle.setLayoutManager(linearLayoutManager);
        getWillData();
    }
    //即将上映请求数据
    public void getWillData()
    {
        getRequest(String.format(Apis.MOVIEWWILL_URL,1,10),MovieWillBean.class);
    }
    //正在热映请求数据
     public void getNowHotData()
     {
         getRequest(String.format(Apis.MOVIEBAANNER_URL,1,10),MovieNowHotBean.class);
     }
    //热门电影请求数据
    public void getHotData(){
        getRequest(String.format(Apis.MOVIEHOT_URL,1,10),MovieHotBean.class);
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
         if (object instanceof MovieHotBean){
            MovieHotBean movieHotBean = (MovieHotBean) object;
            movieHotAdpter.setmList(movieHotBean.getResult());
        }
        if (object instanceof MovieNowHotBean){
            MovieNowHotBean movieNowHotBean = (MovieNowHotBean) object;
            movieNowHotAdpter.setmList(movieNowHotBean.getResult());
        }
        if (object instanceof MovieWillBean){
            MovieWillBean movieWillBean = (MovieWillBean) object;
            movieWillAdpter.setmList(movieWillBean.getResult());
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

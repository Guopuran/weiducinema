package com.bw.movie.main.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.main.my.adpter.MyFollowCinemaAadpter;
import com.bw.movie.main.my.adpter.MyFollowMovieAdpter;
import com.bw.movie.main.my.bean.MyFollowCinemaBean;
import com.bw.movie.main.my.bean.MyFollowMovieBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFollowActivity extends BaseActivity {

    @BindView(R.id.my_movie_follow)
    RadioButton movie_follow;
    @BindView(R.id.my_cinema_follow)
    RadioButton cinema_follow;
    @BindView(R.id.my_follow_xrecycle)
    XRecyclerView recycle;
    @BindView(R.id.rela)
    RelativeLayout relativeLayout;
    MyFollowCinemaAadpter cinemaAadpter;
    MyFollowMovieAdpter movieAdpter;
    private int mpag=1;
    private int cpage=1;
    @Override
    protected void initData() {

      initFollowMovieLayout();
    }
    @OnClick({R.id.my_cinema_follow})
    public void cinemaOnClick()
    {

        initFollowCineamLayout();
    }
    @OnClick({R.id.my_movie_follow})
    public void movieOnClick()
    {

        initFollowMovieLayout();
    }
    //关注的影片展示数据
    public void initFollowMovieLayout(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        movieAdpter = new MyFollowMovieAdpter(this);
        recycle.setAdapter(movieAdpter);
        mpag=1;
        recycle.setLayoutManager(linearLayoutManager);
        recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mpag=1;
                getFollowMovie();
                recycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getFollowMovie();
               recycle.loadMoreComplete();
            }
        });
        getFollowMovie();
    }
    //关注的影院展示数据
    public void initFollowCineamLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cinemaAadpter = new MyFollowCinemaAadpter(this);
        recycle.setAdapter(cinemaAadpter);
        cpage=1;
        recycle.setLayoutManager(linearLayoutManager);
        recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                cpage=1;
                getFollowCinema();
                recycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getFollowCinema();
                recycle.loadMoreComplete();
            }
        });
        getFollowCinema();
    }

    //关注的电影请求数据
    public void  getFollowMovie()
    {
        getRequest(String.format(Apis.MY_FOLLOW_MOVIE,mpag,10),MyFollowMovieBean.class);
    }
    //关注的影院请求数据
    public void getFollowCinema()
    {
        getRequest(String.format(Apis.MY_FOLLLOW_CINEMA,cpage,10),MyFollowCinemaBean.class);
    }
    @Override
    protected void success(Object object) {
         if (object instanceof MyFollowMovieBean){
             MyFollowMovieBean myFollowMovieBean = (MyFollowMovieBean) object;

             if (mpag==1){
                 relativeLayout.setVisibility(View.GONE);
                 movieAdpter.setmList(myFollowMovieBean.getResult());
             }
             else {
                 relativeLayout.setVisibility(View.GONE);
                 movieAdpter.addmList(myFollowMovieBean.getResult());
             }
             mpag++;
             recycle.refreshComplete();
             recycle.loadMoreComplete();
             if (myFollowMovieBean.getResult().size()==0){

                 recycle.setPullRefreshEnabled(false);
                 recycle.setLoadingMoreEnabled(false);
             }
             if (movieAdpter.getmList().size()==0){
                 relativeLayout.setVisibility(View.VISIBLE);
             }
         }
        if (object instanceof MyFollowCinemaBean){
            MyFollowCinemaBean myFollowCinemaBean = (MyFollowCinemaBean) object;
            if (cpage==1){
                relativeLayout.setVisibility(View.GONE);
                cinemaAadpter.setmList(myFollowCinemaBean.getResult());
            }
            else {
                relativeLayout.setVisibility(View.GONE);
                cinemaAadpter.addmList(myFollowCinemaBean.getResult());
            }
            cpage++;
            recycle.refreshComplete();
            recycle.loadMoreComplete();
            if (myFollowCinemaBean.getResult().size()==0){

                recycle.setPullRefreshEnabled(false);
                recycle.setLoadingMoreEnabled(false);
            }
            if (cinemaAadpter.getmList().size()==0){
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_follow;
    }
}

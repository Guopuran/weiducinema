package com.bw.movie.main.cinema.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.main.cinema.adpter.CinemaMessageAdpter;
import com.bw.movie.main.cinema.bean.CinemaMessageBean;
import com.bw.movie.util.Apis;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendFragment extends BaseFragment {
    @BindView(R.id.cinema_recommend_xrecycle)
    XRecyclerView recommend_recycle;
     CinemaMessageAdpter cinemaMessageAdpter;
     private int page=1;
    @Override
    protected void initData() {
     //加载布局
        initCinemaMessageLayout();
    }
    //加载布局
    public void initCinemaMessageLayout(){
        page=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        cinemaMessageAdpter = new CinemaMessageAdpter(getContext());
        recommend_recycle.setAdapter(cinemaMessageAdpter);
        recommend_recycle.setLayoutManager(linearLayoutManager);
        recommend_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getCinemaMessageData();
                recommend_recycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getCinemaMessageData();
                recommend_recycle.loadMoreComplete();
            }
        });
        getCinemaMessageData();
    }
    //请求数据
    public void  getCinemaMessageData(){
        getRequest(String.format(Apis.CONEMARECOMMEND_URL,page,10),CinemaMessageBean.class);
    }
    @Override
    protected void success(Object object) {
         if (object instanceof CinemaMessageBean){
             CinemaMessageBean cinemaMessageBean = (CinemaMessageBean) object;
             if (page==1){
                 cinemaMessageAdpter.setmList(cinemaMessageBean.getResult());
             }
             else {
                 cinemaMessageAdpter.addmList(cinemaMessageBean.getResult());
             }
             page++;
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
    protected int getLayoutResId()
    {
        return R.layout.fragment_recommend;
    }
}

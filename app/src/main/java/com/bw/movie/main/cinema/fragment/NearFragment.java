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

public class NearFragment extends BaseFragment {
    @BindView(R.id.cinema_near_xrecycle)
    XRecyclerView near_recycle;
    CinemaMessageAdpter cinemaMessageAdpter;
    private int page=1;
    @Override
    protected void initData() {
        //加载布局
        initNearCinemaMessageLayout();
    }
    //加载布局
    public void initNearCinemaMessageLayout(){
        page=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        cinemaMessageAdpter = new CinemaMessageAdpter(getContext());
        near_recycle.setAdapter(cinemaMessageAdpter);
        near_recycle.setLayoutManager(linearLayoutManager);
        near_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getNearCinemaMessageData();
                near_recycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getNearCinemaMessageData();
                near_recycle.loadMoreComplete();
            }
        });
        getNearCinemaMessageData();
    }
    //请求数据
    public void  getNearCinemaMessageData(){
        getRequest(String.format(Apis.CONEMARENEAR_URL,page,10),CinemaMessageBean.class);
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
    protected int getLayoutResId() {
        return R.layout.fragment_near;
    }
}

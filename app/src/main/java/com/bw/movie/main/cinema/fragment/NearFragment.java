package com.bw.movie.main.cinema.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.main.cinema.adpter.CinemaMessageAdpter;
import com.bw.movie.main.cinema.bean.CinemaIsFollowBean;
import com.bw.movie.main.cinema.bean.CinemaMessageBean;
import com.bw.movie.main.cinema.bean.CinemaNoFollowBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
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
        //关注的点击事件
        onFollowOnClick();
    }
    //关注的点击事件
    public void onFollowOnClick(){
        cinemaMessageAdpter.setOnFollowOnClick(new CinemaMessageAdpter.onFollowOnClick() {
            @Override
            public void onFollowOnClick(int id, int follow) {
                 if (follow==2){
                     getIsFollowData(id);
                     cinemaMessageAdpter.isFollow(id);
                 }
                 else {
                     getNoFollowData(id);
                     cinemaMessageAdpter.onfollow(id);
                 }
            }
        });
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
    //关注请求数据
    public void getIsFollowData(int id){
        getRequest(String.format(Apis.CINEMAISFOLLOW_URL,id),CinemaIsFollowBean.class);
    }
    //取消请求数据
    public void getNoFollowData(int id){
        getRequest(String.format(Apis.CINEMANOFOLLOW_URL,id),CinemaNoFollowBean.class);
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
        if (object instanceof CinemaIsFollowBean)
        {
            CinemaIsFollowBean cinemaIsFollowBean  = (CinemaIsFollowBean) object;
            if (cinemaIsFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(getContext(),cinemaIsFollowBean.getMessage());
            }
        }
        if (object instanceof CinemaNoFollowBean){
            CinemaNoFollowBean cinemaNoFollowBean  = (CinemaNoFollowBean) object;
            if (cinemaNoFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(getContext(),cinemaNoFollowBean.getMessage());
            }
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

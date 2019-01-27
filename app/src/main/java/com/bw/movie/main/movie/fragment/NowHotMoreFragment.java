package com.bw.movie.main.movie.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.main.movie.adpter.MoreMovieAdpter;
import com.bw.movie.main.movie.bean.MoreMovieBean;
import com.bw.movie.util.Apis;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NowHotMoreFragment extends BaseFragment {
    @BindView(R.id.more_nowhot_recycle)
    XRecyclerView nowhotmore_xrecrcle;
    private  int page=1;
    private MoreMovieAdpter moreMovieAdpter;
    @Override
    protected void initData() {
        //加载布局
        initNowHotMoreLayout();
    }
    //加载布局
    public void initNowHotMoreLayout(){
        page=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        moreMovieAdpter = new MoreMovieAdpter(getContext());
        nowhotmore_xrecrcle.setLayoutManager(linearLayoutManager);
        nowhotmore_xrecrcle.setAdapter(moreMovieAdpter);
        nowhotmore_xrecrcle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getNowHotMoreData();
                nowhotmore_xrecrcle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getNowHotMoreData();
                nowhotmore_xrecrcle.loadMoreComplete();
            }
        });
        getNowHotMoreData();
    }
    //请求数据
    public void getNowHotMoreData(){
        getRequest(String.format(Apis.MOVIEBAANNER_URL,page,10),MoreMovieBean.class);
    }
    @Override
    protected void success(Object object) {
        if (object instanceof MoreMovieBean){
            MoreMovieBean moreMovieBean = (MoreMovieBean) object;
            if (page==1){
                moreMovieAdpter.setmList(moreMovieBean.getResult());
            }
            else {
                moreMovieAdpter.addmList(moreMovieBean.getResult());
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
        return R.layout.fragment_nowhot_more;
    }
}

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

public class WillMoreFragment extends BaseFragment {
    @BindView(R.id.more_will_recycle)
    XRecyclerView willmore_xrecrcle;
    private  int page=1;
    private MoreMovieAdpter moreMovieAdpter;
    @Override
    protected void initData() {
        //加载布局
        initHotMoreLayout();
    }
    //加载布局
    public void initHotMoreLayout(){
        page=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        moreMovieAdpter = new MoreMovieAdpter(getContext());
        willmore_xrecrcle.setLayoutManager(linearLayoutManager);
        willmore_xrecrcle.setAdapter(moreMovieAdpter);
        willmore_xrecrcle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getHotMoreData();
                willmore_xrecrcle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getHotMoreData();
                willmore_xrecrcle.loadMoreComplete();
            }
        });
        getHotMoreData();
    }
    //请求数据
    public void getHotMoreData(){
        getRequest(String.format(Apis.MOVIEWWILL_URL,page,10),MoreMovieBean.class);
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
        return R.layout.fragment_will_more;
    }
}

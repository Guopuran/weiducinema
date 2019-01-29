package com.bw.movie.main.movie.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.details.activity.DetailsActivity;
import com.bw.movie.main.movie.adpter.MoreMovieAdpter;
import com.bw.movie.main.movie.bean.MessageBean;
import com.bw.movie.main.movie.bean.MoreMovieBean;
import com.bw.movie.main.movie.bean.MovieIsFollowBean;
import com.bw.movie.main.movie.bean.MovieNoFollowBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NowHotMoreFragment extends BaseFragment {
    @BindView(R.id.more_nowhot_recycle)
    XRecyclerView nowhotmore_xrecrcle;
    private  int page=1;
    private MoreMovieAdpter moreMovieAdpter;
    private int REQUEST=100;
    @Override
    protected void initData() {
        //加载布局
        initNowHotMoreLayout();
        //点赞和取消点赞
        onFollowClick();
        moreMovieAdpter.setOnItemClickLisenter(new MoreMovieAdpter.onClick() {
            @Override
            public void onClickItem(int id) {
                Intent intent = new Intent(getContext(),DetailsActivity.class);
                intent.putExtra("flag",id+"");;
                startActivityForResult(intent,REQUEST);
            }
        });
    }
    //点赞和取消点赞
    public void onFollowClick(){
        moreMovieAdpter.setFollowOnClick(new MoreMovieAdpter.followOnClikc() {
            @Override
            public void follOnClickLisenter(int id, int follow, int i) {
                if (follow==2){
                    getIsFollowData(id);
                    moreMovieAdpter.isFollow(id,i);
//                    moreMovieAdpter.notifyDataSetChanged();
                }
                else {
                    getNoFollowData(id);
                    moreMovieAdpter.onfollow(id,i);
//                    moreMovieAdpter.notifyDataSetChanged();
                }
            }
        });

    }
    //加载布局
    public void initNowHotMoreLayout(){
        page=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        moreMovieAdpter = new MoreMovieAdpter(getContext());
        nowhotmore_xrecrcle.setLayoutManager(linearLayoutManager);
        nowhotmore_xrecrcle.setAdapter(moreMovieAdpter);
        moreMovieAdpter.setHeadCount(nowhotmore_xrecrcle.getHeaders_includingRefreshCount());
        nowhotmore_xrecrcle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getNowHotMoreData(page);
                nowhotmore_xrecrcle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getNowHotMoreData(page);
                nowhotmore_xrecrcle.loadMoreComplete();
            }
        });
        getNowHotMoreData(page);
    }
    //关注请求数据
    public void getIsFollowData(int id){
        getRequest(String.format(Apis.MOVIEISFOLLOW_URL,id),MovieIsFollowBean.class);
    }
    //取消关注请求数据
    public void getNoFollowData(int id){
        getRequest(String.format(Apis.MOVIENOFOLLOW_URL,id),MovieNoFollowBean.class);
    }
    //请求数据
    public void getNowHotMoreData(int page){
        getRequest(String.format(Apis.MOVIEBAANNER_URL, this.page,10),MoreMovieBean.class);
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
            if (moreMovieBean.getResult().size()<10){
                nowhotmore_xrecrcle.setLoadingMoreEnabled(false);
            }
            page++;
        }
        if (object instanceof MovieIsFollowBean){
            MovieIsFollowBean movieIsFollowBean = (MovieIsFollowBean) object;
            if (movieIsFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(getContext(),movieIsFollowBean.getMessage());
                EventBus.getDefault().post(new MessageBean(page,"send"));
            }
        }
        if (object instanceof MovieNoFollowBean){
            MovieNoFollowBean movieNoFollowBean = (MovieNoFollowBean) object;
            if (movieNoFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(getContext(),movieNoFollowBean.getMessage());
                EventBus.getDefault().post(new MessageBean(page,"send"));
            }
        }
    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(View view)
    {
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_nowhot_more;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       EventBus.getDefault().unregister(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST &&resultCode==getActivity().RESULT_OK){
            page=1;
            getNowHotMoreData(page);
            moreMovieAdpter.notifyDataSetChanged();
        }
    }
}

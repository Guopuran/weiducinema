package com.bw.movie.main.movie.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.details.activity.DetailsActivity;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.login.bean.RefurbishMessageBean;
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

public class WillMoreFragment extends BaseFragment {
    @BindView(R.id.more_will_recycle)
    XRecyclerView willmore_xrecrcle;
    private  int page=1;
    private MoreMovieAdpter moreMovieAdpter;
    private int REQUEST=100;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean loginSuccess;
    @Override
    protected void initData() {
        sharedPreferences = getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //加载布局
        initHotMoreLayout();
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
    //接受传值进行刷新
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void enventbus(RefurbishMessageBean messageBean){
        if (messageBean.getFlag().equals("refurbish")){
            initHotMoreLayout();
        }
    }
    //点赞和取消点赞
    public void onFollowClick(){
        moreMovieAdpter.setFollowOnClick(new MoreMovieAdpter.followOnClikc() {
            @Override
            public void follOnClickLisenter(int id, int follow, int i) {
                loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
                if (loginSuccess){
                    if (follow==2){
                        getIsFollowData(id);
                        moreMovieAdpter.isFollow(id,i);
                    }
                    else {
                        getNoFollowData(id);
                        moreMovieAdpter.onfollow(id,i);
                    }
                }
                else {
                    ToastUtil.showToast(getActivity(),"请先登录");
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
    //加载布局
    public void initHotMoreLayout(){
        page=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        moreMovieAdpter = new MoreMovieAdpter(getContext());
        willmore_xrecrcle.setLayoutManager(linearLayoutManager);
        willmore_xrecrcle.setAdapter(moreMovieAdpter);
        moreMovieAdpter.setHeadCount(willmore_xrecrcle.getHeaders_includingRefreshCount());
        willmore_xrecrcle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getHotMoreData(page);
                willmore_xrecrcle.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                getHotMoreData(page);
                willmore_xrecrcle.loadMoreComplete();
            }
        });
        getHotMoreData(page);
    }

    //得到传值进行刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(MessageBean messageBean){
        if (messageBean.getFlag().equals("send")){
            page =1;
            getHotMoreData(page);
        }
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
    public void getHotMoreData(int page){
        getRequest(String.format(Apis.MOVIEWWILL_URL, this.page,10),MoreMovieBean.class);
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
                willmore_xrecrcle.setLoadingMoreEnabled(false);
            }
            page++;
        }
        if (object instanceof MovieIsFollowBean){
            MovieIsFollowBean movieIsFollowBean = (MovieIsFollowBean) object;
            if (movieIsFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(getContext(),movieIsFollowBean.getMessage());
                EventBus.getDefault().post(new MessageBean(page,"refresh"));
            }
        }
        if (object instanceof MovieNoFollowBean){
            MovieNoFollowBean movieNoFollowBean = (MovieNoFollowBean) object;
            if (movieNoFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(getContext(),movieNoFollowBean.getMessage());
                EventBus.getDefault().post(new MessageBean(page,"refresh"));
            }
        }
    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_will_more;
    }
    @Override
    public void onResume() {
        super.onResume();
        onFollowClick();
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
            getHotMoreData(page);
            moreMovieAdpter.notifyDataSetChanged();
        }
    }
}

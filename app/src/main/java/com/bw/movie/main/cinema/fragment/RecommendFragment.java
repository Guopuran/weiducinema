package com.bw.movie.main.cinema.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.login.bean.RefurbishMessageBean;
import com.bw.movie.main.cinema.adpter.CinemaMessageAdpter;
import com.bw.movie.main.cinema.bean.CinemaIsFollowBean;
import com.bw.movie.main.cinema.bean.CinemaMessageBean;
import com.bw.movie.main.cinema.bean.CinemaNoFollowBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendFragment extends BaseFragment {
    @BindView(R.id.cinema_recommend_xrecycle)
    XRecyclerView recommend_recycle;
     CinemaMessageAdpter cinemaMessageAdpter;
     private int page=1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean loginSuccess;
    @Override
    protected void initData() {
        sharedPreferences = getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
     //加载布局
        initCinemaMessageLayout();
        //关注的点击事件
        onFollowOnClick();
    }
    //接受传值进行刷新
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void enventbus(RefurbishMessageBean messageBean){
        if (messageBean.getFlag().equals("refurbish")){
            initCinemaMessageLayout();
        }
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
    //关注的点击事件
    public void onFollowOnClick(){
        cinemaMessageAdpter.setOnFollowOnClick(new CinemaMessageAdpter.onFollowOnClick() {
            @Override
            public void onFollowOnClick(int id, int follow) {
                loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
                if (loginSuccess){
                    if (follow==2){
                        getIsFollowData(id);
                        cinemaMessageAdpter.isFollow(id);
                    }
                    else {
                        getNoFollowData(id);
                        cinemaMessageAdpter.onfollow(id);
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
    //关注请求数据
    public void getIsFollowData(int id){
        getRequest(String.format(Apis.CINEMAISFOLLOW_URL,id),CinemaIsFollowBean.class);
    }
    //取消请求数据
    public void getNoFollowData(int id){
        getRequest(String.format(Apis.CINEMANOFOLLOW_URL,id),CinemaNoFollowBean.class);
    }
    //请求数据
    public void  getCinemaMessageData(){
        getRequest(String.format(Apis.CONEMARECOMMEND_URL,page,10),CinemaMessageBean.class);
    }
    @Override
    protected void success(Object object) {
         if (object instanceof CinemaMessageBean){
             CinemaMessageBean cinemaMessageBean = (CinemaMessageBean) object;
             List<CinemaMessageBean.ResultBean> result = cinemaMessageBean.getResult();
                 result.remove(result.size()-1);
             if (page==1){
                 cinemaMessageAdpter.setmList(result);
             }
             else {
                 cinemaMessageAdpter.addmList(result);
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
    public void onResume() {
        super.onResume();
        onFollowOnClick();
    }
    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_recommend;
    }
}

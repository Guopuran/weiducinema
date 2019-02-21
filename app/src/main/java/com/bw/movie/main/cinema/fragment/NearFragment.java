package com.bw.movie.main.cinema.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.MyApplication;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.login.bean.RefurbishMessageBean;
import com.bw.movie.main.bean.CinMessageBean;
import com.bw.movie.main.cinema.adpter.CinemaMessageAdpter;
import com.bw.movie.main.cinema.bean.CinemaIsFollowBean;
import com.bw.movie.main.cinema.bean.CinemaMessageBean;
import com.bw.movie.main.cinema.bean.CinemaNoFollowBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearFragment extends BaseFragment {
    @BindView(R.id.cinema_near_xrecycle)
    XRecyclerView near_recycle;
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
        initNearCinemaMessageLayout();
        //关注的点击事件
        onFollowOnClick();
    }
    //接受传值进行刷新
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void enventbus(RefurbishMessageBean messageBean){
        if (messageBean.getFlag().equals("refurbish")){
            initNearCinemaMessageLayout();
        }
    }

    //接受传值进行刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void enventbus(CinMessageBean messageBean){
        if (messageBean.getFlag().equals("chuan")){
            page=1;
            getNearCinemaMessageData(page);
        }
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
                getNearCinemaMessageData(page);
                near_recycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getNearCinemaMessageData(page);
                near_recycle.loadMoreComplete();
            }
        });
        getNearCinemaMessageData(page);
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
    public void  getNearCinemaMessageData(int cpage){
        getRequest(String.format(Apis.CONEMARENEAR_URL,cpage,10),CinemaMessageBean.class);
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
                EventBus.getDefault().post(new CinMessageBean("chuan"));
            }
        }
        if (object instanceof CinemaNoFollowBean){
            CinemaNoFollowBean cinemaNoFollowBean  = (CinemaNoFollowBean) object;
            if (cinemaNoFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(getContext(),cinemaNoFollowBean.getMessage());
                EventBus.getDefault().post(new CinMessageBean("chuan"));
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

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        //检测内存泄漏
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }*/

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_near;
    }
}

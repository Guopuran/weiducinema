package com.bw.movie.main.fragment;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.MyApplication;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.main.activity.LocationActivity;
import com.bw.movie.details.activity.DetailsActivity;
import com.bw.movie.main.movie.activity.MovieMroeActivity;
import com.bw.movie.main.movie.adpter.MoreMovieAdpter;
import com.bw.movie.main.movie.adpter.MovieBannerAdpter;
import com.bw.movie.main.movie.adpter.MovieHotAdpter;
import com.bw.movie.main.movie.adpter.MovieNowHotAdpter;
import com.bw.movie.main.movie.adpter.MovieWillAdpter;
import com.bw.movie.main.movie.bean.MovieBannerBean;
import com.bw.movie.main.movie.bean.MovieHotBean;
import com.bw.movie.main.movie.bean.MovieNowHotBean;
import com.bw.movie.main.movie.bean.MovieWillBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ImageViewAnimationHelper;
import com.bw.movie.util.ToastUtil;
import com.squareup.leakcanary.RefWatcher;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;


public class MovieFragment extends BaseFragment {
    @BindView(R.id.movie_banner_recycle)
    RecyclerCoverFlow recyclerCoverFlow;
    @BindView(R.id.movie_hot_recycle)
    RecyclerView hot_recycle;
    @BindView(R.id.movie_nowhot_recycle)
    RecyclerView nowhot_recycle;
    @BindView(R.id.movie_will_recycle)
    RecyclerView will_recycle;
    @BindView(R.id.movie_search_image)
    ImageView search_image;
    @BindView(R.id.movie_search_text)
    TextView search_text;
    @BindView(R.id.movie_search_relative)
    RelativeLayout search_relative;
    @BindView(R.id.movie_hot_movie_image)
    ImageView hot_more;
    @BindView(R.id.movie_nowhot_movie_image)
    ImageView nowhot_more;
    @BindView(R.id.movie_will_movie_image)
    ImageView will_more;
    @BindView(R.id.movie_location_but)
    Button location_but;
    @BindView(R.id.movie_location_text)
    TextView location_text;
    @BindView(R.id.checked_layout)
    LinearLayout checked_layout;
     MovieBannerAdpter movieBannerAdpter;
     MovieHotAdpter movieHotAdpter;
     MovieNowHotAdpter movieNowHotAdpter;
     MovieWillAdpter movieWillAdpter;
     int REQUEST=101;
    private ImageViewAnimationHelper imageViewAnimationHelper;

    @Override
    protected void initData()
    {
        //轮播请求数据
        getBannerData();
        //设置轮播
        setBannerRecycle();
        //热门的展示
        initHotLayout();
        //正在热映的展示
        initNowHotLayout();
        //即将上映的展示
        initWillLayout();
        //热门的跳转
        hotOnClick();
        //正在热映的跳转
        nowHotOnClick();
        //即将热映的跳转
        willOnClick();
        //banner的跳转
        bannerOnClick();
    }
    //定位的跳转
     @OnClick(R.id.movie_location_but)
     public void locationOnClick(){
         CityPicker.from(getActivity()) //activity或者fragment
                 .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))
                 .setOnPickListener(new OnPickListener() {
                     @Override
                     public void onPick(int position, City data) {
                          location_text.setText(data.getName());
                     }

                     @Override
                     public void onCancel(){
                         ToastUtil.showToast(getActivity(),"取消选择");
                     }

                     @Override
                     public void onLocate() {

                     }
                 })
                 .show();
     }
    //热门的跳转
    public void hotOnClick(){
        movieHotAdpter.setOnHotItemClickLisenter(new MovieHotAdpter.onHotClick() {
            @Override
            public void onHotClickItem(int id) {
                Intent intent = new Intent(getContext(),DetailsActivity.class);
                intent.putExtra("flag",id+"");;
                startActivityForResult(intent,REQUEST);
            }
        });
    }
    //正在热映的跳转
    public void nowHotOnClick(){
       movieNowHotAdpter.setOnNewHotItemClickLisenter(new MovieNowHotAdpter.onNewHotClick() {
           @Override
           public void onNewHotClickItem(int id) {
               Intent intent = new Intent(getContext(),DetailsActivity.class);
               intent.putExtra("flag",id+"");;
               startActivityForResult(intent,REQUEST);
           }
       });
    }
    //banner的跳转
    public void bannerOnClick(){
        movieBannerAdpter.setOnItembannerOnClick(new MovieBannerAdpter.bannerOnClick() {
            @Override
            public void bannerItemOnClickLisenter(int id) {
                Intent intent = new Intent(getContext(),DetailsActivity.class);
                intent.putExtra("flag",id+"");;
                startActivityForResult(intent,REQUEST);
            }
        });
    }
    //即将热映的跳转
    public void  willOnClick(){
        movieWillAdpter.setOnWillItemClickLisenter(new MovieWillAdpter.onWillClick() {
            @Override
            public void onWillClickItem(int id) {
                Intent intent = new Intent(getContext(),DetailsActivity.class);
                intent.putExtra("flag",id+"");;
                startActivityForResult(intent,REQUEST);
            }
        });
    }
    @OnClick(R.id.movie_hot_movie_image)
    public void hotMoreOnClick()
    {
        Intent intent = new Intent(getContext(),MovieMroeActivity.class);
        intent.putExtra("flag",0);
        startActivity(intent);
    }
    @OnClick(R.id.movie_nowhot_movie_image)
    public void nowHotMoreOnClick(){
        Intent intent = new Intent(getContext(),MovieMroeActivity.class);
        intent.putExtra("flag",1);
        startActivity(intent);
    }
    @OnClick(R.id.movie_will_movie_image)
    public void willMoreOnClick(){
        Intent intent = new Intent(getContext(),MovieMroeActivity.class);
        intent.putExtra("flag",2);
        startActivity(intent);
    }
    //弹出的动画
    @OnClick(R.id.movie_search_image)
    public void outAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(search_relative, "translationX",  -400f);
        animator.setDuration(1000);
        animator.start();
    }
    //收回的动画
    @OnClick(R.id.movie_search_text)
    public void inAnimation()
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(search_relative, "translationX",  10f);
        animator.setDuration(500);
        animator.start();
    }
    //设置轮播
    public void setBannerRecycle()
    {
        movieBannerAdpter  = new MovieBannerAdpter(getActivity());
        recyclerCoverFlow.setAdapter(movieBannerAdpter);
        recyclerCoverFlow.smoothScrollToPosition(4);
        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                imageViewAnimationHelper.startAnimation(position);
            }
        });


    }
    //热门的展示
    public void initHotLayout()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        movieHotAdpter = new MovieHotAdpter(getContext());
        hot_recycle.setAdapter(movieHotAdpter);
        hot_recycle.setLayoutManager(linearLayoutManager);
         getHotData();
    }
    //正在热映的展示
    public void initNowHotLayout()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        movieNowHotAdpter = new MovieNowHotAdpter(getContext());
        nowhot_recycle.setAdapter(movieNowHotAdpter);
        nowhot_recycle.setLayoutManager(linearLayoutManager);
        getNowHotData();
    }
    //即将上映的展示
    public void initWillLayout()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        movieWillAdpter = new MovieWillAdpter(getContext());
        will_recycle.setAdapter(movieWillAdpter);
        will_recycle.setLayoutManager(linearLayoutManager);
        getWillData();
    }
    //即将上映请求数据
    public void getWillData()
    {
        getRequest(String.format(Apis.MOVIEWWILL_URL,1,10),MovieWillBean.class);
    }
    //正在热映请求数据
     public void getNowHotData()
     {
         getRequest(String.format(Apis.MOVIEBAANNER_URL,1,10),MovieNowHotBean.class);
     }
    //热门电影请求数据
    public void getHotData(){
        getRequest(String.format(Apis.MOVIEHOT_URL,1,10),MovieHotBean.class);
    }
      //轮播请求数据
    public void getBannerData()
    {
        getRequest(String.format(Apis.MOVIEBAANNER_URL,1,10),MovieBannerBean.class);
    }
    @Override
    protected void success(Object object) {
         if (object instanceof MovieBannerBean){
             MovieBannerBean movieBannerBean = (MovieBannerBean) object;
             movieBannerAdpter.setmList(movieBannerBean.getResult());
             imageViewAnimationHelper = new ImageViewAnimationHelper(getContext(), checked_layout, 2, 31);

         }
         if (object instanceof MovieHotBean){
            MovieHotBean movieHotBean = (MovieHotBean) object;
            movieHotAdpter.setmList(movieHotBean.getResult());
        }
        if (object instanceof MovieNowHotBean){
            MovieNowHotBean movieNowHotBean = (MovieNowHotBean) object;
            movieNowHotAdpter.setmList(movieNowHotBean.getResult());
        }
        if (object instanceof MovieWillBean){
            MovieWillBean movieWillBean = (MovieWillBean) object;
            movieWillAdpter.setmList(movieWillBean.getResult());
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
        return R.layout.fragment_movie;
    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }*/
    //检测内存泄漏
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    private long exitTime=0;
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    //双击退出
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtil.showToast(getActivity(),"再按一次退出程序");
                        exitTime = System.currentTimeMillis();
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }

                return false;
            }
        });
    }
}

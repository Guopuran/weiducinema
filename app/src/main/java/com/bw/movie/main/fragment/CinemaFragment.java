package com.bw.movie.main.fragment;

import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.MyApplication;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.main.cinema.fragment.NearFragment;
import com.bw.movie.main.cinema.fragment.RecommendFragment;
import com.bw.movie.util.ToastUtil;
import com.squareup.leakcanary.RefWatcher;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CinemaFragment extends BaseFragment {
    @BindView(R.id.cinema_viewpager)
    ViewPager cinema_viewpager;
    @BindView(R.id.cinema_group)
    RadioGroup cineam_group;
    @BindView(R.id.movie_cinema_relative)
    RelativeLayout search_relative;
    private List<Fragment> mList;
    @BindView(R.id.movie_location_but1)
     Button location_but;
    @BindView(R.id.movie_location_text1)
     TextView location_text;
    @Override
    protected void initData() {

      mList = new ArrayList<>();
      mList.add(new RecommendFragment());
      mList.add(new NearFragment());
      cinema_viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
          @Override
          public Fragment getItem(int i) {
              return mList.get(i);
          }

          @Override
          public int getCount() {
              return mList.size();
          }
      });
      cinema_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int i, float v, int i1) {

          }

          @Override
          public void onPageSelected(int i) {
               switch (i){
                   case 0:
                     cineam_group.check(R.id.cinema_recommend_but);
                     break;
                   case 1:
                       cineam_group.check(R.id.cinema_near_but);
                       break;
                     default:
                         break;
               }
          }
          @Override
          public void onPageScrollStateChanged(int i) {

          }
      });
      cineam_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
              switch (checkedId){
                  case R.id.cinema_recommend_but:
                      cinema_viewpager.setCurrentItem(0);
                      break;
                  case R.id.cinema_near_but:
                      cinema_viewpager.setCurrentItem(1);
                      break;
                      default:
                          break;
              }
          }
      });
    }
    //定位的点击按钮
    @OnClick({R.id.movie_location_but1})
    public void onClickLocation(){
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
    @Override
    protected void success(Object object) {

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
        return R.layout.fragment_cinema;
    }
    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }
   //检测内存泄漏
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }*/

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

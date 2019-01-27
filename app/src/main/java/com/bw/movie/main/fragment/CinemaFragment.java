package com.bw.movie.main.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.main.cinema.fragment.NearFragment;
import com.bw.movie.main.cinema.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CinemaFragment extends BaseFragment {
    @BindView(R.id.cinema_viewpager)
    ViewPager cinema_viewpager;
    @BindView(R.id.cinema_group)
    RadioGroup cineam_group;
    private List<Fragment> mList;
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
}

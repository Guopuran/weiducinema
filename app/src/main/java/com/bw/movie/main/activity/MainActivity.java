package com.bw.movie.main.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.custom.CustomViewPager;
import com.bw.movie.main.fragment.CinemaFragment;
import com.bw.movie.main.fragment.MovieFragment;
import com.bw.movie.main.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.activity_mian_viewpager)
    CustomViewPager viewPager;
    @BindView(R.id.activity_mian_group)
    RadioGroup group;
    @BindView(R.id.activity_main_but_cineam)
    RadioButton but_cineam;
    @BindView(R.id.activity_main_but_movie)
    RadioButton but_movie;
    @BindView(R.id.activity_main_but_my)
    RadioButton but_my;
    private List<Fragment> mList;
    @Override
    protected void initData() {
       mList = new ArrayList<>() ;
       mList.add(new MovieFragment());
       mList.add(new CinemaFragment());
       mList.add(new MyFragment());
       viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
           @Override
           public Fragment getItem(int i) {
               return mList.get(i);
           }

           @Override
           public int getCount() {
               return mList.size();
           }
       });
       group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.activity_main_but_movie:
                       viewPager.setCurrentItem(0);
                       setAddAnimator(but_movie);
                       setCutAnimator(but_cineam);
                       setCutAnimator(but_my);
                       break;
                   case R.id.activity_main_but_cineam:
                       viewPager.setCurrentItem(1);
                       setAddAnimator(but_cineam);
                       setCutAnimator(but_movie);
                       setCutAnimator(but_my);
                       break;
                   case R.id.activity_main_but_my:
                       viewPager.setCurrentItem(2);
                       setAddAnimator(but_my);
                       setCutAnimator(but_movie);
                       setCutAnimator(but_cineam);
                       break;
                       default:
                           break;
               }
           }
       });
    }
    //点击放大
    private void setAddAnimator(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.17f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.17f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(0);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
    }

    //点击缩小
    private void setCutAnimator(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(0);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
    }

    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}

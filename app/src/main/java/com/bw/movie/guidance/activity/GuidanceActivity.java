package com.bw.movie.guidance.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.guidance.fragment.GuidanceFirstFragment;
import com.bw.movie.guidance.fragment.GuidanceFourthFragment;
import com.bw.movie.guidance.fragment.GuidanceSencondFragment;
import com.bw.movie.guidance.fragment.GuidanceThirdFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuidanceActivity extends BaseActivity {

     @BindView(R.id.guide_pager)
     ViewPager viewPager;
     @BindView(R.id.guide_group)
     RadioGroup group;
     private List<Fragment> mList;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guidance;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //绑定batterknife
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
           mList = new ArrayList<>();
           mList.add(new GuidanceFirstFragment());
           mList.add(new GuidanceSencondFragment());
           mList.add(new GuidanceThirdFragment());
           mList.add(new GuidanceFourthFragment());
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
    }

    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }
}

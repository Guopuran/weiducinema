package com.bw.movie.main.movie.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.main.movie.fragment.HotMoreFragment;
import com.bw.movie.main.movie.fragment.NowHotMoreFragment;
import com.bw.movie.main.movie.fragment.WillMoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieMroeActivity extends BaseActivity {
     @BindView(R.id.movie_more_viewpager)
     ViewPager viewPager;
     @BindView(R.id.movie_more_group)
     RadioGroup group;
     private List<Fragment> mList;
    private int flag;

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 4);
          mList = new ArrayList<>();
        mList.add(new HotMoreFragment());
        mList.add(new NowHotMoreFragment());
        mList.add(new WillMoreFragment());
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
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                  switch (i){
                      case 0:
                          group.check(R.id.activity_movie_more_hot_but);
                          break;
                      case 1:
                          group.check(R.id.activity_movie_more_nowhot_but);
                          break;
                      case 2:
                          group.check(R.id.activity_movie_more_will_but);
                          break;
                          default:
                              break;
                  }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.activity_movie_more_hot_but:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.activity_movie_more_nowhot_but:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.activity_movie_more_will_but:
                        viewPager.setCurrentItem(2);
                        break;
                        default:
                            break;
                }
            }
        });
        setLayout();

    }
    public void setLayout(){
        if (flag==0){
            viewPager.setCurrentItem(0);
            group.check(R.id.activity_movie_more_hot_but);
        }
        else if (flag==1){
            viewPager.setCurrentItem(1);
            group.check(R.id.activity_movie_more_nowhot_but);
        }
        else if(flag==2){
            viewPager.setCurrentItem(2);
            group.check(R.id.activity_movie_more_will_but);
        }
    }
    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_movie_mroe;
    }
}

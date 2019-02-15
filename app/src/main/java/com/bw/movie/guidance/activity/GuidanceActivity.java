package com.bw.movie.guidance.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.guidance.fragment.GuidanceFirstFragment;
import com.bw.movie.guidance.fragment.GuidanceFourthFragment;
import com.bw.movie.guidance.fragment.GuidanceSencondFragment;
import com.bw.movie.guidance.fragment.GuidanceThirdFragment;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.main.activity.MainActivity;

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
     private SharedPreferences sharedPreferences;
     private SharedPreferences.Editor editor;
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
           sharedPreferences = getSharedPreferences("User1",MODE_PRIVATE);
           editor = sharedPreferences.edit();
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
           //点击改变页面
      /*  group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_fir:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radio_sen:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.radio_thi:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.radio_fou:
                        viewPager.setCurrentItem(3);
                        break;
                        default:
                            break;
                }
            }
        });*/
        //滑动页面切换按钮
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                switch (i){
                    case 0:
                        group.check(R.id.radio_fir);
                        break;
                    case 1:
                        group.check(R.id.radio_sen);
                        break;
                    case 2:
                        group.check(R.id.radio_thi);
                        break;
                    case 3:
                        editor.putInt("falg",1);
                        editor.commit();
                        Intent intent = new Intent(GuidanceActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        group.check(R.id.radio_fou);
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //判断是否是第一次进入
        int falg = sharedPreferences.getInt("falg", 0);
        if (falg>0){
            Intent intent = new Intent(GuidanceActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }
}

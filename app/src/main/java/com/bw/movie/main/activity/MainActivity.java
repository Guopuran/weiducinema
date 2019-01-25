package com.bw.movie.main.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private List<Fragment> mList;

    @Override
    protected void initData() {

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

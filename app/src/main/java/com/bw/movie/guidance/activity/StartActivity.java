package com.bw.movie.guidance.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

public class StartActivity extends BaseActivity {
    int i=2;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int t=msg.what;
            if (t==0){
                //进行跳转

                Intent intent = new Intent(StartActivity.this,GuidanceActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

      new Thread(){
          @Override
          public void run() {
              super.run();
              while (i>0){
                  try {
                      i--;
                      //耗时操作
                      sleep(1000);
                      handler.sendEmptyMessage(i);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }
      }.start();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }
}

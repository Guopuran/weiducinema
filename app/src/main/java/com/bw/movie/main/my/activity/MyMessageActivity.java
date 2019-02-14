package com.bw.movie.main.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.main.my.bean.MyUserMeaagerBean;
import com.bw.movie.util.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMessageActivity extends BaseActivity {

    @BindView(R.id.my_cz_image)
    ImageView resetting;
    @BindView(R.id.my_yhtx_image)
    ImageView head_image;
    @BindView(R.id.my_name_text)
    TextView name_text;
    @BindView(R.id.my_brith_text)
    TextView brith_text;
    @BindView(R.id.my_phone_text)
    TextView phone_text;
    @BindView(R.id.my_emile_text)
    TextView email_text;
    @Override
    protected void initData() {
        getUserMessageData();
    }
    //退出登录的点击事件
    @OnClick(R.id.my_cz_image)
    public void czOnClick()
    {
        Intent intent = new Intent(this,ResettingPasswordActivity.class);
        startActivity(intent);
        finish();
    }
    //根据用户id查询用户信息
    public void getUserMessageData(){
        getRequest(Apis.SEARCHMESSAGE,MyUserMeaagerBean.class);
    }
    @Override
    protected void success(Object object) {
         if (object instanceof MyUserMeaagerBean){
             MyUserMeaagerBean userMeaagerBean = (MyUserMeaagerBean) object;
             MyUserMeaagerBean.ResultBean result = userMeaagerBean.getResult();
             name_text.setText(result.getNickName());
             Glide.with(this).load(result.getHeadPic()).into(head_image);
             brith_text.setText(result.getBirthday()+"");
             phone_text.setText(result.getPhone());
         }
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
        return R.layout.activity_my_message;
    }
}

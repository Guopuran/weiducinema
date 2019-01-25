package com.bw.movie.login.activity;



import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.bean.LoginBean;

import com.bw.movie.main.activity.MainActivity;

import com.bw.movie.util.Apis;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
     @BindView(R.id.login_text_phone)
     EditText edit_phone;
     @BindView(R.id.login_text_pwd)
     EditText edit_pawd;
     @BindView(R.id.login_check_rem)
     CheckBox check_rem;
     @BindView(R.id.login_check_auto)
     CheckBox check_auto;
     @BindView(R.id.login_but)
     Button but_login;
     @BindView(R.id.login_text_reg)
     TextView text_reg;
     private String phone;
     private String pwd;
     private String encrypt;
    //逻辑代码
    @Override
    protected void initData() {

    }
    //登录按钮的点击事件
    @OnClick(R.id.login_but)
    public void loginOnClick(){

        phone = edit_phone.getText().toString();
        pwd = edit_pawd.getText().toString();

        phone = edit_phone.getText().toString();
        pwd = edit_pawd.getText().toString();

        //密码加密
        encrypt = EncryptUtil.encrypt(pwd);
        Map<String,String> prams = new HashMap<>();
        prams.put("phone",phone);
        prams.put("pwd",encrypt);
        //请求数据
        postRequest(Apis.LOGIN_URL,prams,LoginBean.class);
    }
    //请求网络成功
    @Override
    protected void success(Object object) {
        if (object instanceof LoginBean){
            LoginBean loginBean = (LoginBean) object;
            if (loginBean.getStatus().equals("0000")){
                ToastUtil.showToast(this,loginBean.getMessage());

                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        }
    }
   //请求网路失败
    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }
}

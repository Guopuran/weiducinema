package com.bw.movie.login.activity;



import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.bean.LoginBean;

import com.bw.movie.main.activity.MainActivity;

import com.bw.movie.util.Apis;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.util.RegularUtil;
import com.bw.movie.util.ToastUtil;
import com.bw.movie.util.WeiXinUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

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
     @BindView(R.id.login_image_eye)
     ImageView image_eye;
     @BindView(R.id.login_weixin)
     ImageView image_weixin;
     private String phone;
     private String pwd;
     private String encrypt;
     private SharedPreferences sharedPreferences;
     private SharedPreferences.Editor editor;
    //逻辑代码
    @Override
    protected void initData() {
          sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
          editor = sharedPreferences.edit();
          //记住密码
        getCheckRem();
        //自动登录?????????????????????????????????????????????
       /* getCheckAuto();*/
        //自动登录的点击事件
        checkremOnClick();
        //小眼睛的显示隐藏
        eyesOnClick();
    }
    //记住密码
    public void getCheckRem(){
        boolean check_rem1 = sharedPreferences.getBoolean("check_rem1", false);
        if (check_rem1){
            String phonenum = sharedPreferences.getString("phone", null);
            String pass = sharedPreferences.getString("pass", null);
            edit_phone.setText(phonenum);
            edit_pawd.setText(pass);
            check_rem.setChecked(true);
        }
    }
    //自动登录
    public void getCheckAuto(){
        boolean check_auto1 = sharedPreferences.getBoolean("check_auto1", false);
        if (check_auto1){
            check_auto.setChecked(true);
            check_rem.setChecked(true);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    //自动登录的点击事件
    public void checkremOnClick(){
        check_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    check_rem.setChecked(true);
                }
                else {
                    editor.clear();
                    editor.commit();
                }

            }
        });
    }

    //微信的登陆的点击事件
    @OnClick(R.id.login_weixin)
    public void onWeiXinOnClick(){
        if (!WeiXinUtil.success(this)) {
           ToastUtil.showToast(this, "请先安装应用");
        } else
            {
            //  验证
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            WeiXinUtil.reg(LoginActivity.this).sendReq(req);
        }
    }
    //登录按钮的点击事件
    @OnClick(R.id.login_but)
    public void loginOnClick(){
        phone = edit_phone.getText().toString();
        pwd = edit_pawd.getText().toString();
        //请求数据
         if (RegularUtil.isNull(phone)){
             ToastUtil.showToast(this,"手机号不可以为空");
             return;
         }
        if (!(RegularUtil.isPhone(phone))) {
            ToastUtil.showToast(this,"请输入正确的手机格式");
            return;
        }
         if (RegularUtil.isPass(pwd)){
             ToastUtil.showToast(this,"密码必须大于6位");
             return;
         }
         //记住密码判断
          if (check_rem.isChecked()){
             editor.putString("phone",phone);
             editor.putString("pass",pwd);
             editor.putBoolean("check_rem1",true);
             editor.commit();
          }
          else {
             //清除所有的数据
             editor.clear();
             editor.commit();
          }
        //自动登录判断
          if (check_auto.isChecked()){
             editor.putBoolean("check_auto1",true);
             editor.commit();
          }

        //登录请求数据
        getLoginData();
    }
    //登录请求数据
    public void getLoginData()
    {
        //密码加密
        encrypt = EncryptUtil.encrypt(pwd);
        Map<String,String> prams = new HashMap<>();
        prams.put("phone",phone);
        prams.put("pwd",encrypt);
        postRequest(Apis.LOGIN_URL,prams,LoginBean.class);
    }
    //注册按钮的点击事件
    @OnClick(R.id.login_text_reg)
    public void regOnClick(){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    //小眼睛的显示隐藏
    @SuppressLint("ClickableViewAccessibility")
    public void eyesOnClick(){
        image_eye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    edit_pawd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    edit_pawd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return true;
            }
        });
    }
    //请求网络成功
    @Override
    protected void success(Object object)
    {
        if (object instanceof LoginBean){
            LoginBean loginBean = (LoginBean) object;
            if (loginBean.getStatus().equals("0000")){
                editor.putString("userId",loginBean.getResult().getUserId()+"");
                editor.putString("sessionId",loginBean.getResult().getSessionId()+"");
                editor.commit();
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

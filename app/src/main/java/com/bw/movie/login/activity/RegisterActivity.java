package com.bw.movie.login.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.bean.LoginBean;
import com.bw.movie.login.bean.RegisterBean;
import com.bw.movie.main.activity.MainActivity;
import com.bw.movie.util.Apis;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
     @BindView(R.id.reg_name)
     EditText edit_name;
     @BindView(R.id.reg_date)
     TextView text_date;
     @BindView(R.id.reg_emil)
     EditText edit_emil;
     @BindView(R.id.reg_pass)
     EditText edit_pass;
     @BindView(R.id.reg_sex)
     EditText edit_sex;
     @BindView(R.id.reg_but)
     Button but_reg;
     @BindView(R.id.reg_phone)
     EditText edit_phone;
    private String emil;
    private String name;
    private String pass;
    private String sex;
    private String phone;
    private String date;

    //逻辑代码
    @Override
    protected void initData() {

    }
      //注册按钮的点击事件
    @OnClick(R.id.reg_but)
    public void regOnClick(){
        emil = edit_emil.getText().toString();
        name = edit_name.getText().toString();
        pass = edit_pass.getText().toString();
        sex = edit_sex.getText().toString();
        phone = edit_phone.getText().toString();
        date = text_date.getText().toString();
        getRegData();
    }
    //注册请求网络
    public void  getRegData(){
        String decrypt = EncryptUtil.encrypt(pass);
        int sex1 = isSex();
        Map<String,String> prams = new HashMap<>();
        prams.put("nickName",name);
        prams.put("phone",phone);
        prams.put("pwd",decrypt);
        prams.put("sex", sex1+"");
        prams.put("birthday",date);
        prams.put("email",emil);
        prams.put("pwd2",decrypt);
        postRequest(Apis.REGISTER_URL,prams,RegisterBean.class);
    }
    //日期选择的点击事件
    @OnClick(R.id.reg_date)
    public void dateOnClick(){
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = getTime(date);
                text_date.setText(time);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .isCenterLabel(false)
                .build();
        pvTime.show();
    }
    //日期的转换
    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    //判断性别男女
    public int isSex(){
        if (sex.equals("男")){
            return  1;
        }
        if (sex.equals("女")){
            return 2;
        }
        return 0;
    }
    //获取网络消息
    @Override
    protected void success(Object object) {
       if (object instanceof RegisterBean){
           RegisterBean registerBean = (RegisterBean) object;
           if (registerBean.getStatus().equals("0000"))
           {
               ToastUtil.showToast(this,registerBean.getMessage());
               Map<String,String> map  = new HashMap<>();
               map.put("phone",phone);
               map.put("pwd",pass);
               postRequest(Apis.LOGIN_URL,map,LoginBean.class);
               Intent intent = new Intent(this,MainActivity.class);
               startActivity(intent);
           }
           else {
               ToastUtil.showToast(this,registerBean.getMessage());
           }

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
        return R.layout.activity_register;
    }
}

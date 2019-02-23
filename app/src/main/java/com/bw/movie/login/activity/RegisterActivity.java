package com.bw.movie.login.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.bean.LoginBean;
import com.bw.movie.login.bean.RegisterBean;
import com.bw.movie.login.bean.TokenBean;
import com.bw.movie.main.activity.MainActivity;
import com.bw.movie.util.Apis;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.util.ToastUtil;
import com.tencent.android.tpush.XGPushConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
     ImageView edit_sex;
     @BindView(R.id.reg_but)
     Button but_reg;
     @BindView(R.id.reg_phone)
     EditText edit_phone;
     @BindView(R.id.radio_man)
     RadioButton man;
     @BindView(R.id.radio_woman)
     RadioButton woman;
    private String emil;
    private String name;
    private String pass;
    private String sex;
    private String phone;
    private String date;
    private String encrypt;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String decrypt;

    //逻辑代码
    @Override
    protected void initData() {
        sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
      //注册按钮的点击事件
    @OnClick(R.id.reg_but)
    public void regOnClick(){
        emil = edit_emil.getText().toString();
        name = edit_name.getText().toString();
        pass = edit_pass.getText().toString();
        phone = edit_phone.getText().toString();
        date = text_date.getText().toString();
        getRegData();
    }
   /* //登录请求数据
    public void getLoginData()
    {
        //密码加密
        encrypt = EncryptUtil.encrypt(pass);
        Map<String,String> prams = new HashMap<>();
        prams.put("phone",phone);
        prams.put("pwd",encrypt);
        postRequest(Apis.LOGIN_URL,prams,LoginBean.class);
    }*/
    //注册请求网络
    public void  getRegData(){
        decrypt = EncryptUtil.encrypt(pass);
        Map<String,String> prams = new HashMap<>();
        prams.put("nickName",name);
        prams.put("phone",phone);
        prams.put("pwd", decrypt);
        prams.put("sex", sex+"");
        prams.put("birthday",date);
        prams.put("email",emil);
        prams.put("pwd2", decrypt);
        postRequest(Apis.REGISTER_URL,prams,RegisterBean.class);
    }
    //点击女
    @OnClick(R.id.radio_man)
    public void onClickWoman(){
            sex=1+"";
    }
    //点击男
    @OnClick(R.id.radio_woman)
    public void onClickman(){
            sex=2+"";
    }

    //收起软键盘
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    //日期选择的点击事件
    @OnClick(R.id.reg_date)
    public void dateOnClick()
    {
        hideInput();
        Calendar selectedDate = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(year-100,0,1);
        endDate.set(year,month,day);
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String time = sDateFormat.format(date);
                text_date.setText(time+"");
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                // 默认全部显示
                .setCancelText("取消")
                //取消按钮文字
                .setSubmitText("确定")
                //确认按钮文字
                .setOutSideCancelable(true)
                //点击屏幕，点在控件外部范围时，是否取消显示
                .setRangDate(startDate,endDate)
                //起始终止年月日设定
                .isCenterLabel(false)
                .setDate(selectedDate)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvTime.show();

    }
    //日期的转换
    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    //获取网络消息
    @Override
    protected void success(Object object)
    {
       if (object instanceof RegisterBean)
       {
           RegisterBean registerBean = (RegisterBean) object;
           if (registerBean.getStatus().equals("0000"))
           {
               ToastUtil.showToast(this,registerBean.getMessage());
               Map<String,String> map  = new HashMap<>();
               map.put("phone",phone);
               map.put("pwd",decrypt );
               postRequest(Apis.LOGIN_URL,map,LoginBean.class);


               // XGPushManager.registerPush(this);
               String token = XGPushConfig.getToken(this);
               Map<String,String> params=new HashMap();
               params.put("token",token);
               params.put("0s",1+"");
               postRequest(Apis.TOKEN,params,TokenBean.class);
           }
           else {
               ToastUtil.showToast(this,registerBean.getMessage());
           }

       }
        if (object instanceof LoginBean){
            LoginBean loginBean = (LoginBean) object;
            if (loginBean.getStatus().equals("0000")){
                editor.putString("userId",loginBean.getResult().getUserId()+"");
                editor.putString("sessionId",loginBean.getResult().getSessionId()+"");
                editor.putBoolean("loginSuccess",true);
                editor.commit();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }else {
                ToastUtil.showToast(RegisterActivity.this,loginBean.getMessage());
            }

        }

       if (object instanceof TokenBean){
           TokenBean tokenBean= (TokenBean) object;
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

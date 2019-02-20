package com.bw.movie.main.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.main.my.bean.MyUserMeaagerBean;
import com.bw.movie.main.my.bean.UpdateUserBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
    private EditText update_nick;
    private RadioGroup update_sex;
    private EditText update_email;
    private String nick;
    private PopupWindow popupWindow;
    private MyUserMeaagerBean userMeaagerBean;
    private int gender;
    @BindView(R.id.my_sex_text1)
     TextView sex_text;
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
    //点击姓名
    @OnClick(R.id.my_name_text)
    public void onClickName()
    {
        updateMessage();
    }
    //点击性别
    @OnClick(R.id.my_sex_text1)
    public void onClickSex(){
        updateMessage();
    }
    //点击邮箱
    @OnClick(R.id.my_emile_text)
    public void onClickEmile(){
        updateMessage();
    }
    @Override
    protected void onResume()
    {
        super.onResume();

    }

    //修改用户信息的popwindow
    private void updateMessage() {
        View view=View.inflate(this,R.layout.popwindow_update_message,null);
        update_nick = view.findViewById(R.id.update_nick);
        update_sex = view.findViewById(R.id.sex_group);
        update_email = view.findViewById(R.id.update_email);
        Button button=view.findViewById(R.id.user_but);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(view,Gravity.CENTER, 0, 0);
        update_nick.setText(userMeaagerBean.getResult().getNickName());
        int sex = userMeaagerBean.getResult().getSex();
        if (sex == 1){
            update_sex.check(R.id.sex_boy);
        }else{
            update_sex.check(R.id.sex_girl);
        }
        update_email.setText(userMeaagerBean.getResult().getEmail());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = update_email.getText().toString();
                nick = update_nick.getText().toString();
                int checkedRadioButtonId = update_sex.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.sex_boy){
                    gender = 1;
                }else if (checkedRadioButtonId == R.id.sex_girl){
                    gender = 2;
                }
                Map<String,String> params=new HashMap<>();
                params.put("nickName", nick);
                params.put("sex",gender+"");
                params.put("email", email);
               postRequest(Apis.UPDATEUSERMESSAGE,params,UpdateUserBean.class);
                popupWindow.dismiss();
            }
        });
    }
    //根据用户id查询用户信息
    public void getUserMessageData(){
        getRequest(Apis.SEARCHMESSAGE,MyUserMeaagerBean.class);
    }
    @Override
    protected void success(Object object) {
         if (object instanceof MyUserMeaagerBean){
             userMeaagerBean = (MyUserMeaagerBean) object;
             MyUserMeaagerBean.ResultBean result = userMeaagerBean.getResult();
             name_text.setText(result.getNickName());
             Glide.with(this).load(result.getHeadPic()).into(head_image);
             String date = new SimpleDateFormat("yyyy-MM-dd").format(result.getBirthday());
             brith_text.setText(date);
             phone_text.setText(result.getPhone());
             email_text.setText(result.getEmail());
             int sex = result.getSex();
             if (sex==1)
             {
                 sex_text.setText("男");
             }
             else
                 {
                 sex_text.setText("女");
             }
         }
         if (object instanceof UpdateUserBean){
             UpdateUserBean updateUserBean = (UpdateUserBean) object;
             if (updateUserBean.getStatus().equals("0000")){
                 ToastUtil.showToast(this,updateUserBean.getMessage());
                 getUserMessageData();
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
        return R.layout.activity_my_message;
    }
}

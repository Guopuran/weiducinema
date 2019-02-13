package com.bw.movie.main.my.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.main.my.bean.MyUpDatePasswordBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResettingPasswordActivity extends BaseActivity {

    @BindView(R.id.old_passwprd)
    EditText old_password;
    @BindView(R.id.new_password)
    EditText new_password;
    @BindView(R.id.new2_password)
    EditText surenew_password;
    @BindView(R.id.sure_upadate)
    Button sure_update;
    @BindView(R.id.my_update_back)
    Button back;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String pass;
    private String decrypt;
    private String newpw;
    private String surenewpw;
    private String oldpw;
    private String enewpw;
    private String esurenewpw;
    private String eoldnewpw;

    @Override
    protected void initData() {
       sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
       editor = sharedPreferences.edit();
        pass = sharedPreferences.getString("pass", null);
        old_password.setText(pass);
    }
    //确认修改的点击事件
    @OnClick(R.id.sure_upadate)
    public void  sureUpdateOnClick(){
        newpw = new_password.getText().toString();
        surenewpw = surenew_password.getText().toString();
        oldpw = old_password.getText().toString();
        enewpw = EncryptUtil.encrypt(newpw);
        esurenewpw = EncryptUtil.encrypt(surenewpw);
        eoldnewpw = EncryptUtil.encrypt(oldpw);
        if (enewpw.equals(esurenewpw)){
            getUpdatePwData(eoldnewpw,enewpw,esurenewpw);
        }
        else {
            ToastUtil.showToast(this,"密码不一致");
        }

    }
    //返回按钮
    @OnClick(R.id.my_update_back)
    public void onClickBack(){
        Intent intent = new Intent(this,MyMessageActivity.class);
        startActivity(intent);
        finish();
    }
    //请求网络
    public void getUpdatePwData(String old,String new1,String new2){
        Map<String,String> map = new HashMap<>();
        map.put("oldPwd",old);
        map.put("newPwd",new1);
        map.put("newPwd2",new2);
        postRequest(Apis.MY_UPDATE_PASSWORD,map,MyUpDatePasswordBean.class);
    }
    @Override
    protected void success(Object object) {
      if (object instanceof MyUpDatePasswordBean)
      {
          MyUpDatePasswordBean upDatePasswordBean = (MyUpDatePasswordBean) object;
          if (upDatePasswordBean.getStatus().equals("0000"))
          {
              ToastUtil.showToast(ResettingPasswordActivity.this,upDatePasswordBean.getMessage());
              Intent intent = new Intent(ResettingPasswordActivity.this,LoginActivity.class);
              startActivity(intent);
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
        return R.layout.activity_resetting_password;
    }
}

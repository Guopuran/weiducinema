package com.bw.movie.main.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.main.my.bean.MySuggestionBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySuggestioActivity extends BaseActivity {
    @BindView(R.id.suggestio)
    EditText suggestion;
    @BindView(R.id.my_suggestio_submit)
    Button submit;
    private String context;
    @BindView(R.id.my_suggestion_relative)
    RelativeLayout relative;
    @BindView(R.id.my_suggesstion_relative2)
    RelativeLayout relative_success;
    @BindView(R.id.my_suggestion_back)
    Button back;
    @Override
    protected void initData() {

    }
    //点击返回按钮
    @OnClick(R.id.my_suggestion_back)
    public void  backOnClick(){
        relative.setVisibility(View.VISIBLE);
        relative_success.setVisibility(View.INVISIBLE);
    }
    //点击提交数据
    @OnClick(R.id.my_suggestio_submit)
    public void  suggsestionOnClick(){
        context = suggestion.getText().toString();

        if (context.length()==0){
            ToastUtil.showToast(this,"请输入反馈内容");
            return;
        }
        else {
            getSuggestionData(context);
        }
        relative.setVisibility(View.INVISIBLE);
        relative_success.setVisibility(View.VISIBLE);
    }
   //提交反馈请求数据
    public void getSuggestionData(String suggestion){
        Map<String,String> parms = new HashMap<>();
        parms.put("context",suggestion);
        postRequest(Apis.MY_SUGGESTIN,parms,MySuggestionBean.class);
    }
    @Override
    protected void success(Object object) {
      if (object instanceof  MySuggestionBean){
          MySuggestionBean suggestionBean = (MySuggestionBean) object;
          if (suggestionBean.getStatus().equals("0000")){
              ToastUtil.showToast(this,suggestionBean.getMessage());
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
        return R.layout.activity_my_suggestio;
    }
}

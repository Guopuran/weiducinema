package com.bw.movie.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.main.my.activity.MyFollowActivity;
import com.bw.movie.main.my.activity.MyMessageActivity;
import com.bw.movie.main.my.activity.MySuggestioActivity;
import com.bw.movie.main.my.activity.TicketRecordActivity;
import com.bw.movie.main.my.bean.MySginBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFragment extends BaseFragment {
    @BindView(R.id.my_follow_image)
    ImageView image_follow;
    @BindView(R.id.my_suggestio_image)
    ImageView suggestio;
    @BindView(R.id.my_back_imgae)
    ImageView back;
    @BindView(R.id.my_message_imgae)
    ImageView message;
    @BindView(R.id.my_sign_text)
    TextView sign;
    @BindView(R.id.my_record_image)
    ImageView record;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean loginSuccess;
    @Override
    protected void initData() {
            sharedPreferences = getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
    }
    //点击关注
    @OnClick(R.id.my_follow_image)
    public void  onClickFollow(){
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),MyFollowActivity.class);
            startActivity(intent);
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
    }
    //点击反馈信息
    @OnClick(R.id.my_suggestio_image)
    public void onClicksuggrstio(){
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),MySuggestioActivity.class);
            startActivity(intent);
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
    }
    //退出登录
    @OnClick(R.id.my_back_imgae)
    public void onClickBack()
    {
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            intent.putExtra("back","main");
            startActivity(intent);
            editor.putString("userId",null);
            editor.putString("sessionId",null);
            editor.putBoolean("loginSuccess",false);
            editor.commit();
            getActivity().finish();
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }
    @OnClick(R.id.my_message_imgae)
    public void onClickMessage()
    {
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),MyMessageActivity.class);
            startActivity(intent);
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }
    //签到的点击事件
    @OnClick(R.id.my_sign_text)
    public void onSingClick(){
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            getSignData();
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }
    //签到的网络请求
    public void getSignData()
    {
        getRequest(Apis.MY_SING,MySginBean.class);
    }
    //点击到消费记录
    @OnClick(R.id.my_record_image)
    public void onClickRecord(){
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),TicketRecordActivity.class);
            startActivity(intent);
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }
    @Override
    protected void success(Object object)
    {
        if (object instanceof MySginBean){
            MySginBean mySginBean = (MySginBean) object;
            if (mySginBean.getStatus().equals("0000")){
                ToastUtil.showToast(getActivity(),mySginBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_my;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    private long exitTime=0;
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //双击退出
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtil.showToast(getActivity(),"再按一次退出程序");
                        exitTime = System.currentTimeMillis();
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}

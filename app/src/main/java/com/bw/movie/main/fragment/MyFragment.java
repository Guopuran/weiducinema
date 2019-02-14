package com.bw.movie.main.fragment;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.main.activity.LocationActivity;
import com.bw.movie.main.my.activity.MyFollowActivity;
import com.bw.movie.main.my.activity.MyMessageActivity;
import com.bw.movie.main.my.activity.MySuggestioActivity;
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
    @Override
    protected void initData() {

    }
    @OnClick(R.id.my_follow_image)
    public void  onClickFollow(){
        Intent intent = new Intent(getActivity(),MyFollowActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.my_suggestio_image)
    public void onClicksuggrstio(){
         Intent intent = new Intent(getActivity(),MySuggestioActivity.class);
         startActivity(intent);
    }
    @OnClick(R.id.my_back_imgae)
    public void onClickBack(){
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    @OnClick(R.id.my_message_imgae)
    public void onClickMessage(){
        Intent intent = new Intent(getActivity(),MyMessageActivity.class);
        startActivity(intent);
    }
    //签到的点击事件
    @OnClick(R.id.my_sign_text)
    public void onSingClick(){
        getSignData();
    }
    //签到的网络请求
    public void getSignData(){
        getRequest(Apis.MY_SING,MySginBean.class);
    }
    @Override
    protected void success(Object object) {
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

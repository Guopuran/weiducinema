package com.bw.movie.details.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.details.adapter.SelectIdTheatreAdapter;
import com.bw.movie.details.bean.SelectIdTheatreBean;
import com.bw.movie.util.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @描述 根据电影ID查找电影院
 * @创建日期 2019/1/29 9:39
 */
public class TheatreActivity extends BaseActivity {


    @BindView(R.id.theatre_text_name)
    TextView text_name;
    @BindView(R.id.theatre_recy)
    RecyclerView theatre_recy;
    @BindView(R.id.theatre_button_back)
    Button button_back;
    private String movieid;
    private SelectIdTheatreAdapter selectIdTheatreAdapter;
    private int index;

    @Override
    protected void initData() {
        //获取值
        initIntent();
        //发送请求
        setUrl();
        //初始化recy
        initRecy();
    }

    private void initRecy() {
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        theatre_recy.setLayoutManager(linearLayoutManager);
        //设置适配器
        selectIdTheatreAdapter = new SelectIdTheatreAdapter(this);
        theatre_recy.setAdapter(selectIdTheatreAdapter);
        /*selectIdTheatreAdapter.setOnImageClick(new SelectIdTheatreAdapter.OnImageClick() {
            @Override
            public void getdata(int id, int great, int position) {
                index=position;
                if (great==0){//进行关注
                    getRequest(String.format(Apis.CINEMAISFOLLOW_URL,id),CinemaIsFollowBean.class);
                }else{//取消关注
                    getRequest(String.format(Apis.CINEMANOFOLLOW_URL,id),CinemaNoFollowBean.class);

                }
            }
        });*/
    }

    private void setUrl() {
        getRequest(String.format(Apis.SELECT_ID_THEATRE, movieid), SelectIdTheatreBean.class);
    }

    private void initIntent() {
        Intent intent = getIntent();
        movieid = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        text_name.setText(name);
    }

    @Override
    protected void success(Object object) {
        if (object instanceof SelectIdTheatreBean) {
            SelectIdTheatreBean selectIdTheatreBean = (SelectIdTheatreBean) object;
            selectIdTheatreAdapter.setList(selectIdTheatreBean.getResult());
        }
       /* if (object instanceof CinemaIsFollowBean){
            CinemaIsFollowBean cinemaIsFollowBean= (CinemaIsFollowBean) object;
            if (cinemaIsFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(this,cinemaIsFollowBean.getMessage());
                selectIdTheatreAdapter.getlike(index);
            }
        }
        if (object instanceof CinemaNoFollowBean){
            CinemaNoFollowBean cinemaNoFollowBean= (CinemaNoFollowBean) object;
            if (cinemaNoFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(this,cinemaNoFollowBean.getMessage());
                selectIdTheatreAdapter.getcancel(index);
            }
        }*/
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
        return R.layout.activity_theatre;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.theatre_button_back)
    public void onViewClicked() {
        finish();
    }
}

package com.bw.movie.main.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.main.my.adpter.MySystemMessageAdpter;
import com.bw.movie.main.my.bean.MySystemMessageBean;
import com.bw.movie.main.my.bean.MySystemMessageReadBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySystemMessageActivity extends BaseActivity {

    @BindView(R.id.system_message_recycler)
    XRecyclerView system_recycle;
    MySystemMessageAdpter systemMessageAdpter;
    private int page=1;

    @Override
    protected void initData() {
        //加载布局
        initSystemMessageLoayout();
        //点击读取消息
        onCLick();
    }
    //点击读取消息
    public void onCLick(){
        systemMessageAdpter.setOnClic(new MySystemMessageAdpter.OnClick() {
            @Override
            public void OnClickLisenter(int id, int i) {
                getSystemMesageReadData(id);
                systemMessageAdpter.onRefresh(i);
            }
        });
    }
    //加载布局
    public void initSystemMessageLoayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        system_recycle.setLayoutManager(linearLayoutManager);
        systemMessageAdpter = new MySystemMessageAdpter(this);
        system_recycle.setAdapter(systemMessageAdpter);
        system_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getSystemMessageList();
                system_recycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getSystemMessageList();
                system_recycle.loadMoreComplete();
            }
        });
        getSystemMessageList();
    }
    //读取消息
    public void getSystemMesageReadData(int id){
        getRequest(String.format(Apis.SYSTEM_MESSAGE_READ,id),MySystemMessageReadBean.class);
    }
    //读取类表请求数据
    public void getSystemMessageList()
    {
        getRequest(String.format(Apis.SYSTEM_MESSAGE_LIST,page,10),MySystemMessageBean.class);
    }
    @Override
    protected void success(Object object) {
           if (object instanceof MySystemMessageBean){
               MySystemMessageBean systemMessageBean = (MySystemMessageBean) object;
               if (page==1){
                   systemMessageAdpter.setmList(systemMessageBean.getResult());
               }
               else {
                   systemMessageAdpter.addmList(systemMessageBean.getResult());
               }
               page++;
           }
           if (object instanceof MySystemMessageReadBean){
               MySystemMessageReadBean systemMessageReadBean = (MySystemMessageReadBean) object;
               if (systemMessageReadBean.getStatus().equals("0000")){
                   ToastUtil.showToast(this,systemMessageReadBean.getMessage());

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
        return R.layout.activity_my_system_message;
    }
}

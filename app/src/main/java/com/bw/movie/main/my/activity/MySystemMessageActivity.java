package com.bw.movie.main.my.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.main.my.adpter.MySystemMessageAdpter;
import com.bw.movie.main.my.bean.MySystemMessageBean;
import com.bw.movie.main.my.bean.MySystemMessageReadBean;
import com.bw.movie.main.my.bean.SystemMessageCountBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySystemMessageActivity extends BaseActivity {

    @BindView(R.id.system_message_recycler)
    XRecyclerView system_recycle;
    MySystemMessageAdpter systemMessageAdpter;
    private int page=1;
     @BindView(R.id.system_message_back)
    ImageView system_message_back;
     @BindView(R.id.system_message_unread)
    TextView unread;
    private int count;
    @Override
    protected void initData()
    {
        //加载布局
        initSystemMessageLoayout();
        //点击读取消息
        onCLick();
        //未读消息请求网络
        getSystemMessage();
    }
    //点击读取消息
    public void onCLick(){
        systemMessageAdpter.setOnClick(new MySystemMessageAdpter.OnClick() {
            @Override
            public void OnClickLisenter(final int id, final int i, String mesage) {
                View view = View.inflate(MySystemMessageActivity.this,R.layout.system_mesage_pop_item,null);
                final TextView system_message = view.findViewById(R.id.system_message);
                system_message.setText(mesage);
                Dialog dialog = new AlertDialog.Builder(MySystemMessageActivity.this)
                        .setView(view)
                        .setPositiveButton("知道了",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getSystemMesageReadData(id);
                                        systemMessageAdpter.onRefresh(i);
                                        count--;
                                        unread.setText("("+ count +"条未读)");

                                    }
                                })
                        /* .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              *//*  Toast.makeText(MySystemMessageActivity.this, "您已取消操作", Toast.LENGTH_LONG).show();*//*
                            }
                        })*/.create();
                dialog.show();
            }
        });
    }
    //点击返回按钮
    @OnClick(R.id.system_message_back)
    public void backOnClick(){
        finish();
    }
    //加载布局
    public void initSystemMessageLoayout()
    {
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
            public void onLoadMore()
            {
                getSystemMessageList();
                system_recycle.loadMoreComplete();
            }
        });
        getSystemMessageList();
    }
    //未读消息请求网络
    public void getSystemMessage()
    {
        getRequest(Apis.SYSTEM_MESSAGE_COUNT,SystemMessageCountBean.class);
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
           if (object instanceof SystemMessageCountBean){
              SystemMessageCountBean systemMessageCountBean = (SystemMessageCountBean) object;
                   count = systemMessageCountBean.getCount();
                   unread.setText("("+ count +"条未读)");
           }
    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState)
    {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_my_system_message;
    }
}

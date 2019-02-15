package com.bw.movie.main.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.main.my.adpter.MyCompleAdpter;
import com.bw.movie.main.my.adpter.MyWaitPayAdpter;
import com.bw.movie.main.my.bean.MyTicketRecrodBean;
import com.bw.movie.main.my.bean.MyTicketRecrodBean1;
import com.bw.movie.util.Apis;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TicketRecordActivity extends BaseActivity {

    @BindView(R.id.my_ticket_recycle)
    XRecyclerView recyclerView;
    @BindView(R.id.my_waitpay_radio)
    RadioButton waitpay;
    @BindView(R.id.my_complete_radio)
    RadioButton complete;
    private int wpage=1;
    private int cpage=1;
    MyWaitPayAdpter waitPayAdpter;
    MyCompleAdpter compleAdpter;
    @Override
    protected void initData() {
      initWaitLayout();
    }
    @OnClick(R.id.my_waitpay_radio)
    public void waitOnClick(){
        initWaitLayout();
    }
    @OnClick(R.id.my_complete_radio)
    public void comOnClick(){
        initComLayout();
    }

    //待付款加载布局
    public void  initWaitLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        waitPayAdpter = new MyWaitPayAdpter(this);
        wpage=1;
        recyclerView.setAdapter(waitPayAdpter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                wpage=1;
                recyclerView.refreshComplete();
                getWatiData();
            }

            @Override
            public void onLoadMore() {
                getWatiData();
                recyclerView.loadMoreComplete();
            }
        });
        getWatiData();
    }
    //已完成加载布局
    public void  initComLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        compleAdpter = new MyCompleAdpter(this);
        cpage=1;
        recyclerView.setAdapter(compleAdpter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                cpage=1;
                recyclerView.refreshComplete();
                getComData();
            }
            @Override
            public void onLoadMore() {
                getComData();
                recyclerView.loadMoreComplete();
            }
        });
        getComData();
    }
    //待付款请求数据
    public void getWatiData(){
        getRequest(String.format(Apis.MY_TICKETRECROD,wpage,10,1),MyTicketRecrodBean.class);
    }
    //已完成请求数据
    public void getComData(){
        getRequest(String.format(Apis.MY_TICKETRECROD,cpage,10,2),MyTicketRecrodBean1.class);
    }
    @Override
    protected void success(Object object) {
        if (object instanceof MyTicketRecrodBean){
            MyTicketRecrodBean ticketRecrodBean = (MyTicketRecrodBean) object;
            if (wpage==1){
               waitPayAdpter.setmList(ticketRecrodBean.getResult());

            }
           else {
               waitPayAdpter.addmList(ticketRecrodBean.getResult());
           }
           wpage++;
        }
        if (object instanceof MyTicketRecrodBean1){
            MyTicketRecrodBean1 ticketRecrodBean1 = (MyTicketRecrodBean1) object;
            if (cpage==1){
                compleAdpter.setmList(ticketRecrodBean1.getResult());

            }
            else {
                 compleAdpter.addmList(ticketRecrodBean1.getResult());
            }
            cpage++;
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
        return R.layout.activity_ticket_record;
    }
}

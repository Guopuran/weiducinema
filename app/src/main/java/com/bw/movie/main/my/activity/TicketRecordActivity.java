package com.bw.movie.main.my.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.custom.PayDialog;
import com.bw.movie.main.my.adpter.MyCompleAdpter;
import com.bw.movie.main.my.adpter.MyWaitPayAdpter;
import com.bw.movie.main.my.bean.MyTicketRecrodBean;
import com.bw.movie.main.my.bean.MyTicketRecrodBean1;
import com.bw.movie.seat.activity.SeatActivity;
import com.bw.movie.seat.bean.AliBean;
import com.bw.movie.seat.bean.PayMessageBaen;
import com.bw.movie.seat.bean.PayResult;
import com.bw.movie.seat.bean.WXPayBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
import com.bw.movie.util.WeiXinUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 *
 * @描述 订单页面
 *
 * @创建日期 2019/2/18 10:43
 *
 */
public class TicketRecordActivity extends BaseActivity {

    @BindView(R.id.my_ticket_recycle)
    XRecyclerView recyclerView;
    @BindView(R.id.my_waitpay_radio)
    RadioButton waitpay;
    @BindView(R.id.my_complete_radio)
    RadioButton complete;
    @BindView(R.id.rela)
    RelativeLayout relativeLayout;
    private int wpage=1;
    private int cpage=1;
    MyWaitPayAdpter waitPayAdpter;
    MyCompleAdpter compleAdpter;
    private RadioButton dialog_weixin_button;
    private RadioButton dialog_zhifubao_button;
    private RadioGroup dialog_pay_group;
    private TextView dialog_text_pay;
    private final int WX_PAY_CODE=1;
    private MyTicketRecrodBean ticketRecrodBean;
    private List<MyTicketRecrodBean.ResultBean> result;
    private String orderid;
    private final int SDK_PAY_FLAG=1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.showToast(TicketRecordActivity.this,"支付成功");
                        complete.setChecked(true);
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。

                        ToastUtil.showToast(TicketRecordActivity.this,"支付失败");
                    }
                    break;
                }
            }
        };
    };
    private AliBean aliBean;

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
        waitPayAdpter.setonclick(new MyWaitPayAdpter.onclick() {
            @Override
            public void setonclick(double price,String orderId) {
                orderid=orderId;
                initDialog(price);
            }
        });
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
    public void initClick(int i)
    {

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
            result = ticketRecrodBean.getResult();
            if (wpage==1){
                relativeLayout.setVisibility(View.GONE);
               waitPayAdpter.setmList(ticketRecrodBean.getResult());

            }
           else {
                relativeLayout.setVisibility(View.GONE);
               waitPayAdpter.addmList(ticketRecrodBean.getResult());
           }
           wpage++;
            recyclerView.loadMoreComplete();;
            recyclerView.refreshComplete();
            if (ticketRecrodBean.getResult().size()==0){
                recyclerView.setLoadingMoreEnabled(false);
            }
            if (waitPayAdpter.getmList().size()==0){
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }
        if (object instanceof MyTicketRecrodBean1){
            MyTicketRecrodBean1 ticketRecrodBean1 = (MyTicketRecrodBean1) object;
            if (cpage==1){
                relativeLayout.setVisibility(View.GONE);
                compleAdpter.setmList(ticketRecrodBean1.getResult());

            }
            else {
                relativeLayout.setVisibility(View.GONE);
                compleAdpter.addmList(ticketRecrodBean1.getResult());
            }
            cpage++;
            recyclerView.loadMoreComplete();;
            recyclerView.refreshComplete();
            if (ticketRecrodBean1.getResult().size()==0)
            {
                recyclerView.setLoadingMoreEnabled(false);
            }
            if (compleAdpter.getmList().size()==0){
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }
        if (object instanceof WXPayBean){
            WXPayBean wxPayBean= (WXPayBean) object;
            if (wxPayBean.getStatus().equals("0000")){
                WeiXinUtil.weiXinPay(this,wxPayBean);
            }
        }
        if (object instanceof AliBean){
            aliBean = (AliBean) object;
            if (aliBean.getStatus().equals("0000")){
                final String orderInfo = aliBean.getResult();   // 订单信息

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(TicketRecordActivity.this);
                        Map <String,String> result = alipay.payV2(orderInfo,true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        }
    }

    public View initDiaLog(int getLayoutId) {
        View pay_view = View.inflate(this, getLayoutId, null);
        final PayDialog dialog = new PayDialog(this, pay_view);
        ImageView image_back = pay_view.findViewById(R.id.dialog_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();

        return pay_view;
    }

    @Override
    protected void failed(String error) {
        ToastUtil.showToast(this,error);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //注册
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ticket_record;
    }

    private void initDialog(final double price) {
        View view = initDiaLog(R.layout.dialog_pay);
        dialog_pay_group = view.findViewById(R.id.radio_pay);
        dialog_weixin_button = view.findViewById(R.id.weixin_pay);
        dialog_zhifubao_button = view.findViewById(R.id.zhifubao_pay);
        dialog_text_pay = view.findViewById(R.id.text_pay);
        dialog_text_pay.setText("微信支付"+new BigDecimal(price).setScale(2,BigDecimal.ROUND_DOWN)+"元");
        dialog_pay_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.weixin_pay:
                        dialog_text_pay.setText("微信支付"+new BigDecimal(price).setScale(2,BigDecimal.ROUND_DOWN)+"元");
                        break;
                    case R.id.zhifubao_pay:
                        dialog_text_pay.setText("支付宝支付"+new BigDecimal(price).setScale(2,BigDecimal.ROUND_DOWN)+"元");
                        break;
                }
            }
        });
        //点击支付
        clickPay();
    }
    private void clickPay() {

        dialog_text_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_weixin_button.isChecked()){
                    initWeixinPayUrl();
                }
                if (dialog_zhifubao_button.isChecked()){
                    initAliPayUrl();
                }
            }
        });
    }
    //支付宝支付路径
    private void initAliPayUrl() {

        Map<String,String> params=new HashMap<>();
        params.put("payType","2");
        params.put("orderId",orderid);
        postRequest(Apis.PAY,params,AliBean.class);

    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void initOpen(PayMessageBaen payMessageBaen){
        if (payMessageBaen.getPay_message().equals("pay")){
            complete.setChecked(true);
        }
    }
    private void initWeixinPayUrl() {
        Map<String,String> params=new HashMap<>();
        params.put("payType",WX_PAY_CODE+"");
        params.put("orderId",orderid);
        postRequest(Apis.PAY,params,WXPayBean.class);
    }

    /**
     *  MD5加密
     * @param sourceStr
     * @return
     */
    public  String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册
        EventBus.getDefault().unregister(this);
    }
}

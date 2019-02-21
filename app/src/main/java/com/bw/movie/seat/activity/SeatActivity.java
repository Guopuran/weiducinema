package com.bw.movie.seat.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.MyApplication;
import com.bw.movie.R;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.base.BaseActivity;;
import com.bw.movie.custom.CustomDialog;
import com.bw.movie.custom.PayDialog;
import com.bw.movie.details.bean.MovieScheduleBean;
import com.bw.movie.main.my.activity.TicketRecordActivity;
import com.bw.movie.seat.bean.AliBean;
import com.bw.movie.seat.bean.CinemaSeatBean;
import com.bw.movie.seat.bean.IndentPayBean;
import com.bw.movie.seat.bean.PayMessageBaen;
import com.bw.movie.seat.bean.PayResult;
import com.bw.movie.seat.bean.WXPayBean;
import com.bw.movie.seat.custom.SeatTable;
import com.bw.movie.util.Apis;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.util.ToastUtil;
import com.bw.movie.util.WeiXinUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class SeatActivity extends BaseActivity {

    @BindView(R.id.cinema_seat_table_text_beginTime)
    TextView mTextView_beginTime;

    @BindView(R.id.cinema_seat_table_text_endTime)
    TextView mTextView_endTime;

    @BindView(R.id.cinema_seat_table_text_hall)
    TextView mTextView_hall;

    @BindView(R.id.item_cinema_seat_text_price)
    TextView mTextView_price;

    @BindView(R.id.item_cinema_detail_img_v)
    ImageView mImageView_v;

    public SeatTable seatTableView;
    @BindView(R.id.item_cinema_detail_img_x)
    ImageView mImageView_x;
    private int mSeatsTotal=0;
    double totalPrice;
    private MovieScheduleBean.ResultBean resultBean;
    private double price;
    private RadioButton dialog_weixin_button;
    private RadioButton dialog_zhifubao_button;
    private RadioGroup dialog_pay_group;
    private TextView dialog_text_pay;
    private IndentPayBean indentPayBean;
    private final int WX_PAY_CODE=1;
    private AliBean aliBean;
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
                        ToastUtil.showToast(SeatActivity.this,"支付成功");
                        Intent intent=new Intent(SeatActivity.this,TicketRecordActivity.class);
                        startActivity(intent);
                        EventBus.getDefault().postSticky(new PayMessageBaen("pay"));
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。

                        ToastUtil.showToast(SeatActivity.this,"支付失败");
                    }
                    break;
                }
            }
        };
    };
    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        //接收值
        initIntent();
    }

    private void initIntent() {
        Intent intent=getIntent();
        resultBean = (MovieScheduleBean.ResultBean) intent.getSerializableExtra("item");
        mTextView_beginTime.setText(resultBean.getBeginTime());
        mTextView_endTime.setText(resultBean.getEndTime());
        mTextView_hall.setText(resultBean.getScreeningHall());
        totalPrice = resultBean.getPrice();
    }

    @Override
    public void initData() {
        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName(resultBean.getScreeningHall()+"荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                mSeatsTotal++;
                price=totalPrice*mSeatsTotal;
                mTextView_price.setText(new BigDecimal(price).setScale(2,BigDecimal.ROUND_DOWN) + "");

            }

            @Override
            public void unCheck(int row, int column) {
                mSeatsTotal--;
                price=totalPrice*mSeatsTotal;
                mTextView_price.setText(new BigDecimal(price).setScale(2,BigDecimal.ROUND_DOWN) + "");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);

    }

    @OnClick(R.id.item_cinema_detail_img_x)
    public void onImgXClickListener() {
        finish();
    }

    @OnClick(R.id.item_cinema_detail_img_v)
    public void onImgVClickListener() {
        BigDecimal bigDecimal = new BigDecimal(price).setScale(2, BigDecimal.ROUND_DOWN);

        if (price<=0.00d){
            ToastUtil.showToast(SeatActivity.this,"请先选座");
            return ;
        }

        //生成订单
        initIndentUrl();

    }

    private void initIndentUrl() {
        Map<String,String> params=new HashMap<>();
        params.put("scheduleId",resultBean.getId()+"");
        params.put("amount",mSeatsTotal+"");
        SharedPreferences mSharedPreferences=MyApplication.getContext().getSharedPreferences("User",Context.MODE_PRIVATE);
        String userId = mSharedPreferences.getString("userId","");
        String qianming=userId+resultBean.getId()+mSeatsTotal+"movie";
        //String encrypt = EncryptUtil.encrypt(qianming);
        String s = MD5(qianming);
        params.put("sign",s);
        postRequest(Apis.INDENTPAY,params,IndentPayBean.class);
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
    public void success(Object object) {
        if (object instanceof IndentPayBean){
            indentPayBean = (IndentPayBean) object;
            if (indentPayBean.getStatus().equals("0000")){
                ToastUtil.showToast(SeatActivity.this, indentPayBean.getMessage());
                initDialog();
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
                        PayTask alipay = new PayTask(SeatActivity.this);
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

    private void initDialog() {
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
        params.put("orderId",indentPayBean.getOrderId());
        postRequest(Apis.PAY,params,AliBean.class);

    }

    private void initWeixinPayUrl() {
        Map<String,String> params=new HashMap<>();
        params.put("payType",WX_PAY_CODE+"");
        params.put("orderId",indentPayBean.getOrderId());
        postRequest(Apis.PAY,params,WXPayBean.class);
    }

    @Override
    protected void failed(String error) {
        ToastUtil.showToast(this,error);
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_seat;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacks(null);
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
}

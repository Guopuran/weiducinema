package com.bw.movie.seat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.bw.movie.R;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.base.BaseActivity;;
import com.bw.movie.custom.CustomDialog;
import com.bw.movie.custom.PayDialog;
import com.bw.movie.details.bean.MovieScheduleBean;
import com.bw.movie.seat.bean.CinemaSeatBean;
import com.bw.movie.seat.custom.SeatTable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.math.BigDecimal;

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
        View view = initDiaLog(R.layout.dialog_pay);


    }
    public View initDiaLog(int getLayoutId) {
        View details_view = View.inflate(this, getLayoutId, null);
        final PayDialog dialog = new PayDialog(this, details_view);
        dialog.show();

        return details_view;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    protected void failed(String error) {

    }



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_seat;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}

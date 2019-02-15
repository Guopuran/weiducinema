package com.bw.movie.main.my.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.main.my.bean.MyTicketRecrodBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWaitPayAdpter extends RecyclerView.Adapter<MyWaitPayAdpter.ViewHolder> {

    private List<MyTicketRecrodBean.ResultBean> mList;
    private Context mContext;
    public MyWaitPayAdpter(Context mContext)
    {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<MyTicketRecrodBean.ResultBean> list)
    {
        mList.clear();
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addmList(List<MyTicketRecrodBean.ResultBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext,R.layout.ticket_wait,null);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.ticketWaitName.setText(mList.get(i).getMovieName());
        viewHolder.ticketWaitOrder.setText(mList.get(i).getOrderId());
        viewHolder.ticketWaitMovie.setText(mList.get(i).getCinemaName());
        viewHolder.ticketWaitFilm.setText(mList.get(i).getScreeningHall());
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(mList.get(i).getCreateTime());
        viewHolder.ticketWaitTime.setText(date);
        viewHolder.ticketWaitNum.setText(mList.get(i).getAmount()+"张");
        viewHolder.ticketWaitPrice.setText(mList.get(i).getPrice()+"元");
        viewHolder.ticketWaitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:这里是去付款
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ticket_wait_name)
        TextView ticketWaitName;
        @BindView(R.id.ticket_wait_order)
        TextView ticketWaitOrder;
        @BindView(R.id.ticket_wait_movie)
        TextView ticketWaitMovie;
        @BindView(R.id.ticket_wait_film)
        TextView ticketWaitFilm;
        @BindView(R.id.ticket_wait_time)
        TextView ticketWaitTime;
        @BindView(R.id.ticket_wait_num)
        TextView ticketWaitNum;
        @BindView(R.id.ticket_wait_price)
        TextView ticketWaitPrice;
        @BindView(R.id.ticket_wait_btn)
        Button ticketWaitBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

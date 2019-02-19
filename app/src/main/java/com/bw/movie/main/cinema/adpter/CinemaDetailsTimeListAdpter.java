package com.bw.movie.main.cinema.adpter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.details.bean.MovieScheduleBean;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.main.cinema.bean.CinemaDetailsTimeListBean;
import com.bw.movie.seat.activity.SeatActivity;
import com.bw.movie.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CinemaDetailsTimeListAdpter extends RecyclerView.Adapter<CinemaDetailsTimeListAdpter.ViewHolder> {
   private List<MovieScheduleBean.ResultBean> mList;
   private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean loginSuccess;

    public CinemaDetailsTimeListAdpter(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("User",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<MovieScheduleBean.ResultBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CinemaDetailsTimeListAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = View.inflate(mContext, R.layout.cinema_details_timelist_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaDetailsTimeListAdpter.ViewHolder viewHolder, final int i) {
         viewHolder.endtime.setText(mList.get(i).getEndTime());
         viewHolder.starttime.setText(mList.get(i).getBeginTime());
         viewHolder.flmstudio.setText(mList.get(i).getScreeningHall());
         viewHolder.moeny.setText(mList.get(i).getPrice()+"");
         viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
                 if (loginSuccess){
                     Intent intent=new Intent(mContext,SeatActivity.class);
                     intent.putExtra("item",(Serializable)mList.get(i));
                     mContext.startActivity(intent);
                 }
                 else {
                     ToastUtil.showToast(mContext,"请先登录");
                     Intent intent = new Intent(mContext,LoginActivity.class);
                     mContext.startActivity(intent);
                 }

             }
         });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cinema_details_timelist_end)
        TextView endtime;
        @BindView(R.id.cinema_details_timelist_time_start)
        TextView starttime;
        @BindView(R.id.cinema_details_timelist_flmstudio)
        TextView flmstudio;
        @BindView(R.id.cinema_details_timelist_moeny)
        TextView moeny;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

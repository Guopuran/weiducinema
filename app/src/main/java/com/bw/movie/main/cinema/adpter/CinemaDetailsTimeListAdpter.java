package com.bw.movie.main.cinema.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.main.cinema.bean.CinemaDetailsTimeListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CinemaDetailsTimeListAdpter extends RecyclerView.Adapter<CinemaDetailsTimeListAdpter.ViewHolder> {
   private List<CinemaDetailsTimeListBean.ResultBean> mList;
   private Context mContext;

    public CinemaDetailsTimeListAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<CinemaDetailsTimeListBean.ResultBean> mList) {
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
    public void onBindViewHolder(@NonNull CinemaDetailsTimeListAdpter.ViewHolder viewHolder, int i) {
         viewHolder.endtime.setText(mList.get(i).getEndTime());
         viewHolder.starttime.setText(mList.get(i).getBeginTime());
         viewHolder.flmstudio.setText(mList.get(i).getScreeningHall());
         viewHolder.moeny.setText(mList.get(i).getPrice()+"");
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

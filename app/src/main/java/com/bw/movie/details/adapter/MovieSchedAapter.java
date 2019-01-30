package com.bw.movie.details.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.details.bean.MovieScheduleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieSchedAapter extends RecyclerView.Adapter<MovieSchedAapter.ViewHolder> {
    private List<MovieScheduleBean.ResultBean> list;
    private Context context;

    public MovieSchedAapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<MovieScheduleBean.ResultBean> mlist) {
        if (mlist != null) {
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    public MovieScheduleBean.ResultBean getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cinema_details_timelist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getdata(getItem(i), context, i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cinema_details_timelist_flmstudio)
        TextView timelist_flmstudio;
        @BindView(R.id.cinema_details_timelist_time_start)
        TextView time_start;
        @BindView(R.id.lines)
        TextView lines;
        @BindView(R.id.cinema_details_timelist_end)
        TextView timelist_end;
        @BindView(R.id.end)
        TextView end;
        @BindView(R.id.moeny)
        TextView moeny;
        @BindView(R.id.cinema_details_timelist_moeny)
        TextView timelist_moeny;
        @BindView(R.id.cinema_details_go_buy)
        ImageView details_go_buy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void getdata(MovieScheduleBean.ResultBean item, Context context, int i) {
            timelist_flmstudio.setText(item.getScreeningHall());
            time_start.setText(item.getBeginTime());
            timelist_end.setText(item.getEndTime());
            timelist_moeny.setText(item.getPrice()+"");
        }
    }
}

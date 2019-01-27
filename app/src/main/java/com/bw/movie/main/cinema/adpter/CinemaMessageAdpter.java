package com.bw.movie.main.cinema.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.main.cinema.bean.CinemaMessageBean;
import com.bw.movie.util.GlidRoundUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CinemaMessageAdpter extends RecyclerView.Adapter<CinemaMessageAdpter.ViewHolder> {
    private List<CinemaMessageBean.ResultBean> mList;
    private Context mContext;

    public CinemaMessageAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<CinemaMessageBean.ResultBean> list) {
        mList.clear();
        if (list!=null){
         mList.addAll(list);
       }
     notifyDataSetChanged();
    }
    public void addmList(List<CinemaMessageBean.ResultBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext,R.layout.cineme_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mList.get(i).getLogo()).apply(RequestOptions.bitmapTransform(new GlidRoundUtils(5)))
                .into(viewHolder.cinema_image);
        viewHolder.cinema_address.setText(mList.get(i).getAddress());
        viewHolder.cinema_distance.setText(mList.get(i).getDistance()+"");
        viewHolder.cinema_name.setText(mList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cinema_item_addreess)
        TextView  cinema_address;
        @BindView(R.id.cinema_item_distance)
        TextView cinema_distance;
        @BindView(R.id.cinema_item_image)
        ImageView cinema_image;
        @BindView(R.id.cinema_item_name)
        TextView cinema_name;
        @BindView(R.id.cinema_item_follow)
        ImageView cinema_follow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

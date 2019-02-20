package com.bw.movie.main.my.adpter;

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
import com.bw.movie.main.my.bean.MyFollowCinemaBean;
import com.bw.movie.util.GlidRoundUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFollowCinemaAadpter  extends RecyclerView.Adapter<MyFollowCinemaAadpter.ViewHolder> {
   private List<MyFollowCinemaBean.ResultBean> mList;
   private Context mContext;

    public MyFollowCinemaAadpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public List<MyFollowCinemaBean.ResultBean> getmList() {
        return mList;
    }

    public void setmList(List<MyFollowCinemaBean.ResultBean> list) {
       mList.clear();
       if (list!=null){
           mList.addAll(list);
       }
        notifyDataSetChanged();
    }
    public void addmList(List<MyFollowCinemaBean.ResultBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext,R.layout.my_follow_cinema_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
         viewHolder.address.setText(mList.get(i).getAddress());
         viewHolder.name.setText(mList.get(i).getName());
        Glide.with(mContext)
                .load(mList.get(i).getLogo())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.follow_cinema_item_address)
        TextView address;
        @BindView(R.id.follow_cinema_item_name)
        TextView name;
        @BindView(R.id.follow_cinema_item_image)
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

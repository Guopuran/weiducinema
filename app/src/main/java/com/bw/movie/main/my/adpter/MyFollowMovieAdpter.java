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
import com.bw.movie.main.my.bean.MyFollowMovieBean;
import com.bw.movie.util.GlidRoundUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFollowMovieAdpter extends RecyclerView.Adapter<MyFollowMovieAdpter.ViewHolder> {
    private List<MyFollowMovieBean.ResultBean> mList;
    private Context mContext;

    public MyFollowMovieAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public List<MyFollowMovieBean.ResultBean> getmList() {
        return mList;
    }

    public void setmList(List<MyFollowMovieBean.ResultBean> list) {
        mList.clear();
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addmList(List<MyFollowMovieBean.ResultBean> list) {

        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = View.inflate(mContext,R.layout.my_follow_movie_item,null)  ;
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
         viewHolder.context.setText(mList.get(i).getSummary());
         viewHolder.time.setText(mList.get(i).getReleaseTime());

         viewHolder.naem.setText(mList.get(i).getName());
         Glide.with(mContext)
                .load(mList.get(i).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                .into(viewHolder.image);
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.follow_movie_item_context)
        TextView context;
        @BindView(R.id.follow_movie_item_name)
        TextView naem;
        @BindView(R.id.follow_movie_item_time)
        TextView time;
        @BindView(R.id.follow_movie_item_image)
        ImageView image;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

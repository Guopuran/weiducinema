package com.bw.movie.main.movie.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.main.movie.bean.MovieBannerBean;
import com.bw.movie.main.movie.bean.MovieHotBean;
import com.bw.movie.util.GlidRoundUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieHotAdpter extends RecyclerView.Adapter<MovieHotAdpter.ViewHodler> {
    private List<MovieHotBean.ResultBean> mList;
    private Context mContext;

    public MovieHotAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<MovieHotBean.ResultBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.frag_movie_hot_item,null);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler viewHodler, final int i) {
        Glide.with(mContext)
                .load(mList.get(i%mList.size()).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                .into(viewHodler.hot_image);
        viewHodler.hot_image.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHodler.hot_title.setText(mList.get(i).getName());
        viewHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monHotClick!=null){
                    monHotClick.onHotClickItem(mList.get(i).getId());
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class ViewHodler extends RecyclerView.ViewHolder
    {
        @BindView(R.id.frag_hot_item_imaeg)
        ImageView hot_image;
        @BindView(R.id.frag_hot_item_title)
        TextView hot_title;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //跳转详情的接口回调
    onHotClick monHotClick;
    public void setOnHotItemClickLisenter(onHotClick onHotClick){
        monHotClick  = onHotClick;
    }
    public interface  onHotClick{
        void onHotClickItem(int id);
    }
}

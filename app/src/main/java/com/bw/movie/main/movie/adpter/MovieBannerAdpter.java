package com.bw.movie.main.movie.adpter;

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
import com.bw.movie.main.movie.bean.MovieBannerBean;
import com.bw.movie.util.GlidRoundUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieBannerAdpter extends RecyclerView.Adapter<MovieBannerAdpter.ViewHolder> {

    private List<MovieBannerBean.ResultBean> mList;
    private Context mContext;

    public MovieBannerAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<MovieBannerBean.ResultBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
          View view = View.inflate(mContext, R.layout.movie_banner_item,null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext)
                .load(mList.get(i%mList.size()).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                .into(viewHolder.image_banner);
        viewHolder.image_banner.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.title_banner.setText(mList.get(i).getName());
        viewHolder.time_banner.setText(mList.get(i).getReleaseTimeShow());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       @BindView(R.id.movie_banner_item_imaeg)
       ImageView image_banner;
        @BindView(R.id.movie_banner_item_title)
        TextView title_banner;
        @BindView(R.id.movie_banner_item_time)
        TextView time_banner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

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
import com.bw.movie.main.movie.bean.MovieNowHotBean;
import com.bw.movie.main.movie.bean.MovieWillBean;
import com.bw.movie.util.GlidRoundUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieWillAdpter extends RecyclerView.Adapter<MovieWillAdpter.ViewHodler> {
    private List<MovieWillBean.ResultBean> mList;
    private Context mContext;
    public MovieWillAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<MovieWillBean.ResultBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.frag_movie_will_item,null);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler viewHodler, int i) {
        Glide.with(mContext)
                .load(mList.get(i%mList.size()).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                .into(viewHodler.will_image);
        viewHodler.will_image.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHodler.will_title.setText(mList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.frag_will_item_imaeg)
        ImageView will_image;
        @BindView(R.id.frag_will_item_title)
        TextView will_title;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

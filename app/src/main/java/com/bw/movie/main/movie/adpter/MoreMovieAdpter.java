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
import com.bw.movie.details.DetailsActivity;
import com.bw.movie.main.movie.bean.MoreMovieBean;
import com.bw.movie.main.movie.fragment.HotMoreFragment;
import com.bw.movie.util.GlidRoundUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreMovieAdpter extends RecyclerView.Adapter<MoreMovieAdpter.ViewHolder> {
    private List<MoreMovieBean.ResultBean> mList;
    private Context mContext;

    public MoreMovieAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<MoreMovieBean.ResultBean> list) {
       mList.clear();
       if (list!=null){
           mList.addAll(list);
       }
       notifyDataSetChanged();
    }
    public void addmList(List<MoreMovieBean.ResultBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.movie_more_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(mList.get(i%mList.size()).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                .into(viewHolder.image);
        viewHolder.image.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.title.setText(mList.get(i).getName());
        viewHolder.context.setText(mList.get(i).getSummary());
          viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(mContext,DetailsActivity.class);
                  intent.putExtra("id",mList.get(i).getId()+"");
                  mContext.startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.more_item_image)
        ImageView image;
        @BindView(R.id.more_item_follow)
        ImageView follow;
        @BindView(R.id.more_item_title)
        TextView title;
        @BindView(R.id.more_item_context)
        TextView context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

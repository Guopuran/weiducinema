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
import com.bw.movie.main.cinema.bean.CinemaDetailsBannerBean;
import com.bw.movie.main.movie.bean.MovieBannerBean;
import com.bw.movie.util.GlidRoundUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CinemaDetailsBannerAdpter extends RecyclerView.Adapter<CinemaDetailsBannerAdpter.ViewHolder> {

    private List<CinemaDetailsBannerBean.ResultBean> mList;
    private Context mContext;

    public CinemaDetailsBannerAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<CinemaDetailsBannerBean.ResultBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
          View view = View.inflate(mContext, R.layout.cinema_details_banner_item,null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(mList.get(i%mList.size()).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                .into(viewHolder.image_banner);
         viewHolder.image_banner.setScaleType(ImageView.ScaleType.FIT_XY);
         viewHolder.title_banner.setText(mList.get(i).getName());
         viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (monClick!=null){
                     monClick.onClickLisenter(mList.get(i).getId());
                 }
             }
         });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       @BindView(R.id.cinema_detalis_banner_item_image)
       ImageView image_banner;
        @BindView(R.id.cinema_detalis_banner_item_title)
        TextView title_banner;
        @BindView(R.id.cinema_detalis_banner_item_time)
        TextView time_banner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
  onClick monClick;
    public void setOnCllickLisenter(onClick onClick){
        monClick = onClick;
    }
   public interface onClick{
        void onClickLisenter(int movieid);
   }
}

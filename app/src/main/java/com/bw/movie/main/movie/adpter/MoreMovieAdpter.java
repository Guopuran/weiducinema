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
    private int mListHeadCount = 0;

    public MoreMovieAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setHeadCount(int count ){
        mListHeadCount = count;

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
        try{
           final  MoreMovieBean.ResultBean resultBean = mList.get(i);



        Glide.with(mContext)
                .load(resultBean.getImageUrl())
              /*.apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))*/
                .into(viewHolder.image);
         viewHolder.image.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.title.setText(resultBean.getName());
        viewHolder.context.setText("简介:"+resultBean.getSummary());
          viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                 if (monClick!=null){
                     monClick.onClickItem(resultBean.getId());
                 }
              }
          });
          if (mList.get(i).getFollowMovie()==1){
              viewHolder.follow.setImageResource(R.mipmap.com_icon_collection_selected);
          }
          else {
              viewHolder.follow.setImageResource(R.mipmap.com_icon_collection_default);
          }
          viewHolder.follow.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                    if (mfollowOnClikc!=null){
                        mfollowOnClikc.follOnClickLisenter(resultBean.getId(),resultBean.getFollowMovie(),i);
                    }
              }
          });
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
    //关注设置值
    public void isFollow(int postion,int j){
       for (int i=0;i<mList.size();i++){
           if (mList.get(i).getId()==postion){
               mList.get(i).setFollowMovie(1);
               notifyItemChanged(i+mListHeadCount, mList.get(i));
           }
       }

    }
  //取消关注设置值
    public void onfollow(int postion,int j){
        for (int i=0;i<mList.size();i++){
            if (mList.get(i).getId()==postion){
                mList.get(i).setFollowMovie(2);
                notifyItemChanged(i+mListHeadCount, mList.get(i));
            }
        }

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

    //关注按钮的点击接口回调
    followOnClikc mfollowOnClikc;
    public void setFollowOnClick(followOnClikc followOnClikc){
        mfollowOnClikc=followOnClikc;
    }
    public interface followOnClikc{
        void follOnClickLisenter(int id,int follow,int i);
    }
    //跳转详情的接口回调
    onClick monClick;
    public void setOnItemClickLisenter(onClick onClick){
        monClick=onClick;
    }
    public interface  onClick{
        void onClickItem(int id);
    }
}

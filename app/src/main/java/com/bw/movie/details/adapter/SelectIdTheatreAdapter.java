package com.bw.movie.details.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.details.bean.SelectIdTheatreBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @描述 根据电影ID查询电影院
 * @创建日期 2019/1/29 10:41
 */
public class SelectIdTheatreAdapter extends RecyclerView.Adapter<SelectIdTheatreAdapter.ViewHolder> {
    private List<SelectIdTheatreBean.ResultBean> list;
    private Context context;

    public SelectIdTheatreAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<SelectIdTheatreBean.ResultBean> mlist) {
        if (mlist != null) {
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    public SelectIdTheatreBean.ResultBean getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recy_theatre_item, viewGroup, false);
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
    //点赞改变
    public void getlike(int position){
        list.get(position).setFollowCinema(1);
        notifyDataSetChanged();
    }
    //取消点赞改变
    public void getcancel(int position){
        list.get(position).setFollowCinema(0);
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_theatre_image_logo)
        ImageView image_logo;
        @BindView(R.id.item_theatre_text_name)
        TextView text_name;
        @BindView(R.id.item_theatre_text_address)
        TextView text_address;
        @BindView(R.id.item_theatre_text_distance)
        TextView text_distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void getdata(final SelectIdTheatreBean.ResultBean item, Context context, final int i) {
            Glide.with(context).load(item.getLogo()).into(image_logo);
            image_logo.setScaleType(ImageView.ScaleType.FIT_XY);
            text_name.setText(item.getName());
            text_address.setText(item.getAddress());
            text_distance.setText(item.getDistance()+"km");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClick!=null){
                        mOnClick.getdata(item);
                    }
                }
            });

        }
    }
    private OnClick mOnClick;
    public void setOnClick(OnClick mOnClick){
        this.mOnClick=mOnClick;
    }


    public interface OnClick{
        void getdata(SelectIdTheatreBean.ResultBean resultBean);
    }

}

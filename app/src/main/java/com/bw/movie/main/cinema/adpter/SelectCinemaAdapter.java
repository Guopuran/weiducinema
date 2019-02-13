package com.bw.movie.main.cinema.adpter;

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
import com.bw.movie.main.cinema.bean.SelectCommentCinemaBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCinemaAdapter extends RecyclerView.Adapter<SelectCinemaAdapter.ViewHolder> {

    private List<SelectCommentCinemaBean.ResultBean> list;
    private Context context;

    public SelectCinemaAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<SelectCommentCinemaBean.ResultBean> mlist) {
        list.clear();
        if (mlist != null) {
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    public void addList(List<SelectCommentCinemaBean.ResultBean> mlist) {
        if (mlist != null) {
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    public SelectCommentCinemaBean.ResultBean getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.xrecy_select_comment_cinema_item, viewGroup, false);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.xrecy_comment_cinema_image)
        ImageView cinema_image;
        @BindView(R.id.xrecy_comment_cinema_text_name)
        TextView cinema_text_name;
        @BindView(R.id.xrecy_comment_cinema_text_content)
        TextView cinema_text_content;
        @BindView(R.id.xrecy_comment_cinema_text_time)
        TextView cinema_text_time;
        @BindView(R.id.xrecy_comment_cinema_text_num)
        TextView cinema_text_num;
        @BindView(R.id.xrecy_comment_cinema_image_num)
        ImageView cinema_image_num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

        public void getdata(SelectCommentCinemaBean.ResultBean item, Context context, int i) {

            Glide.with(context).load(item.getCommentHeadPic()).into(cinema_image);
            cinema_text_name.setText(item.getCommentUserName());
            cinema_text_content.setText(item.getCommentContent());
            final String DATA = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATA, Locale.getDefault());
            String format = dateFormat.format(item.getCommentTime());
            cinema_text_time.setText(format);
            cinema_text_num.setText(item.getGreatNum());
            if (item.getIsGreat()==0){
               Glide.with(context).load(R.mipmap.com_icon_praise_default).into(cinema_image_num);
            }else{
                Glide.with(context).load(R.mipmap.com_icon_praise_selected).into(cinema_image_num);
            }
        }
    }
}

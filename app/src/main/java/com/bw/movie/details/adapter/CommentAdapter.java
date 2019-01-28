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
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.details.bean.SelectCommentBean;
import com.bw.movie.util.GlidRoundUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<SelectCommentBean.ResultBean> list;
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List mlist) {
        list.clear();
        if (mlist != null) {
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }
    public void addList(List mlist) {
        if (mlist != null) {
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    public SelectCommentBean.ResultBean getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyx_dialog_review_item_item, viewGroup, false);
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
        @BindView(R.id.item_item_review_image_header)
        ImageView image_header;
        @BindView(R.id.item_item_review_text_name)
        TextView text_name;
        @BindView(R.id.item_item_review_text_content)
        TextView ext_content;
        @BindView(R.id.item_item_review_text_time)
        TextView text_time;
        @BindView(R.id.item_item_review_text_praiseNum)
        TextView text_praiseNum;
        @BindView(R.id.item_item_review_image_praise)
        ImageView image_praise;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void getdata(SelectCommentBean.ResultBean item, Context context, int i) {
            Glide.with(context).load(item.getReplyHeadPic())
                    .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(180)))
                    .into(image_header);
            text_name.setText(item.getReplyUserName());
            ext_content.setText(item.getReplyContent());
            final String DATA = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATA, Locale.getDefault());
            String format = dateFormat.format(item.getCommentTime());
            text_time.setText(format);
        }
    }
}

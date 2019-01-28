package com.bw.movie.details.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
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
import com.bw.movie.details.bean.SelectReviewBean;
import com.bw.movie.util.GlidRoundUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<SelectReviewBean.ResultBean> list;
    private Context context;
    private List<SelectCommentBean.ResultBean> comment_list;
    public ReviewAdapter(Context context) {
        this.context = context;
        list = new ArrayList();
        comment_list=new ArrayList<>();
    }

    public void setComment_list(List<SelectCommentBean.ResultBean> mcomment_list) {
        comment_list.addAll(mcomment_list);
        notifyDataSetChanged();
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


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyx_dialog_review_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getdata(list.get(i),comment_list,context,i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //点赞改变
    public void getlike(int position){
        list.get(position).setIsGreat(1);
        list.get(position).setGreatNum(list.get(position).getGreatNum()+1);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_review_dialog_image_header)
        ImageView dialog_image_header;
        @BindView(R.id.item_review_dialog_text_name)
        TextView dialog_text_name;
        @BindView(R.id.item_review_dialog_text_content)
        TextView dialog_text_content;
        @BindView(R.id.item_review_dialog_text_time)
        TextView dialog_text_time;
        @BindView(R.id.item_review_dialog_text_commentNum)
        TextView ext_commentNum;
        @BindView(R.id.item_review_dialog_image_comment)
        ImageView image_comment;
        @BindView(R.id.item_review_dialog_text_praiseNum)
        TextView dialog_text_praiseNum;
        @BindView(R.id.item_review_dialog_image_praise)
        ImageView dialog_image_praise;
        @BindView(R.id.item_review_dialog_text_num)
        TextView dialog_text_num;
        @BindView(R.id.item_review_dialog_xrecy)
        XRecyclerView dialog_xrecy;
        private int flag=0;
        private int comment_page=1;
        private CommentAdapter commentAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void getdata(final SelectReviewBean.ResultBean item, final List<SelectCommentBean.ResultBean> comment_list, final Context context, final int i) {
            Glide.with(context).load(item.getCommentHeadPic())
                    .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(180)))
                    .into(dialog_image_header);
            dialog_text_name.setText(item.getCommentUserName());
            dialog_text_content.setText(item.getMovieComment());
            ext_commentNum.setText(item.getReplyNum()+"");
            dialog_text_num.setText("共"+item.getReplyNum()+"条评论");
            dialog_text_praiseNum.setText(item.getGreatNum()+"");
            final String DATA = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATA, Locale.getDefault());
            String format = dateFormat.format(item.getCommentTime());
            dialog_text_time.setText(format);
            if (item.getIsGreat()==1){
                Glide.with(context).load(R.mipmap.com_icon_praise_selected).into(dialog_image_praise);
            }else{
                Glide.with(context).load(R.mipmap.com_icon_praise_default).into(dialog_image_praise);
            }

            //点赞和取消点赞
            dialog_image_praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msetPraise!=null){
                        msetPraise.changePraise(item.getCommentId(),item.getIsGreat(),i);
                    }
                }
            });





            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            dialog_xrecy.setLayoutManager(linearLayoutManager);
            image_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag%2==0){
                        comment_page=1;
                        initXrecy(item);
                        dialog_text_num.setVisibility(View.VISIBLE);
                        dialog_xrecy.setVisibility(View.VISIBLE);
                        if (msetSend!=null){
                            msetSend.send(comment_page,item.getCommentId());
                        }
                        commentAdapter = new CommentAdapter(context);
                        dialog_xrecy.setAdapter(commentAdapter);
                        if (comment_page==1){
                            commentAdapter.setList(comment_list);
                        }else{
                            commentAdapter.addList(comment_list);
                        }
                        comment_page++;
                        dialog_xrecy.refreshComplete();
                        dialog_xrecy.loadMoreComplete();

                    }else{
                        dialog_text_num.setVisibility(View.GONE);
                        List<SelectCommentBean.ResultBean> list=new ArrayList<>();
                        commentAdapter.setList(list);
                    }
                    flag++;
                }
            });

        }

        private void initXrecy(final SelectReviewBean.ResultBean item) {

            dialog_xrecy.setPullRefreshEnabled(true);
            dialog_xrecy.setLoadingMoreEnabled(true);
            dialog_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    comment_page=1;
                    if (msetSend!=null){

                           msetSend.send(comment_page, item.getCommentId());
                    }
                }

                @Override
                public void onLoadMore() {
                    if (msetSend!=null){
                        msetSend.send(comment_page, item.getCommentId());
                    }
                }
            });



        }
    }


    private setSend msetSend;
    public void getSend(setSend msetSend){
        this.msetSend=msetSend;
    }

    public interface setSend{
        void send(int comment_page, int commentId);
    }

    private setPraise msetPraise;
    public void getPraise(setPraise msetPraise){
        this.msetPraise=msetPraise;
    }
    public interface setPraise{
        void changePraise(int id,int great,int position) ;
    }

}

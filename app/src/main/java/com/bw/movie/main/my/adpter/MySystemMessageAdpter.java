package com.bw.movie.main.my.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.main.my.bean.MySystemMessageBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.PUT;

public class MySystemMessageAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MySystemMessageBean.ResultBean> mList;
    private Context mContext;
     private final int UNREAD=0;
     private final   int READ=1;
    public MySystemMessageAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<MySystemMessageBean.ResultBean> list) {
       mList.clear();
       if (list!=null){
           mList.addAll(list);
       }
       notifyDataSetChanged();
    }
    public void addmList(List<MySystemMessageBean.ResultBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
         if (mList.get(position).getStatus()==UNREAD){
             return UNREAD;
         }
         if (mList.get(position).getStatus()==READ){
             return READ;
         }
        return 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case UNREAD:
                View view = View.inflate(mContext, R.layout.read_unread,null);
                return new unReadViewHolder(view);
            case READ:
                View view1 =  View.inflate(mContext,R.layout.read_success,null);
                return  new ReadViewHolder(view1);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        switch (itemViewType){
            case UNREAD:
                 unReadViewHolder unReadViewHolder  = (unReadViewHolder) viewHolder;
                 unReadViewHolder.count.setText(mList.get(i).getContent());
                String date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                        new java.util.Date(mList.get(i).getPushTime()));
                unReadViewHolder.time.setText(date);
                unReadViewHolder.title.setText(mList.get(i).getTitle());
                unReadViewHolder.read_relative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (monClick!=null){
                            monClick.OnClickLisenter(mList.get(i).getId(),i);
                        }
                    }
                });
                break;
            case READ:
                ReadViewHolder readViewHolder = (ReadViewHolder) viewHolder;
                readViewHolder.read_count.setText(mList.get(i).getContent());
                String date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                        new java.util.Date(mList.get(i).getPushTime()));
                readViewHolder.read_time.setText(date1);
                break;
                 default:
                    break;
        }
    }

    @Override
    public int getItemCount() {
        return  mList.size();
    }
    //未读
    public class  unReadViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.read_title)
        TextView title;
        @BindView(R.id.read_count)
        TextView count;
        @BindView(R.id.read_time)
        TextView time;
        @BindView(R.id.read_relative)
        RelativeLayout read_relative;
        public unReadViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //已读
    public class ReadViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.read_success_title)
        TextView read_title;
        @BindView(R.id.read_success_count)
        TextView read_count;
        @BindView(R.id.read_success_time)
        TextView read_time;
        public ReadViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //读取消息接口回调
    OnClick monClick;
    public void setOnClick(OnClick onClic){
        monClick = onClic;
    }
    public interface  OnClick{
        void OnClickLisenter(int id,int i);
    }
}

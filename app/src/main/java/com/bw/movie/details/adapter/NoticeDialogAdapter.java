package com.bw.movie.details.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.details.bean.DetailsMovieBean;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;


public class NoticeDialogAdapter extends RecyclerView.Adapter<NoticeDialogAdapter.ViewHolder> {

    private List<DetailsMovieBean.ResultBean.ShortFilmListBean> list;
    private Context context;

    public NoticeDialogAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<DetailsMovieBean.ResultBean.ShortFilmListBean> mlist) {
        if (mlist != null) {
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recy_dialog_notice_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getdata(list.get(i), viewHolder, i);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //private NiceVideoPlayer mNiceVideoPlayer;
        private JZVideoPlayerStandard videoplayer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // mNiceVideoPlayer = itemView.findViewById(R.id.item_notice_dialog_video);
            videoplayer=itemView.findViewById(R.id.videoplayer);
        }

        public void getdata(DetailsMovieBean.ResultBean.ShortFilmListBean shortFilmListBean, ViewHolder viewHolder, int i) {
            /*mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
            mNiceVideoPlayer.setUp(shortFilmListBean.getVideoUrl(), null);
            TxVideoPlayerController controller = new TxVideoPlayerController(context);
            controller.setTitle("预告");
            Glide.with(context).load(shortFilmListBean.getImageUrl()).into(controller.imageView());
            mNiceVideoPlayer.setController(controller);*/
            videoplayer.setUp(shortFilmListBean.getVideoUrl(),
                    JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
            Uri uri = Uri.parse(shortFilmListBean.getImageUrl());
            Glide.with(context).load(uri).into(videoplayer.thumbImageView);

            //videoplayer.thumbImageView.setImageBitmap(shortFilmListBean.getImageUrl());
        }
    }
}

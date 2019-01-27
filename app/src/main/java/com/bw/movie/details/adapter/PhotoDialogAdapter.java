package com.bw.movie.details.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.util.GlidRoundUtils;

import java.util.ArrayList;
import java.util.List;

public class PhotoDialogAdapter extends RecyclerView.Adapter<PhotoDialogAdapter.ViewHolder> {
    private List<String> list;
    private Context context;

    public PhotoDialogAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<String> mlist) {
        if (mlist!=null){
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recy_dialog_photo_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getdata(list.get(i),context,i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item_photo_dialog_image);
        }

        public void getdata(final String s, Context context, int i) {
            Glide.with(context).load(s)
//                    .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mimageOnClick!=null){
                        mimageOnClick.onClick(s);
                    }
                }
            });
        }
    }

    private imageOnClick mimageOnClick;
    public void setImageOnClick(imageOnClick mimageOnClick){
        this.mimageOnClick=mimageOnClick;
    }
    public interface imageOnClick{
        void onClick(String imageUrl);
    }
}

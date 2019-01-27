package com.bw.movie.details.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsDialogAdapter extends RecyclerView.Adapter<DetailsDialogAdapter.ViewHolder> {
    private List<String> list;
    private Context context;

    public DetailsDialogAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.recy_dialog_details_item, viewGroup, false);
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
        private TextView name;
        private TextView englishname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.item_details_dialog_name);
            englishname=itemView.findViewById(R.id.item_details_dialog_englishname);
        }

        public void getdata(String s, Context context, int i) {
            name.setText(s);
        }
    }
}

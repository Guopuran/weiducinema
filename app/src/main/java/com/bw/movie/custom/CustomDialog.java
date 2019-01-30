package com.bw.movie.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bw.movie.R;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

public class CustomDialog extends Dialog {
    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    int  hei;
    public CustomDialog(Context context, View view, boolean isCancelable, boolean isBackCancelable,int hei) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.view = view;
        this.iscancelable = isCancelable;
        this.isBackCancelable=isBackCancelable;
        this.hei=hei;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        setCanceledOnTouchOutside(isBackCancelable);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);


        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = hei;
        window.setAttributes(params);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void cancel() {
        super.cancel();

    }
}


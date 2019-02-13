package com.bw.movie.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bw.movie.R;

public class PayDialog extends Dialog {
    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;

    public PayDialog(Context context, View view) {
        super(context, R.style.SelectImage);
        this.context = context;
        this.view = view;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);//这行一定要写在前面
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);


        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = context.getResources().getDimensionPixelOffset(R.dimen.dp_178);
        window.setAttributes(params);
    }
}

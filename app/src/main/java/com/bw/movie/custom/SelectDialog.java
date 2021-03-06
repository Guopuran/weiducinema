package com.bw.movie.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bw.movie.R;
/**
 *
 * @描述 自定义dialog
 *
 * @创建日期 2019/2/13 16:54
 *
 */
public class SelectDialog extends Dialog {
    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    int  hei;
    public SelectDialog(Context context, View view) {
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
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }
}

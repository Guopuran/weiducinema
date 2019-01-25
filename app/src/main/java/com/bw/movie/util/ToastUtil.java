package com.bw.movie.util;

import android.content.Context;
import android.widget.Toast;

/**
 *
 * @描述 吐司工具类
 *
 * @创建日期 2019/1/25 0:31
 *
 */
public class ToastUtil {
    private static Toast toast;//在类前面声明吐司，确保在这个页面只有一个吐司

    //需要谈吐司的地方调用showToast()

    public static void showToast(Context context, String msg) {



        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();//关闭吐司显示
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }

        toast.show();//重新显示吐司
    }

}

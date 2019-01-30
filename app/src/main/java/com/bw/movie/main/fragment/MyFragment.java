package com.bw.movie.main.fragment;

import android.view.KeyEvent;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.util.ToastUtil;

public class MyFragment extends BaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_my;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    private long exitTime=0;
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    //双击退出
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtil.showToast(getActivity(),"再按一次退出程序");
                        exitTime = System.currentTimeMillis();
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }

                return false;
            }
        });
    }
}

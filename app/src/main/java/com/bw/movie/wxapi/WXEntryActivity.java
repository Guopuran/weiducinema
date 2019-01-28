package com.bw.movie.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.bean.WeiXinBean;
import com.bw.movie.main.activity.MainActivity;
import com.bw.movie.util.Apis;
import com.bw.movie.util.WeiXinUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;
import java.util.Map;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
      String code;
      private SharedPreferences sharedPreferences;
      private SharedPreferences.Editor editor;
    @Override
    protected void initData() {
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(),this);
        sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wxentry;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(final BaseResp baseResp)
    {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                //主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //得到code
                        code = ((SendAuth.Resp) baseResp).code;
                        Map<String,String> map = new HashMap<>();
                        map.put("code",code);
                        postRequest(Apis.WEIXINLOGON_URL,map,WeiXinBean.class);
                    }
                });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
                default:
                    break;
        }
    }
    @Override
    protected void success(Object object) {
        if (object instanceof WeiXinBean) {
            WeiXinBean bean = (WeiXinBean) object;
            if (((WeiXinBean) object).getStatus().equals("0000")) {
                int userId = bean.getResult().getUserId();
                String sessionId = bean.getResult().getSessionId();
                editor.putString("userId", userId + "");
                editor.putString("sessionId", sessionId);
                editor.commit();
                Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}

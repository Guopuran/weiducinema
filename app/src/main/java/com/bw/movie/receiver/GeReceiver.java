package com.bw.movie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class GeReceiver extends XGPushBaseReceiver {
    private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
    public static final String LogTag = "TPushReceiver";
    private void show(Context context, String text) {
//		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
        // TODO Auto-generated method stub
        if (context == null || xgPushRegisterResult == null) {
            return;
        }
        String text = "";
        if (i == XGPushBaseReceiver.SUCCESS) {
            text = xgPushRegisterResult + "注册成功";
            // 在这里拿token
            String token = xgPushRegisterResult.getToken();
        } else {
            text = xgPushRegisterResult + "注册失败，错误码：" + i;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    @Override
    public void onUnregisterResult(Context context, int i) {
        if (context == null) {
            return;
        }
        String text = "";
        if (i == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + i;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {
        if (context == null) {
            return;
        }
        String text = "";
        if (i == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + s + "\"设置成功";
        } else {
            text = "\"" + s + "\"设置失败,错误码：" + i;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {
        if (context == null) {
            return;
        }
        String text = "";
        if (i == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + s + "\"删除成功";
        } else {
            text = "\"" + s + "\"删除失败,错误码：" + i;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
// TODO Auto-generated method stub
        String text = "收到消息:" + xgPushTextMessage.toString();
        // 获取自定义key-value
        String customContent = xgPushTextMessage.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("A44FJ9N7N9EY")) {
                    String value = obj.getString("A44FJ9N7N9EY");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理消息的过程...
        Log.d(LogTag, text);
        show(context, text);
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        if (context == null || xgPushClickedResult == null) {
            return;
        }
        String text = "";
        if (xgPushClickedResult.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + xgPushClickedResult;
        } else if (xgPushClickedResult.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + xgPushClickedResult;
        }
        Toast.makeText(context, "广播接收到通知被点击:" + xgPushClickedResult.toString(),
                Toast.LENGTH_SHORT).show();
        // 获取自定义key-value
        String customContent = xgPushClickedResult.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("A44FJ9N7N9EY")) {
                    String value = obj.getString("A44FJ9N7N9EY");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理的过程。。。
        Log.d(LogTag, text);
        show(context, text);
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

            if (context == null || xgPushShowedResult == null) {
                return;
            }
           /* XGNotification notific = new XGNotification();
            notific.setMsg_id(notifiShowedRlt.getMsgId());
            notific.setTitle(notifiShowedRlt.getTitle());
            notific.setContent(notifiShowedRlt.getContent());
            // notificationActionType==1为Activity，2为url，3为intent
            notific.setNotificationActionType(notifiShowedRlt
                    .getNotificationActionType());
            // Activity,url,intent都可以通过getActivity()获得
            notific.setActivity(notifiShowedRlt.getActivity());
            notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(Calendar.getInstance().getTime()));
            NotificationService.getInstance(context).save(notific);
            context.sendBroadcast(intent);*/
            show(context, "您有1条新消息, " + "通知被展示 ， " + xgPushShowedResult.toString());

}
}
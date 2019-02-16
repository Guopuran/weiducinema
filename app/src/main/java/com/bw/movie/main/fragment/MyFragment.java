package com.bw.movie.main.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.main.activity.LocationActivity;
import com.bw.movie.main.movie.bean.UpdateCodeBean;
import com.bw.movie.main.my.activity.MyFollowActivity;
import com.bw.movie.main.my.activity.MyMessageActivity;
import com.bw.movie.main.my.activity.MySuggestioActivity;
import com.bw.movie.main.my.activity.MySystemMessageActivity;
import com.bw.movie.main.my.activity.TicketRecordActivity;
import com.bw.movie.main.my.bean.HeadImageBean;
import com.bw.movie.main.my.bean.MySginBean;
import com.bw.movie.main.my.bean.MyUserMeaagerBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.GlidRoundUtils;
import com.bw.movie.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 *
 * @描述 我的界面
 *
 * @创建日期 2019/2/15 11:37
 *
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.my_follow_image)
    ImageView image_follow;
    @BindView(R.id.my_suggestio_image)
    ImageView suggestio;
    @BindView(R.id.my_back_imgae)
    ImageView back;
    @BindView(R.id.my_message_imgae)
    ImageView message;
    @BindView(R.id.my_sign_text)
    TextView sign;
    @BindView(R.id.my_record_image)
    ImageView record;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean loginSuccess;
    @BindView(R.id.my_head_image)
    ImageView head_image;
    @BindView(R.id.my_name_text)
    TextView name_text;
    @BindView(R.id.my_newversion_image)
    ImageView newversion_image;
     @BindView(R.id.my_xtmessage_imgae)
     ImageView systemMessage;
    private static final int PHOTO_REQUEST_CAREMA=1;//拍照
    private static final int PHOTO_REQUEST_GALLERY=2;//从相册中选择
    private static final int PHOTO_REQUEST_CUT=3;//裁剪之后
    private String path=Environment.getExternalStorageDirectory()+"/header_image.png";
    private Dialog dialog;
    private File file;
    private static final String PHOTO_FILE_MAME="header_image.jpg";//临时文件名
    @Override
    protected void initData() {
        //请求信息
        initPresonUrl();
    }

    private void initPresonUrl() {
        getRequest(Apis.SEARCHMESSAGE,MyUserMeaagerBean.class);
            sharedPreferences = getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
    }

    //点击关注
    @OnClick(R.id.my_follow_image)
    public void  onClickFollow(){
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),MyFollowActivity.class);
            startActivity(intent);
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
    }
    //点击反馈信息
    @OnClick(R.id.my_suggestio_image)
    public void onClicksuggrstio(){
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),MySuggestioActivity.class);
            startActivity(intent);
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
    }
    //退出登录
    @OnClick(R.id.my_back_imgae)
    public void onClickBack()
    {
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            intent.putExtra("back","main");
            startActivity(intent);
            editor.putString("userId",null);
            editor.putString("sessionId",null);
            editor.putBoolean("loginSuccess",false);
            editor.commit();
            getActivity().finish();
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }
    //个人信息的点击事件
    @OnClick(R.id.my_message_imgae)
    public void onClickMessage()
    {
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),MyMessageActivity.class);
            startActivity(intent);
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }

    //版本更新的点击事件
    @OnClick(R.id.my_newversion_image)
    public void my_suggestio_image()
    {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(newversion_image, "rotation", 0.0f, 720f);
        rotation.setDuration(2000);
        rotation.start();
        getRequest(Apis.SUGGESTIO_URL, UpdateCodeBean.class);

    }

    //头像的点击事件
    @OnClick(R.id.my_head_image)
    public void onClickHead_Image(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            String[] mStatenetwork = new String[]{
                    //写的权限
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    //读的权限
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    //入网权限
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    //WIFI权限
                    Manifest.permission.ACCESS_WIFI_STATE,
                    //读手机权限
                    Manifest.permission.READ_PHONE_STATE,
                    //网络权限
                    Manifest.permission.INTERNET,
                    //相机
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_APN_SETTINGS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
            };
            ActivityCompat.requestPermissions(getActivity(), mStatenetwork, 100);
        }
        show();
    }
    //签到的点击事件
    @OnClick(R.id.my_sign_text)
    public void onSingClick(){
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            getSignData();
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }
    //系统消息的点击事件
    @OnClick(R.id.my_xtmessage_imgae)
    public void ststemMessageClick(){
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent =  new Intent(getActivity(),MySystemMessageActivity.class);
            startActivity(intent);
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
    }
    //签到的网络请求
    public void getSignData()
    {
        getRequest(Apis.MY_SING,MySginBean.class);
    }
    //点击到消费记录
    @OnClick(R.id.my_record_image)
    public void onClickRecord(){
        loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
        if (loginSuccess){
            Intent intent = new Intent(getActivity(),TicketRecordActivity.class);
            startActivity(intent);
        }
        else {
            ToastUtil.showToast(getActivity(),"请先登录");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }
    @Override
    protected void success(Object object)
    {
        if (object instanceof MySginBean){
            MySginBean mySginBean = (MySginBean) object;
            if (mySginBean.getStatus().equals("0000")){
                ToastUtil.showToast(getActivity(),mySginBean.getMessage());
            }
        }
        if (object instanceof HeadImageBean ){
            HeadImageBean headImageBean= (HeadImageBean) object;
            if (headImageBean.getStatus().equals("0000")){
                head_image.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity()).load(headImageBean.getHeadPath())
                        .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(180)))
                        .into(head_image);
                ToastUtil.showToast(getActivity(),"上传成功");
            }
        }
        if (object instanceof MyUserMeaagerBean){
            MyUserMeaagerBean userMeaagerBean = (MyUserMeaagerBean) object;
            MyUserMeaagerBean.ResultBean result = userMeaagerBean.getResult();
            name_text.setText(result.getNickName());
            Glide.with(this).load(result.getHeadPic())
                    .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(180)))
                    .into(head_image);

        }
        if(object instanceof UpdateCodeBean){
            UpdateCodeBean updateCodeBean= (UpdateCodeBean) object;
            if(updateCodeBean.getStatus().equals("0000")){
                final String downloadUrl = updateCodeBean.getDownloadUrl();
                if(updateCodeBean.getFlag()==1){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("有新版本，需要更新");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            openBrowser(getContext(),downloadUrl);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else if(updateCodeBean.getFlag()==2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("没新版本，不需要更新");
                    builder.show();
                }
            }else{
                ToastUtil.showToast(getActivity(),updateCodeBean.getMessage());
            }
        }

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
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

    public void show() {

        dialog = new Dialog(getActivity(), R.style.ctionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout, null);
        //初始化控件
        inflate.findViewById(R.id.choosePhoto).setOnClickListener(this);
        inflate.findViewById(R.id.takePhoto).setOnClickListener(this);
        inflate.findViewById(R.id.btn_cancel).setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        if(dialogWindow == null){
            return;
        }
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    //裁剪图片
    private void crop(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //支持裁剪
        intent.putExtra("CROP",true);
        //裁剪的比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪后输出图片的尺寸大小
        intent.putExtra("outputX",250);
        intent.putExtra("outputY",250);
        //将图片返回给data
        intent.putExtra("return-data",true);
        startActivityForResult(intent,PHOTO_REQUEST_CUT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PHOTO_REQUEST_CAREMA&&resultCode==getActivity().RESULT_OK){//相机返回的数据
            if (hasSdcard()){
                crop(Uri.fromFile(new File(path)));
            }else{
                Toast.makeText(getActivity(), "未找到存储啦，无法存储照片", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode==PHOTO_REQUEST_GALLERY&&resultCode==getActivity().RESULT_OK){//相册返回的数据
            //得到图片的全路径
            if (data!=null){
                Uri uri = data.getData();
                crop(uri);
            }
        }else if(requestCode==PHOTO_REQUEST_CUT&&resultCode==getActivity().RESULT_OK){//剪切回来的照片数据
            if (data!=null){
                Bitmap bitmap = data.getParcelableExtra("data");
                //head_image.setImageBitmap(bitmap);
                //将bitmap转换为uri
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null,null));

                String[] proj = { MediaStore.Images.Media.DATA };

                Cursor actualimagecursor = getActivity().managedQuery(uri,proj,null,null,null);

                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                actualimagecursor.moveToFirst();

                String img_path = actualimagecursor.getString(actual_image_column_index);

                File file = new File(img_path);
                //mIpresenterImpl.postImageRequestIpresenter(Apis.SHOW_IMAGE_URL,list,ImageBean.class);
                //mIpresenterImpl.postimageRequestIpresenter(Apis.SHOW_IMAGE_URL,file,ImageBean.class);
                postImageRequest(Apis.HEAD_IMAGE,file,HeadImageBean.class);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //判断SD卡是否挂载
    public boolean hasSdcard(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takePhoto:
                //打开相机
                Intent intent_takePhoto=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (hasSdcard()){//判断SD卡是否可用
                    file=new File(Environment.getExternalStorageDirectory(),PHOTO_FILE_MAME);
                    //存放到内存中
                    intent_takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                }
                startActivityForResult(intent_takePhoto, PHOTO_REQUEST_CAREMA);

                dialog.dismiss();
                break;
            case R.id.choosePhoto:
                Intent intent_choosePhoto=new Intent(Intent.ACTION_PICK);
                intent_choosePhoto.setType("image/*");
                startActivityForResult(intent_choosePhoto,PHOTO_REQUEST_GALLERY);

                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }
    public static void openBrowser(Context context, String url){
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager()); // 打印Log   ComponentName到底是什么
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.bw.movie.details;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BaseFullBottomSheetFragment;
import com.bw.movie.custom.CustomDialog;
import com.bw.movie.details.bean.DetailsMovieBean;
import com.bw.movie.details.fragment.DetailsFragment;
import com.bw.movie.util.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @描述 影片的详情
 * @创建日期 2019/1/25 16:52
 */
public class DetailsActivity extends BaseActivity{
    @BindView(R.id.details_iamge_back_top)
    ImageView back_image;
    @BindView(R.id.details_movie_button_attention)
    Button button_attention;
    @BindView(R.id.details_movie_image_big)
    ImageView image_big;
    @BindView(R.id.details_movie_text_name)
    TextView text_name;
    @BindView(R.id.details_lin_movie_text_details)
    TextView text_details;
    @BindView(R.id.details_lin_movie_text_notice)
    TextView text_notice;
    @BindView(R.id.details_lin_movie_text_photo)
    TextView text_photo;
    @BindView(R.id.details_lin_movie_text_review)
    TextView text_review;
    @BindView(R.id.details_movie_button_back)
    Button button_back;
    @BindView(R.id.details_movie_button_buy)
    Button button_buy;
    private int screenHeight;
    private int dialog_height;

    @Override
    protected void initData() {
        back_image.setAlpha(0.2f);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        dialog_height = height-getResources().getDimensionPixelOffset(R.dimen.dp_110);


        //请求详情
        initDetailUrl();
    }


    private void initDetailUrl() {
        getRequest(String.format(Apis.SELECT_MOVIE_DETAILS,12),DetailsMovieBean.class);
    }

    @Override
    protected void success(Object object) {
       if (object instanceof DetailsMovieBean){
           DetailsMovieBean detailsMovieBean= (DetailsMovieBean) object;
           DetailsMovieBean.ResultBean result = detailsMovieBean.getResult();
           Glide.with(this).load(result.getImageUrl()).into(image_big);
           Glide.with(this).load(result.getImageUrl()).into(back_image);
           back_image.setScaleType(ImageView.ScaleType.FIT_XY);
           //设置电影mingc
           text_name.setText(result.getName());
           //设置是否被关注
           if (result.getFollowMovie()==1){
               button_attention.setSelected(true);
           }else{
               button_attention.setSelected(false);
           }

       }

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_details;
    }

    @OnClick({ R.id.details_movie_button_attention,  R.id.details_lin_movie_text_details, R.id.details_lin_movie_text_notice, R.id.details_lin_movie_text_photo, R.id.details_lin_movie_text_review, R.id.details_movie_button_back, R.id.details_movie_button_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //是否关注
            case R.id.details_movie_button_attention:
                break;
            //影片详情
            case R.id.details_lin_movie_text_details:
                View _details = initDiaLog(R.layout.frag_details);

                break;
            //影片预告
            case R.id.details_lin_movie_text_notice:

                break;
            //影片剧照
            case R.id.details_lin_movie_text_photo:
                break;
            //影片影评
            case R.id.details_lin_movie_text_review:
                break;
            //返回按钮
            case R.id.details_movie_button_back:
                break;
            //购票按钮
            case R.id.details_movie_button_buy:
                break;
            default:break;
        }
    }
    public View initDiaLog(int getLayoutId){
        View details_view=View.inflate(this,getLayoutId,null);
        final CustomDialog dialog=new CustomDialog(this,details_view,false,true,dialog_height);
        dialog.show();
        ImageView image_back=details_view.findViewById(R.id.dialog_back);;
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return  details_view;
    }
}

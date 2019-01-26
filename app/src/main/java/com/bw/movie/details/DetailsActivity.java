package com.bw.movie.details;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.details.bean.DetailsMovieBean;
import com.bw.movie.util.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @描述 影片的详情
 * @创建日期 2019/1/25 16:52
 */
public class DetailsActivity extends BaseActivity {
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

    @Override
    protected void initData() {
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
}

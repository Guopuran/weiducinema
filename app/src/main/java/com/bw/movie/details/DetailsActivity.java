package com.bw.movie.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import butterknife.BindView;
/**
 *
 * @描述 影片的详情
 *
 * @创建日期 2019/1/25 16:52
 *
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
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_details;
    }
}

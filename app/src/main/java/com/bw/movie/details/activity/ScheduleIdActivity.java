package com.bw.movie.details.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.details.adapter.MovieSchedAapter;
import com.bw.movie.details.bean.DetailsMovieBean;
import com.bw.movie.details.bean.MovieScheduleBean;
import com.bw.movie.details.bean.SelectIdTheatreBean;
import com.bw.movie.util.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @描述 电影在该影院的排期
 * @创建日期 2019/1/29 18:33
 */
public class ScheduleIdActivity extends BaseActivity {


    @BindView(R.id.scheId_text_theatrename)
    TextView text_theatrename;
    @BindView(R.id.scheId_text_image_gps)
    ImageView _image_gps;
    @BindView(R.id.scheId_text_address)
    TextView text_address;
    @BindView(R.id.scheId_cinema_image_header)
    ImageView cinema_image_header;
    @BindView(R.id.scheId_cinema_text_cinema_name)
    TextView cinema_text_cinema_name;
    @BindView(R.id.scheId_cinema_text_type)
    TextView cinema_text_type;
    @BindView(R.id.scheId_cinema_text_director)
    TextView cinema_text_director;
    @BindView(R.id.scheId_cinema_text_longtime)
    TextView cinema_text_longtime;
    @BindView(R.id.scheId_cinema_text_place)
    TextView cinema_text_place;
    @BindView(R.id.scheId_recy)
    RecyclerView scheId_recy;
    @BindView(R.id.theatre_button_back)
    Button button_back;
    @BindView(R.id.leixing)
    TextView leixing;
    @BindView(R.id.daoyan)
    TextView daoyan;
    @BindView(R.id.shichang)
    TextView shichang;
    @BindView(R.id.chandi)
    TextView chandi;
    @BindView(R.id.rela)
    RelativeLayout rela;
    private String movieId;
    private String cinemaId;
    private DetailsMovieBean.ResultBean resultMovie;
    private SelectIdTheatreBean.ResultBean resultCinema;
    private MovieSchedAapter movieSchedAapter;

    @Override
    protected void initData() {
        //获取值
        initIntent();
        //输入基本信息
        initname();
        //初始化recy
        initRecy();
        //选择电影排期
        initUrl();
    }

    private void initRecy() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        scheId_recy.setLayoutManager(linearLayoutManager);
        movieSchedAapter = new MovieSchedAapter(this);
        scheId_recy.setAdapter(movieSchedAapter);
    }

    private void initUrl() {
        getRequest(String.format(Apis.SELECT_CINEMA_MOVIE, resultCinema.getId(), resultMovie.getId()), MovieScheduleBean.class);
    }


    private void initIntent() {
        Intent intent = getIntent();
        resultMovie = (DetailsMovieBean.ResultBean) intent.getSerializableExtra("resultMovie");
        resultCinema = (SelectIdTheatreBean.ResultBean) intent.getSerializableExtra("resultCinema");
    }

    private void initname() {
        text_theatrename.setText(resultCinema.getName());
        text_address.setText(resultCinema.getAddress());

        cinema_text_cinema_name.setText(resultMovie.getName());
        Glide.with(this).load(resultMovie.getImageUrl()).into(cinema_image_header);
        cinema_text_type.setText(resultMovie.getMovieTypes());
        cinema_text_director.setText(resultMovie.getDirector());
        cinema_text_longtime.setText(resultMovie.getDuration());
        cinema_text_place.setText(resultMovie.getPlaceOrigin());

    }

    @Override
    protected void success(Object object) {
        if (object instanceof MovieScheduleBean) {
            MovieScheduleBean movieScheduleBean = (MovieScheduleBean) object;
            movieSchedAapter.setList(movieScheduleBean.getResult());
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
        return R.layout.activity_schedule_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.theatre_button_back)
    public void onViewClicked() {
        finish();
    }
}

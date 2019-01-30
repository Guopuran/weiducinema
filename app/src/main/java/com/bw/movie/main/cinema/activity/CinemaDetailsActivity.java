package com.bw.movie.main.cinema.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.custom.CustomDialog;
import com.bw.movie.main.cinema.adpter.CinemaDetailsBannerAdpter;
import com.bw.movie.main.cinema.adpter.CinemaDetailsTimeListAdpter;
import com.bw.movie.main.cinema.bean.CinemaDetailsBannerBean;
import com.bw.movie.main.cinema.bean.CinemaDetailsTimeListBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.GlidRoundUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class CinemaDetailsActivity extends BaseActivity {
    @BindView(R.id.cinema_details_address)
    TextView cinema_address;
    @BindView(R.id.cinema_details_image)
    ImageView cinema_image;
    @BindView(R.id.cinema_details_name)
    TextView cinema_name;
    @BindView(R.id.cinema_details_banner_recycle)
    RecyclerCoverFlow recyclerCoverFlow;
    @BindView(R.id.cinema_details_timelist_recycle)
    RecyclerView timelist_recycle;
    private int cinemaId;
    private String cinemaImage;
    private String cinemaNmae;
    private String cinemaAddress;
    private CinemaDetailsBannerAdpter cinemaDetailsBannerAdpter;
    private int movieids;
    private CinemaDetailsTimeListAdpter cinemaDetailsTimeListAdpter;
    private List<CinemaDetailsTimeListBean.ResultBean> result1;
    private List<CinemaDetailsBannerBean.ResultBean> result;
    private int dialog_height;

    @Override
    protected void initData() {
        //得到从intend传过来的值并赋值
        getIntentData();
        //bannder请求数据
        getBannerData();
        //设置banner图
        setBannerRecycle();
        // //得到轮播图点击事件传来的id
        getMovieId();
        //计算屏幕的高度
        initHeight();

    }

    private void initHeight() {
        //计算整个屏幕的高度
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        dialog_height = height - getResources().getDimensionPixelOffset(R.dimen.dp_150);
    }

    //得到轮播图点击事件传来的id
    public void getMovieId() {
        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                int flag = position % result.size();
                movieids = result.get(flag).getId();
                initTimeListLayout();
            }
        });
    }

    //得到从intend传过来的值并赋值
    public void getIntentData() {
        Intent intent = getIntent();
        cinemaId = intent.getIntExtra("cinemaId", 0);
        cinemaImage = intent.getStringExtra("cinemaImage");
        cinemaNmae = intent.getStringExtra("cinemaNmae");
        cinemaAddress = intent.getStringExtra("cinemaAddress");
        Glide.with(this).load(cinemaImage).apply(RequestOptions.bitmapTransform(new GlidRoundUtils(5)))
                .into(cinema_image);
        cinema_image.setScaleType(ImageView.ScaleType.FIT_XY);
        cinema_address.setText(cinemaAddress);
        cinema_name.setText(cinemaNmae);
    }

    //排期表加载布局
    public void initTimeListLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        timelist_recycle.setLayoutManager(linearLayoutManager);
        cinemaDetailsTimeListAdpter = new CinemaDetailsTimeListAdpter(this);
        timelist_recycle.setAdapter(cinemaDetailsTimeListAdpter);

        getTimeListData();
    }

    //设置banner图
    public void setBannerRecycle() {
        cinemaDetailsBannerAdpter = new CinemaDetailsBannerAdpter(this);
        recyclerCoverFlow.setAdapter(cinemaDetailsBannerAdpter);
        recyclerCoverFlow.smoothScrollToPosition(4);
    }

    //bannder请求数据
    public void getBannerData()

    {
        getRequest(String.format(Apis.CINEMADETAILSBANNER_URL, cinemaId), CinemaDetailsBannerBean.class);
    }

    //排期表请求数据
    public void getTimeListData() {
        getRequest(String.format(Apis.CINEMADDETAILSTIMELIST_URL, cinemaId, movieids), CinemaDetailsTimeListBean.class);
    }

    @Override
    protected void success(Object object) {
        if (object instanceof CinemaDetailsBannerBean) {
            CinemaDetailsBannerBean cinemaDetailsBannerBean = (CinemaDetailsBannerBean) object;
            result = cinemaDetailsBannerBean.getResult();
            cinemaDetailsBannerAdpter.setmList(result);
        }
        if (object instanceof CinemaDetailsTimeListBean) {
            CinemaDetailsTimeListBean cinemaDetailsTimeListBean = (CinemaDetailsTimeListBean) object;
            result1 = cinemaDetailsTimeListBean.getResult();

            cinemaDetailsTimeListAdpter.setmList(result1);
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
        return R.layout.activity_cinema_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.cinema_details_image)
    public void onViewClicked() {
        View dialog_view = initDiaLog(R.layout.dialog_cinema);
        setDialogView(dialog_view);
        initCinemaUrl();
    }

    private void initCinemaUrl() {

    }

    private void setDialogView(View dialog_view) {
        TextView cinema_text_details=dialog_view.findViewById(R.id.dialog_cinema_text_details);
        TextView cinema_text_comment=dialog_view.findViewById(R.id.dialog_cinema_text_comment);
        View line_details=dialog_view.findViewById(R.id.line1);
        View line_comment=dialog_view.findViewById(R.id.line2);
        TextView cinema_text_address=dialog_view.findViewById(R.id.dialog_cinema_text_address);
        TextView cinema_text_phone=dialog_view.findViewById(R.id.dialog_cinema_text_phone);
        TextView cinema_text_path=dialog_view.findViewById(R.id.dialog_cinema_text_path);
        TextView cinema_text_subway=dialog_view.findViewById(R.id.dialog_cinema_text_subway);

    }

    public View initDiaLog(int getLayoutId) {
        View details_view = View.inflate(this, getLayoutId, null);
        final CustomDialog dialog = new CustomDialog(this, details_view, false, true, dialog_height);
        dialog.show();
        ImageView image_back = details_view.findViewById(R.id.dialog_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        return details_view;
    }
}

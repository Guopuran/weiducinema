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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.custom.CustomDialog;
import com.bw.movie.details.bean.MovieScheduleBean;
import com.bw.movie.main.cinema.adpter.CinemaDetailsBannerAdpter;
import com.bw.movie.main.cinema.adpter.CinemaDetailsTimeListAdpter;
import com.bw.movie.main.cinema.adpter.SelectCinemaAdapter;
import com.bw.movie.main.cinema.bean.CinemaDetailsBannerBean;
import com.bw.movie.main.cinema.bean.CinemaDetailsBean;
import com.bw.movie.main.cinema.bean.CinemaDetailsTimeListBean;
import com.bw.movie.main.cinema.bean.SelectCommentCinemaBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.GlidRoundUtils;
import com.bw.movie.util.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

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
    private List<MovieScheduleBean.ResultBean> result1;
    private List<CinemaDetailsBannerBean.ResultBean> result;
    private int dialog_height;
    private TextView cinema_text_details;
    private TextView cinema_text_comment;
    private View line_details;
    private View line_comment;
    private TextView cinema_text_address;
    private TextView cinema_text_phone;
    private TextView cinema_text_path;
    private TextView cinema_text_subway;
    private ConstraintLayout details_con;
    private ConstraintLayout comment_con;
    private XRecyclerView comment_xrecy;
    private  int comment_page;
    private SelectCinemaAdapter selectCinemaAdapter;

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
        dialog_height = height - getResources().getDimensionPixelOffset(R.dimen.dp_200);
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
        getRequest(String.format(Apis.CINEMADDETAILSTIMELIST_URL, cinemaId, movieids), MovieScheduleBean.class);
    }

    @Override
    protected void success(Object object) {
        if (object instanceof CinemaDetailsBannerBean) {
            CinemaDetailsBannerBean cinemaDetailsBannerBean = (CinemaDetailsBannerBean) object;
            result = cinemaDetailsBannerBean.getResult();
            cinemaDetailsBannerAdpter.setmList(result);
        }
        if (object instanceof MovieScheduleBean) {
            MovieScheduleBean movieScheduleBean = (MovieScheduleBean) object;
            result1 = movieScheduleBean.getResult();
            cinemaDetailsTimeListAdpter.setmList(result1);
        }
        if (object instanceof CinemaDetailsBean){
            CinemaDetailsBean cinemaDetailsBean= (CinemaDetailsBean) object;
            CinemaDetailsBean.ResultBean cinema_result = cinemaDetailsBean.getResult();

            cinema_text_address.setText(cinema_result.getAddress());
            cinema_text_phone.setText(cinema_result.getPhone());
            cinema_text_subway.setText(cinema_result.getVehicleRoute());
        }
        if (object instanceof SelectCommentCinemaBean){
            SelectCommentCinemaBean selectCommentCinemaBean= (SelectCommentCinemaBean) object;
            if (comment_page==1){
                selectCinemaAdapter.setList(selectCommentCinemaBean.getResult());
            }else{
                selectCinemaAdapter.addList(selectCommentCinemaBean.getResult());
            }
            comment_xrecy.refreshComplete();
            comment_xrecy.loadMoreComplete();
            if (selectCommentCinemaBean.getResult().size()==0){
                comment_xrecy.setPullRefreshEnabled(false);
                comment_xrecy.setLoadingMoreEnabled(false);
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtil.showToast(this,error);
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
        getRequest(String.format(Apis.SELECT_CINEMA_DEATILS,movieids),CinemaDetailsBean.class);
    }

    private void setDialogView(final View dialog_view) {
        cinema_text_details = dialog_view.findViewById(R.id.dialog_cinema_text_details);
        cinema_text_comment = dialog_view.findViewById(R.id.dialog_cinema_text_comment);
        line_details = dialog_view.findViewById(R.id.line1);
        line_comment = dialog_view.findViewById(R.id.line2);
        cinema_text_address = dialog_view.findViewById(R.id.dialog_cinema_text_address);
        cinema_text_phone = dialog_view.findViewById(R.id.dialog_cinema_text_phone);
        cinema_text_path = dialog_view.findViewById(R.id.dialog_cinema_text_path);
        cinema_text_subway = dialog_view.findViewById(R.id.dialog_cinema_text_subway);
        details_con = dialog_view.findViewById(R.id.details_con);
        comment_con = dialog_view.findViewById(R.id.comment_con);
        //点击详情
        cinema_text_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line_details.setVisibility(View.VISIBLE);
                line_comment.setVisibility(View.INVISIBLE);
                details_con.setVisibility(View.VISIBLE);
                comment_con.setVisibility(View.INVISIBLE);
                initCinemaUrl();
            }
        });
        cinema_text_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line_details.setVisibility(View.INVISIBLE);
                line_comment.setVisibility(View.VISIBLE);
                details_con.setVisibility(View.INVISIBLE);
                comment_con.setVisibility(View.VISIBLE);
                initXrecy(dialog_view);
            }
        });

    }

    //初始化xrecy
    private void initXrecy(View dialog_view) {
        comment_page=1;
        comment_xrecy = dialog_view.findViewById(R.id.comment_xrecy);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        comment_xrecy.setLayoutManager(linearLayoutManager);
        comment_xrecy.setPullRefreshEnabled(true);
        comment_xrecy.setLoadingMoreEnabled(true);
        selectCinemaAdapter = new SelectCinemaAdapter(this);
        comment_xrecy.setAdapter(selectCinemaAdapter);
        comment_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                comment_page=1;
                initCommentUrl(comment_page);
            }

            @Override
            public void onLoadMore() {
                comment_page++;
                initCommentUrl(comment_page);
            }
        });
        initCommentUrl(comment_page);
    }

    private void initCommentUrl(int comment_flag) {
        getRequest(String.format(Apis.SELECT_COMMENT_CINEMA,movieids,comment_flag,5),SelectCommentCinemaBean.class);
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

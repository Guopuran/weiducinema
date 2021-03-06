package com.bw.movie.details.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.custom.CustomDialog;
import com.bw.movie.custom.DividerDecoration;
import com.bw.movie.custom.SelectDialog;
import com.bw.movie.details.adapter.DetailsDialogAdapter;
import com.bw.movie.details.adapter.NoticeDialogAdapter;
import com.bw.movie.details.adapter.PhotoDialogAdapter;
import com.bw.movie.details.adapter.ReviewAdapter;
import com.bw.movie.details.bean.DetailsMovieBean;
import com.bw.movie.details.bean.InsertCommentBean;
import com.bw.movie.details.bean.NextPraiseBean;
import com.bw.movie.details.bean.SelectCommentBean;
import com.bw.movie.details.bean.SelectReviewBean;
import com.bw.movie.login.activity.LoginActivity;
import com.bw.movie.login.bean.RefurbishMessageBean;
import com.bw.movie.main.movie.activity.MovieMroeActivity;
import com.bw.movie.main.movie.bean.MovieIsFollowBean;
import com.bw.movie.main.movie.bean.MovieNoFollowBean;
import com.bw.movie.util.Apis;
import com.bw.movie.util.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * @描述 影片的详情
 * @创建日期 2019/1/25 16:52
 */
public class DetailsActivity extends BaseActivity {
    @BindView(R.id.details_iamge_back_top)
    ImageView back_image;
    @BindView(R.id.details_movie_image_attention)
    ImageView image_attention;
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
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean loginSuccess;

    private int dialog_height;
    private ImageView dialog_image;
    private TextView dialog_text_type;
    private TextView dialog_text_director;
    private TextView dialog_text_timelong;
    private TextView dialog_text_place;
    private TextView dialog_text_content;
    private RecyclerView dialog_text_recy;
    private DetailsMovieBean.ResultBean resultMovie;
    private final int ITEMCOUNT=2;
    private int page;
    private final int COUNT=5;
    private ReviewAdapter reviewAdapter;
    private XRecyclerView review_dialog_xrecy;
    private int comment_flag;
    private String movieId;
    private ImageView dialog_image_write;
    private LinearLayout revice_linear_write;
    private EditText review_edit_write;
    private TextView revice_text_send;

    @Override
    protected void initData() {
        sharedPreferences =  getSharedPreferences("User",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        resultMovie = new DetailsMovieBean.ResultBean();
        //改变图片的透明度
        back_image.setAlpha(0.2f);
        //计算整个屏幕的高度
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        dialog_height = height - getResources().getDimensionPixelOffset(R.dimen.dp_110);
        initIntent();
        //请求详情
        initDetailUrl();
    }
    //接受传值进行刷新
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void enventbus(RefurbishMessageBean messageBean){
        if (messageBean.getFlag().equals("refurbish")){
            initDetailUrl();
        }
    }
    private void initIntent() {
        Intent intent=getIntent();
        movieId = intent.getStringExtra("flag");
    }


    private void initDetailUrl() {
        getRequest(String.format(Apis.SELECT_MOVIE_DETAILS, movieId), DetailsMovieBean.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void success(Object object) {
        if (object instanceof DetailsMovieBean) {
            DetailsMovieBean detailsMovieBean = (DetailsMovieBean) object;
            DetailsMovieBean.ResultBean resultBean = detailsMovieBean.getResult();
            resultMovie=resultBean;
            Glide.with(this).load(resultBean.getImageUrl()).into(image_big);
            Glide.with(this).load(resultBean.getImageUrl()).into(back_image);
            back_image.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置电影mingc
            text_name.setText(resultBean.getName());
            //设置是否被关注
            if (resultBean.getFollowMovie() == 1) {
                Glide.with(this).load(R.mipmap.com_icon_collection_selected).into(image_attention);
            } else {
                Glide.with(this).load(R.mipmap.com_icon_collection_default).into(image_attention);
            }
        }
        if (object instanceof SelectReviewBean){
            SelectReviewBean selectReviewBean= (SelectReviewBean) object;
            if (page==1){
                reviewAdapter.setList(selectReviewBean.getResult());
            }else{
                reviewAdapter.addList(selectReviewBean.getResult());
            }
            review_dialog_xrecy.refreshComplete();
            review_dialog_xrecy.loadMoreComplete();
            if (selectReviewBean.getResult().size()<5){
                review_dialog_xrecy.loadMoreComplete();
            }
        }
        if (object instanceof SelectCommentBean){
            SelectCommentBean selectCommentBean= (SelectCommentBean) object;
            reviewAdapter.setComment_list(selectCommentBean.getResult());
        }
        if (object instanceof NextPraiseBean){
            NextPraiseBean nextPraiseBean= (NextPraiseBean) object;
            if (nextPraiseBean.getStatus().equals("0000")){
                ToastUtil.showToast(this,nextPraiseBean.getMessage());
            }
        }
        if (object instanceof MovieNoFollowBean){
            MovieNoFollowBean movieNoFollowBean= (MovieNoFollowBean) object;
            if (movieNoFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(this,movieNoFollowBean.getMessage());
                // Glide.with(this).load(R.mipmap.com_icon_collection_default).into(image_attention);
                initDetailUrl();
            }
        }
        if (object instanceof MovieIsFollowBean){
            MovieIsFollowBean movieIsFollowBean= (MovieIsFollowBean) object;
            if (movieIsFollowBean.getStatus().equals("0000")){
                ToastUtil.showToast(this,movieIsFollowBean.getMessage());
                //Glide.with(this).load(R.mipmap.com_icon_collection_selected).into(image_attention);
                initDetailUrl();
            }
        }
        if (object instanceof InsertCommentBean){
            InsertCommentBean insertCommentBean= (InsertCommentBean) object;
            if (insertCommentBean.getStatus().equals("0000")){
                review_edit_write.setText("");
                revice_linear_write.setVisibility(View.GONE);
                ToastUtil.showToast(this,insertCommentBean.getMessage());
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
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_details;
    }

    @OnClick({R.id.details_movie_image_attention, R.id.details_lin_movie_text_details, R.id.details_lin_movie_text_notice, R.id.details_lin_movie_text_photo, R.id.details_lin_movie_text_review, R.id.details_movie_button_back, R.id.details_movie_button_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //是否关注
            case R.id.details_movie_image_attention:
                loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
                 if (loginSuccess){
                     if (resultMovie.getFollowMovie()==1){//取消关注
                         cancelUrl();
                     }else{//点击关注
                         NextUrl();
                     }
                 }
                 else {
                     ToastUtil.showToast(this,"请先登录");
                     Intent intent = new Intent(this,LoginActivity.class);
                     startActivity(intent);
                 }
                break;
            //影片详情
            case R.id.details_lin_movie_text_details:
                View view_details = initDiaLog(R.layout.dialog_details);
                getDetailsView(view_details);
                break;
            //影片预告
            case R.id.details_lin_movie_text_notice:
                View view_notice = initDiaLog(R.layout.dialog_notice);
                getNoticeView(view_notice);

                break;
            //影片剧照
            case R.id.details_lin_movie_text_photo:
                View view_photo = initDiaLog(R.layout.dialog_photo);
                getPhotoView(view_photo);
                break;
            //影片影评
            case R.id.details_lin_movie_text_review:
                page=1;
                selectReviceUrl(page);
                View view_review = initDiaLog(R.layout.dialog_review);
                getReviewView(view_review);
                break;
            //返回按钮
            case R.id.details_movie_button_back:
                Intent intent=new Intent(this,MovieMroeActivity.class);
                //设置返回数据
                setResult(RESULT_OK, intent);
                //关闭Activity
                finish();
                break;
            //购票按钮
            case R.id.details_movie_button_buy:
                loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
                if (loginSuccess){
                    Intent buy_intent=new Intent(this,TheatreActivity.class);
                    buy_intent.putExtra("resultMovie", (Serializable) resultMovie);
                    startActivity(buy_intent);
                }
                else {
                    ToastUtil.showToast(this,"请先登录");
                    Intent intent1 = new Intent(this,LoginActivity.class);
                    startActivity(intent1);
                }
                break;
            default:
                break;
        }
    }

    //取消关注
    private void cancelUrl() {
        getRequest(String.format(Apis.MOVIENOFOLLOW_URL,resultMovie.getId()),MovieNoFollowBean.class);
    }

    //点击关注
    private void NextUrl() {
        getRequest(String.format(Apis.MOVIEISFOLLOW_URL,resultMovie.getId()),MovieIsFollowBean.class);
    }

    private void selectReviceUrl(int page) {
        getRequest(String.format(Apis.SELECT_REVIEW,movieId,page,COUNT),SelectReviewBean.class);
    }

    //影评
    private void getReviewView(View view_review) {
        review_dialog_xrecy = view_review.findViewById(R.id.revice_dialog_xrecy);
        dialog_image_write = view_review.findViewById(R.id.revice_dialog_image_write);
        revice_linear_write = view_review.findViewById(R.id.layout_write);
        review_edit_write = view_review.findViewById(R.id.revice_dialog_edit_write);
        revice_text_send = view_review.findViewById(R.id.revice_dialog_text_send);
        initWrite();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        review_dialog_xrecy.setLayoutManager(linearLayoutManager);
        //支持刷新加载
        review_dialog_xrecy.setPullRefreshEnabled(true);
        review_dialog_xrecy.setLoadingMoreEnabled(true);
        //设置适配器
        reviewAdapter = new ReviewAdapter(this);
        review_dialog_xrecy.setAdapter(reviewAdapter);
        review_dialog_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh()
            {
                page=1;
                selectReviceUrl(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                selectReviceUrl(page);
            }
        });
        reviewAdapter.getSend(new ReviewAdapter.setSend() {
            @Override
            public void send(int comment_page, int commentId) {
                comment_flag=comment_page;
                getRequest(String.format(Apis.SELECT_COMMENT,commentId,comment_flag,COUNT),SelectCommentBean.class);
            }
        });

        //点赞
        reviewAdapter.getPraise(new ReviewAdapter.setPraise()
        {
            @Override
            public void changePraise(int id, int great, int position) {
                loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
                if (loginSuccess){
                    NextCommentUrl(id);
                    if (great==0){
                        reviewAdapter.getlike(position);
                    }
                }
                else
                    {
                    ToastUtil.showToast(DetailsActivity.this,"请先登录");
                    Intent intent1 = new Intent(DetailsActivity.this,LoginActivity.class);
                    startActivity(intent1);
                }

            }
        });

    }

    //写评论
    private void initWrite() {
        dialog_image_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSuccess = sharedPreferences.getBoolean("loginSuccess", false);
                if (loginSuccess){
                    revice_linear_write.setVisibility(View.VISIBLE);
                }
                else {
                    ToastUtil.showToast(DetailsActivity.this,"请先登录");
                    Intent intent1 = new Intent(DetailsActivity.this,LoginActivity.class);
                    startActivity(intent1);
                }



            }
        });
        //发送评论
        revice_text_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit_write = review_edit_write.getText().toString();

                /**
                 * 字符串转换unicode
                 */
                StringBuffer unicode = new StringBuffer();
                for (int i = 0; i < edit_write.length(); i++) {
                    // 取出每一个字符
                    char c = edit_write.charAt(i);
                    // 转换为unicode
                    unicode.append("\\u" + Integer.toHexString(c));
                }
                String s = unicode.toString();


                Map<String,String> params=new HashMap<>();
                params.put("movieId",movieId);
                params.put("commentContent",s);
                postRequest(Apis.INSERT_COMMENT,params,InsertCommentBean.class);

            }
        });
    }

    //进行点赞
    private void NextCommentUrl(int id) {
        Map<String,String> params=new HashMap<>();
        params.put("commentId",id+"");
        postRequest(Apis.NEXT_COMMENT,params,NextPraiseBean.class);
    }

    private void getNoticeView(View view_notice) {
        RecyclerView notice_dialog_recy = view_notice.findViewById(R.id.notice_dialog_recy);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        notice_dialog_recy.setLayoutManager(linearLayoutManager);
        NoticeDialogAdapter noticeDialogAdapter=new NoticeDialogAdapter(this);
        notice_dialog_recy.setAdapter(noticeDialogAdapter);
        noticeDialogAdapter.setList(resultMovie.getShortFilmList());
       /* notice_dialog_recy.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = (NiceVideoPlayer) ((NoticeDialogAdapter.ViewHolder) holder).itemView;
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });*/

    }
    private void getPhotoView(View view_photo) {
        RecyclerView daialog_photo_recy=view_photo.findViewById(R.id.photo_dialog_recy);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,OrientationHelper.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        daialog_photo_recy.setLayoutManager(staggeredGridLayoutManager);
        PhotoDialogAdapter photoDialogAdapter=new PhotoDialogAdapter(this);
        daialog_photo_recy.setAdapter(photoDialogAdapter);
        photoDialogAdapter.setList(resultMovie.getPosterList());
        //自定义分割线
        DividerDecoration dividerDecoration=new DividerDecoration(6);
        daialog_photo_recy.addItemDecoration(dividerDecoration);
        photoDialogAdapter.setImageOnClick(new PhotoDialogAdapter.imageOnClick() {
            @Override
            public void onClick(String imageUrl) {
                selectImageeUrl(imageUrl);
            }
        });


    }
    private void selectImageeUrl(String imageUrl) {
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.image_dialog, null);
        //初始化控件
        ImageView imageView = inflate.findViewById(R.id.dialog_image);
        final SelectDialog selectDialog = new SelectDialog(this, inflate);
        Glide.with(this).load(imageUrl).into(imageView);
        selectDialog.show();
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
            }
        });
    }



    //详情
    private void getDetailsView(View view_details) {

        dialog_text_type = view_details.findViewById(R.id.detaols_dialog_text_type);
        dialog_text_director = view_details.findViewById(R.id.detaols_dialog_text_director);
        dialog_text_timelong = view_details.findViewById(R.id.detaols_dialog_text_timelong);
        dialog_text_place = view_details.findViewById(R.id.detaols_dialog_text_place);
        dialog_image = view_details.findViewById(R.id.details_dialog_image);
        dialog_text_content = view_details.findViewById(R.id.detaols_dialog_text_content);

        Glide.with(this).load(resultMovie.getImageUrl()).into(dialog_image);
        dialog_image.setScaleType(ImageView.ScaleType.FIT_XY);
        dialog_text_type.setText(resultMovie.getMovieTypes());
        dialog_text_director.setText(resultMovie.getDirector());
        dialog_text_timelong.setText(resultMovie.getDuration());
        dialog_text_place.setText(resultMovie.getPlaceOrigin());
        dialog_text_content.setText(resultMovie.getSummary());
        dialog_text_recy = view_details.findViewById(R.id.details_dialog_recy);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        dialog_text_recy.setLayoutManager(linearLayoutManager);
        DetailsDialogAdapter detailsAdapter=new DetailsDialogAdapter(this);
        String[] split = resultMovie.getStarring().split(",");
        dialog_text_recy.setAdapter(detailsAdapter);
        List<String> list_name=new ArrayList<>();
        for (int i=0;i<split.length;i++){
            list_name.add(split[i]);
        }
        detailsAdapter.setList(list_name);
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

    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        //NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        JZVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayerStandard.releaseAllVideos();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

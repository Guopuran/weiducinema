package com.bw.movie.main.movie.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.main.movie.fragment.HotMoreFragment;
import com.bw.movie.main.movie.fragment.NowHotMoreFragment;
import com.bw.movie.main.movie.fragment.WillMoreFragment;
import com.bw.movie.util.ImageViewAnimationHelper;
import com.bw.movie.util.ToastUtil;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieMroeActivity extends BaseActivity {
     @BindView(R.id.movie_more_viewpager)
     ViewPager viewPager;
     @BindView(R.id.movie_more_group)
     RadioGroup group;
     @BindView(R.id.movie_more_search_relative)
    RelativeLayout movie_more_relative;
     @BindView(R.id.movie_more_back_image)
    ImageView image_back;
     @BindView(R.id.movie_more_location_but)
    Button location_but;
     @BindView(R.id.movie_more_location_text)
    TextView location_text;
     private List<Fragment> mList;
    private int flag;
    private String moviemore;
    private ImageViewAnimationHelper imageViewAnimationHelper;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String adCode;
    private String cityCode;
    private String province;
    private String city;
    private double longitude;
    private double latitude;
    @Override
    protected void initData() {
        //接受intent的值
        ButterKnife.bind(this);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 4);
        mList = new ArrayList<>();
        mList.add(new HotMoreFragment());
        mList.add(new NowHotMoreFragment());
        mList.add(new WillMoreFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mList.get(i);
            }
            @Override
            public int getCount() {
                return mList.size();
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                  switch (i){
                      case 0:
                          group.check(R.id.activity_movie_more_hot_but);
                          break;
                      case 1:
                          group.check(R.id.activity_movie_more_nowhot_but);
                          break;
                      case 2:
                          group.check(R.id.activity_movie_more_will_but);
                          break;
                          default:
                              break;
                  }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.activity_movie_more_hot_but:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.activity_movie_more_nowhot_but:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.activity_movie_more_will_but:
                        viewPager.setCurrentItem(2);
                        break;
                        default:
                            break;
                }
            }
        });
        setLayout();
        //定位
        //开始定位，这里模拟一下定位
        mlocationClient = new AMapLocationClient(this);
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation!=null){
                    if(aMapLocation.getErrorCode() == 0){
                        //获取纬度
                        latitude = aMapLocation.getLatitude();
                        //获取经度
                        longitude = aMapLocation.getLongitude();
                        //城市信息
                        city = aMapLocation.getCity();
                        //省信息
                        province = aMapLocation.getProvince();
                        //城市编码
                        cityCode = aMapLocation.getCityCode();
                        //地区编码
                        adCode = aMapLocation.getAdCode();
                        CityPicker.from(MovieMroeActivity.this).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
                        location_text.setText(city);
                        mlocationClient.stopLocation();

                    }
                }
            }
        });
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();

    }



    public void setLayout(){
        if (flag==0){
            viewPager.setCurrentItem(0);
            group.check(R.id.activity_movie_more_hot_but);
        }
        else if (flag==1){
            viewPager.setCurrentItem(1);
            group.check(R.id.activity_movie_more_nowhot_but);
        }
        else if(flag==2){
            viewPager.setCurrentItem(2);
            group.check(R.id.activity_movie_more_will_but);
        }
    }
    //定位的点击事件
    @OnClick(R.id.movie_more_location_but)
    public void  locationOnClick(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            String[] mStatenetwork = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
            };
            ActivityCompat.requestPermissions(this, mStatenetwork, 100);
        }
        CityPicker.from(this) //activity或者fragment
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        location_text.setText(data.getName());
                    }

                    @Override
                    public void onCancel(){
                        ToastUtil.showToast(MovieMroeActivity.this,"取消选择");
                    }

                    @Override
                    public void onLocate() {
                        //定位接口，需要APP自身实现，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.from(MovieMroeActivity.this).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
                            }
                        }, 3000);
                    }
                })
                .show();
    }
    @OnClick(R.id.movie_more_back_image)
    public void backOnClick(){
        finish();
    }
    //弹出的动画
    @OnClick(R.id.movie_search_image)
    public void outAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(movie_more_relative, "translationX",  -400f);
        animator.setDuration(1000);
        animator.start();
    }
    //收回的动画
    @OnClick(R.id.movie_search_text)
    public void inAnimation()
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(movie_more_relative, "translationX",  10f);
        animator.setDuration(500);
        animator.start();
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
        return R.layout.activity_movie_mroe;
    }
}

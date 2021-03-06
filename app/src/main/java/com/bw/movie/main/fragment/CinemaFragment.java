package com.bw.movie.main.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.MyApplication;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.main.cinema.fragment.NearFragment;
import com.bw.movie.main.cinema.fragment.RecommendFragment;
import com.bw.movie.util.ImageViewAnimationHelper;
import com.bw.movie.util.ToastUtil;
import com.squareup.leakcanary.RefWatcher;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CinemaFragment extends BaseFragment {
    @BindView(R.id.cinema_viewpager)
    ViewPager cinema_viewpager;
    @BindView(R.id.cinema_group)
    RadioGroup cineam_group;
    @BindView(R.id.movie_cinema_relative)
    RelativeLayout search_relative;
    private List<Fragment> mList;
    @BindView(R.id.movie_location_but1)
     Button location_but;
    @BindView(R.id.movie_location_text1)
     TextView location_text;
    @BindView(R.id.movie_search_edit)
    EditText search_edit;
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

      mList = new ArrayList<>();
      mList.add(new RecommendFragment());
      mList.add(new NearFragment());
      cinema_viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
          @Override
          public Fragment getItem(int i) {
              return mList.get(i);
          }

          @Override
          public int getCount() {
              return mList.size();
          }
      });
       cinema_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int i, float v, int i1) {

          }

          @Override
          public void onPageSelected(int i) {
               switch (i){
                   case 0:
                     cineam_group.check(R.id.cinema_recommend_but);
                     break;
                   case 1:
                       cineam_group.check(R.id.cinema_near_but);
                       break;
                     default:
                         break;
               }
          }
          @Override
          public void onPageScrollStateChanged(int i) {

          }
      });
      cineam_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
              switch (checkedId){
                  case R.id.cinema_recommend_but:
                      cinema_viewpager.setCurrentItem(0);
                      break;
                  case R.id.cinema_near_but:
                      cinema_viewpager.setCurrentItem(1);
                      break;
                      default:
                          break;
              }
          }
      });
        //定位
        //开始定位，这里模拟一下定位
        mlocationClient = new AMapLocationClient(getActivity());
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
                        CityPicker.from(getActivity()).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
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
    //定位的点击按钮
    @OnClick({R.id.movie_location_but1})
    public void onClickLocation(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            String[] mStatenetwork = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
            };
            ActivityCompat.requestPermissions(getActivity(), mStatenetwork, 100);
        }
        CityPicker.from(getActivity()) //activity或者fragment

                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        location_text.setText(data.getName());
                    }

                    @Override
                    public void onCancel(){
                        ToastUtil.showToast(getActivity(),"取消选择");
                    }

                    @Override
                    public void onLocate() {
                        CityPicker.from(getActivity()).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
                    }
                })
                .show();
    }
    //弹出的动画
    @OnClick(R.id.movie_search_image)
    public void outAnimation(){
        search_edit.setText("");
        ObjectAnimator animator = ObjectAnimator.ofFloat(search_relative, "translationX",  -400f);
        animator.setDuration(1000);
        animator.start();
    }
    //收回的动画
    @OnClick(R.id.movie_search_text)
    public void inAnimation()
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(search_relative, "translationX",  10f);
        animator.setDuration(500);
        animator.start();
        hideKeyboard(getView());
    }
    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(View view) {
       ButterKnife.bind(this,view);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cinema;
    }
    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }
   //检测内存泄漏
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }*/

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
    //隐藏虚拟键盘
    public static void hideKeyboard(View v){
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {

            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );
        }
    }
}

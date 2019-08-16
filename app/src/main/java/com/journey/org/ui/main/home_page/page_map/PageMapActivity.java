package com.journey.org.ui.main.home_page.page_map;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.ActivityPageMapBinding;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 景区地图
 *
 * @author 逍遥
 * @Date 2019/8/2
 */
public class PageMapActivity extends BaseActivity<ActivityPageMapBinding, PageMapViewModel> {
    // 百度地图辅助类
    private BaiduMap mBaiduMap;
    // 定位类
    private LocationClient mLocationClient;
    // 景区经度
    private double mLongitude = 108.17915965086003;
    // 景区纬度
    private double mLatitude = 26.499389873224153;

    @Override
    public void initParam() {
        super.initParam();
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        mLatitude = bundle.getDouble("longitude", 26.499389873224153);
        mLongitude = bundle.getDouble("latitude", 108.17915965086003);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_page_map;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        // 百度地图辅助类
        mBaiduMap = binding.scenicMap.getMap();
        // 展示中心点
        showCenterPoint(mLatitude, mLongitude);
        //  展示景区点
        showScenicPoint(mLatitude, mLongitude);
        // 初始化toolbar
        viewModel.initToolbar("西江千户苗寨");
        // 开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 初始化定位
        initLocation();
        // 展示景区点
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        /*********************************************百度地图**************************************/
        // 百度地图地图单击点击事件
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                String name = mapPoi.getName();
                KLog.e("===========name" + name);
                ToastUtils.showShort(name);



                return false;
            }
        });
        // 地图 Marker 覆盖物点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setAlpha(0.6f);
                return false;
            }
        });
        /*********************************************其他*****************************************/
        viewModel.onClickToScenicEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                Intent i1 = new Intent();
                // 驾车路线规划
                i1.setData(Uri.parse("baidumap://map/direction?region=guiyang&origin=26.499389873224153,108.17915965086003&destination=西江千户苗寨&coord_type=bd09ll&mode=driving&src=andr.baidu.openAPIdemo"));
                startActivity(i1);
            }
        });
    }

    // 初始化定位
    private void initLocation() {
        // 定位初始化
        mLocationClient = new LocationClient(this);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(6000);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
    }

    // 展示中心点
    private void showCenterPoint(double la, double lo) {
        if (mBaiduMap == null) {
            return;
        }
        LatLng centerPoint = new LatLng(la, lo); //设定中心点坐标
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(centerPoint)
                .zoom(18)
                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
    }

    // 展示景区点
    private void showScenicPoint(double la, double lo) {
        if (mBaiduMap == null) {
            return;
        }
        //定义Maker坐标点
        LatLng point = new LatLng(la, lo);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_primary_address);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    @Override
    protected void onResume() {
        binding.scenicMap.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        binding.scenicMap.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        binding.scenicMap.onDestroy();
        super.onDestroy();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mBaiduMap == null) {
                return;
            }
            KLog.e("=========================经纬度：" + location.getLongitude());
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }
}

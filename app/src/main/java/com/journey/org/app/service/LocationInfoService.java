package com.journey.org.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * 定位的服务
 *
 * @author 逍遥
 * @Date 2019/8/6
 */
public class LocationInfoService extends Service {
    // 定位参数类
    private LocationClient mLocationClient;
    // 定位位置信息监听回调
    private BDAbstractLocationListener mBDLocationListener;
    // 第一次发送地址信息到主界面
    private boolean isFirstMain = false;

    public BDLocation location;

    public LocationInfoService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());

        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);
        // 声明定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(false);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();
        RxBus.getDefault().toObservable(Boolean.class)
                .observeOn(AndroidSchedulers.mainThread()) //回调到主线程更新UI
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(final Boolean b) throws Exception {
                        if (b) {
                            // 启动定位
                            mLocationClient.start();
                        }
                    }
                });
    }

    // 地理信息监听回调
    private class MyBDLocationListener extends BDAbstractLocationListener {
        BDLocation location;

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
                this.location = location;
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String address = location.getAddrStr();
                SPUtils.getInstance().put("city", location.getCity());
                KLog.e("address:" + address + " latitude:" + latitude
                        + " longitude:" + longitude + "—");
                RxBus.getDefault().post(location.getCity());
                RxBus.getDefault().post(location);
                if (mLocationClient.isStarted()) {
                    // 获得位置之后停止定位
                    mLocationClient.stop();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消监听函数
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
    }
}

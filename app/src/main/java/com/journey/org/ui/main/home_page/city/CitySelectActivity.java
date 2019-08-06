package com.journey.org.ui.main.home_page.city;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.journey.org.R;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 城市选择
 *
 * @author 逍遥
 * @Date 2019/8/6
 */
public class CitySelectActivity extends AppCompatActivity {

    private List<HotCity> hotCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        initData();
    }

    // 初始化数据
    private void initData() {
        RxBus.getDefault().toObservable(BDLocation.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BDLocation>() {
                    @Override
                    public void accept(final BDLocation location) throws Exception {
                        KLog.e("==============cityCode" + location.getCityCode());
                        CityPicker.from(CitySelectActivity.this)
                                .locateComplete(new LocatedCity(location.getCity(),
                                        location.getProvince(), location.getCityCode()), LocateState.SUCCESS);
                    }
                });
        hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        CityPicker.from(this)
                .enableAnimation(true)
                .setAnimationStyle(R.style.CustomAnim)
                .setLocatedCity(null)
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        ToastUtils.showShort("=======点击的数据：%s，%s" + data.getName() + "=====" + data.getCode());
                        Intent intent = new Intent();
                        intent.putExtra("cityName", data.getName());
                        intent.putExtra("cityCode", data.getCode());
                        setResult(Activity.RESULT_OK, intent);
                        KLog.e("=======点击的数据：%s，%s" + data.getName() + "=====" + data.getCode());
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        finish();
                    }

                    @Override
                    public void onLocate() {
                        RxBus.getDefault().post(true);
                    }
                }).show();
    }
}

package com.journey.org.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.service.LocationInfoService;
import com.journey.org.databinding.ActivitySplashBinding;
import com.journey.org.ui.main.MainActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.AndroidBarUtil;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * Splash 界面 需要在该界面就完成对当前人所在的位置进行定位，以便在主页的时候能通过该城市进行景区最高等级的选择
 *
 * @author 逍遥
 * @Date 2019/5/24
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        statusBarHeight = AndroidBarUtil.getStatusBarHeight(this);
        SPUtils.getInstance().put("toolbarHeight", statusBarHeight);
        // 隐藏导航栏 隐藏状态栏
        AndroidBarUtil.NavigationBarStatusBar(this, true);
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        startService(new Intent(this, LocationInfoService.class));
    }

    /**
     * 权限申请
     */
    private void requestPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_WIFI_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            enterMain();
                        } else {
                            showPermissionsDialog();
                        }
                    }
                });

    }

    private void enterMain() {
        // 设置3秒进入主界面
        Observable.just("")
                .delay(2, TimeUnit.SECONDS)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle((LifecycleProvider) this))
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(MainActivity.class);
                        finish();
                    }
                });
    }

    private void showPermissionsDialog() {
        MaterialDialogUtils.showBasicDialog(this, "系统提示", "需要授权才能使用！")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //重新发起请求
                        requestPermission();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(MainActivity.class);
                        finish();
                    }
                })
                .show();
    }

    // 6.0系统动态获取权限
       /* if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }*/
}

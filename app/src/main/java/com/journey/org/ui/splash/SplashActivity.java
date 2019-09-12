package com.journey.org.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.service.LocationInfoService;
import com.journey.org.databinding.ActivitySplashBinding;
import com.journey.org.ui.main.MainActivity;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.AndroidBarUtil;
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
}

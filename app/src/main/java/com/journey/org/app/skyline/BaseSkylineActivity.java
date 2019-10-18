package com.journey.org.app.skyline;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.journey.org.R;
import com.journey.org.app.Config;
import com.skyline.core.CoreServices;
import com.skyline.teapi.ApiException;
import com.skyline.teapi.IPosition;
import com.skyline.teapi.ISGWorld;
import com.skyline.terraexplorer.TEApp;
import com.skyline.terraexplorer.models.AppLinks;
import com.skyline.terraexplorer.models.FavoritesStorage;
import com.skyline.terraexplorer.models.LocalBroadcastManager;
import com.skyline.terraexplorer.models.UI;
import com.skyline.terraexplorer.views.TEGLRenderer;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Objects;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.Utils;

public abstract class BaseSkylineActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<V, VM> {

    protected SkylineOptHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 给另起动的进程设置当前进程所在的Activity
        TEApp.setMainActivityContext(this);
        // 新进程链接初始化
        AppLinks.initializeAsync();
        // 初始化核心服务
        CoreServices.Init(this);
        // setContentView(R.layout.activity_base_skyline);
    }

    /**
     * 该方法运行在Activity的onCreate()里面
     */
    @Override
    public void initData() {
        super.initData();
        // 需要的权限请求(当前为读写权限)
        requestPermissions();
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) { // 如何权限已经赋予了 则进行启动引擎
                            // 通知当前为三维地图加载之前(这是在加载三维地图之前可以做的操作)
                            onBeforeLoad();
                            // 启动引擎
                            startup();
                        } else { // 权限还未赋予，提示只有权限赋予了才能加载三维地图
                            showPermissionsDialog();
                        }
                    }
                });
    }

    /**
     * 展示权限是否赋予的Dialog()
     */
    private void showPermissionsDialog() {
        MaterialDialogUtils.showBasicDialog(this, "系统提示", "需要文件的读写权限才能进行加载三维地图，请赋予文件的读写权限！")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) { // 点击确认
                        // 重新发起权限的赋予
                        requestPermissions();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() { // 点击取消
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // 关闭当前Activity
                        finish();
                    }
                })
                .show();
    }

    /**
     * 启动引擎
     */
    private void startup() {
        // 广播监听三维地图渲染器开始加载
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) { // 广播
                // 初始化FavoritesStorage(存储器)
                FavoritesStorage.Init();
                // 通知三维地图正在加载中
                onLoading();
                // 开启渲染线程
                UI.runOnRenderThread(new Runnable() {
                    @Override
                    public void run() {
                        setISGWorld();
                    }
                });
                // 解除广播监听
                LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this);
            }
        }, new IntentFilter(TEGLRenderer.ENGINE_INITIALIZED)); // 广播注册

    }

    /**
     * 配置ISWorld
     */
    private void setISGWorld() {
        // 注册监听ISGWorld
        ISGWorld.getInstance().getCoordServices().getSourceCoordinateSystem().InitLatLong();
        // 设置阴影颜色
        ISGWorld.getInstance().SetOptionParam("GlobalShadowColor", 0x99000000);
        // 判断地图进程是否是Debug()
        if (TEApp.isDebug()) { // 如果是Debug
            ISGWorld.getInstance().SetParam(8360, null);
        }
        ISGWorld.getInstance().SetParam(8350, Utils.getContext().getResources().getDisplayMetrics().density);
        ISGWorld.getInstance().SetParam(8370, UI.scaleFactor());
        // 运行在异步渲染线程里面
        UI.runOnRenderThreadAsync(new Runnable() {
            @Override
            public void run() {
                setFly();
            }


        });
    }

    /**
     * 设置Fly(飞)
     */
    private void setFly() {
        //  Config
        //2、获取fly文件路径
        final String lastOpenedProject = Config.FLY_PATH;
        //3、打开fly
        openProject(lastOpenedProject);
    }

    /**
     * 打开fly
     *
     * @param lastOpenedProject
     */
    private void openProject(String lastOpenedProject) {
        //监听是否加载完成
        ISGWorld.getInstance().addOnLoadFinishedListener(new ISGWorld.OnLoadFinishedListener() {
            @Override
            public void OnLoadFinished(final boolean bSuccess) {
                //移除监听
                ISGWorld.getInstance().removeOnLoadFinishedListener(this);
                UI.runOnUiThreadAsync(new Runnable() {
                    @Override
                    public void run() {
                        //回调到UI线程
                        if (bSuccess) {
                            KLog.e("加载完成：" + bSuccess);
                            UI.runOnRenderThreadAsync(new Runnable() {
                                @Override
                                public void run() {
                                    // 1065 和 0 隐藏缩放按钮
                                    ISGWorld.getInstance().getCommand().Execute(1065, 0);
                                }
                            });
                            //创建helper
                            helper = new SkylineOptHelper();
                            //加载完成
                            onLoadFinish();
                        } else {
                            KLog.e("加载失败：" + bSuccess);
                            onLoadFail(new ApiException("open project fail"));
                        }
                    }
                });
            }
        });
        try {
            //打开fly
            ISGWorld.getInstance().getProject().Open(lastOpenedProject);
        } catch (final ApiException ex) {
            ex.printStackTrace();
            UI.runOnUiThreadAsync(new Runnable() {
                @Override
                public void run() {
                    onLoadFail(ex);
                }
            });
        }
    }

    //飞到默认兴趣点
    protected void flyDefaultPoint() {
        flyPoint(Config.getX(), Config.getY(), Config.getAltitude(), Config.getDistance(), Config.getPitch(), Config.getYaw());
    }

    protected void flyPoint(double X, double Y) {
        flyPoint(X, Y, Config.getAltitude(), Config.getDistance(), Config.getPitch(), Config.getYaw());
    }

    protected void flyPoint(double X, double Y, double Altitude, double Distance) {
        flyPoint(X, Y, Altitude, Distance, Config.getPitch(), Config.getYaw());
    }

    protected void flyPoint(double X, double Y, double Altitude, double Distance, double Pitch) {
        flyPoint(X, Y, Altitude, Distance, Pitch, Config.getYaw());
    }

    //飞到指定经纬度高度
    protected void flyPoint(double X, double Y, double Altitude, double Distance, double Pitch, double Yaw) {
        //构建点对象
        IPosition position = ISGWorld.getInstance().getCreator().CreatePosition();
        position.setX(X);
        position.setY(Y);
        position.setAltitudeType(Config.getAltitudeType());
        position.setAltitude(Altitude);
        position.setDistance(Distance);
        position.setPitch(Pitch);
        position.setYaw(Yaw);
        //飞操作
        ISGWorld.getInstance().getNavigate()
                .FlyTo(position);
    }

    // 加载三维地图之前
    protected abstract void onBeforeLoad();

    // 三维地图加载中
    protected abstract void onLoading();

    // 三维地图加载完成
    protected abstract void onLoadFinish();

    // 三维地图加载失败
    protected abstract void onLoadFail(ApiException e);

}

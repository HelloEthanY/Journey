package com.journey.org.ui.main.home_technology.page_skyline;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioGroup;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.journey.org.R;
import com.journey.org.BR;
import com.journey.org.app.skyline.BaseSkylineActivity;
import com.journey.org.databinding.ActivityPageSkylineBinding;
import com.skyline.teapi.ApiException;
import com.skyline.terraexplorer.models.UI;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class PageSkylineActivity extends BaseSkylineActivity<ActivityPageSkylineBinding, PageSkylineViewModel> {

    private BridgeWebView mWebView;
    private String url;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_page_skyline;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        //导航栏点击事件
        binding.layoutSkylineNav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.skyline_page:
                        url = "file:///android_asset/web/skyline/merchant_home_page.html";
                        mWebView.loadUrl(url);
                        ToastUtils.showShort("首页");
                        break;

                    case R.id.skyline_classification:
                        url = "file:///android_asset/web/echarts.html";
                        mWebView.loadUrl(url);
                        ToastUtils.showShort("地块分类");
                        break;

                    case R.id.skyline_land:
                        ToastUtils.showShort("项目用地");
                        break;

                    case R.id.skyline_Plan:
                        ToastUtils.showShort("规划单元");
                        break;

                    case R.id.skyline_personal:
                        ToastUtils.showShort("个人中心");
                        break;
                }
            }
        });
        /****************************** 这里是注册了由Android 调 JS 的方法****************************/
        binding.skylineOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("办公场所");
                mWebView.callHandler("dataToJs", "测数据001", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {

                    }
                });
            }
        });
        /********************************************结束*********************************************/
    }

    /********************************* 这里是注册了由js调Android 的方法*******************************/
    private void initRegisterHandler() {
        mWebView.registerHandler("dataToAndroid", // 方法名
                new BridgeHandler() {
                    @Override
                    public void handler(String data, CallBackFunction function) {
                        function.onCallBack("submitFromWeb exe, response data 中文 from Java");
                        ToastUtils.showShort("js调用了Android 代码" + data);
                    }
                });
    }

    /********************************************结束*********************************************/

    @Override
    public void initData() {
        super.initData();
        mWebView = binding.webSkyline;
        url = "http://192.168.17.128:8080/";
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.setBackgroundColor(0);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
        initRegisterHandler();
    }

    @Override
    protected void onBeforeLoad() {
        KLog.e("初始化三维引擎...");
        binding.tvLoading.setText("初始化三维引擎...");
    }

    @Override
    protected void onLoading() {
        KLog.e("开始加载...");
        binding.tvLoading.setText("开始加载...");
    }

    @Override
    protected void onLoadFinish() {
        KLog.e("加载成功");
        binding.tvLoading.setVisibility(View.GONE);
        UI.runOnRenderThreadAsync(new Runnable() {
            @Override
            public void run() {
                helper.flyDefaultPoint();
            }
        });
    }

    @Override
    protected void onLoadFail(ApiException e) {
        KLog.e("加载失败");
        binding.tvLoading.setText("加载失败");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出杀死当前进程
            android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.finish();
    }

    @Override
    protected void onDestroy() {
        // 防止内存泄漏
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}

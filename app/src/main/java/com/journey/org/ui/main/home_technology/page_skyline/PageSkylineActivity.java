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

import com.journey.org.R;
import com.journey.org.BR;
import com.journey.org.app.skyline.BaseSkylineActivity;
import com.journey.org.databinding.ActivityPageSkylineBinding;
import com.skyline.teapi.ApiException;
import com.skyline.terraexplorer.models.UI;

import me.goldze.mvvmhabit.utils.KLog;

public class PageSkylineActivity extends BaseSkylineActivity<ActivityPageSkylineBinding, PageSkylineViewModel> {

    private WebView mWebView;
    private WebSettings webSettings;
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

    }

    @Override
    public void initData() {
        super.initData();
        url = "file:///android_asset/web/skyline/merchant_home_page.html";
        // url = "http://localhost:63342/StudyCSSProject/merchant_home_page.html";
        mWebView = binding.webSkyline;
        webSettings = mWebView.getSettings();
        initSettings();
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
        initWebView();
    }

    // 初始化webView
    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        mWebView.setBackgroundColor(0);
        mWebView.addJavascriptInterface(this, "androidObject");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                KLog.e(url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
    }

    /**
     * js 调用 Android的方法
     *
     * @return
     */
    @JavascriptInterface
    public String loadWebChartData() {
        Log.i("qcl0228", "js调用了安卓的方法");
        return "我是js调用安卓获取的数据";
    }

    // 初始化 setting
    private void initSettings() {
        webSettings.setJavaScriptEnabled(true);//访问的页面与Javascript交互
        //  mWebView.addJavascriptInterface(, "jsObj");
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setBuiltInZoomControls(false); //设置是否可缩放，会出现缩放工具（若为true则上面的设值也默认为true）
        webSettings.setDisplayZoomControls(false); //隐藏缩放工具
        //设置优先使用缓存:
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
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
    protected void onResume() {
        super.onResume();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(false);
        }
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

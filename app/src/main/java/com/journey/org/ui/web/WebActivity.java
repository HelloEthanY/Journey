package com.journey.org.ui.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.ActivityWebBinding;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * webActivity
 *
 * @author 逍遥
 * @Date 2019/8/6
 */
public class WebActivity extends BaseActivity<ActivityWebBinding, WebViewModel> {
    // 网站地址
    private String webUrl = "http://www.baidu.com/";

    private String title = "";

    @Override
    public void initParam() {
        super.initParam();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                webUrl = bundle.getString("webUrl", webUrl);
                title = bundle.getString("title", "");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_web;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        // 初始化toolbar
        viewModel.initToolbar(title, false);
        // 初始化WebView
        initWebView();
    }

    // 初始化WebView
    private void initWebView() {
        // 加载网络地址
        binding.webView.loadUrl(webUrl);
        // 初始化webView 的辅助类
        initWebSetting();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.webView.getSettings()
                            .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }
            }

        });
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

    }

    // 初始化webView 的辅助类
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSetting() {
        //声明WebSettings子类
        WebSettings webSettings = binding.webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //支持插件
        // webSettings.setPluginsEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (binding.webView != null) {
            binding.webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            binding.webView.clearHistory();
            ((ViewGroup) binding.webView.getParent()).removeView(binding.webView);
            binding.webView.destroy();
        }
        super.onDestroy();
    }
}

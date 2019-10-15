package com.journey.org.ui.main.home_technology.page_chart.web_chart;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.databinding.FragmentWebChartBinding;

import org.json.JSONException;
import org.json.JSONObject;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 引入webView 来加载 eCharts（网页版的图表）
 *
 * @author 逍遥
 * @Date 2019/10/14
 */
public class WebChartFragment extends BaseLazyFragment<FragmentWebChartBinding, WebChartViewModel> {
    private WebView mWebView;
    private WebSettings webSettings;
    private String url;


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_web_chart;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    protected void lazyLoadData() {
        url = "file:///android_asset/web/echarts.html";
        mWebView = binding.webChart;
        webSettings = mWebView.getSettings();

        initSettings();
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
        initWebView();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject result = new JSONObject();
                try {
                    result.put("result", "this is webChart data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String s = result.toString();
                // 大于等于 Android api 19
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.evaluateJavascript("loadWebChartData(" + s + ")", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {

                                }
                            });
                        }
                    });
                } else {
                    mWebView.loadUrl("javascript:loadWebChartData(" + s + ")");
                }
            }
        });
    }

    // 初始化webView
    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        mWebView.addJavascriptInterface(this, "androidObject");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                KLog.e(url);
//                Bundle bundle = new Bundle();
//                bundle.putString(GlobalKey.URL, url);
//                bundle.putString(GlobalKey.TITLE, "正在加载...");
//                startActivity(WebViewActivity.class, bundle);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
               /* String title = view.getTitle();
                if (title != null) {
                     viewModel.setTitleText(title);
                }*/
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
    public void onResume() {
        super.onResume();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
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

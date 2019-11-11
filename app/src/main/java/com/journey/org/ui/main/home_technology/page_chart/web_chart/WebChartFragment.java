package com.journey.org.ui.main.home_technology.page_chart.web_chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.databinding.FragmentWebChartBinding;

/**
 * NestedScrollView 嵌套 BridgeWebView 加载图表
 *
 * @author 逍遥
 * @Date 2019/10/14
 */
public class WebChartFragment extends BaseLazyFragment<FragmentWebChartBinding, WebChartViewModel> {
    private BridgeWebView mWebView;
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
        url = "file:///android_asset/web/tiwt_home_page.html";
        mWebView = binding.webChart;
        mWebView.setDefaultHandler(new DefaultHandler());
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
        initWebView();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
    }

    // 初始化webView
    private void initWebView() {
        // mWebView.setBackgroundColor(0);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                ViewGroup.LayoutParams params = mWebView.getLayoutParams();
                params.width = getResources().getDisplayMetrics().widthPixels;
                params.height = mWebView.getHeight() - binding.scrollView.getHeight();
                mWebView.setLayoutParams(params);
            }
        });
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

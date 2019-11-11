package com.journey.org.ui.main.home_technology.page_bridge;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.ActivityPageBridgeBinding;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * android  与  html 互调  中间使用 BridgeWebView
 *
 * @author 逍遥
 * @Date 2019/10/24
 */
public class PageBridgeActivity extends BaseActivity<ActivityPageBridgeBinding, BaseViewModel> {

    private BridgeWebView mWebView;

    private String url;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_page_bridge;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        mWebView = binding.bridgeWebView;
        url = "file:///android_asset/web/android_js.html";
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.setBackgroundColor(0); //  设置背景为透明度
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
        initRegisterHandler();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        /****************************** 这里是注册了由Android 调 JS 的方法****************************/
        binding.bridgePersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("个人中心");
                mWebView.callHandler("dataToJs", "测数据00" + index++, new CallBackFunction() {
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

}



package com.journey.org.ui.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.github.lzyzsd.jsbridge.BridgeWebView;

/**
 * 不能滑动的webView
 *
 * @author 逍遥
 * @Date 2019/11/11
 */
public class NoScrollWebView extends BridgeWebView {

    public NoScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NoScrollWebView(Context context) {
        super(context);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(0, 0);
    }
}

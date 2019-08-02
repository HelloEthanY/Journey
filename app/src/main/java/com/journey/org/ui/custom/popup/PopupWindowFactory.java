package com.journey.org.ui.custom.popup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.journey.org.ui.custom.popup.page_detail.PageDetailPopupWindow;

/**
 * popup管理类
 *
 * @author 逍遥
 * @Date 2019/8/2
 */
public class PopupWindowFactory {

    /**
     * 展示首页详情菜单PopupWindow
     *
     * @param activity
     * @param width
     * @param height
     * @param fragment
     * @return
     */
    public static PageDetailPopupWindow getPageDetailPopupWindow(FragmentActivity activity, int width, int height, Fragment fragment) {
        return new PageDetailPopupWindow(activity, width, height, fragment);
    }

}

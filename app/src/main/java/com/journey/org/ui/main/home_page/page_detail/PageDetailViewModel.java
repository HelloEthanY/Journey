package com.journey.org.ui.main.home_page.page_detail;

import android.app.Application;
import android.support.annotation.NonNull;

import com.journey.org.app.base.BaseToolbarViewModel;

/**
 * 首页详情
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class PageDetailViewModel extends BaseToolbarViewModel {

    public PageDetailViewModel(@NonNull Application application) {
        super(application);
    }

    // 初始化 toolbar
    public void initToolbar(String name) {
        setToolbarTitle(name);
        setVisibleLeft(true);
        setVisibleMenu(true);
    }

}

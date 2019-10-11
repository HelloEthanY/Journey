package com.journey.org.ui.main.home_technology.page_chart;

import android.app.Application;
import android.support.annotation.NonNull;

import com.journey.org.app.base.BaseToolbarViewModel;

public class PageChartViewModel extends BaseToolbarViewModel {

    public PageChartViewModel(@NonNull Application application) {
        super(application);
    }

    // 初始化toolbar
    public void initToolbar(String title) {
        toolbarTitle.set(title);
        onShowMenu.set(false);
    }

}

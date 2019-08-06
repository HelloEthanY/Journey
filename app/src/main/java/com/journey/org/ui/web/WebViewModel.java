package com.journey.org.ui.web;

import android.app.Application;
import android.support.annotation.NonNull;

import com.journey.org.app.base.BaseToolbarViewModel;

/**
 * web
 *
 * @author 逍遥
 * @Date 2019/8/6
 */
public class WebViewModel extends BaseToolbarViewModel {
    public WebViewModel(@NonNull Application application) {
        super(application);
    }

    public void initToolbar(String title, boolean isShowMenu) {
        toolbarTitle.set(title);
        onShowMenu.set(isShowMenu);
    }

}

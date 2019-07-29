package com.journey.org.ui.main.home_mine;

import android.app.Application;
import android.support.annotation.NonNull;

import com.journey.org.app.base.BaseToolbarViewModel;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * 我的
 *
 * @author yu
 * @Date 2019/5/24
 */
public class HomeMineViewModel extends BaseToolbarViewModel {

    public HomeMineViewModel(@NonNull Application application) {
        super(application);

    }

    // 初始化 toolbar
    public void initToolbar() {
        setToolbarTitle("我的");
        setVisiableLeft(false);
    }


}

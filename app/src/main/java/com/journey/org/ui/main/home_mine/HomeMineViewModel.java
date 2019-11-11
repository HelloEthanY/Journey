package com.journey.org.ui.main.home_mine;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.journey.org.R;
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
    public void initToolbar(Context context) {
        setToolbarTitle("我的");
        setVisibleLeft(true);
        setVisibleMenu(true);
        setLeftIconMenu(ContextCompat.getDrawable(context, R.mipmap.ic_main_qr_code));
        setRightIconMenu(ContextCompat.getDrawable(context, R.mipmap.ic_main_qr_code));
    }
}

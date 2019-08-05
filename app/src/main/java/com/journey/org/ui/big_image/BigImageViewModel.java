package com.journey.org.ui.big_image;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.journey.org.app.base.BaseToolbarViewModel;

/**
 * 查看大图
 *
 * @author 逍遥
 * @Date 2019/8/5
 */
public class BigImageViewModel extends BaseToolbarViewModel {

    public BigImageViewModel(@NonNull Application application) {
        super(application);
    }

    public void initToolbar(String title) {
        toolbarTitle.set(title);
        onShowMenu.set(false);
    }

    // 图片地址
    public ObservableField<String> bigImageUrl = new ObservableField<>();

}

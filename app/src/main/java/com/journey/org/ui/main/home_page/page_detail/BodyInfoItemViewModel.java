package com.journey.org.ui.main.home_page.page_detail;

import android.support.annotation.NonNull;

import com.journey.org.data.home_page.PageDetailBodyEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * 景区-视频列表
 *
 * @author 逍遥
 * @Date 2019/7/30
 */
public class BodyInfoItemViewModel extends ItemViewModel<PageDetailViewModel> {

    public PageDetailBodyEntity.BodyBean bodyBean;
    // 展示内容
    public boolean isVisibility;

    public BodyInfoItemViewModel(@NonNull PageDetailViewModel viewModel, PageDetailBodyEntity.BodyBean bodyBean) {
        super(viewModel);
        this.bodyBean = bodyBean;
        isVisibility = "".equals(bodyBean.getBodyTitle());
    }
}

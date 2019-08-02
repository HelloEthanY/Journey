package com.journey.org.ui.main.home_page.page_detail;

import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * 景区-信息列表
 *
 * @author 逍遥
 * @Date 2019/7/30
 */
public class HeadInfoItemViewModel extends ItemViewModel<PageDetailViewModel> {
    // 名称
    public String name;
    // 内容
    public String nameContent;

    public HeadInfoItemViewModel(@NonNull PageDetailViewModel viewModel, String name, String nameContent) {
        super(viewModel);
        this.name = name;
        this.nameContent = nameContent;
    }
}

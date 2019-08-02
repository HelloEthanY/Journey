package com.journey.org.ui.main.home_page.page_detail;

import android.support.annotation.NonNull;

import com.journey.org.data.home_page.PageDetailVideoEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 景区-视频列表
 *
 * @author 逍遥
 * @Date 2019/7/30
 */
public class HeadVideoItemViewModel extends ItemViewModel<PageDetailViewModel> {

    public PageDetailVideoEntity.ItemListBean bean;

    public HeadVideoItemViewModel(@NonNull PageDetailViewModel viewModel, PageDetailVideoEntity.ItemListBean bean) {
        super(viewModel);
        this.bean = bean;
    }

    // item 的点击事件
    public BindingCommand onClickVideoItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.onVideoPathEvent.setValue(bean);
        }
    });
}

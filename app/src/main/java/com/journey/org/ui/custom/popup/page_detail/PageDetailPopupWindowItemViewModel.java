package com.journey.org.ui.custom.popup.page_detail;

import android.support.annotation.NonNull;

import com.journey.org.data.home_page.PageDetailMenuEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 首页详情之菜单
 *
 * @author 逍遥
 * @Date 2019/8/2
 */
public class PageDetailPopupWindowItemViewModel extends ItemViewModel<PageDetailPopupWindowViewModel> {
    public PageDetailMenuEntity entity;

    public PageDetailPopupWindowItemViewModel(@NonNull PageDetailPopupWindowViewModel viewModel, PageDetailMenuEntity entity) {
        super(viewModel);
        this.entity = entity;
    }

    // item  的点击事件
    public BindingCommand onClickItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.onClickItemEvent.setValue(entity);
        }
    });
}

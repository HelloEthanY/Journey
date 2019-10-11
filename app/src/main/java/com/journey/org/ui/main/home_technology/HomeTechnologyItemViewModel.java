package com.journey.org.ui.main.home_technology;

import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 技术首页
 *
 * @author yu
 * @Date 2019/5/28
 */
public class HomeTechnologyItemViewModel extends MultiItemViewModel<HomeTechnologyViewModel> {

    public String url;

    public HomeTechnologyItemViewModel(@NonNull HomeTechnologyViewModel viewModel, String url) {
        super(viewModel);
        this.url = url;
    }

    // item 的点击事件
    public BindingCommand onClickItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // viewModel.onClickItemEvent.setValue(url);
        }
    });
}

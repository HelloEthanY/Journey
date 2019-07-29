package com.journey.org.ui.main.home_page;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 首页
 *
 * @author yu
 * @Date 2019/5/28
 */
public class HomePageItemViewModel extends ItemViewModel<HomePageViewModel> {

    public String url;
    public Drawable drawable;

    public HomePageItemViewModel(@NonNull HomePageViewModel viewModel, String url, Drawable drawable) {
        super(viewModel);
        this.url = url;
        this.drawable = drawable;
    }

    // item 的点击事件
    public BindingCommand onClickItem = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          //  viewModel.onClickItemEvent.setValue(url);
        }
    });


}

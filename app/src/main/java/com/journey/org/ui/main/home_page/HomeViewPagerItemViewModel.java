package com.journey.org.ui.main.home_page;

import android.support.annotation.NonNull;

import com.journey.org.data.home_page.HomePageBannerEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * ViewPger 的 item
 *
 * @author yu
 * @Date 2019/5/28
 */
public class HomeViewPagerItemViewModel extends ItemViewModel<HomePageViewModel> {
    // banner 数据类
    public HomePageBannerEntity dataBean;

    public HomeViewPagerItemViewModel(@NonNull HomePageViewModel viewModel, HomePageBannerEntity dataBean) {
        super(viewModel);
        this.dataBean = dataBean;
    }

    // item 的点击事件
    public BindingCommand onClickItem = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.onClickBannerItemEvent.setValue(dataBean);
        }
    });

}

package com.journey.org.ui.main.home_technology;

import android.support.annotation.NonNull;

import com.journey.org.data.home_page.HomePageBannerEntity;
import com.journey.org.ui.main.home_page.HomePageViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * ViewPger 的 item
 *
 * @author yu
 * @Date 2019/5/28
 */
public class TechnologyViewPagerItemViewModel extends ItemViewModel<HomeTechnologyViewModel> {
    // banner 数据类
    public HomePageBannerEntity dataBean;

    public TechnologyViewPagerItemViewModel(@NonNull HomeTechnologyViewModel viewModel, HomePageBannerEntity dataBean) {
        super(viewModel);
        this.dataBean = dataBean;
    }

    // item 的点击事件
    public BindingCommand onClickItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          //  viewModel.onClickBannerItemEvent.setValue(dataBean);
        }
    });

}

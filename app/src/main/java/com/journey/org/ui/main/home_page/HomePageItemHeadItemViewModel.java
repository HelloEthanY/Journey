package com.journey.org.ui.main.home_page;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.journey.org.ui.main.home_technology.page_chart.PageChartFragment;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HomePageItemHeadItemViewModel extends ItemViewModel<HomePageViewModel> {

    public String title;
    public Drawable drawable;

    public HomePageItemHeadItemViewModel(@NonNull HomePageViewModel viewModel, String title, Drawable drawable) {
        super(viewModel);
        this.title = title;
        this.drawable = drawable;
    }

    public BindingCommand onClickHeadItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (title) {
                case "攻略":
                    ToastUtils.showShort("攻略");
                    break;
                case "订酒店":
                    ToastUtils.showShort("订酒店");
                    break;
                case "美食":
                    ToastUtils.showShort("美食");
                    break;
                case "游记":
                    ToastUtils.showShort("游记");
                    break;
                case "购物":
                    ToastUtils.showShort("购物");
                    break;
                case "购票":
                    ToastUtils.showShort("购票");
                    break;
            }
        }
    });
}

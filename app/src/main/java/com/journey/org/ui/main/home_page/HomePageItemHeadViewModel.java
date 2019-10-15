package com.journey.org.ui.main.home_page;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.journey.org.BR;
import com.journey.org.R;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomePageItemHeadViewModel extends MultiItemViewModel<HomePageViewModel> {

    // RecycleView 的数据集合
    public ObservableList<HomePageItemHeadItemViewModel> items = new ObservableArrayList<>();
    // RecycleView 的 binding
    public ItemBinding<HomePageItemHeadItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_home_page_head_item);

    public HomePageItemHeadViewModel(@NonNull HomePageViewModel viewModel, Application application) {
        super(viewModel);
        loadData(viewModel, application);
    }

    private void loadData(HomePageViewModel viewModel, Application application) {
        items.add(new HomePageItemHeadItemViewModel(viewModel, "攻略", ContextCompat.getDrawable(application, R.mipmap.ic_app_defalt)));
        items.add(new HomePageItemHeadItemViewModel(viewModel, "订酒店", ContextCompat.getDrawable(application, R.mipmap.ic_home_hotel)));
        items.add(new HomePageItemHeadItemViewModel(viewModel, "美食", ContextCompat.getDrawable(application, R.mipmap.ic_home_foot)));
        items.add(new HomePageItemHeadItemViewModel(viewModel, "游记", ContextCompat.getDrawable(application, R.mipmap.ic_home_travel)));
        items.add(new HomePageItemHeadItemViewModel(viewModel, "购物", ContextCompat.getDrawable(application, R.mipmap.ic_home_shop)));
        items.add(new HomePageItemHeadItemViewModel(viewModel, "购票", ContextCompat.getDrawable(application, R.mipmap.ic_home_tickets)));
    }
}

package com.journey.org.ui.main.home_technology;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.journey.org.BR;
import com.journey.org.R;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 技术首页头部item
 *
 * @author 逍遥
 * @Date 2019/10/11
 */
public class HomeTechnologyItemHeadViewModel extends MultiItemViewModel<HomeTechnologyViewModel> {

    // RecycleView 的数据集合
    public ObservableList<HomeTechnologyItemHeadItemViewModel> items = new ObservableArrayList<>();
    // RecycleView 的 binding
    public ItemBinding<HomeTechnologyItemHeadItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_home_technology_head_item);

    public HomeTechnologyItemHeadViewModel(@NonNull HomeTechnologyViewModel viewModel, Application application) {
        super(viewModel);
        loadData(viewModel, application);
    }

    private void loadData(HomeTechnologyViewModel viewModel, Application application) {
        items.add(new HomeTechnologyItemHeadItemViewModel(viewModel, "图表", ContextCompat.getDrawable(application, R.mipmap.ic_home_chart)));
    }

}

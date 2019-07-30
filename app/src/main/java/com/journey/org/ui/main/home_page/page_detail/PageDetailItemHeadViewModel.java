package com.journey.org.ui.main.home_page.page_detail;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 景区详情头布局
 *
 * @author 逍遥
 * @Date 2019/7/30
 */
public class PageDetailItemHeadViewModel extends MultiItemViewModel<PageDetailViewModel> {

    public PageDetailItemHeadViewModel(@NonNull PageDetailViewModel viewModel) {
        super(viewModel);
        loadData();
    }

    // 播发视频
    public BindingCommand onStartBroadcastCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.onStartBroadcastEvent.call();
        }
    });
    /*****************************************视频列表*********************************************/
    public ItemBinding<HeadVideoItemViewModel> videoItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_page_head_video);

    public ObservableList<HeadVideoItemViewModel> videoItems = new ObservableArrayList<>();


    /*****************************************景区信息*********************************************/
    public ItemBinding<HeadInfoItemViewModel> infoItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_page_head_info);

    public ObservableList<HeadInfoItemViewModel> infoItems = new ObservableArrayList<>();

    // 加载数据
    public void loadData() {
        // 视频数据
        for (int i = 0; i < 4; i++) {
            videoItems.add(new HeadVideoItemViewModel(viewModel));
        }
        // 景区信息数据
        for (int i = 0; i < 10; i++) {
            infoItems.add(new HeadInfoItemViewModel(viewModel));
        }
    }
}

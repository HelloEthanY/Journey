package com.journey.org.ui.main.home_page.page_detail;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseToolbarViewModel;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * 首页详情
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class PageDetailViewModel extends BaseToolbarViewModel {

    public static final String DETAIL_HEAD = "detail_head";
    public static final String DETAIL_BODY = "detail_body";

    public SingleLiveEvent<Void> onStartBroadcastEvent = new SingleLiveEvent<>();

    public PageDetailViewModel(@NonNull Application application) {
        super(application);
    }

    // 初始化 toolbar
    public void initToolbar(String name) {
        setToolbarTitle(name);
        setVisibleLeft(true);
        setVisibleMenu(true);
    }

    /***********************************************************************************************/
    public ItemBinding<MultiItemViewModel> itemBinding = ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MultiItemViewModel item) {
            String itemType = (String) item.getItemType();
            if (DETAIL_HEAD.equals(itemType)) { // 头布局
                itemBinding.set(BR.viewModel, R.layout.item_page_detail_head);
            } else { // 体布局
                itemBinding.set(BR.viewModel, R.layout.item_page_detail_body);
            }
        }
    });

    public ObservableList<MultiItemViewModel> items = new ObservableArrayList<>();

    // 加载数据
    public void loadData() {
        MultiItemViewModel itemViewModel = new PageDetailItemHeadViewModel(this);
        itemViewModel.multiItemType(DETAIL_HEAD);
        items.add(itemViewModel);

        for (int i = 0; i < 15; i++) {
            MultiItemViewModel itemViewModel1 = new PageDetailItemBodyViewModel(this);
            itemViewModel1.multiItemType(DETAIL_BODY);
            items.add(itemViewModel1);
        }
    }
}

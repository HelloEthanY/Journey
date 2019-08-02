package com.journey.org.ui.custom.popup.page_detail;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.data.home_page.PageDetailMenuEntity;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 首页详情之菜单
 *
 * @author yu
 * @Date 2019/6/19
 */
public class PageDetailPopupWindowViewModel extends BaseViewModel {

    public SingleLiveEvent<PageDetailMenuEntity> onClickItemEvent = new SingleLiveEvent<>();

    public PageDetailPopupWindowViewModel(@NonNull Application application) {
        super(application);
        loadStateData(application);
    }

    public ObservableList<PageDetailPopupWindowItemViewModel> items = new ObservableArrayList<>();

    public ItemBinding<PageDetailPopupWindowItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_page_detail_popup);

    public void loadStateData(Application application) {
        items.clear();
        PageDetailMenuEntity entity = new PageDetailMenuEntity();
        entity.setDrawable(ContextCompat.getDrawable(application, R.mipmap.ic_tickets));
        entity.setTitle("门票");
        items.add(new PageDetailPopupWindowItemViewModel(this, entity));
        PageDetailMenuEntity entity1 = new PageDetailMenuEntity();
        entity1.setDrawable(ContextCompat.getDrawable(application, R.mipmap.ic_collection));
        entity1.setTitle("收藏");
        items.add(new PageDetailPopupWindowItemViewModel(this, entity1));
        PageDetailMenuEntity entity2 = new PageDetailMenuEntity();
        entity2.setDrawable(ContextCompat.getDrawable(application, R.mipmap.ic_share));
        entity2.setTitle("分享");
        items.add(new PageDetailPopupWindowItemViewModel(this, entity2));
    }
}

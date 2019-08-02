package com.journey.org.ui.main.home_page.page_detail;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.data.home_page.PageDetailBodyEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 景区详情体布局
 *
 * @author 逍遥
 * @Date 2019/7/30
 */
public class PageDetailItemBodyViewModel extends MultiItemViewModel<PageDetailViewModel> {

    public PageDetailBodyEntity entity;

    public PageDetailItemBodyViewModel(@NonNull PageDetailViewModel viewModel, PageDetailBodyEntity entity) {
        super(viewModel);
        this.entity = entity;
        loadData(entity);
    }

    public ItemBinding<BodyInfoItemViewModel> infoItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_page_body_info);

    public ObservableList<BodyInfoItemViewModel> infoItems = new ObservableArrayList<>();

    // 加载数据
    public void loadData(PageDetailBodyEntity entity) {
        for (PageDetailBodyEntity.BodyBean bean : entity.getBodyBeans()) {
            infoItems.add(new BodyInfoItemViewModel(viewModel, bean));
        }
    }

}

package com.journey.org.ui.big_image;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseToolbarViewModel;
import com.journey.org.data.home_page.PagePhotoEntity;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 查看大图
 *
 * @author 逍遥
 * @Date 2019/8/5
 */
public class BigImageViewModel extends BaseToolbarViewModel {

    public BigImageViewModel(@NonNull Application application) {
        super(application);
    }

    public void initToolbar() {
        onShowMenu.set(true);
    }

    public void setToolbarTitle(PagePhotoEntity.FeedListBean entity) {
        if ("video".equals(entity.getType())) {
            toolbarViewModel.setToolbarTitle(entity.getEntry().getAuthor().getDescription());
        } else {
            toolbarViewModel.setToolbarTitle(entity.getEntry().getTitle());
        }
    }

    public ItemBinding<BigImageItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_big_image);

    public ObservableList<BigImageItemViewModel> items = new ObservableArrayList<>();

    // 图片地址
    public ObservableField<String> bigImageUrl = new ObservableField<>();

    public void requestBigImageData(PagePhotoEntity pagePhotoEntity) {
        for (PagePhotoEntity.FeedListBean photoEntity : pagePhotoEntity.getFeedList()) {
            items.add(new BigImageItemViewModel(this, photoEntity));
        }
    }
}

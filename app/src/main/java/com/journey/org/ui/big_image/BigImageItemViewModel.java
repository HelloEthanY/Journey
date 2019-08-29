package com.journey.org.ui.big_image;

import android.support.annotation.NonNull;

import com.journey.org.data.home_page.PagePhotoEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class BigImageItemViewModel extends ItemViewModel<BigImageViewModel> {

    public PagePhotoEntity.FeedListBean photoEntity;

    public String imageUrl;

    public BigImageItemViewModel(@NonNull BigImageViewModel viewModel, PagePhotoEntity.FeedListBean photoEntity) {
        super(viewModel);
        this.photoEntity = photoEntity;
        if ("video".equals(photoEntity.getType())) {
            imageUrl = photoEntity.getEntry().getCover();
        } else {
            imageUrl = photoEntity.getEntry().getTitle_image().getUrl();
        }
    }
}

package com.journey.org.ui.main.home_page.page_photo;

import android.support.annotation.NonNull;

import com.journey.org.data.home_page.PagePhotoEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 景区相册
 *
 * @author 逍遥
 * @Date 2019/8/5
 */
public class PagePhotoItemViewModel extends ItemViewModel<PagePhotoViewModel> {

    public PagePhotoEntity.FeedListBean entity;

    public String url;
    public String title;

    public PagePhotoItemViewModel(@NonNull PagePhotoViewModel viewModel, PagePhotoEntity.FeedListBean entity) {
        super(viewModel);
        this.entity = entity;
        if ("video".equals(entity.getType())) {
            url = entity.getEntry().getCover();
            title = entity.getEntry().getAuthor().getDescription();
        } else {
            url = entity.getEntry().getTitle_image().getUrl();
            title = entity.getEntry().getTitle();
        }
    }

    // item的点击事件
    public BindingCommand onClickItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.onClickItemEvent.setValue(entity);
        }
    });
}

package com.journey.org.ui.main.home_page.page_photo;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseToolbarViewModel;
import com.journey.org.app.manager.HttpManager;
import com.journey.org.data.home_page.PagePhotoEntity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 景区相册
 *
 * @author 逍遥
 * @Date 2019/8/5
 */
public class PagePhotoViewModel extends BaseToolbarViewModel {
    // 页数
    private int page = 1;

    public PagePhotoEntity photoEntity;

    public PagePhotoViewModel(@NonNull Application application) {
        super(application);
    }

    // 初始化toolbar
    public void initToolbar(String title) {
        toolbarTitle.set(title);
        onShowMenu.set(false);
    }

    public ItemBinding<PagePhotoItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_page_photo);

    public ObservableList<PagePhotoItemViewModel> items = new ObservableArrayList<>();

    public SingleLiveEvent<PagePhotoEntity.FeedListBean> onClickItemEvent = new SingleLiveEvent<>();

    // 加载图片
    public void loadPhotoList(boolean refresh) {
        if (refresh) {
            page = 1;
            items.clear();
        } else {
            ++page;
        }
        HttpManager.getInstance().getPagePhotoList(page)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (page == 1) {
                            showDialog();
                        }
                    }
                })
                .subscribe(new Consumer<PagePhotoEntity>() {
                    @Override
                    public void accept(PagePhotoEntity pagePhotoEntity) throws Exception {
                        if (page == 1) {
                            dismissDialog();
                        }
                        if (pagePhotoEntity == null) {
                            return;
                        }
                        if (page == 1) {
                            PagePhotoViewModel.this.photoEntity = pagePhotoEntity;
                        } else {
                            photoEntity.getFeedList().addAll(pagePhotoEntity.getFeedList());
                        }
                        for (PagePhotoEntity.FeedListBean entity : pagePhotoEntity.getFeedList()) {
                            items.add(new PagePhotoItemViewModel(PagePhotoViewModel.this, entity));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (page == 1) {
                            dismissDialog();
                        }
                    }
                });
    }
}

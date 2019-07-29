package com.journey.org.ui.main.home_page;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BasePlayAndroidEntity;
import com.journey.org.app.manager.HttpManager;
import com.journey.org.data.home_page.HomePageBannerEntity;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 旅游系统 - 首页
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class HomePageViewModel extends BaseViewModel {
    // banner item 的点击事件
    public SingleLiveEvent<HomePageBannerEntity> onClickBannerItemEvent = new SingleLiveEvent<>();
    // item 的点击事件
    public SingleLiveEvent<String> onClickItemEvent = new SingleLiveEvent<>();

    public HomePageViewModel(@NonNull Application application) {
        super(application);
        // 初始化RecycleView 的数据
        initItemData(application);
    }

    // ViewPager 的数据集合
    public ObservableList<HomeViewPagerItemViewModel> viewPagerList = new ObservableArrayList<>();
    // ViewPager 的 binding
    public ItemBinding<HomeViewPagerItemViewModel> pagerItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_home_page_view_pager);

    // RecycleView
    public ObservableList<HomePageItemViewModel> items = new ObservableArrayList<>();
    public ItemBinding<HomePageItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_home_page);

    // 初始化RecycleView 的数据
    private void initItemData(Application application) {
        HomePageItemViewModel hotelViewModel = new HomePageItemViewModel(this, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=27495044,3539569886&fm=26&gp=0.jpg",
                ContextCompat.getDrawable(application, R.mipmap.ic_marker_hotel));
        items.add(hotelViewModel);

        HomePageItemViewModel groceryViewModel = new HomePageItemViewModel(this, "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1981636719,1972835883&fm=26&gp=0.jpg",
                ContextCompat.getDrawable(application, R.mipmap.ic_marker_grocery));
        items.add(groceryViewModel);

        HomePageItemViewModel snacksViewModel = new HomePageItemViewModel(this, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3563365738,1288177324&fm=26&gp=0.jpg",
                ContextCompat.getDrawable(application, R.mipmap.ic_marker_snacks));
        items.add(snacksViewModel);

        HomePageItemViewModel storeViewModel = new HomePageItemViewModel(this, "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1560594871,3464168313&fm=26&gp=0.jpg",
                ContextCompat.getDrawable(application, R.mipmap.ic_marker_store));
        items.add(storeViewModel);
    }

    // 选择城市
    public BindingCommand onClickSelectCity = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("点击选择城市");
        }
    });

    // 点击扫描二维码
    public BindingCommand onClickQR = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("点击扫描二维码");
        }
    });

    // 点击搜索框
    public BindingCommand onClickSearch = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("点击搜索框");
        }
    });
    // 得到banner 的数据
    public void requestBannerData() {
        HttpManager.getInstance()
                .getHomePageBanner()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<BasePlayAndroidEntity<List<HomePageBannerEntity>>>() {
                    @Override
                    public void accept(BasePlayAndroidEntity<List<HomePageBannerEntity>> playAndroidBannerEntity) throws Exception {
                        dismissDialog();
                        if (playAndroidBannerEntity.getErrorCode() == 0) {
                            for (HomePageBannerEntity dataBean : playAndroidBannerEntity.getData()) {
                                HomeViewPagerItemViewModel viewPagerViewModel = new HomeViewPagerItemViewModel(
                                        HomePageViewModel.this, dataBean);
                                viewPagerList.add(viewPagerViewModel);
                            }
                        } else {
                            ToastUtils.showShort(playAndroidBannerEntity.getErrorMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ToastUtils.showShort("请求异常！");
                    }
                });
    }
}

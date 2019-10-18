package com.journey.org.ui.main.home_technology;

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
import com.journey.org.ui.main.home_page.HomePageViewModel;
import com.journey.org.ui.main.home_page.HomeViewPagerItemViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class HomeTechnologyViewModel extends BaseViewModel {

    // 头布局类型
    public static final String TECHNOLOGY_HEAD = "technology_head";
    // 体布局类型
    public static final String TECHNOLOGY_BODY = "technology_body";

    public HomeTechnologyViewModel(@NonNull Application application) {
        super(application);
        initItemData(application);
    }

    public SingleLiveEvent<String> onBodyItemClick = new SingleLiveEvent<>();

    /***************************************顶部viewPager******************************************/
    // ViewPager 的数据集合
    public ObservableList<TechnologyViewPagerItemViewModel> viewPagerList = new ObservableArrayList<>();
    // ViewPager 的 binding
    public ItemBinding<TechnologyViewPagerItemViewModel> pagerItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_home_technology_view_pager);

    /***************************************RecycleView******************************************/
    public ObservableList<MultiItemViewModel> items = new ObservableArrayList<>();

    public ItemBinding<MultiItemViewModel> itemBinding = ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MultiItemViewModel item) {
            String itemType = (String) item.getItemType();
            if (TECHNOLOGY_HEAD.equals(itemType)) {// 头布局
                itemBinding.set(BR.viewModel, R.layout.item_home_technology_head);
            } else {
                itemBinding.set(BR.viewModel, R.layout.item_home_technology);
            }
        }
    });

    // 初始化RecycleView 的数据
    private void initItemData(Application application) {
        // 添加头数据
        MultiItemViewModel headerViewModel = new HomeTechnologyItemHeadViewModel(this, application);
        headerViewModel.multiItemType(TECHNOLOGY_HEAD);
        items.add(headerViewModel);
        // 首页体布局数据
        MultiItemViewModel hotelViewModel = new HomeTechnologyItemViewModel(this,
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=27495044,3539569886&fm=26&gp=0.jpg",
                "信鸽三维地图",
                "测试使用webView加载html和Android的skyline共同使用开发");
        hotelViewModel.multiItemType(TECHNOLOGY_BODY);
        items.add(hotelViewModel);

    }

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
                                TechnologyViewPagerItemViewModel viewPagerViewModel = new TechnologyViewPagerItemViewModel(
                                        HomeTechnologyViewModel.this, dataBean);
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

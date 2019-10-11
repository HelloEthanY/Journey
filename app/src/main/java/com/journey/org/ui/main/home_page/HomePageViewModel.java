package com.journey.org.ui.main.home_page;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
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
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * 旅游系统 - 首页
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class HomePageViewModel extends BaseViewModel {
    // 头布局类型
    public static final String HOME_HEAD = "home_head";
    // 体布局类型
    public static final String HOME_BODY = "home_body";

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

    /***************************************多列表布局**************************************************/
    public ObservableList<MultiItemViewModel> items = new ObservableArrayList<>();

    public ItemBinding<MultiItemViewModel> itemBinding = ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MultiItemViewModel item) {
            String itemType = (String) item.getItemType();
            KLog.e("=======itemType:" + itemType);
            if (HOME_HEAD.equals(itemType)) {// 头布局
                itemBinding.set(BR.viewModel, R.layout.item_home_page_head);
            } else {
                itemBinding.set(BR.viewModel, R.layout.item_home_page);
            }
        }
    });


    // 初始化RecycleView 的数据
    private void initItemData(Application application) {
        // 添加头数据
        MultiItemViewModel headerViewModel = new HomePageItemHeadViewModel(this,application);
        headerViewModel.multiItemType(HOME_HEAD);
        items.add(headerViewModel);
        // 首页体布局数据
        MultiItemViewModel hotelViewModel = new HomePageItemViewModel(this, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=27495044,3539569886&fm=26&gp=0.jpg",
                ContextCompat.getDrawable(application, R.mipmap.ic_marker_hotel));
        hotelViewModel.multiItemType(HOME_BODY);
        items.add(hotelViewModel);

        MultiItemViewModel groceryViewModel = new HomePageItemViewModel(this, "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1981636719,1972835883&fm=26&gp=0.jpg",
                ContextCompat.getDrawable(application, R.mipmap.ic_marker_grocery));
        items.add(groceryViewModel);

        MultiItemViewModel snacksViewModel = new HomePageItemViewModel(this, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3563365738,1288177324&fm=26&gp=0.jpg",
                ContextCompat.getDrawable(application, R.mipmap.ic_marker_snacks));
        snacksViewModel.multiItemType(HOME_BODY);
        items.add(snacksViewModel);

        MultiItemViewModel storeViewModel = new HomePageItemViewModel(this, "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1560594871,3464168313&fm=26&gp=0.jpg",
                ContextCompat.getDrawable(application, R.mipmap.ic_marker_store));
        storeViewModel.multiItemType(HOME_BODY);
        items.add(storeViewModel);
    }

    // 选择城市
    public ObservableField<String> cityNameField = new ObservableField<>("贵阳");
    public SingleLiveEvent<Void> onClickSelectCityEvent = new SingleLiveEvent<>();
    public BindingCommand onClickSelectCityCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("点击选择城市");
            onClickSelectCityEvent.call();
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

    // 请求得到景区数据
    public void requestScenicData() {

    }

}

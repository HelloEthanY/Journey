package com.journey.org.ui.main.home_travel.foot;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.ui.custom.SpinnerItemData;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 美食
 *
 * @author 逍遥
 * @Date 2019/10/15
 */
public class FootViewModel extends BaseViewModel {

    public List<IKeyAndValue> typeItemData = new ArrayList<>();
    public List<IKeyAndValue> distanceItemData = new ArrayList<>();
    public List<IKeyAndValue> sortingItemData = new ArrayList<>();

    //选择距离
    public String selectDistanceNum = "";
    //选择排序
    public String selectSortingNum = "";
    //选择类型
    public String selectTypeNum = "";

    public FootViewModel(@NonNull Application application) {
        super(application);
        initIKeyValue();
    }

    private void initIKeyValue() {
        typeItemData.add(new SpinnerItemData("全部", ""));
        typeItemData.add(new SpinnerItemData("火锅", ""));
        typeItemData.add(new SpinnerItemData("自助餐", ""));
        typeItemData.add(new SpinnerItemData("小吃快餐", ""));
        typeItemData.add(new SpinnerItemData("西餐", ""));
        typeItemData.add(new SpinnerItemData("川湘菜", ""));
        typeItemData.add(new SpinnerItemData("江浙菜", ""));
        distanceItemData.add(new SpinnerItemData("5千米", ""));
        distanceItemData.add(new SpinnerItemData("3千米", ""));
        distanceItemData.add(new SpinnerItemData("1千米", ""));
        distanceItemData.add(new SpinnerItemData("5百米", ""));
        sortingItemData.add(new SpinnerItemData("智能排序", ""));
        sortingItemData.add(new SpinnerItemData("离我最近", ""));
        sortingItemData.add(new SpinnerItemData("好评优先", ""));
    }

    // 距离选择
    public BindingCommand<IKeyAndValue> onDistanceSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            selectDistanceNum = iKeyAndValue.getValue();
        }
    });

    // 排序选择
    public BindingCommand<IKeyAndValue> onSortingSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            selectSortingNum = iKeyAndValue.getValue();
        /*    pageNo = 1;
            isRefreshing = false;
            searchContentObservable.set("");
            requestComList();*/
        }
    });

    // 类型选择
    public BindingCommand<IKeyAndValue> onTypeSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            selectTypeNum = iKeyAndValue.getValue();
        }
    });

    public ObservableList<FootItemViewModel> items = new ObservableArrayList<>();

    public ItemBinding<FootItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_travel_foot);

    public void requestData() {
        for (int i = 0; i < 6; i++) {
            items.add(new FootItemViewModel(this, i));
        }
    }
}

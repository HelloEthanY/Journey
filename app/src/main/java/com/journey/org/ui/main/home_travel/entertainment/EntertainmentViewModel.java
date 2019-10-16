package com.journey.org.ui.main.home_travel.entertainment;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 鱼类
 *
 * @author 逍遥
 * @Date 2019/10/15
 */
public class EntertainmentViewModel extends BaseViewModel {

    public EntertainmentViewModel(@NonNull Application application) {
        super(application);
    }

    public ItemBinding<EntertainmentItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_travel_entertainment);

    public ObservableList<EntertainmentItemViewModel> items = new ObservableArrayList<>();

    public void requesst() {
        for (int i = 0; i < 10; i++) {
            items.add(new EntertainmentItemViewModel(this, i));
        }
    }
}

package com.journey.org.ui.main.home_travel.entertainment;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.ui.main.home_travel.TravelItemImageViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 娱乐
 *
 * @author 逍遥
 * @Date 2019/10/16
 */
public class EntertainmentItemViewModel extends ItemViewModel<EntertainmentViewModel> {

    public int index;

    public EntertainmentItemViewModel(@NonNull EntertainmentViewModel viewModel, int index) {
        super(viewModel);
        this.index = index;
        initImageList(index);
    }

    private void initImageList(int index) {
        if (index % 2 == 0) {
            items.add(new TravelItemImageViewModel(viewModel));
            items.add(new TravelItemImageViewModel(viewModel));
            items.add(new TravelItemImageViewModel(viewModel));
        } else {
            items.add(new TravelItemImageViewModel(viewModel));
            items.add(new TravelItemImageViewModel(viewModel));
            items.add(new TravelItemImageViewModel(viewModel));
            items.add(new TravelItemImageViewModel(viewModel));
        }
    }

    public ObservableList<TravelItemImageViewModel> items = new ObservableArrayList<>();

    public ItemBinding<TravelItemImageViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_travel_image_list);
}

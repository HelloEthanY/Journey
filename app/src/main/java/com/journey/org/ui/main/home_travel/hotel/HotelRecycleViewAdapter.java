package com.journey.org.ui.main.home_travel.hotel;

import android.databinding.ViewDataBinding;

import com.journey.org.databinding.ItemTravelHotelBinding;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class HotelRecycleViewAdapter extends BindingRecyclerViewAdapter<HotelItemViewModel> {
    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, HotelItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        ItemTravelHotelBinding _binding = (ItemTravelHotelBinding) binding;


    }
}

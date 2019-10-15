package com.journey.org.ui.main.home_travel.hotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.R;
import com.journey.org.BR;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.databinding.FragmentTravelHotelBinding;

/**
 * 酒店
 *
 * @author 逍遥
 * @Date 2019/10/15
 */
public class HotelFragment extends BaseLazyFragment<FragmentTravelHotelBinding, HotelViewModel> {
    @Override
    protected void lazyLoadData() {
        binding.setAdapter(new HotelRecycleViewAdapter());
        viewModel.requestData();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_travel_hotel;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}

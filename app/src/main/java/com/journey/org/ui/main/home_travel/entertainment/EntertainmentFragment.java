package com.journey.org.ui.main.home_travel.entertainment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.databinding.FragmentTravelEntertainmentBinding;

/**
 * 娱乐
 * @author 逍遥
 * @Date 2019/10/15
 *
 */
public class EntertainmentFragment extends BaseLazyFragment<FragmentTravelEntertainmentBinding,EntertainmentViewModel> {
    @Override
    protected void lazyLoadData() {
        
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_travel_entertainment;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}

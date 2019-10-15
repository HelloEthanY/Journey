package com.journey.org.ui.main.home_travel.foot;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.databinding.FragmentTravelFootBinding;

/**
 * 美食
 *
 * @author 逍遥
 * @Date 2019/10/15
 */
public class FootFragment extends BaseLazyFragment<FragmentTravelFootBinding, FootViewModel> {

    @Override
    protected void lazyLoadData() {
        binding.setAdapter(new FootRecycleViewAdapter());
        viewModel.requestData();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_travel_foot;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

    }
}

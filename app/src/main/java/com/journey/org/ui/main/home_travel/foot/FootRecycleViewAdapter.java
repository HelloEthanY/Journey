package com.journey.org.ui.main.home_travel.foot;

import android.databinding.ViewDataBinding;
import android.text.Html;

import com.journey.org.databinding.ItemTravelFootBinding;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class FootRecycleViewAdapter extends BindingRecyclerViewAdapter<FootItemViewModel> {
    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, FootItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        ItemTravelFootBinding _binding = (ItemTravelFootBinding) binding;
        if (item.index % 2 == 0) {
            _binding.footDetail.setText("该美食城简介：主要有那些店铺卖那些美食，主要有那些店铺卖那些美食，主要有那些店铺卖那些美食，主要有那些店铺卖那些美食");
        } else {
            _binding.footDetail.setText(Html.fromHtml("3.5km   西式快餐<br/>人均价<font color=#FF0000>￥29</font>"));
        }
    }
}

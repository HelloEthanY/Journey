package com.journey.org.ui.main.home_travel.entertainment;

import android.databinding.ViewDataBinding;
import android.text.Html;

import com.journey.org.databinding.ItemTravelEntertainmentBinding;
import com.journey.org.databinding.ItemTravelFootBinding;
import com.journey.org.ui.main.home_travel.foot.FootItemViewModel;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class EntertainmentRecycleViewAdapter extends BindingRecyclerViewAdapter<EntertainmentItemViewModel> {
    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, EntertainmentItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        ItemTravelEntertainmentBinding _binding = (ItemTravelEntertainmentBinding) binding;
        _binding.enterDetail.setText(Html.fromHtml("253条评论     <font color=#00cc33>2.6公里</font>"));
        _binding.enterPrice.setText(Html.fromHtml("<font color=#FF0000>￥299</font>起"));
        if (item.index % 2 == 0) {
            _binding.enterType.setText("KTV");
        } else {
            _binding.enterType.setText("酒吧");
        }
    }
}

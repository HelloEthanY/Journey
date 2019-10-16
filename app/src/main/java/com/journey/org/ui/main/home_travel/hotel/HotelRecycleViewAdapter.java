package com.journey.org.ui.main.home_travel.hotel;

import android.databinding.ViewDataBinding;
import android.text.Html;

import com.journey.org.databinding.ItemTravelHotelBinding;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class HotelRecycleViewAdapter extends BindingRecyclerViewAdapter<HotelItemViewModel> {
    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, HotelItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        ItemTravelHotelBinding _binding = (ItemTravelHotelBinding) binding;
        _binding.hotelPrice.setText(Html.fromHtml("<font color=#FF0000>￥142</font>起"));
        _binding.hotelIntroduce.setText(Html.fromHtml("<font color=#00cc33>地理位置好 | 房间很干净 | 出行方便</font>"));
        _binding.hotelComment.setText(Html.fromHtml("<font color=#00cc33>4.7分 | </font>355条评论|五星级"));
    }
}

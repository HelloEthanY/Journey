package com.journey.org.ui.big_image;

import android.databinding.ViewDataBinding;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.journey.org.data.home_page.PagePhotoEntity;
import com.journey.org.databinding.ItemBigImageBinding;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class BigImageRecycleViewAdapter extends BindingRecyclerViewAdapter<BigImageItemViewModel> {
    // 定位
    private PagePhotoEntity.FeedListBean feedListBean;

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, BigImageItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        this.feedListBean = item.photoEntity;
        ItemBigImageBinding _binding = (ItemBigImageBinding) binding;
        // 初始化photoView控件
        initPhotoView(_binding);
    }

    public PagePhotoEntity.FeedListBean getFeedListBean() {
        return feedListBean;
    }

    // 初始化photoView控件
    private void initPhotoView(ItemBigImageBinding _binding) {
        // 启用图片缩放功能
        _binding.ivBig.enable();
        // 获取/设置 最大缩放倍数
        _binding.ivBig.setMaxScale(50);
        // 设置动画的插入器
        _binding.ivBig.setInterpolator(new DecelerateInterpolator());
        // 获取/设置 动画持续时间
        _binding.ivBig.setAnimaDuring(1000);
    }
}

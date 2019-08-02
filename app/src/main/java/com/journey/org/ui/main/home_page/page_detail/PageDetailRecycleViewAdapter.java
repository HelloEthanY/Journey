package com.journey.org.ui.main.home_page.page_detail;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.journey.org.databinding.ItemPageDetailHeadBinding;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.utils.KLog;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

/**
 * 景区详情adapter
 * 视频播发地址 ： https://cdn.yihtc.com/jiuzhai/video/sangji-niao.mp4
 *
 * @author 逍遥
 * @Date 2019/7/31
 */
public class PageDetailRecycleViewAdapter extends BindingRecyclerViewAdapter<MultiItemViewModel> {
    // 上下文
    public Context context;

    private ItemPageDetailHeadBinding _headBinding;

    private Activity activity;

    // 构造函数
    public PageDetailRecycleViewAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, MultiItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        String itemType = (String) item.getItemType();
        if ("detail_head".equals(itemType)) {
            KLog.e("=============重新初始化了");
            // item_page_detail_head
            _headBinding = (ItemPageDetailHeadBinding) binding;

        } else {

        }
    }

    // 监听是否可见
    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder.getAdapterPosition() == 0) {

        }
    }

    // 重新初始化
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

    }
}

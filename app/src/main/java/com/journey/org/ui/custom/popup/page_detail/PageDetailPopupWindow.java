package com.journey.org.ui.custom.popup.page_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

import com.journey.org.R;
import com.journey.org.app.inf.OnItemClickListener;
import com.journey.org.data.home_page.PageDetailMenuEntity;
import com.journey.org.databinding.PopupPageDetailBinding;

import java.util.List;

/**
 * 首页详情之菜单
 *
 * @author yu
 * @Date 2019/6/19
 */
public class PageDetailPopupWindow extends PopupWindow {

    private FragmentActivity activity;
    private Fragment fragment;
    private PageDetailPopupWindowViewModel viewModel;
    private OnItemClickListener<PageDetailMenuEntity> mListener;

    public PageDetailPopupWindow(FragmentActivity activity, int width, int height, Fragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
        initView(width, height);
    }

    /**
     * 初始化PopupWindow
     */
    private void initView(int width, int height) {
        PopupPageDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity),
                R.layout.popup_page_detail, null, false);
        if (fragment != null) {
            viewModel = ViewModelProviders.of(fragment).get(PageDetailPopupWindowViewModel.class);
        } else {
            viewModel = ViewModelProviders.of(activity).get(PageDetailPopupWindowViewModel.class);
        }
        binding.setViewModel(viewModel);
        setContentView(binding.getRoot());
        setWidth(width);
        setHeight(height);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 是否响应外部点击事件
        setOutsideTouchable(false);
        setFocusable(true);
        // 设置popupWindow动画
        setAnimationStyle(R.style.popupWindowAnim);
        initViewObservable();
    }

    private void initViewObservable() {
        if (viewModel != null && fragment != null) {
            viewModel.onClickItemEvent.observe(fragment, new Observer<PageDetailMenuEntity>() {
                @Override
                public void onChanged(@Nullable PageDetailMenuEntity content) {
                    if (mListener != null) {
                        mListener.itemClick(content);
                        dismiss();
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener<PageDetailMenuEntity> listener) {
        mListener = listener;
    }
}

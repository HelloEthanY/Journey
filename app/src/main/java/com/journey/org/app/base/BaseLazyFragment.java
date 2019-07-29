package com.journey.org.app.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create Author：goldze
 * Create Date：2018/12/20
 * Description：Fragment懒加载
 */

public abstract class BaseLazyFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<V, VM> {
    //Fragment是否加载
    private boolean isFragmentLoad;
    //Fragment的根布局
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isFragmentLoad && getUserVisibleHint()) {
            lazyLoadData();
            isFragmentLoad = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        if (!isFragmentLoad && isVisibleToUser) {
            lazyLoadData();
            isFragmentLoad = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFragmentLoad = false;
        rootView = null;
    }

    /**
     * 懒加载数据，让子类实现
     */
    protected abstract void lazyLoadData();
}

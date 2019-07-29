package com.journey.org.ui.main.home_page;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.data.home_page.HomePageBannerEntity;
import com.journey.org.databinding.FragmentHomePageBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * 旅游系统 - 首页
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class HomePageFragment extends BaseLazyFragment<FragmentHomePageBinding, HomePageViewModel> {

    @Override
    protected void lazyLoadData() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home_page;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        // 请求banner 数据
        viewModel.requestBannerData();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        // 暂时禁止上拉加载更多
        binding.pageRefresh.setEnableLoadMore(false);
        // 下拉刷新
        binding.pageRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(800);
            }
        });

        // Item 的点击事件
        viewModel.onClickItemEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // startActivity(ScenicMapActivity.class);
            }
        });

        // banner 的item 的点击事件
        viewModel.onClickBannerItemEvent.observe(this, new Observer<HomePageBannerEntity>() {
            @Override
            public void onChanged(@Nullable HomePageBannerEntity homePageBannerEntity) {

            }
        });
    }


}

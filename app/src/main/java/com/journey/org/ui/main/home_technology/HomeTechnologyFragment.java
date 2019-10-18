package com.journey.org.ui.main.home_technology;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.app.skyline.SkylineContainerActivity;
import com.journey.org.databinding.FragmentHomeTechnologyBinding;
import com.journey.org.ui.main.home_technology.page_skyline.PageSkylineActivity;
import com.journey.org.ui.main.home_technology.page_skyline.PageSkylineFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import me.goldze.mvvmhabit.base.ContainerActivity;

/**
 * 开发Android 中将要用的技术列表
 *
 * @author 逍遥
 * @Date 2019/10/11
 */
public class HomeTechnologyFragment extends BaseLazyFragment<FragmentHomeTechnologyBinding, HomeTechnologyViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home_technology;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void lazyLoadData() {
        viewModel.requestBannerData();
        // 暂时禁止上拉加载更多
        binding.pageRefresh.setEnableLoadMore(false);
        // 下拉刷新
        binding.pageRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(800);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.onBodyItemClick.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
           /*     Intent intent = new Intent(getContext(), SkylineContainerActivity.class);
                intent.putExtra(ContainerActivity.FRAGMENT, PageSkylineFragment.class.getCanonicalName());
                startActivity(intent);*/
                startActivity(PageSkylineActivity.class);
            }
        });
    }
}

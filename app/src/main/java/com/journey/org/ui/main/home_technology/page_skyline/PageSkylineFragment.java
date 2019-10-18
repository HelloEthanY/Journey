package com.journey.org.ui.main.home_technology.page_skyline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.skyline.BaseSkylineFragment;
import com.journey.org.databinding.FragmentPageSkylineBinding;
import com.skyline.teapi.ApiException;
import com.skyline.terraexplorer.models.UI;

import me.goldze.mvvmhabit.utils.KLog;

public class PageSkylineFragment extends BaseSkylineFragment<FragmentPageSkylineBinding, PageSkylineViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_page_skyline;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onBeforeLoad() {
        KLog.e("初始化三维引擎...");
        binding.tvLoading.setText("初始化三维引擎...");
    }

    @Override
    protected void onLoading() {
        KLog.e("开始加载...");
        binding.tvLoading.setText("开始加载...");
    }

    @Override
    protected void onLoadFinish() {
        KLog.e("加载成功");
        binding.tvLoading.setVisibility(View.GONE);
        UI.runOnRenderThreadAsync(new Runnable() {
            @Override
            public void run() {
                helper.flyDefaultPoint();
            }
        });
    }

    @Override
    protected void onLoadFail(ApiException e) {
        KLog.e("加载失败");
        binding.tvLoading.setText("加载失败");
    }
}

package com.journey.org.ui.main.home_technology.page_chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseFragmentPagerAdapter;
import com.journey.org.databinding.FragmentPageChartBinding;
import com.journey.org.ui.main.home_technology.page_chart.custom_chart.CustomChartFragment;
import com.journey.org.ui.main.home_technology.page_chart.mpandroid_chart.MpAndroidFragment;
import com.journey.org.ui.main.home_technology.page_chart.web_chart.WebChartFragment;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 图表展示
 *
 * @author 逍遥
 * @Date 2019/10/10
 */
public class PageChartFragment extends BaseFragment<FragmentPageChartBinding, PageChartViewModel> {
    // fragment 集合
    private List<Fragment> fragmentList = new ArrayList<>();
    // 标题集合
    private List<String> titles = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_page_chart;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar("图表");
        getFragment();
        getTitle();
        initAdapter();
    }

    // 初始化 adapter
    private void initAdapter() {
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), fragmentList, titles);
        binding.chartViewpager.setAdapter(adapter);
        binding.chartTab.setupWithViewPager(binding.chartViewpager);
        binding.chartViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.chartTab));
    }

    // 初始化添加fragment
    private void getFragment() {
        fragmentList.clear();
        // MPAndroid 图表
        fragmentList.add(new MpAndroidFragment());
        // 自定义图表
        fragmentList.add(new CustomChartFragment());
        // 网页版eCharts图表
        fragmentList.add(new WebChartFragment());
    }

    // 初始化添加标题
    private void getTitle() {
        titles.clear();
        titles.add("MPAndroid图表");
        titles.add("自定义图表");
        titles.add("网页版eCharts图表");
    }
}

package com.journey.org.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.FragmentBasePagerBinding;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * 抽取的二级BasePagerFragment
 *
 * @author 逍遥
 * @Date 2019/10/14
 */
public abstract class BasePagerFragment extends BaseFragment<FragmentBasePagerBinding, BaseViewModel> {

    private List<Fragment> mFragments;
    private List<String> titlePager;

    protected abstract List<Fragment> pagerFragment();

    protected abstract List<String> pagerTitleString();

    protected abstract void initToolbarTitle();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_base_pager;
    }

    @Override
    public void initStatusBar() {
        super.initStatusBar();
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) binding.layoutStatusBar.getLayoutParams();
        layoutParams.height = SPUtils.getInstance().getInt("toolbarHeight");
        binding.layoutStatusBar.setLayoutParams(layoutParams);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        initToolbarTitle();
        mFragments = pagerFragment();
        titlePager = pagerTitleString();
        initAdapter();
    }

    // 初始化 adapter
    private void initAdapter() {
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), mFragments, titlePager);
        binding.baseViewpager.setAdapter(adapter);
        binding.baseTab.setupWithViewPager(binding.baseViewpager);
        binding.baseViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.baseTab));
    }
}

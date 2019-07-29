package com.journey.org.ui.main.home_page.page_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.FragmentPageDetailBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * 首页详情
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class PageDetailFragment extends BaseFragment<FragmentPageDetailBinding, PageDetailViewModel> {
    // 景区ID
    private String id;
    // 景区名称
    private String scenicName;

    @Override
    public void initStatusBar() {
        super.initStatusBar();
   /*     ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) binding.include.layoutStatusBar.getLayoutParams();
        layoutParams.height = SPUtils.getInstance().getInt("toolbarHeight");
        binding.include.layoutStatusBar.setLayoutParams(layoutParams);*/
    }

    @Override
    public void initParam() {
        super.initParam();
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        scenicName = bundle.getString("name", "");
        id = bundle.getString("id", "");
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_page_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar(scenicName);
    }
}

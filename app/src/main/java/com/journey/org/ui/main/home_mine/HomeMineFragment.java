package com.journey.org.ui.main.home_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.databinding.FragmentHomeMineBinding;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * 我的
 *
 * @author yu
 * @Date 2019/5/24
 */
public class HomeMineFragment extends BaseLazyFragment<FragmentHomeMineBinding, HomeMineViewModel> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void lazyLoadData() {


    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home_mine;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initStatusBar() {
        super.initStatusBar();
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) binding.include.layoutStatusBar.getLayoutParams();
        layoutParams.height = SPUtils.getInstance().getInt("toolbarHeight");
        binding.include.layoutStatusBar.setLayoutParams(layoutParams);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar(getContext());
    }
}

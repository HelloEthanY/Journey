package com.journey.org.ui.main.home_page.page_map;

import android.os.Bundle;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.ActivityPageMapBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 景区地图
 *
 * @author 逍遥
 * @Date 2019/8/2
 */
public class PageMapActivity extends BaseActivity<ActivityPageMapBinding, PageMapViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_page_map;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}

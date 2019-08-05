package com.journey.org.ui.big_image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.FragmentBigImageBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 查看大图
 *
 * @author 逍遥
 * @Date 2019/8/5
 */
public class BigImageFragment extends BaseFragment<FragmentBigImageBinding, BigImageViewModel> {
    // 图片地址
    private String imageUrl;
    // 标题
    private String title;

    @Override
    public void initParam() {
        super.initParam();
        Bundle bundle = getArguments();
        if (bundle != null) {
            imageUrl = bundle.getString("imageUrl", "");
            title = bundle.getString("title", "");
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_big_image;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        if (title != null) {
            // 初始化 toolbar
            viewModel.initToolbar(title);
        }
        // 设置图片地址
        viewModel.bigImageUrl.set(imageUrl);
    }
}

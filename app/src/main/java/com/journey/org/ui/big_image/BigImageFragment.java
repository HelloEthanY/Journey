package com.journey.org.ui.big_image;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.utils.ShareUtil;
import com.journey.org.databinding.FragmentBigImageBinding;

import me.goldze.mvvmhabit.base.BaseFragment;

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
        // 初始化photoView控件
        initPhotoView();
    }

    // 初始化photoView控件
    private void initPhotoView() {
        // 启用图片缩放功能
        binding.ivBig.enable();
        // 获取/设置 最大缩放倍数
        binding.ivBig.setMaxScale(50);
        // 设置动画的插入器
        //photoView.setInterpolator(Interpolator interpolator);
        // 获取/设置 动画持续时间
        // photoView.setAnimaDuring(int during);

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        // toolbar 的menu点击事件
        viewModel.onClickMenuEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void o) {
                ShareUtil.shareText(getActivity(), title, "", imageUrl);
            }
        });
    }
}

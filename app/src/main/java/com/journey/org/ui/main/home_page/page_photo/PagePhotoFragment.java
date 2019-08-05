package com.journey.org.ui.main.home_page.page_photo;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.data.home_page.PagePhotoEntity;
import com.journey.org.databinding.FragmentPagePhotoBinding;
import com.journey.org.ui.big_image.BigImageFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 景区相册
 *
 * @author 逍遥
 * @Date 2019/8/5
 */
public class PagePhotoFragment extends BaseFragment<FragmentPagePhotoBinding, PagePhotoViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_page_photo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        // 初始化toolbar
        viewModel.initToolbar("西江千户苗寨");
        // 初始化图片数据
        viewModel.loadPhotoList(true);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        // 下拉刷新
        binding.photoRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.loadPhotoList(true);
                refreshLayout.finishRefresh(800);
            }
        });
        // 上拉加载更多
        binding.photoRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.loadPhotoList(false);
                refreshLayout.finishLoadMore(800);
            }
        });
        // item 的点击事件
        viewModel.onClickItemEvent.observe(this, new Observer<PagePhotoEntity.FeedListBean>() {
            @Override
            public void onChanged(@Nullable PagePhotoEntity.FeedListBean feedListBean) {
                if (feedListBean != null) {
                    Bundle bundle = new Bundle();
                    if ("video".equals(feedListBean.getType())) {
                        bundle.putString("title", feedListBean.getEntry()
                                .getAuthor().getDescription());
                        bundle.putString("imageUrl", feedListBean.getEntry().getRaw_cover());
                    } else {
                        bundle.putString("title", feedListBean.getEntry()
                                .getTitle());
                        bundle.putString("imageUrl", feedListBean.getEntry().getTitle_image().getUrl());
                    }
                    startContainerActivity(BigImageFragment.class.getCanonicalName(), bundle);
                }
            }
        });
    }
}

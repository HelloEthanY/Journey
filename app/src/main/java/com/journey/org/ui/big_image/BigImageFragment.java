package com.journey.org.ui.big_image;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.utils.ShareUtil;
import com.journey.org.data.home_page.PagePhotoEntity;
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
    private PagePhotoEntity photoEntity;
    // 标题
    private int index;
    //
    private int position = 0;

    private BigImageRecycleViewAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    public void initParam() {
        super.initParam();
        Bundle bundle = getArguments();
        if (bundle != null) {
            photoEntity = (PagePhotoEntity) bundle.getSerializable("images");
            index = bundle.getInt("index", 0);
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
        // 创建adapter
        adapter = new BigImageRecycleViewAdapter();
        // 设置adapter
        binding.setAdapter(adapter);
        // 设置图片地址
        viewModel.requestBigImageData(photoEntity);
        // 设置RecycleView 滑动到相应的位置
        binding.bigImageContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.bigImageContent.scrollToPosition(index);
            }
        }, 20);
        // 将SnapHelper attach 到RecyclrView  LinearSnapHelper 一次可以滑动多页  PagerSnapHelper限制一次只滑动一页
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        // 设置snapHelper 可以有像ViewPager的效果
        snapHelper.attachToRecyclerView(binding.bigImageContent);
        // 设置toolbar
        viewModel.initToolbar();
        if (photoEntity.getFeedList().size() >= index) {
            viewModel.setToolbarTitle(photoEntity.getFeedList().get(index));
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        // toolbar 的menu点击事件
        viewModel.onClickMenuEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void o) {
                PagePhotoEntity.FeedListBean feedListBean = photoEntity.getFeedList().get(position);
                if (feedListBean != null) {
                    if ("video".equals(feedListBean.getType())) {
                        ShareUtil.shareText(getActivity(),
                                feedListBean.getEntry().getAuthor().getDescription()
                                , "", feedListBean.getEntry().getRaw_cover());
                    } else {
                        ShareUtil.shareText(getActivity(),
                                feedListBean.getEntry().getTitle()
                                , "", feedListBean.getEntry().getTitle_image().getUrl());
                    }
                }
            }
        });
        // RecyclerView的滑动监听事件
        binding.bigImageContent.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (manager != null) {
                    //获取RecyclerView当前顶部显示的第一个条目对应的索引
                    position = manager.findFirstVisibleItemPosition();
                    viewModel.setToolbarTitle(photoEntity.getFeedList().get(position));
                } else {
                    manager = (LinearLayoutManager) binding.bigImageContent.getLayoutManager();
                    //获取RecyclerView当前顶部显示的第一个条目对应的索引
                    position = manager.findFirstVisibleItemPosition();
                    viewModel.setToolbarTitle(photoEntity.getFeedList().get(position));
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}

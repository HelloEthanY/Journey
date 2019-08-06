package com.journey.org.ui.main.home_page.page_detail;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.inf.OnItemClickListener;
import com.journey.org.app.utils.SystemUtil;
import com.journey.org.data.home_page.PageDetailMenuEntity;
import com.journey.org.data.home_page.PageDetailVideoEntity;
import com.journey.org.databinding.FragmentPageDetailBinding;
import com.journey.org.ui.custom.popup.PopupWindowFactory;
import com.journey.org.ui.custom.popup.page_detail.PageDetailPopupWindow;
import com.journey.org.ui.main.home_page.page_map.PageMapActivity;
import com.journey.org.ui.main.home_page.page_photo.PagePhotoFragment;
import com.journey.org.ui.main.home_page.page_ticket.PageTicketFragment;
import com.journey.org.ui.web.WebActivity;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 首页详情
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class PageDetailFragment extends BaseFragment<FragmentPageDetailBinding, PageDetailViewModel> {
    // 景区ID
    private String mId;
    // 景区名称
    private String mScenicName;
    // adapter
    private PageDetailRecycleViewAdapter mAdapter;
    // 菜单
    private PageDetailPopupWindow mDetailMenuPopupWindow;


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
        mScenicName = bundle.getString("name", "");
        mId = bundle.getString("id", "");
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
        // 初始化popupWindow
        initPopup();
        // 不允许上拉加载更多
        binding.pageDetailRefresh.setEnableLoadMore(false);
        // 创建adapter
        mAdapter = new PageDetailRecycleViewAdapter(getContext(), getActivity());
        // 设置adapter
        binding.setAdapter(mAdapter);
        // 初始化toolbar
        viewModel.toolbarTitle.set("西江千户苗寨");
        // 加载数据
        viewModel.loadData();
        // 加载视频数据
        viewModel.loadVideoListData(true);
    }

    // 初始化popupWindow
    private void initPopup() {
        mDetailMenuPopupWindow = PopupWindowFactory.getPageDetailPopupWindow(getActivity(),
                SystemUtil.dp2px(getContext(), 100),
                SystemUtil.dp2px(getContext(), 140), this);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        // toolbar 的menu点击事件
        viewModel.onClickMenuEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void o) {
                mDetailMenuPopupWindow.showAsDropDown(binding.ivMenu,
                        0, 0, Gravity.BOTTOM);
            }
        });
        //菜单 popupWindow 的item 的点击事件
        mDetailMenuPopupWindow.setOnItemClickListener(new OnItemClickListener<PageDetailMenuEntity>() {
            @Override
            public void itemClick(PageDetailMenuEntity pageDetailMenuEntity) {
                switch (pageDetailMenuEntity.getTitle()) {
                    case "门票":
                        Bundle bundle = new Bundle();
                        bundle.putString("webUrl","http://www.baidu.com/");
                        startActivity(WebActivity.class, bundle);
                        //  startContainerActivity(PageTicketFragment.class.getCanonicalName(), bundle);
                        break;

                    case "收藏":

                        break;

                    case "分享":
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT,
                                "https://baike.baidu.com/item/%E8%A5%BF%E6%B1%9F%E5%8D%83%E6%88%B7%E8%8B%97%E5%AF%A8");
                        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
                        shareIntent = Intent.createChooser(shareIntent, "Here is the title of Select box");
                        startActivity(shareIntent);
                        break;
                }
            }
        });
        /*******************************************视频**************************************/
        // 视频准备好了监听
        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        // 设置视频播发错误监听
        binding.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                binding.ivStart.setVisibility(View.VISIBLE);
                binding.videoView.stopPlayback();
                return false;
            }
        });
        // 设置视频播发完成监听
        binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                binding.ivStart.setVisibility(View.VISIBLE);
            }
        });
        // 点击开始播发按钮
        binding.ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.videoView.start();
                binding.ivStart.setVisibility(View.GONE);
            }
        });

        /*******************************************头布局**************************************/
        // 预订门票
        viewModel.onClickBookingEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                Bundle bundle = new Bundle();
                bundle.putString("webUrl","http://www.baidu.com/");
                startActivity(WebActivity.class, bundle);
                // startContainerActivity(PageTicketFragment.class.getCanonicalName(), bundle);
            }
        });
        // 景区图片集合
        viewModel.onClickScenicImgEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                startContainerActivity(PagePhotoFragment.class.getCanonicalName());
            }
        });
        // 地理位置点击事件
        viewModel.onClickBaiMapEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void bean) {
                Bundle bundle = new Bundle();
                bundle.putDouble("longitude", 26.499389873224153);
                bundle.putDouble("latitude", 108.17915965086003);
                startActivity(PageMapActivity.class, bundle);
            }
        });
        // 视频地址回调
        viewModel.onVideoPathEvent.observe(this, new Observer<PageDetailVideoEntity.ItemListBean>() {
            @Override
            public void onChanged(@Nullable PageDetailVideoEntity.ItemListBean bean) {
                if (bean != null)
                    binding.videoView.setVideoURI(Uri.parse(bean.getData().getPlayUrl()));
            }
        });
        /*******************************************体布局**************************************/

    }

    @Override
    public void onStop() {
        super.onStop();
        if (binding.videoView != null) {
            binding.videoView.stopPlayback();
        }
    }
}

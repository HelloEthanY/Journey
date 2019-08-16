package com.journey.org.ui.main.home_page.page_detail;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dou361.ijkplayer.listener.OnPlayerBackListener;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
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
import com.journey.org.ui.web.WebActivity;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 首页详情
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class PageDetailActivity extends BaseActivity<FragmentPageDetailBinding, PageDetailViewModel> {
    // 景区ID
    private String mId;
    // 景区名称
    private String mScenicName;
    // adapter
    private PageDetailRecycleViewAdapter mAdapter;
    // 菜单
    private PageDetailPopupWindow mDetailMenuPopupWindow;
    // 横竖屏切换
    private boolean isScreen = false;

    @Override
    public void initParam() {
        super.initParam();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        mScenicName = bundle.getString("name", "");
        mId = bundle.getString("id", "");
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_page_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    private PlayerView player;

    @Override
    public void initData() {
        super.initData();
        // 创建播放器管理器
        player = new PlayerView(this)
                .setTitle("")
                .setScaleType(PlayStateParams.fitparent)
                .forbidTouch(false)
                .hideBack(true)
                .hideMenu(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        /*Glide.with(this)
                                .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                .placeholder(R.color.cl_default)
                                .error(R.color.cl_error)
                                .into(ivThumbnail);*/
                    }
                })
                .setPlayerBackListener(new OnPlayerBackListener() {
                    @Override
                    public void onPlayerBack() {
                        //这里可以简单播放器点击返回键
                        finish();
                    }
                });
        // 初始化popupWindow
        initPopup();
        // 不允许上拉加载更多
        binding.pageDetailRefresh.setEnableLoadMore(false);
        // 创建adapter
        mAdapter = new PageDetailRecycleViewAdapter(this, this);
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
        mDetailMenuPopupWindow = PopupWindowFactory.getPageDetailPopupWindow(this,
                SystemUtil.dp2px(this, 100),
                SystemUtil.dp2px(this, 140), null);
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
                        bundle.putString("webUrl", "http://www.baidu.com/");
                        startActivity(WebActivity.class, bundle);
                        break;

                    case "收藏":

                        break;

                    case "分享":
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT,
                                "https://baike.baidu.com/item/%E8%A5%BF%E6%B1%9F%E5%8D%83%E6%88%B7%E8%8B%97%E5%AF%A8");
                        // 切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
                        shareIntent = Intent.createChooser(shareIntent, "Here is the title of Select box");
                        startActivity(shareIntent);
                        break;
                }
            }
        });
        /*******************************************视频**************************************/
        // 视频地址回调
        viewModel.onVideoPathEvent.observe(this, new Observer<PageDetailVideoEntity.ItemListBean>() {
            @Override
            public void onChanged(@Nullable PageDetailVideoEntity.ItemListBean bean) {
                if (bean != null) {
                    player.setTitle(bean.getData().getTitle())
                            .setPlaySource(bean.getData().getPlayUrl())
                            .startPlay();
                }
            }
        });
        /*******************************************头布局**************************************/
        // 预订门票
        viewModel.onClickBookingEvent.observe(this, new Observer<Void>()

        {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                Bundle bundle = new Bundle();
                bundle.putString("webUrl", "http://www.baidu.com/");
                startActivity(WebActivity.class, bundle);
                // startContainerActivity(PageTicketFragment.class.getCanonicalName(), bundle);
            }
        });
        // 景区图片集合
        viewModel.onClickScenicImgEvent.observe(this, new Observer<Void>()

        {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                startContainerActivity(PagePhotoFragment.class.getCanonicalName());
            }
        });
        // 地理位置点击事件
        viewModel.onClickBaiMapEvent.observe(this, new Observer<Void>()

        {
            @Override
            public void onChanged(@Nullable Void bean) {
                Bundle bundle = new Bundle();
                bundle.putDouble("longitude", 26.499389873224153);
                bundle.putDouble("latitude", 108.17915965086003);
                startActivity(PageMapActivity.class, bundle);
            }
        });

        /*******************************************体布局**************************************/
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {// 当前等于横屏
            // 隐藏状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            // 隐藏导航栏
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // 修改高度
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                    binding.layoutAppbar.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            binding.layoutAppbar.setLayoutParams(params);
            binding.pageDetailRefresh.setEnableRefresh(false);
            binding.mainPageToolbar.setVisibility(View.GONE);
        } else { // 当前为竖屏
            // 显示状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            // 显示导航栏
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
            // 修改高度
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                    binding.layoutAppbar.getLayoutParams();
            params.height = SystemUtil.dp2px(this, 240);
            binding.layoutAppbar.setLayoutParams(params);
            binding.mainPageToolbar.setVisibility(View.VISIBLE);
            binding.pageDetailRefresh.setEnableRefresh(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}

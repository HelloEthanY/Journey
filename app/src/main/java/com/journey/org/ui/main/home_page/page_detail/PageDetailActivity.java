package com.journey.org.ui.main.home_page.page_detail;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.inf.OnItemClickListener;
import com.journey.org.app.utils.SystemUtil;
import com.journey.org.data.home_page.PageDetailMenuEntity;
import com.journey.org.data.home_page.PageDetailVideoEntity;
import com.journey.org.databinding.FragmentPageDetailBinding;
import com.journey.org.ui.custom.ijkplayer.media.AndroidMediaController;
import com.journey.org.ui.custom.ijkplayer.media.PlayerManager;
import com.journey.org.ui.custom.popup.PopupWindowFactory;
import com.journey.org.ui.custom.popup.page_detail.PageDetailPopupWindow;
import com.journey.org.ui.main.home_page.page_map.PageMapActivity;
import com.journey.org.ui.main.home_page.page_photo.PagePhotoFragment;
import com.journey.org.ui.web.WebActivity;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

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
    // 播发管理器
    private PlayerManager mPlayerManager;
    // 横竖屏切换
    private boolean isScreen = false;
    private AndroidMediaController mController;

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

    @Override
    public void initData() {
        super.initData();
        // 创建播放器管理器
        mPlayerManager = new PlayerManager(this, binding.videoView);
        //
        mController = new AndroidMediaController(this);
        // 设置控制器
        mPlayerManager.getVideoView().setMediaController(mController);
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
                        // 切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
                        shareIntent = Intent.createChooser(shareIntent, "Here is the title of Select box");
                        startActivity(shareIntent);
                        break;
                }
            }
        });
        /*******************************************视频**************************************/

        mPlayerManager.setPlayerStateListener(new PlayerManager.PlayerStateListener() {
            @Override
            public void onComplete() { // 播放完成
                binding.ivStart.clearAnimation();
                binding.ivStart.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {// 播发错误
                ToastUtils.showShort("播发错误");
                binding.ivStart.clearAnimation();
                binding.ivStart.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoading() { // 正在加载
                binding.ivStart.setVisibility(View.VISIBLE);
                startAnim();
            }

            @Override
            public void onPlay() { // 正在播发
                binding.ivStart.clearAnimation();
                binding.ivStart.setVisibility(View.INVISIBLE);
            }
        });

        // 视频准备好了监听
      /*  binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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

*/
        // 点击屏幕隐藏导航栏
        binding.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    View decorView = getWindow().getDecorView();
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //(修改这个选项，可以设置不同模式)
                                    //使用下面三个参数，可以使内容显示在system bar的下面，防止system bar显示或
                                    //隐藏时，Activity的大小被resize。
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    // 隐藏导航栏和状态栏
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
            }
        });

        // 点击切换横竖屏
        binding.ivFullScreen.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (isScreen) { // 竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else { // 横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                isScreen = !isScreen;
            }
        });

        // 点击开始播发按钮
        binding.ivStart.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mPlayerManager.play("");
                // binding.videoView.start();
                binding.ivStart.setVisibility(View.GONE);
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
        // 视频地址回调
        viewModel.onVideoPathEvent.observe(this, new Observer<PageDetailVideoEntity.ItemListBean>() {
            @Override
            public void onChanged(@Nullable PageDetailVideoEntity.ItemListBean bean) {
                if (bean != null) {

                    if (mPlayerManager.isPlaying()) {
                        System.out.println("==============视频正在播发");
                        mPlayerManager.getVideoView().seekTo(mPlayerManager.getDuration());
                        //  mPlayerManager.onDestroy();
                        mPlayerManager.stop();
                    } else {
                        mPlayerManager.play(bean.getData().getPlayUrl());
                    }
                }
            }
        });
        /*******************************************体布局**************************************/

    }

    //开始播发动画
    private void startAnim() {
        AnimationSet animset = new AnimationSet(false);
        RotateAnimation mrotate = new RotateAnimation(0,
                360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mrotate.setDuration(800);
        LinearInterpolator ddd = new LinearInterpolator();
        mrotate.setInterpolator(ddd);
        mrotate.setRepeatCount(10000);
        mrotate.setFillAfter(true);
        animset.addAnimation(mrotate);
        binding.ivStart.startAnimation(animset);
    }


    //横竖屏监听
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

            //  AndroidBarUtil.statusBarHide(this);
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
    protected void onPause() {
        super.onPause();
        mPlayerManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerManager.onDestroy();
    }
}

package com.journey.org.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.ActivityMainBinding;
import com.journey.org.ui.main.home_mine.HomeMineFragment;
import com.journey.org.ui.main.home_page.HomePageFragment;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.AndroidBarUtil;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * 旅游系统
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {

    // 首页Fragment 集合
    private List<Fragment> mFragments;
    // 底部Tab控制器
    private NavigationController navigationController;
    // 选择办公场所之前的 tab 的下标
    private int oldTabIndex = 0;
    //退出时的时间
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidBarUtil.statusBarHide(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        // 初始化Fragment
        initFragment();
        // 初始化底部Tab
        initBottomTab();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomePageFragment());
        // mFragments.add(new OfficeFragment());
        //  mFragments.add(new LandFragment());
        mFragments.add(new HomeMineFragment());
        commitAllowingStateLoss(0);
    }

    /**
     * 初始化底部Tab
     */
    private void initBottomTab() {
        PageNavigationView.MaterialBuilder item = binding.mainTab.material();
        item.addItem(R.mipmap.ic_home_page, getString(R.string.home_page));
        // item.addItem(R.mipmap.ic_home_work, getString(R.string.home_office));
        // item.addItem(R.mipmap.ic_launcher, getString(R.string.home_land));
        item.addItem(R.mipmap.ic_home_mine, getString(R.string.home_mine));

        item.setDefaultColor(ContextCompat.getColor(this, R.color.color8AGrey));
        navigationController = item.build();
        //也可以设置Item选中事件的监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                oldTabIndex = old;
                commitAllowingStateLoss(index);
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }

    /**
     * 显示旧的Tab页面
     */
    public void showOldFragment() {
        showFragment(oldTabIndex);
    }

    /**
     * 切换Tab
     *
     * @param index
     */
    private void showFragment(int index) {
        if (index < 0 || index >= mFragments.size()) {
            return;
        }
        navigationController.setSelect(index);
    }

    private void commitAllowingStateLoss(int index) {
        hideAllFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frameLayout, mFragments.get(index));
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(index + "");
        if (currentFragment != null) {
            transaction.attach(currentFragment);
        } else {
            currentFragment = mFragments.get(index);
            transaction.add(R.id.main_frameLayout, currentFragment, index + "");
        }
        transaction.commitAllowingStateLoss();
    }

    //隐藏所有Fragment
    private void hideAllFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(i + "");
            if (currentFragment != null) {
                transaction.detach(currentFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }


    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showShort("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

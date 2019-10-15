package com.journey.org.app.base;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * FragmentPager适配器
 *
 * @author 逍遥
 * @Date 2019/10/14
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private List<String> titles;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

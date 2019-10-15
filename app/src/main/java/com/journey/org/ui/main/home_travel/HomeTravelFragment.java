package com.journey.org.ui.main.home_travel;

import android.support.v4.app.Fragment;

import com.journey.org.app.base.BasePagerFragment;
import com.journey.org.ui.main.home_travel.entertainment.EntertainmentFragment;
import com.journey.org.ui.main.home_travel.foot.FootFragment;
import com.journey.org.ui.main.home_travel.hotel.HotelFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 出行
 *
 * @author 逍遥
 * @Date 2019/10/15
 */
public class HomeTravelFragment extends BasePagerFragment {

    @Override
    protected List<Fragment> pagerFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FootFragment());
        fragmentList.add(new HotelFragment());
        fragmentList.add(new EntertainmentFragment());

        return fragmentList;
    }

    @Override
    protected List<String> pagerTitleString() {
        List<String> titles = new ArrayList<>();
        titles.add("美食");
        titles.add("酒店");
        titles.add("娱乐");
        return titles;
    }

    @Override
    protected void initToolbarTitle() {
        // viewModel.setRightIconMenu();
        // viewModel.initToolbar("出行", true, false);
    }
}

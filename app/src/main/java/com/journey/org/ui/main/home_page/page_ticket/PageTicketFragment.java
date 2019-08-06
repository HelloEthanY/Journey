package com.journey.org.ui.main.home_page.page_ticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.databinding.FragmentPageTicketBinding;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 门票
 *
 * @author 逍遥
 * @Date 2019/8/6
 */
public class PageTicketFragment extends BaseFragment<FragmentPageTicketBinding, PageTicketViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_page_ticket;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}

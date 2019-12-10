package com.journey.org.ui.main.home_page;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.data.home_page.HomePageBannerEntity;
import com.journey.org.databinding.FragmentHomePageBinding;
import com.journey.org.ui.main.MainActivity;
import com.journey.org.ui.main.home_page.city.CitySelectActivity;
import com.journey.org.ui.main.home_page.page_detail.PageDetailActivity;
import com.journey.org.ui.web.WebActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 旅游系统 - 首页
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class HomePageFragment extends BaseLazyFragment<FragmentHomePageBinding, HomePageViewModel> {

    private final int REQUEST_CODE_SCAN = 0x003;

    @Override
    protected void lazyLoadData() {
        // 请求banner 数据
        viewModel.requestBannerData();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home_page;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        RxBus.getDefault().post(true);
        RxBus.getDefault().toObservable(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String city) throws Exception {
                        if (viewModel != null) {
                            KLog.e("收到当前城市=" + city);
                            viewModel.cityNameField.set(city);
                        }
                    }
                });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        // 暂时禁止上拉加载更多
        binding.pageRefresh.setEnableLoadMore(false);
        // 下拉刷新
        binding.pageRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(800);
            }
        });

        // Item 的点击事件
        viewModel.onClickItemEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Bundle bundle = new Bundle();
                bundle.putString("id", s);
                bundle.putString("name", "西江千户苗寨");
                // startContainerActivity(PageDetailActivity.class.getCanonicalName(), bundle);
                startActivity(PageDetailActivity.class, bundle);
            }
        });

        // banner 的item 的点击事件
        viewModel.onClickBannerItemEvent.observe(this, new Observer<HomePageBannerEntity>() {
            @Override
            public void onChanged(@Nullable HomePageBannerEntity homePageBannerEntity) {
                if (homePageBannerEntity == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("webUrl", homePageBannerEntity.getUrl());
                bundle.putString("title", homePageBannerEntity.getTitle());
                startActivity(WebActivity.class, bundle);
            }
        });

        // 点击选择城市
        viewModel.onClickSelectCityEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {

                Intent intent = new Intent(getActivity(), CitySelectActivity.class);
                startActivityForResult(intent, 200);
                getActivity().overridePendingTransition(R.anim.custom_push_down_in,
                        R.anim.popup_out_anim);
            }
        });
        // 点击扫一扫
        binding.mainHeadEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null) {
            String cityName = data.getStringExtra("cityName");
            String cityCode = data.getStringExtra("cityCode");
            if (viewModel != null) {
                viewModel.cityNameField.set(cityName);
            }
        } else if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) { // // 扫描二维码/条码回传
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                // result.setText("扫描结果为：" + content);
                ToastUtils.showShort("扫描的内容为：" + content);
            }
        }
    }
}

package com.journey.org.ui.main.home_technology;

import android.support.annotation.NonNull;

import com.journey.org.ui.main.home_technology.page_bridge.PageBridgeActivity;
import com.journey.org.ui.main.home_technology.pagezxing.PageZxingFragment;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 技术首页
 *
 * @author yu
 * @Date 2019/5/28
 */
public class HomeTechnologyItemViewModel extends MultiItemViewModel<HomeTechnologyViewModel> {
    // 技术图片
    public String url;
    // 技术名称
    public String name;
    // 技术描述
    public String introduce;

    public HomeTechnologyItemViewModel(@NonNull HomeTechnologyViewModel viewModel, String url, String name, String introduce) {
        super(viewModel);
        this.url = url;
        this.name = name;
        this.introduce = introduce;
    }

    // item 的点击事件
    public BindingCommand onClickItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (name) {
                case "信鸽三维地图":
                    viewModel.onBodyItemClick.setValue(name);
                    break;

                case "android-js混合开发":
                    viewModel.startActivity(PageBridgeActivity.class);
                    break;
                case "android-zxing二维码":
                    viewModel.startContainerActivity(PageZxingFragment.class.getCanonicalName());
                    break;
            }
        }
    });
}

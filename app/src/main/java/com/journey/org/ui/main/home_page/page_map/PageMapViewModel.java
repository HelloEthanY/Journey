package com.journey.org.ui.main.home_page.page_map;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.journey.org.app.base.BaseToolbarViewModel;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 景区地图
 *
 * @author 逍遥
 * @Date 2019/8/2
 */
public class PageMapViewModel extends BaseToolbarViewModel {

    public PageMapViewModel(@NonNull Application application) {
        super(application);
    }

    public void initToolbar(String title) {
        toolbarTitle.set(title);
        onShowMenu.set(false);
    }

    // 到这里
    public SingleLiveEvent<Void> onClickToScenicEvent = new SingleLiveEvent<>();
    // 到这里
    public BindingCommand onClickToScenicCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onClickToScenicEvent.call();
        }
    });
    // 搜索周边
    public BindingCommand onClickSurroundCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("搜索周边");
        }
    });


}

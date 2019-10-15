package com.journey.org.app.base;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.journey.org.R;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author 逍遥
 * @Date 2019/10/14
 */
public class BasePagerViewModel <M extends BaseModel> extends BaseViewModel<M> {

    public Drawable rightIcon;

    public BasePagerViewModel(@NonNull Application application) {
        super(application);
        rightIcon = ContextCompat.getDrawable(application, R.mipmap.ic_search);
    }

    // toolbar 标题
    public ObservableField<String> toolbarTitle = new ObservableField<>("出行");
    // 是否展示返回按钮
    public ObservableBoolean onShowFinish = new ObservableBoolean(true);
    // 菜单按钮点击事件回调
    public SingleLiveEvent<Void> onClickMenuEvent = new SingleLiveEvent<>();
    // 是否展示菜单按钮
    public ObservableBoolean onShowMenu = new ObservableBoolean(true);
    // 点击结束按钮
    public BindingCommand onClickFinishCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    //点击菜单
    public BindingCommand onClickMenuCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onClickMenuEvent.call();
        }
    });

    // 设置toolbar 的内容
    public void setToolbarTitle(String title) {
        toolbarTitle.set(title);
    }

    // 设置是否展示返回按钮
    public void setVisibleLeft(boolean isShow) {
        onShowFinish.set(isShow);
    }

    // 设置是否展示菜单
    public void setVisibleMenu(boolean isShow) {
        onShowMenu.set(isShow);
    }

    // 修改右边展示图标
    public void setRightIconMenu(Drawable drawable) {
        this.rightIcon = drawable;
    }

    public void initToolbar(String title, boolean isShowFinish, boolean isShowMenu) {
        onShowFinish.set(isShowFinish);
        onShowMenu.set(isShowMenu);
        toolbarTitle.set(title);
    }
}

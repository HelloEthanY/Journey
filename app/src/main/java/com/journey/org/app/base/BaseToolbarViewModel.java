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
 * toolbar
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class BaseToolbarViewModel<M extends BaseModel> extends BaseViewModel<M> {

    //兼容databinding，去泛型化
    public BaseToolbarViewModel toolbarViewModel;

    public Drawable rightIcon;
    public Drawable leftIcon;

    public BaseToolbarViewModel(@NonNull Application application) {
        this(application, null);
    }

    public BaseToolbarViewModel(@NonNull Application application, M model) {
        super(application, model);
        toolbarViewModel = this;
        rightIcon = ContextCompat.getDrawable(application, R.mipmap.ic_round_menu);
        leftIcon = ContextCompat.getDrawable(application, R.mipmap.ic_white_return);
    }

    // 菜单按钮点击事件回调
    public SingleLiveEvent<Void> onClickMenuEvent = new SingleLiveEvent<>();
    // 是否展示返回按钮
    public ObservableBoolean onShowFinish = new ObservableBoolean(true);
    // 是否展示菜单按钮
    public ObservableBoolean onShowMenu = new ObservableBoolean(true);
    // toolbar 标题
    public ObservableField<String> toolbarTitle = new ObservableField<>();
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

    public void setLeftIconMenu(Drawable drawable) {
        this.leftIcon = drawable;
    }
}

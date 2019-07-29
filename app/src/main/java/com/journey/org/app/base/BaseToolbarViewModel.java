package com.journey.org.app.base;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * toolbar
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class BaseToolbarViewModel<M extends BaseModel> extends BaseViewModel<M> {

    //兼容databinding，去泛型化
    public BaseToolbarViewModel toolbarViewModel;

    public BaseToolbarViewModel(@NonNull Application application) {
        this(application, null);
    }

    public BaseToolbarViewModel(@NonNull Application application, M model) {
        super(application, model);
        toolbarViewModel = this;
    }
    // 是否展示返回按钮
    public ObservableBoolean onShowFinish = new ObservableBoolean(true);
    // toolbar 标题
    public ObservableField<String> toolbarTitle = new ObservableField<>();

    public BindingCommand onClickFinishCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public void setToolbarTitle(String title){
        toolbarTitle.set(title);
    }

    public void setVisiableLeft(boolean isShow){
        onShowFinish.set(isShow);
    }

}

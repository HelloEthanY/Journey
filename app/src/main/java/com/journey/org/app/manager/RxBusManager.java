package com.journey.org.app.manager;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 消息传递
 *
 * @author yu
 * @Date 2019/4/15
 */
public class RxBusManager {

    private RxManager rxManager;

    private static RxBusManager instance;

    public static RxBusManager getDefault() {
        if (instance == null) {
            synchronized (RxBusManager.class) {
                if (instance == null) {
                    instance = new RxBusManager();
                }
            }
        }
        return instance;
    }

    // 初始化
    public <T> RxBusManager init(Class<T> eventType) {
        RxBus.getDefault().toObservable(eventType).subscribe(new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                rxManager.result(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort(throwable.getMessage());
            }
        });
        return instance;
    }

    public void setRxManager(RxManager rxManager) {
        this.rxManager = rxManager;
    }
}

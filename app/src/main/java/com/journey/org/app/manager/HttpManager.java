package com.journey.org.app.manager;

import com.journey.org.app.API;
import com.journey.org.app.Config;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.utils.RxUtils;

public class HttpManager {

    private volatile static HttpManager INSTANCE = null;

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 得到玩Android Banner数据
     *
     * @return
     */
    public Observable getHomePageBanner() {
        return RetrofitManager.getInstance(Config.PLAY_ANDROID_BASE_URL)
                .create(API.class)
                .getHomePageBanner()
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer());
    }
}

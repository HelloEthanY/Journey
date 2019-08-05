package com.journey.org.app.manager;

import android.webkit.WebSettings;

import com.journey.org.app.API;
import com.journey.org.app.Config;

import java.util.HashMap;
import java.util.Map;

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

    // 得到玩Android Banner数据
    public Observable getHomePageBanner() {
        return RetrofitManager.getInstance(Config.PLAY_ANDROID_BASE_URL)
                .create(API.class)
                .getHomePageBanner()
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer());
    }

    // 网易视频
    public Observable getPageDetailVideoList() {
        // Map<String,String> header = new HashMap<>();
        return ArcgisRetrofitManager.getInstance(Config.WANGYI_BASE_URL)
                .create(API.class)
                .getPageDetailVideoList()
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer());
    }

    // 图虫壁纸
    public Observable getPagePhotoList(int page) {
        // Map<String,String> header = new HashMap<>();
        return ArcgisRetrofitManager.getInstance(Config.TUCHONG_BASE_URL)
                .create(API.class)
                .getPagePhotoList(page)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer());
    }
}

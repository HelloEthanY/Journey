package com.journey.org.app;

import com.journey.org.app.base.BasePlayAndroidEntity;
import com.journey.org.data.home_page.HomePageBannerEntity;
import com.journey.org.data.home_page.PageDetailVideoEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 数据接口类
 *
 * @author 逍遥
 * @Date 2019/7/1
 */
public interface API {

    /*******************************************玩Android *****************************************/
    //  https://www.wanandroid.com/banner/json
    @GET("banner/json")
    Observable<BasePlayAndroidEntity<List<HomePageBannerEntity>>> getHomePageBanner();

    // 得到视频地址 - 网易视频

    /**
     * http://baobab.kaiyanapp.com/api/v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83
     *
     * @return 被观察者
     */
    @GET("api/v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<PageDetailVideoEntity> getPageDetailVideoList();

}

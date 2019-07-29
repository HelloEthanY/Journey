package com.journey.org.app;

import com.journey.org.app.base.BasePlayAndroidEntity;
import com.journey.org.data.home_page.HomePageBannerEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

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

}

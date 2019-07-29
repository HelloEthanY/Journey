package com.journey.org.app.manager;

import android.content.Context;
import android.text.TextUtils;

import com.journey.org.app.Config;
import com.journey.org.app.JourneyApplication;
import com.journey.org.app.utils.HttpsUtil;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.BuildConfig;
import me.goldze.mvvmhabit.http.cookie.CookieJarImpl;
import me.goldze.mvvmhabit.http.cookie.store.PersistentCookieStore;
import me.goldze.mvvmhabit.http.interceptor.BaseInterceptor;
import me.goldze.mvvmhabit.http.interceptor.CacheInterceptor;
import me.goldze.mvvmhabit.http.interceptor.logging.Level;
import me.goldze.mvvmhabit.http.interceptor.logging.LoggingInterceptor;
import me.goldze.mvvmhabit.utils.KLog;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求管理类
 *
 * @author yu
 * @Date 2019/4/4
 */
public class RetrofitManager {
    //超时时间
    private static final int DEFAULT_TIMEOUT = 15;
    //缓存时间
    private static final int CACHE_TIMEOUT = 15 * 1024 * 1024;
    // 上下文
    private Context mContext = JourneyApplication.getInstance().getApplicationContext();
    //
    private OkHttpClient mOkHttpClient;
    //
    private Retrofit mRetrofit;
    // 缓存
    private Cache mCache = null;
    // 缓存文件
    private File mCacheFile = null;
    // 网络请求
    private static RetrofitManager instance = null;
    // 请求接口 前缀
    private static final String BASE_URL = Config.BASE_URL;

    /**
     * 单例模式
     *
     * @return
     */
    public static RetrofitManager getInstance() {
        synchronized (HttpManager.class) {
            if (instance == null) {
                instance = new RetrofitManager();
            }
            return instance;
        }
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static RetrofitManager getInstance(String baseUrl) {
        synchronized (HttpManager.class) {
            if (instance == null) {
                instance = new RetrofitManager(baseUrl);
            }
            return instance;
        }
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static RetrofitManager getInstance(String baseUrl, Map<String, String> header) {
        synchronized (HttpManager.class) {
            if (instance == null) {
                instance = new RetrofitManager(baseUrl, header);
            }
            return instance;
        }
    }

    private RetrofitManager() {
        this(BASE_URL, null);
    }

    private RetrofitManager(String baseUrl) {
        this(baseUrl, null);
    }

    private RetrofitManager(String baseUrl, Map<String, String> header) {
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = BASE_URL;
        }
        // 判断缓存文件是否为空
        if (mCacheFile == null) {
            // 创建缓存文件
            mCacheFile = new File(mContext.getCacheDir(), "resolute_cache");
        }
        try {
            // 判断缓存类是否为空
            if (mCache == null) {
                // 创建缓存类 参数一：缓存的文件地址 参数二：缓存的时间
                mCache = new Cache(mCacheFile, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            KLog.e("Could not create http cache", e);
        }
        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory();
        mOkHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
                // .cache(mCache)
                .addInterceptor(new BaseInterceptor(header))
                .addInterceptor(new CacheInterceptor(mContext))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(new LoggingInterceptor
                        .Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request") // request的Tag
                        .response("Response")// Response的Tag
                        .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                        .build())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return mRetrofit.create(service);
    }

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }
}

package com.journey.org.app.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * Create by yu 2018/10/30 9:13
 *
 * @author yu
 */
public class ThreadPoolManager {
    private static ThreadPoolManager mInstance = null;
    private ExecutorService service;

    private ThreadPoolManager() {
        init();
    }

    public static ThreadPoolManager getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPoolManager();
                }
            }
        }
        return mInstance;
    }

    private void init() {
        service = Executors.newFixedThreadPool(6);
    }

    public void execute(Runnable runnable) {
        if (service != null) {
            service.execute(runnable);
        }
    }
}

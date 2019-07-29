package com.journey.org.app;


import com.journey.org.R;
import com.journey.org.app.base.BaseResponse;
import com.journey.org.app.utils.CodeRule;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public abstract class ApiDisposableObserver<T> extends DisposableObserver<T> {
    public abstract void onResult(T t);

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        KLog.e(e.getMessage());
        ToastUtils.showShort(R.string.net_error);
    }

    @Override
    public void onNext(Object o) {
        BaseResponse baseResponse = (BaseResponse) o;
        switch (baseResponse.getCode()) {
            case CodeRule.CODE_200:
                //请求成功, 正确的操作方式
                onResult((T) baseResponse.getResult());
                break;
            case CodeRule.CODE_220:
                // 请求成功, 正确的操作方式, 并消息提示
                onResult((T) baseResponse.getResult());
                break;
            case CodeRule.CODE_300:
                //请求失败，不打印Message
                KLog.e("请求失败" + baseResponse.getCode());
                ToastUtils.showShort(R.string.code_error, baseResponse.getCode());
                break;
            case CodeRule.CODE_330:
                //请求失败，打印Message
                ToastUtils.showShort(baseResponse.getMessage());
                break;
            case CodeRule.CODE_500:
                KLog.e("服务器内部异常" + baseResponse.getCode());
                //服务器内部异常
                ToastUtils.showShort(R.string.code_error, baseResponse.getCode());
                break;
            case CodeRule.CODE_503:
                //参数为空
                KLog.e("参数为空");
                break;
            case CodeRule.CODE_502:
                //没有数据
                KLog.e("没有数据");
                break;
            case CodeRule.CODE_510:
                //无效的Token，提示跳入登录页
                ToastUtils.showShort("token已过期，请重新登录");
                //跳入登录页
                startLoginActivity();
                break;
            case CodeRule.CODE_530:
                ToastUtils.showShort("请先登录");
                //跳入登录页
                startLoginActivity();
                break;
            case CodeRule.CODE_551:
                KLog.e("请求的操作异常终止：未知的页面类型" + baseResponse.getCode());
                ToastUtils.showShort(R.string.code_error, baseResponse.getCode());
                break;
            default:
                KLog.e("其他：" + baseResponse.getCode());
                ToastUtils.showShort(R.string.code_error, baseResponse.getCode());
                break;
        }
    }

    /**
     * 跳入登录页
     */
    private void startLoginActivity() {
        //关闭所有页面
        AppManager.getAppManager().finishAllActivity();
        //跳入登录
    /*    Intent intent = new Intent(MerchantApplication.getInstance(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MerchantApplication.getInstance().startActivity(intent);*/
    }
}
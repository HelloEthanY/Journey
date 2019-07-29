package com.journey.org.app.utils;

/**
 * Created by Administrator on 2017/9/4 0004.
 * Code规则
 */

public class CodeRule {
    //请求成功, 正确的操作方式
    public static final int CODE_200 = 200;
    //请求成功, 消息提示
    public static final int CODE_220 = 220;
    //请求失败，不打印Message
    public static final int CODE_300 = 300;
    //请求失败，打印Message
    public static final int CODE_330 = -2;
    //服务器内部异常
    public static final int CODE_500 = 500;
    //参数为空
    public static final int CODE_503 = 503;
    //没有数据
    public static final int CODE_502 = 502;
    //无效的Token
    public static final int CODE_510 = 510;
    //未登录
    public static final int CODE_530 = -6;
    //请求的操作异常终止：未知的页面类型
    public static final int CODE_551 = 551;
}

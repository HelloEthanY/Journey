package com.journey.org.app;

import com.skyline.teapi.AltitudeTypeCode;

import me.goldze.mvvmhabit.utils.SPUtils;

public class Config {
    // 备用 地址 http://192.168.17.8:8026/
    public static final String BASE_URL = "http://192.168.17.8:8026/Mobile/";

    public static final String URL = "url";

    public static final String TITLE = "title"; // 标题

    /***************************************玩Android baseUrl ************************************/
    public static final String PLAY_ANDROID_BASE_URL = "https://www.wanandroid.com/";
    // 网易
    public static final String WANGYI_BASE_URL = "http://baobab.kaiyanapp.com/";
    // 图虫壁纸
    public static final String TUCHONG_BASE_URL = "https://api.tuchong.com/";

    /***************************************下面是关于skyline****************************************/

    public static String FLY_PATH = "http://61.243.44.116:8000/" + "AppFiles/fly/DefaultMobile_All.fly";
    // public static String FLY_PATH = "http://61.243.44.116:8040/SG/projects/test.Mobile.431577";
    public static final double X = 106.621242; // 经度
    public static final double Y = 26.384871; // 纬度
    public static final double Altitude = 0.0; // 高度
    public static final int AltitudeType = AltitudeTypeCode.ATC_TERRAIN_ABSOLUTE; // 高度类型
    public static final double Distance = 6976.074202; // 视角距离
    public static final double Pitch = -50.993128; // 旋转角度
    public static final double Yaw = 56.0; // 偏航角度

    public static void GuiAn() {
        setFlyPath("http://61.243.44.116:8000/AppFiles/fly/DefaultMobile_All.fly");
        setX(106.621242);
        setY(26.384871);
        setAltitude(0.0);
        setAltitudeType(AltitudeTypeCode.ATC_TERRAIN_ABSOLUTE);
        setDistance(6976.074202);
        setPitch(-50.993128);
        setYaw(56.0);
    }

    public static void HuaWei_And_Apple() {
        setFlyPath("http://61.243.44.116/HuaWei_Apple/HuaWei_And_Apple_Mobile.fly");
        setX(106.465264);
        setY(26.360431);
        setAltitude(1254.9536);
        setAltitudeType(AltitudeTypeCode.ATC_TERRAIN_ABSOLUTE);
        setDistance(677.58823);
        setPitch(-49.999116);
        setYaw(0.0);
    }

    public static void ShiGuangGuiZhou() {
        setFlyPath("http://61.243.44.116/HuaWei_Apple/SGGZ_Mobile.fly");
        setX(106.462694);
        setY(26.581143);
        setAltitude(1208.129);
        setAltitudeType(AltitudeTypeCode.ATC_TERRAIN_ABSOLUTE);
        setDistance(1144.2816);
        setPitch(-48.943894);
        setYaw(258.99972);
    }

    public static void YinChuan() {
        setFlyPath("http://61.243.44.116/HuaWei_Apple/YinChuan_Mobile.fly");
    }

    public static String getFlyPath() {
        return SPUtils.getInstance().getString("FLY_PATH");//FLY文件
    }

    public static void setFlyPath(String flyPath) {
        SPUtils.getInstance().put("FLY_PATH", flyPath);
    }

    public static double getX() {
        return Double.parseDouble(SPUtils.getInstance().getString("X"));// 经度
    }

    public static void setX(double x) {
        SPUtils.getInstance().put("X", x + "");
    }

    public static double getY() {
        return Double.parseDouble(SPUtils.getInstance().getString("Y"));// 纬度
    }

    public static void setY(double y) {
        SPUtils.getInstance().put("Y", y + "");
    }

    public static double getAltitude() {
        return Double.parseDouble(SPUtils.getInstance().getString("Altitude"));// 高度
    }

    public static void setAltitude(double altitude) {
        SPUtils.getInstance().put("Altitude", altitude + "");
    }

    public static int getAltitudeType() {
        return SPUtils.getInstance().getInt("AltitudeType");// 高度类型
    }

    public static void setAltitudeType(int altitudeType) {
        SPUtils.getInstance().put("AltitudeType", altitudeType);
    }

    public static double getDistance() {
        return Double.parseDouble(SPUtils.getInstance().getString("Distance"));// 视角距离
    }

    public static void setDistance(double distance) {
        SPUtils.getInstance().put("Distance", distance + "");
    }

    public static double getPitch() {
        return Double.parseDouble(SPUtils.getInstance().getString("Pitch"));// 旋转角度
    }

    public static void setPitch(double pitch) {
        SPUtils.getInstance().put("Pitch", pitch + "");
    }

    public static double getYaw() {
        return Double.parseDouble(SPUtils.getInstance().getString("Yaw"));// 偏航角度
    }

    public static void setYaw(double yaw) {
        SPUtils.getInstance().put("Yaw", yaw + "");
    }

}

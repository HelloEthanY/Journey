package com.journey.org.ui.custom.chart.pieview;

import android.support.annotation.ColorInt;

import io.reactivex.annotations.NonNull;

public class PieEntity {
    /**
     * 板块颜色
     */
    private int color;

    /**
     * 板块数据
     */
    private float data;

    /**
     * 板块文字
     */
    private String msg;

    /**
     * 该板块是否凸起
     */
    private boolean isRaised;


    public PieEntity(float data, @NonNull String msg) {
        this.data = data;
        this.msg = msg;
    }

    public PieEntity(float data, @NonNull String msg, @ColorInt int color) {
        this.color = color;
        this.data = data;
        this.msg = msg;
    }

    public PieEntity(float data, @NonNull String msg, boolean isRaised) {
        this.data = data;
        this.msg = msg;
        this.isRaised = isRaised;
    }

    public PieEntity(float data, @NonNull String msg, @ColorInt int color, boolean isRaised) {
        this.color = color;
        this.data = data;
        this.msg = msg;
        this.isRaised = isRaised;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isRaised() {
        return isRaised;
    }

    public void setRaised(boolean raised) {
        isRaised = raised;
    }
}

package com.journey.org.ui.custom.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.PieChart;
import com.journey.org.R;

/**
 * 实现饼状图换行和小于百分之5的展示方式
 * @author 逍遥
 * @Date 2019/10/11
 *
 */
public class PieChartFixCover extends PieChart {
    public PieChartFixCover(Context context) {
        this(context, null);
    }

    public PieChartFixCover(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartFixCover(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MPAndroidChartUtil);
            String mode = a.getString(R.styleable.MPAndroidChartUtil_mp_chart_out_value_place_mode);
            boolean auto_adapt_text_size = a.getBoolean(R.styleable.MPAndroidChartUtil_mp_chart_auto_adapt_text_size, false);
            a.recycle();
            ((PieChartRendererFixCover) mRenderer).setMode(mode);
            ((PieChartRendererFixCover) mRenderer).setAuto_adapt_text_size(auto_adapt_text_size);
        }
    }

    @Override
    protected void init() {
        super.init();
        //此处把mRenderer替换成我们自己的PieChartRenderer
        mRenderer = new PieChartRendererFixCover(this, mAnimator, mViewPortHandler);
        setRenderer(mRenderer);
    }
}

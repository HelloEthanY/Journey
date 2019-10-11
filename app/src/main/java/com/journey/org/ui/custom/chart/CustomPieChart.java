package com.journey.org.ui.custom.chart;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.PieChart;

/**
 * 实现饼状图换行
 * @author 逍遥
 * @Date 2019/10/10
 *
 */
public class CustomPieChart extends PieChart {
    public CustomPieChart(Context context) {
        super(context);
    }

    public CustomPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        //此处把mRenderer替换成我们自己的PieChartRenderer
        mRenderer = new CustomPieChartRenderer(this, mAnimator, mViewPortHandler);
        setRenderer(mRenderer);
    }
}

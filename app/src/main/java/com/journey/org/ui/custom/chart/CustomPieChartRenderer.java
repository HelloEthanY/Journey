package com.journey.org.ui.custom.chart;

import android.graphics.Canvas;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.renderer.PieChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * 实现饼状图换行
 *
 * @author 逍遥
 * @Date 2019/10/10
 */
public class CustomPieChartRenderer extends PieChartRenderer {

    public CustomPieChartRenderer(PieChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color) {
        // super.drawValue(c, formatter, value, entry, dataSetIndex, x, y, color);
        mValuePaint.setColor(color);
        String[] texts = formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler).split(System.getProperty("line.separator"));
        for (String text : texts) {
            c.drawText(text, x, y, mValuePaint);
            y = y + 30;
        }
        /*String temp = "";
        String save = "";
        String[] texts = formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler).split(System.getProperty("line.separator"));
        System.out.println("=======长度：" + texts.length);
        if (texts.length > 0) {
            String[] values = texts[0].split("");
            if (values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    System.out.println("输出为test：" + values[i]);
                    temp += values[i];
                    if (i % 5 == 0 && i != 0) {
                        System.out.println("输出为：" + temp);
                        c.drawText(temp, x, y, mValuePaint);
                        temp = "";
                        save = "";
                        y = y + 30;
                    } else {
                        save += values[i];
                        System.out.println("输出为save：" + save);
                    }
                }
                c.drawText(save, x, y, mValuePaint);
            }
        }*/
    }
}

package com.journey.org.ui.main.home_technology.page_chart.mpandroid_chart;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.databinding.FragmentMpandroidChartBinding;
import com.journey.org.ui.custom.chart.StringAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * MPAndroid 图表
 *
 * @author 逍遥
 * @Date 2019/10/14
 */
public class MpAndroidFragment extends BaseLazyFragment<FragmentMpandroidChartBinding, MpAndroidViewModel> {

    protected Typeface tfLight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
    }

    @Override
    protected void lazyLoadData() {
        initPieChart();
        initHorizontalBarChart();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_mpandroid_chart;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    // 饼状图
    private void initPieChart() {
        int[] colorArray = getResources().getIntArray(R.array.ran_type_color);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(5, "很大安科" + 5 + " 个"));
        entries.add(new PieEntry(10, "大苏打安顺" + 10 + " 个"));
        entries.add(new PieEntry(5, "啊大事按" + 5 + " 个"));
        entries.add(new PieEntry(20, "啊大署大" + 20 + " 个"));
        entries.add(new PieEntry(10, "安顺大是的" + 10 + " 个"));
        entries.add(new PieEntry(5, "啊时代的按" + 5 + " 个"));
        entries.add(new PieEntry(60, "啊署大" + 60 + " 个"));
        //将数据装入集合
        PieDataSet dataSet = new PieDataSet(entries, "");
        //设置数据的颜色
        dataSet.setColors(colorArray);
        //设置图形百分比字体颜色
        dataSet.setValueTextColor(ContextCompat.getColor(this.getContext(), R.color.black));
        dataSet.setValueTextSize(12f);
        dataSet.setDrawValues(true); //是否显示值
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                PieEntry pieEntry = (PieEntry) entry;
                StringBuilder sb = new StringBuilder();
                sb.append(pieEntry.getLabel())
                        .append("(")
                        .append((int) (value + 0.5))
                        .append("%")
                        .append(")");
                return sb.toString();
            }
        });

        //数据集合设置到界面
        PieData data = new PieData(dataSet);
        binding.pieChart.setUsePercentValues(true);//设置显示成比例
        binding.pieChart.setRotationEnabled(true);//设置不可旋转
        binding.pieChart.setRotationAngle(-100);
        binding.pieChart.setData(data);
        binding.pieChart.setCenterText("饼状图");
        binding.pieChart.setCenterTextColor(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
        binding.pieChart.setHoleRadius(60f);
        binding.pieChart.setDrawSliceText(false);//块的文本是否显示
        binding.pieChart.setDescription(null);//右下角显示图形描述
        binding.pieChart.setExtraOffsets(0, 40, 0, 5);        //设置pieChart图表上下左右的偏移，类似于外边距
        // 获取pieCahrt图列
        Legend l = binding.pieChart.getLegend();
        l.setEnabled(true);                    //是否启用图列（true：下面属性才有意义）
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setForm(Legend.LegendForm.DEFAULT); //设置图例的形状
        l.setYOffset(-80f);                      //设置比例块Y轴偏移量
        l.setDrawInside(false);
        //刷新
        binding.pieChart.animateXY(1000, 1000);
        binding.pieChart.invalidate();
    }

    // 横向柱状图
    private void initHorizontalBarChart() {
        // 设置数据
        float barWidth = 0.5f;
        final List<String> stringList1 = new ArrayList<>();
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList1.add("荷城街道看打卡机卡的加速度加速度加速度尽快" + i);
            float val = (float) (Math.random() * 2.5);
            values.add(new BarEntry(i, val,
                    getResources().getDrawable(R.drawable.ic_launcher_foreground)));
        }

        // binding.horizontalBarChart.setOnChartValueSelectedListener(this);
        // chart.setHighlightEnabled(false);
        binding.horizontalBarChart.setDrawBarShadow(false);
        binding.horizontalBarChart.setDrawValueAboveBar(true);
        binding.horizontalBarChart.getDescription().setEnabled(false);
        // binding.horizontalBarChart.setMaxVisibleValueCount(60);
        binding.horizontalBarChart.setPinchZoom(false);
        binding.horizontalBarChart.setDrawGridBackground(false);
        XAxis xl = binding.horizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(1f);

        xl.setValueFormatter(new StringAxisValueFormatter(stringList1));

        YAxis yl = binding.horizontalBarChart.getAxisLeft();
        yl.setTypeface(tfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = binding.horizontalBarChart.getAxisRight();
        yr.setTypeface(tfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);
        binding.horizontalBarChart.setFitBars(true);
        binding.horizontalBarChart.animateY(2500);
        Legend l = binding.horizontalBarChart.getLegend();
        l.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(2f);
        BarDataSet set1;
        if (binding.horizontalBarChart.getData() != null &&
                binding.horizontalBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) binding.horizontalBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            binding.horizontalBarChart.getData().notifyDataChanged();
            binding.horizontalBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "DataSet 1");
            set1.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(tfLight);
            data.setBarWidth(barWidth);
            binding.horizontalBarChart.setData(data);
        }
/*
        final List<String> title = new ArrayList<>();
        List<IBarDataSet> dataSets = new ArrayList<>();
        ArrayList<BarEntry> yVals = new ArrayList<>();
        int[] colorArray = getResources().getIntArray(R.array.ran_type_color);
        Legend legend = binding.horizontalBarChart.getLegend();
        legend.setEnabled(false);
        // 对于X轴的设置
        XAxis xAxis = binding.horizontalBarChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);//设置最小的区间，避免标签的迅速增多
        //设置X轴的刻度数
        // xAxis.setLabelCount(title.size() - 1, false);
        xAxis.setDrawGridLines(true);//设置竖状的线是否显示
        xAxis.setCenterAxisLabels(false);//设置标签居中
        // 让x轴在下面
        binding.horizontalBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //隐藏右y轴
        YAxis axisRight = binding.horizontalBarChart.getAxisRight();
        axisRight.setEnabled(false);
        axisRight.setAxisMinimum(0f);
        axisRight.setDrawGridLines(false);//设置横状的线是否显示
        axisRight.setEnabled(false);//隐藏右边轴和数字
        binding.horizontalBarChart.setPinchZoom(false);//挤压缩放
        binding.horizontalBarChart.animateXY(1000, 1000);
        binding.horizontalBarChart.setDescription(null);//参数：右下角显示图形描述
        binding.horizontalBarChart.setDoubleTapToZoomEnabled(false); // 设置为false以禁止通过在其上双击缩放图表。
        title.add("好的好久哈加速度哈加速度氨基酸电话爱健身的嘉实多");
        yVals.add(new BarEntry(0, (float) 20));
        title.add("好的好久哈加速度哈加速度氨基酸电话爱");
        yVals.add(new BarEntry(1, (float) 90));
        title.add("好的好久哈加速度哈加速度氨基酸电话爱健身");
        yVals.add(new BarEntry(2, (float) 100));
        title.add("好的好久哈加速度哈加速度");
        yVals.add(new BarEntry(3, (float) 80));
        //  xAxis.setValueFormatter(new StringAxisValueFormatter(title));
        BarDataSet dataSet = new BarDataSet(yVals, "");
        dataSet.setValues(yVals);
        dataSet.setValueTextSize(9f);
        dataSet.setFormLineWidth(1f);
        //
        dataSet.setFormSize(12.f);
        dataSet.setColors(colorArray);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int) value + "万元";
            }
        });
        dataSets.add(dataSet);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(12f);
        data.setBarWidth(0.6f);
        xAxis.setLabelCount(title.size(), false);
        //设置数据
        binding.horizontalBarChart.setData(data);*/
    }

}

package com.journey.org.ui.main.home_technology.page_chart.custom_chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseLazyFragment;
import com.journey.org.databinding.FragmentCustomChartBinding;
import com.journey.org.ui.custom.chart.pieview.PieEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义图表
 *
 * @author 逍遥
 * @Date 2019/10/14
 */
public class CustomChartFragment extends BaseLazyFragment<FragmentCustomChartBinding, CustomChartViewModel> {
    @Override
    protected void lazyLoadData() {
        initPieChart();
        initPieChart1();
        initPieChart2();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_custom_chart;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    //技术水平饼状图
    private void initPieChart() {
        int[] colorArray = getResources().getIntArray(R.array.ran_type_color);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(5, "很大安科技的安顺大卡机测试" + 5 + " 个"));
        entries.add(new PieEntry(10, "大苏打安顺大事大阿大撒测试" + 10 + " 个"));
        entries.add(new PieEntry(5, "啊大事按时的啊测试" + 5 + " 个"));
        entries.add(new PieEntry(20, "啊大署大测试" + 20 + " 个"));
        entries.add(new PieEntry(10, "安顺大是的测试" + 10 + " 个"));
        entries.add(new PieEntry(5, "啊时代的按时的按时的测试" + 5 + " 个"));
        entries.add(new PieEntry(60, "啊署大按时的安顺测试" + 60 + " 个"));
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
                String label = pieEntry.getLabel();
                String[] split = label.split("");
                for (int i = 0; i < split.length; i++) {
                    sb.append(split[i]);
                    if (i % 8 == 0 && i / 4 != 0 && i / 2 != 0 && i != 0) {
                        sb.append("\n");
                    }
                }
                sb.append("(")
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
        binding.pieChart.setCenterText("可以换行饼状图");
        binding.pieChart.setCenterTextColor(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
        binding.pieChart.setHoleRadius(60f);
        binding.pieChart.setDrawSliceText(false);//块的文本是否显示
        binding.pieChart.setDescription(null);//右下角显示图形描述
        binding.pieChart.setExtraOffsets(0, 40, 0, 0);        //设置pieChart图表上下左右的偏移，类似于外边距
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

    // 小于百分之5
    private void initPieChart1() {
        int[] colorArray = getResources().getIntArray(R.array.ran_type_color);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0, "很大安科技的安顺大卡机测试" + 0 + " 个"));
        entries.add(new PieEntry(10, "大苏打安顺大事大阿大撒测试" + 10 + " 个"));
        entries.add(new PieEntry(1, "啊大事按时的啊测试" + 1 + " 个"));
        entries.add(new PieEntry(20, "啊大署大测试" + 20 + " 个"));
        entries.add(new PieEntry(10, "安顺大是的测试" + 10 + " 个"));
        entries.add(new PieEntry(2, "啊时代的按时的按时的测试" + 2 + " 个"));
        entries.add(new PieEntry(60, "啊署大按时的安顺测试" + 60 + " 个"));
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
                String label = pieEntry.getLabel();
                String[] split = label.split("");
                for (int i = 0; i < split.length; i++) {
                    sb.append(split[i]);
                    if (i % 8 == 0 && i / 4 != 0 && i / 2 != 0 && i != 0) {
                        sb.append("\n");
                    }
                }
                sb.append("(")
                        .append((int) (value + 0.5))
                        .append("%")
                        .append(")");
                return sb.toString();
            }
        });
        //数据集合设置到界面
        PieData data = new PieData(dataSet);
        binding.pieChart1.setUsePercentValues(true);//设置显示成比例
        binding.pieChart1.setRotationEnabled(true);//设置不可旋转
        binding.pieChart1.setRotationAngle(-100);
        binding.pieChart1.setData(data);
        binding.pieChart1.setCenterText("可以换行饼状图");
        binding.pieChart1.setCenterTextColor(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
        binding.pieChart1.setHoleRadius(60f);
        binding.pieChart1.setDrawSliceText(false);//块的文本是否显示
        binding.pieChart1.setDescription(null);//右下角显示图形描述
        binding.pieChart1.setExtraOffsets(0, 40, 0, 0);        //设置pieChart图表上下左右的偏移，类似于外边距
        // 获取pieCahrt图列
        Legend l = binding.pieChart1.getLegend();
        l.setEnabled(false);                    //是否启用图列（true：下面属性才有意义）
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setForm(Legend.LegendForm.DEFAULT); //设置图例的形状
        l.setYOffset(-80f);                      //设置比例块Y轴偏移量
        l.setDrawInside(false);
        //刷新
        binding.pieChart1.animateXY(1000, 1000);
        binding.pieChart1.invalidate();
    }

    // 自定义的饼状图
    private void initPieChart2() {
        List<PieEntity> list = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            list.add(new PieEntity(i * 20, String.format("第%s区", i)));
        }
        binding.pieChart2.setData(list)
                .setShowAnimator(true)
                .refresh();
    }

}

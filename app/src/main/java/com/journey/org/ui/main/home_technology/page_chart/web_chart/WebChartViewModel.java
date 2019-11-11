package com.journey.org.ui.main.home_technology.page_chart.web_chart;

import android.app.Application;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;

import com.journey.org.ui.custom.SpinnerItemData;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * 引入webView 来加载 eCharts（网页版的图表）
 *
 * @author 逍遥
 * @Date 2019/10/14
 */
public class WebChartViewModel extends BaseViewModel {

    public ObservableFloat allSjzcz = new ObservableFloat();

    public ObservableFloat allSjss = new ObservableFloat();

    // 年
    public List<IKeyAndValue> yearTimeItemDatas = new ArrayList<>();
    // 季度
    public List<IKeyAndValue> quarterTimeItemDatas = new ArrayList<>();
    // 月
    public List<IKeyAndValue> monthTimeItemDatas = new ArrayList<>();



    public WebChartViewModel(@NonNull Application application) {
        super(application);
        int yearTime = 2019;
        yearTimeItemDatas.add(new SpinnerItemData("请选择年", "0"));
        yearTimeItemDatas.add(new SpinnerItemData(String.valueOf(yearTime), String.valueOf(yearTime)));
        yearTimeItemDatas.add(new SpinnerItemData(String.valueOf(yearTime - 1), String.valueOf(yearTime - 1)));
        yearTimeItemDatas.add(new SpinnerItemData(String.valueOf(yearTime - 2), String.valueOf(yearTime - 2)));
        yearTimeItemDatas.add(new SpinnerItemData(String.valueOf(yearTime - 3), String.valueOf(yearTime - 3)));
        yearTimeItemDatas.add(new SpinnerItemData(String.valueOf(yearTime - 4), String.valueOf(yearTime - 4)));
        yearTimeItemDatas.add(new SpinnerItemData(String.valueOf(yearTime - 5), String.valueOf(yearTime - 5)));
        // 季度
        quarterTimeItemDatas.add(new SpinnerItemData("请选择季度", "0"));
        // 1 代表 1-3月
        quarterTimeItemDatas.add(new SpinnerItemData("一", "1"));
        // 1 代表 4-6月
        quarterTimeItemDatas.add(new SpinnerItemData("二", "4"));
        // 1 代表 7-9月
        quarterTimeItemDatas.add(new SpinnerItemData("三", "7"));
        // 1 代表 10-12月
        quarterTimeItemDatas.add(new SpinnerItemData("四", "11"));
        // 月
        monthTimeItemDatas.add(new SpinnerItemData("请选择月", "0"));
        monthTimeItemDatas.add(new SpinnerItemData("1", "1"));
        monthTimeItemDatas.add(new SpinnerItemData("2", "2"));
        monthTimeItemDatas.add(new SpinnerItemData("3", "3"));
        monthTimeItemDatas.add(new SpinnerItemData("4", "4"));
        monthTimeItemDatas.add(new SpinnerItemData("5", "5"));
        monthTimeItemDatas.add(new SpinnerItemData("6", "6"));
        monthTimeItemDatas.add(new SpinnerItemData("7", "7"));
        monthTimeItemDatas.add(new SpinnerItemData("8", "8"));
        monthTimeItemDatas.add(new SpinnerItemData("9", "9"));
        monthTimeItemDatas.add(new SpinnerItemData("10", "10"));
        monthTimeItemDatas.add(new SpinnerItemData("11", "11"));
        monthTimeItemDatas.add(new SpinnerItemData("12", "12"));
    }

    /******************************************产值***********************************************/
    // 点击年选择使月和季度重新开始选择
    public SingleLiveEvent<Void> selectZczYearEvent = new SingleLiveEvent<>();
    //年
    public String selectZczYearTimeNum = "";
    // 季度
    public String selectZczQuarterTimeNum = "";
    //月
    public String selectZczMonthTimeNum = "";

    // 年时间条件选择
    public BindingCommand<IKeyAndValue> onZczYearTimeSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            selectZczYearTimeNum = iKeyAndValue.getValue();
            if (!"0".equals(selectZczYearTimeNum)) {
                selectZczYearEvent.call();
               // requestHomeDatasType("cz", "year", selectZczYearTimeNum + "-1");
            }
        }
    });

    // 季度
    public BindingCommand<IKeyAndValue> onZczQuarterTimeSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            if (!"0".equals(selectZczYearTimeNum)) {
                selectZczQuarterTimeNum = iKeyAndValue.getValue();
                if (!"0".equals(selectZczQuarterTimeNum)) {
                  //  requestHomeDatasType("cz", "", selectZczYearTimeNum + "-" + selectZczQuarterTimeNum);
                }
            }/* else {
                ToastUtils.showShort("请选择年！");
            }*/
        }
    });
    // 月
    public BindingCommand<IKeyAndValue> onZczMonthTimeSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            if (!"0".equals(selectZczYearTimeNum)) {
                selectZczMonthTimeNum = iKeyAndValue.getValue();
                if (!"0".equals(selectZczMonthTimeNum)) {
                   // requestHomeDatasType("cz", "month", selectZczYearTimeNum + "-" + selectZczMonthTimeNum);
                }
            }/* else {
                ToastUtils.showShort("请选择年！");
            }*/
        }
    });
    /******************************************税收***********************************************/
    // 点击年选择使月和季度重新开始选择
    public SingleLiveEvent<Void> selectZssYearEvent = new SingleLiveEvent<>();
    //年
    public String selectZssYearTimeNum = "";
    // 季度
    public String selectZssQuarterTimeNum = "";
    //月
    public String selectZssMonthTimeNum = "";

    // 年时间条件选择
    public BindingCommand<IKeyAndValue> onZssYearTimeSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            selectZssYearTimeNum = iKeyAndValue.getValue();
            if (!"0".equals(selectZssYearTimeNum)) {
                selectZssYearEvent.call();
              //  requestHomeDatasType("ss", "year", selectZssYearTimeNum + "-1");
            }
        }
    });

    // 季度
    public BindingCommand<IKeyAndValue> onZssQuarterTimeSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            if (!"0".equals(selectZssYearTimeNum)) {
                selectZssQuarterTimeNum = iKeyAndValue.getValue();
                if (!"0".equals(selectZssQuarterTimeNum)) {
                   // requestHomeDatasType("ss", "", selectZssYearTimeNum + "-" + selectZssQuarterTimeNum);
                }
            }/* else {
                ToastUtils.showShort("请选择年！");
            }*/
        }
    });
    // 月
    public BindingCommand<IKeyAndValue> onZssMonthTimeSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            if (!"0".equals(selectZssYearTimeNum)) {
                selectZssMonthTimeNum = iKeyAndValue.getValue();
                if (!"0".equals(selectZssMonthTimeNum)) {
                  //  requestHomeDatasType("ss", "month", selectZssYearTimeNum + "-" + selectZssMonthTimeNum);
                }
            } /*else {
                ToastUtils.showShort("请选择年！");
            }*/
        }
    });


}

package com.journey.org.ui.custom;

import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;
/**
 * 
 * @author 逍遥
 * @Date 2019/10/15
 *
 */
public class SpinnerItemData implements IKeyAndValue {

    private String key;
    private String value;

    public SpinnerItemData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}

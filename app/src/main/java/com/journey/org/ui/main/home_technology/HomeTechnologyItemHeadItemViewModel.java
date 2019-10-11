package com.journey.org.ui.main.home_technology;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.journey.org.ui.main.home_technology.page_chart.PageChartFragment;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class HomeTechnologyItemHeadItemViewModel extends ItemViewModel<HomeTechnologyViewModel> {

    public String title;
    public Drawable drawable;

    public HomeTechnologyItemHeadItemViewModel(@NonNull HomeTechnologyViewModel viewModel, String title, Drawable drawable) {
        super(viewModel);
        this.title = title;
        this.drawable = drawable;
    }

    public BindingCommand onClickHeadItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (title) {

                case "图表":
                    viewModel.startContainerActivity(PageChartFragment.class.getCanonicalName());
                    break;
            }
        }
    });
}

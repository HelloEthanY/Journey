package com.journey.org.ui.main.home_page.page_detail;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.manager.HttpManager;
import com.journey.org.data.home_page.PageDetailVideoEntity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 景区详情头布局
 *
 * @author 逍遥
 * @Date 2019/7/30
 */
public class PageDetailItemHeadViewModel extends MultiItemViewModel<PageDetailViewModel> {

    // public String videoPath = "https://cdn.yihtc.com/jiuzhai/video/sangji-niao.mp4";
    public String videoPath = "http://flv3.bn.netease.com/tvmrepo/2018/6/H/9/EDJTRBEH9/SD/EDJTRBEH9-mobile.mp4";

    public String urlSecond = "http://img2.imgtn.bdimg.com/it/u=2008708245,2006465819&fm=26&gp=0.jpg";

    public String urlFirst = "http://img5.imgtn.bdimg.com/it/u=1034050540,225879325&fm=26&gp=0.jpg";

    public PageDetailItemHeadViewModel(@NonNull PageDetailViewModel viewModel) {
        super(viewModel);
        loadData();
    }

    /*****************************************景区信息*********************************************/
    public ItemBinding<HeadInfoItemViewModel> infoItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_page_head_info);

    public ObservableList<HeadInfoItemViewModel> infoItems = new ObservableArrayList<>();
    // 订景区门票
    public BindingCommand onClickBookingCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.onClickBookingEvent.call();
        }
    });

    // 点击地理位置
    public BindingCommand onClickBaiduMapCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.onClickBaiMapEvent.call();
        }
    });

    // 点击景区图片
    public BindingCommand onClickScenicImgCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.onClickScenicImgEvent.call();
        }
    });


    // 加载数据
    public void loadData() {
        // 景区信息数据
        infoItems.add(new HeadInfoItemViewModel(viewModel, "中文名称：", "黄果树瀑布"));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "宽度：", "101米"));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "瀑布类型：", "侵蚀裂典型瀑布"));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "开放时间：", "全天"));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "所属水系：", "珠江.西江.南盘江.北盘江.打帮河 "));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "门票价格：", "旺季180.00元 淡季160.00元 "));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "地理位置：", "贵州安顺市镇宁县"));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "著名景点：", "坝陵河大桥"));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "所属地形：", "喀斯特地形地区"));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "建议游玩时长：", "3-4小时"));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "高：", "77.8米"));
        infoItems.add(new HeadInfoItemViewModel(viewModel, "适宜游玩季节：", "6月-10月最佳 "));
    }
}

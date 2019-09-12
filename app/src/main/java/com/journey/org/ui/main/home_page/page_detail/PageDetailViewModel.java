package com.journey.org.ui.main.home_page.page_detail;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.journey.org.BR;
import com.journey.org.R;
import com.journey.org.app.base.BaseToolbarViewModel;
import com.journey.org.app.manager.HttpManager;
import com.journey.org.data.home_page.PageDetailBodyEntity;
import com.journey.org.data.home_page.PageDetailVideoEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * 首页详情
 *
 * @author 逍遥
 * @Date 2019/7/29
 */
public class PageDetailViewModel extends BaseViewModel {
    // 页数
    public int page = 0;
    // 头布局类型
    public static final String DETAIL_HEAD = "detail_head";
    // 体布局类型
    public static final String DETAIL_BODY = "detail_body";

    public PageDetailViewModel(@NonNull Application application) {
        super(application);
    }

    // toolbar 标题
    public ObservableField<String> toolbarTitle = new ObservableField<>();
    // 菜单按钮点击事件回调
    public SingleLiveEvent<Void> onClickMenuEvent = new SingleLiveEvent<>();

    // 点击结束按钮
    public BindingCommand onClickFinishCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    //点击菜单
    public BindingCommand onClickMenuCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onClickMenuEvent.call();
        }
    });

    /*******************************************头布局**************************************/
    // 地理位置点击事件传递
    public SingleLiveEvent<Void> onClickBaiMapEvent = new SingleLiveEvent<>();
    // 订景区门票
    public SingleLiveEvent<Void> onClickBookingEvent = new SingleLiveEvent<>();
    // 景区图片集合
    public SingleLiveEvent<Void> onClickScenicImgEvent = new SingleLiveEvent<>();

    /*****************************************视频列表*********************************************/
    public ItemBinding<HeadVideoItemViewModel> videoItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_page_head_video);

    public ObservableList<HeadVideoItemViewModel> videoItems = new ObservableArrayList<>();
    // 视频列表点击事件传递
    public SingleLiveEvent<PageDetailVideoEntity.ItemListBean> onVideoPathEvent = new SingleLiveEvent<>();
    /*******************************************多布局的景区详情**************************************/
    public ItemBinding<MultiItemViewModel> itemBinding = ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MultiItemViewModel item) {
            String itemType = (String) item.getItemType();
            if (DETAIL_HEAD.equals(itemType)) { // 头布局
                itemBinding.set(BR.viewModel, R.layout.item_page_detail_head);
            } else { // 体布局
                itemBinding.set(BR.viewModel, R.layout.item_page_detail_body);
            }
        }
    });

    public ObservableList<MultiItemViewModel> items = new ObservableArrayList<>();

    // 加载数据
    public void loadData() {
        // 添加头布局 数据
        MultiItemViewModel itemViewModel = new PageDetailItemHeadViewModel(this);
        itemViewModel.multiItemType(DETAIL_HEAD);
        items.add(itemViewModel);

        // 交通
        PageDetailBodyEntity bodyEntity0 = new PageDetailBodyEntity();
        List<PageDetailBodyEntity.BodyBean> bodyBeanList0 = new ArrayList<>();
        bodyEntity0.setTitle("旅游贴士");
        PageDetailBodyEntity.BodyBean bean0 = new PageDetailBodyEntity.BodyBean();
        bean0.setBodyTitle("");
        bean0.setBodyContent("    交通：\n" +
                "1、凯里客车站乘坐往西江镇的中巴，车费20—25元，45分钟左右可到达，交通顺畅，中班车次不多。或者在凯里花20元打的士直达西江。\n" +
                "2、凯里客车站坐雷山车，半小时一趟，一个小时路程，12.5元/人，然后在雷山车站换乘到西江的中巴，10元/人，早上七点到下午五点四十，每小时一班。雷山到西江包车，面的70元。\n" +
                "住宿：\n" +
                "农家乐备有很好的吃住条件，基本满足客人的要求。");
        bodyBeanList0.add(bean0);
        bodyEntity0.setBodyBeans(bodyBeanList0);
        MultiItemViewModel itemViewModel0 = new PageDetailItemBodyViewModel(this, bodyEntity0);
        itemViewModel0.multiItemType(DETAIL_BODY);
        items.add(itemViewModel0);

        // 历史沿革
        PageDetailBodyEntity bodyEntity1 = new PageDetailBodyEntity();
        List<PageDetailBodyEntity.BodyBean> bodyBeanList1 = new ArrayList<>();
        bodyEntity1.setTitle("历史沿革");
        PageDetailBodyEntity.BodyBean bean1 = new PageDetailBodyEntity.BodyBean();
        bean1.setBodyTitle("");
        bean1.setBodyContent("    5000多年前，生活在黄河中下游平原地区的九黎部落在向北扩张，与东进和南下的炎帝、黄帝部落发生剧烈的武力冲突，经过长时间的征战，以蚩尤为首的九黎部落在涿鹿地区被击败，蚩尤被黄帝擒杀。大部分苗族先民被迫开始第一次大迁徙，放弃黄河中下游地区而退回到长江中下游平原，并于洞庭湖和鄱阳湖之滨建立了“三苗国”。随着三苗部落的日渐强大，尧、舜多次对“三苗”进行征剿。舜帝位，“南巡狩猎”，对不服舜帝管制的“三苗”进一步攻掠，苗族先民再次被迫向西南和西北地区迁徙，其中被迫向西北迁徙的这支苗族先民一部分融合于“羌人”，成为西羌的先民，一部分则因人口增多，耕地少而向平原地区迁徙，从青海往南到四川南部、云南东部、贵州西部，有的更向南、向西深入老挝、越南等地。而往西南迁徙的苗族先民则与楚人和睦相处，成为后来“楚蛮”的主要成员。 [1] \n" +
                "战国时期，秦灭楚，一部分苗族背井离乡，长途跋涉西迁，进入武陵山区的五溪一带，形成历史上著名的“武陵蛮”。\n" +
                "西汉时期，这部分苗族先民在这里较快地发展起来，形成与汉王朝相抗衡的一股势力。\n" +
                "公元47年，汉王朝派出军队征剿“武陵蛮”，迫使苗族再次离乡背井，一部分进入黔东北地区（今铜仁一带），一部分南下广西融水，后又溯都柳江而上到达今天的榕江、雷山、台江、施秉等地。苗族在数次大迁徙中，分化成了许多不同的分支。其中，柳氏族、西氏族、尤氏族、苟氏族等几乎是同时到达贵州榕江，由于西氏族在榕江多处辗转，到达西江的时间晚于柳氏族。西氏族到达西江的年代约在600多年以前，但在西氏族到达以前，这里已经居住着苗族“赏”氏族。西江地名中的“西”指西氏族，“江”通“讨”，即西江是“西”氏族向“赏”氏族讨来的地方，“西江”因此而得名。“西”氏族到达并定居在西江以后，陆续又有其他苗族分支迁来，形成以“西”氏族为主体的苗族融合体。传说西江有千年以上历史。西江苗族和苗族先祖蚩尤之间有着密切的关系。根据《林荫记》中记录的西江苗族子连父名的世系谱，从蚩尤到1732年间共有284代，说明生活在西江的苗族是蚩尤的直系后裔。 [1] \n" +
                "春秋战国时期，雷山属牂牁国与且兰国之边地，战国时属大夜郎国，秦时属象郡且兰县边境，西汉时处且兰、毋敛两县之间，东汉时属毋敛县，三国属蜀国之牂牁郡辖之边地，魏晋时期属牂牁郡宾化县境，唐朝时属于罗恭县，五代至宋朝属夔州路绍庆府羁縻州，元初属“管外苗族地区”，元朝中期属湖广省播州宣慰司，明属管外苗族地区。 [2] \n" +
                "西江千户苗寨\n" +
                "西江千户苗寨(6张)\n" +
                "清乾隆年间，清政府为了管理苗疆，对苗族人民实行编户定籍，强行取消苗族子连父名的传统，用苗名的谐音来定汉姓，西江境内苗族的蒋、唐、侯、杨、董、宋、顾、龙、陆、李、梁、毛、陈、金、吴等姓就是由此而来。从秦汉到元、明、清初，雷公山大山区朝廷的设置虽有涉及，但郡县制、羁縻州对这一地区的统治极弱，甚至没有直接治理，在历史上多被称为“蛮荒之地”、“生苗”、“生界”等。\n" +
                "雍正七年（1729年），贵州巡抚张广泗开辟苗疆，设“新疆六厅”，置丹江厅，下辖丹江卫和凯里卫，西江属丹江卫。\n" +
                "乾隆三年（1738年），丹江卫设置了分土司，包括黄茅岭司、鸡讲司、乌叠司，鸡讲司就位于现西江西南附近的营上村，从此西江被列入中原政权的治理范围。\n" +
                "1914年，丹江改厅称县，西江属其辖内。 [1] \n" +
                "1944年，置雷山设置局，西江复归雷山管辖，改为西江镇。\n" +
                "1945年，丹江撤县，西江改归台江县管辖。\n" +
                "1950年，雷山设立县人民政府，西江属于第二区公所。 [1] \n" +
                "1954年，建立雷山县苗族自治区，西江千户苗寨所在地属西江区，1959年，雷山、炉山、丹寨、麻江并入凯里大县，西江属于凯里县的雷山片。\n" +
                "1961年，恢复雷山县，建丹江、西江、大塘、永乐四区、44个公社，千户苗寨当时属于西江区西江镇。1992年，撤区并乡后，千户苗寨属于西江镇管辖至今。 [1]  ");
        bodyBeanList1.add(bean1);
        bodyEntity1.setBodyBeans(bodyBeanList1);
        MultiItemViewModel itemViewModel1 = new PageDetailItemBodyViewModel(this, bodyEntity1);
        itemViewModel1.multiItemType(DETAIL_BODY);
        items.add(itemViewModel1);


        // 地理环境
        PageDetailBodyEntity bodyEntity2 = new PageDetailBodyEntity();
        List<PageDetailBodyEntity.BodyBean> bodyBeanList2 = new ArrayList<>();
        bodyEntity2.setTitle("地理环境");
        PageDetailBodyEntity.BodyBean bean21 = new PageDetailBodyEntity.BodyBean();
        bean21.setBodyTitle("地理位置");
        bean21.setBodyContent("    西江千户苗寨，位于贵州省黔东南苗族侗族自治州雷山县东北部的雷公山麓，距离县城36千米，距离黔东南州州府凯里35千米，距离省会贵阳市约200千米。由10余个依山而建的自然村寨相连成片，是目前中国乃至全世界最大的苗族聚居村寨。");

        PageDetailBodyEntity.BodyBean bean22 = new PageDetailBodyEntity.BodyBean();
        bean22.setBodyTitle("地形地貌");
        bean22.setBodyContent("    西江千户苗寨所在地形为典型河流谷地，清澈见底的白水河穿寨而过，苗寨的主体位于河流东北侧的河谷坡地上。千百年来，西江苗族同胞在这里日出而耕，日落而息，在苗寨上游地区开辟出大片的梯田，形成了农耕文化与田园风光。");

        PageDetailBodyEntity.BodyBean bean23 = new PageDetailBodyEntity.BodyBean();
        bean23.setBodyTitle("地理气候");
        bean23.setBodyContent("    西江千户苗寨属亚热带湿润山地季风气候，年降水量约1300～1500毫米，年平均气温14～16℃，冬无严寒，夏无酷暑，清凉宜人。");

        bodyBeanList2.add(bean21);
        bodyBeanList2.add(bean22);
        bodyBeanList2.add(bean23);
        bodyEntity2.setBodyBeans(bodyBeanList2);
        MultiItemViewModel itemViewModel2 = new PageDetailItemBodyViewModel(this, bodyEntity2);
        itemViewModel2.multiItemType(DETAIL_BODY);
        items.add(itemViewModel2);

        // 自然资源
        PageDetailBodyEntity bodyEntity3 = new PageDetailBodyEntity();
        List<PageDetailBodyEntity.BodyBean> bodyBeanList3 = new ArrayList<>();
        bodyEntity3.setTitle("自然资源");
        PageDetailBodyEntity.BodyBean bean31 = new PageDetailBodyEntity.BodyBean();
        bean31.setBodyTitle("");
        bean31.setBodyContent("    西江千户苗寨境内河流长度为16.8公里，流域面积为65.39平方公里，平均比降45.7%，最大洪水流量455立方米/秒，最枯流量0.25立方米/秒，年平均流量为1.84立方米/秒。探明的矿点：开觉和白水河硅矿各1个，主要含砷、铅、锌等。开觉矿点可供开采50年以上。其他自然资源有森林资源和水资源等，境内森林覆盖率85.15%，有植物杉树、松树、枫香树、板栗树、青杠树、樟树、茶子树、映山红等居多。桂皮、木姜、杞木、杜仲、五倍子等几百种树种和药材、果树、茶树等。");
        bodyBeanList3.add(bean31);
        bodyEntity3.setBodyBeans(bodyBeanList3);
        // 添加体布局数据
        MultiItemViewModel itemViewModel3 = new PageDetailItemBodyViewModel(this, bodyEntity3);
        itemViewModel3.multiItemType(DETAIL_BODY);
        items.add(itemViewModel3);

        // 景点景观
        PageDetailBodyEntity bodyEntity4 = new PageDetailBodyEntity();
        List<PageDetailBodyEntity.BodyBean> bodyBeanList4 = new ArrayList<>();
        bodyEntity4.setTitle("景点景观");
        PageDetailBodyEntity.BodyBean bean41 = new PageDetailBodyEntity.BodyBean();
        bean41.setBodyTitle("吊脚楼");
        bean41.setBodyContent("    西江千户苗寨的苗族建筑以木质的吊脚楼为主，为穿斗式歇山顶结构。分平地吊脚楼和斜坡吊脚楼两大类，一般为三层的四榀三间或五榀四间结构。底层用于存放生产工具、关养家禽与牲畜、储存肥料或用作厕所。第二层用作客厅、堂屋、卧室和厨房，堂屋外侧建有独特的“美人靠”，苗语称“阶息”，主要用于乘凉、刺绣和休息，是苗族建筑的一大特色。第三层主要用于存放谷物、饲料等生产、生活物资。 [4]  西江苗族吊脚楼源于上古居民的南方干栏式建筑，运用长方形、三角形、菱形等多重结构的组合，构成三维空间的网络体系，与周围的青山绿水和田园风光融为一体，和谐统一，相得益彰，是中华上古居民建筑的活化石；在建筑学等方面具有很高的美学价值。 [4]  反映苗族居民珍惜土地、节约用地的民族心理，在我国当前人多地少的形势下具有积极的教育意义。上梁的祝辞和立房歌，具有浓厚的苗族宗教文化色彩。是苗族传统文化重要的承载者。");

        PageDetailBodyEntity.BodyBean bean42 = new PageDetailBodyEntity.BodyBean();
        bean42.setBodyTitle("风雨桥");
        bean42.setBodyContent("    出于改善村寨风水条件和方便居民生活考虑，多数苗寨在村寨附近建有风雨桥，以关风蓄气和挡风遮雨。西江以前有风雨木桥，主要有平寨通往欧嘎的平寨风雨桥和南贵村关锁整个西江大寨风水的南寿风雨桥。由于是木质结构，几经修复又被洪水冲毁。2008年西江修建的风雨桥有五座，是连接大寨和西江中学的弓形水泥风雨桥、主道一号弓形水泥风雨桥、连接大寨对面的也薅寨二号及四号弓形水泥风雨桥、连接南贵弓形水泥风雨桥，由于以前的风雨桥的建造属全木式结构，容易被大水冲垮，现所修建的风雨桥全采用水泥和木材的混合结构，使得风雨桥的坚实性和抵御洪水的能力大大增加。");

        PageDetailBodyEntity.BodyBean bean43 = new PageDetailBodyEntity.BodyBean();
        bean43.setBodyTitle("千户灯夜景");
        bean43.setBodyContent("    每到黄昏时分，千家万户就亮起了灯。随着天色越来越暗，西江千户苗寨变成了灯的海洋，可以看到苗寨呈现那牛头的形状。为使游客更好地观赏西江千户苗寨夜景，景区在山坡高处的路边修建了观景台，还开通了观光车。");

        PageDetailBodyEntity.BodyBean bean44 = new PageDetailBodyEntity.BodyBean();
        bean44.setBodyTitle("苗寨歌舞");
        bean44.setBodyContent("    每天上午和下午各有一场由当地苗族同胞表演的民族歌舞节目，\n" +
                "苗族歌舞\n" +
                "苗族歌舞\n" +
                "苗族人自己表演的歌舞节目有当地的色彩，华丽的服饰、欢快的歌舞和美丽的爱情故事能使你更加了解苗族的人文风情。\n" +
                "苗族古歌演唱，演唱者全是寨中的老人，用苗族古语演唱其史诗般宏大的古歌（苗族古歌有四部分，涵括\n" +
                "万物起源、天地洪荒及辛酸迁徙史等）能就此传承下去，也是一大功德。遇到特别活动或是有重要人物出现，还是能够看到掌坳的铜鼓舞、方祥的高排芦笙、反排的木鼓舞等。");

        bodyBeanList4.add(bean41);
        bodyBeanList4.add(bean42);
        bodyBeanList4.add(bean43);
        bodyBeanList4.add(bean44);
        bodyEntity4.setBodyBeans(bodyBeanList4);
        // 添加体布局数据
        MultiItemViewModel itemViewModel4 = new PageDetailItemBodyViewModel(this, bodyEntity4);
        itemViewModel4.multiItemType(DETAIL_BODY);
        items.add(itemViewModel4);

        // 民俗文化
        PageDetailBodyEntity bodyEntity5 = new PageDetailBodyEntity();
        List<PageDetailBodyEntity.BodyBean> bodyBeanList5 = new ArrayList<>();
        bodyEntity5.setTitle("民俗文化");
        PageDetailBodyEntity.BodyBean bean51 = new PageDetailBodyEntity.BodyBean();
        bean51.setBodyTitle("西江苗族");
        bean51.setBodyContent("    西江苗族是黔东南苗族的重要组成部分之一，现主要居住的是苗族的“西”氏族。\n" +
                "作为全世界最大的苗寨，西江千户苗寨拥有深厚的苗族文化底蕴，苗族建筑、服饰、银饰、语言、饮食、传统习俗不但典型，而且保存较好。西江苗族过去穿长袍，包头巾头帕，颜色都是黑色的，故称“黑苗”，也称“长裙苗”。西江苗族的语言属于汉藏语系苗瑶语族苗语支中部方言的北部次方言，这里现使用的文字是通用的汉语言文字，尽管汉语言是西江苗族与外界交流的必备语言工具，但苗族之间的语言交流仍然使用传统的苗语。也是重要的苗族之一，这也是黔东南的重要的代表之一。");

        PageDetailBodyEntity.BodyBean bean52 = new PageDetailBodyEntity.BodyBean();
        bean52.setBodyTitle("管理方式");
        bean52.setBodyContent("    清雍正以前，地方事务多由自然领袖管理，与汉族地区有显著差别，实行自主管理内部事务。西江苗族的自然领袖主要包括“方老”、“寨老”、“族老”、“理老”、“榔头”、“鼓藏头”、“活路头”等，不同性质的自然领袖其职责也不同，相互之间具有分工协作的性质，共同维护苗寨的安全与利益。“方老”是自然地方的最高领袖，每个自然地方下辖若干相互有密切联系的村寨，“寨老”是每个苗寨的最高领袖，“族老”则是某一家族的领袖，“理老”一般由德高望众、学识丰富的人担任，主要负责民间纠纷的调解、裁断，“榔头”主要负责刑罚，维持地方治安，“鼓藏头”负责召集和主持祭祀、祭祖活动，“活路头”则主持安排农业生产，是苗寨“农业部部长”。其中，鼓藏头和活路头是世袭的，而其他自然领袖一般是群众选举出来的。“议榔”是苗族社会为了维护地方治安和社会秩序，由方老、寨老、榔头等组织的群众议事会，以对内部的各种重要纠纷和外敌入侵进行商议、决断。议榔大会一般每年举行一次，如果社会安定，无争无议，也可两、三年举行一次，遇外敌来犯时则临时召开。西江的议榔一般是分头在各寨子的风景林中举行。清政府在苗疆实施“改土归流”后，西江苗寨接受中央政府的管辖，方老、寨老等自然领袖已基本不存在了，但负责祭祀和生产的鼓藏头与活路头仍得以世袭保留至今。长期以来，农业一直在西江千户苗寨产业结构中占据着绝对的优势地位。刀耕火种的农业生产方式虽能养活生活在这里的数千人口，人们过着世外桃源般自给自足生活，由于与外界联系甚少，社会经济发展速度较为缓慢。");

        bodyBeanList5.add(bean51);
        bodyBeanList5.add(bean52);

        bodyEntity5.setBodyBeans(bodyBeanList5);
        MultiItemViewModel itemViewModel5 = new PageDetailItemBodyViewModel(this, bodyEntity5);
        itemViewModel5.multiItemType(DETAIL_BODY);
        items.add(itemViewModel5);

        // 景区荣誉
        PageDetailBodyEntity bodyEntity6 = new PageDetailBodyEntity();
        List<PageDetailBodyEntity.BodyBean> bodyBeanList6 = new ArrayList<>();
        bodyEntity6.setTitle("景区荣誉");
        PageDetailBodyEntity.BodyBean bean6 = new PageDetailBodyEntity.BodyBean();
        bean6.setBodyTitle("");
        bean6.setBodyContent("    1982年，西江苗寨被贵州省人民政府列为贵州东线民族风情旅游景点。 [1] \n" +
                "1992年被列为省级文物保护单位。 [1] \n" +
                "2004年被列为全省首期村镇保护和建设项目5个重点民族村镇之一。 [1] \n" +
                "2005年，西江千户苗寨吊脚楼被列入首批国家级非物质文化遗产名录。 [4] \n" +
                "2005年11月“中国民族博物馆西江千户苗寨馆”在此挂牌。");
        bodyBeanList6.add(bean6);
        bodyEntity6.setBodyBeans(bodyBeanList6);
        MultiItemViewModel itemViewModel6 = new PageDetailItemBodyViewModel(this, bodyEntity6);
        itemViewModel6.multiItemType(DETAIL_BODY);
        items.add(itemViewModel6);
    }

    // 请求视频地址
    public void loadVideoListData(boolean isRefresh) {
        if (isRefresh) {
            page = 0;
            videoItems.clear();
        } else {
            page = page + 10;
        }
        HttpManager.getInstance().getPageDetailVideoList()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<PageDetailVideoEntity>() {
                    @Override
                    public void accept(PageDetailVideoEntity entity) throws Exception {
                        if (entity == null && entity.getItemList() == null) {
                            return;
                        }
                        if (entity.getItemList().size() > 0) {
                            onVideoPathEvent.setValue(entity.getItemList().get(0));
                        }
                        for (PageDetailVideoEntity.ItemListBean itemListBean : entity.getItemList()) {
                            videoItems.add(new HeadVideoItemViewModel(PageDetailViewModel.this, itemListBean));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("错误" + throwable.getMessage());
                        KLog.e("=================" + throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }


}

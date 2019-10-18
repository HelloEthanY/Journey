package com.journey.org.app.skyline;

import android.text.TextUtils;

import com.journey.org.app.Config;
import com.skyline.teapi.AltitudeTypeCode;
import com.skyline.teapi.I3DMLFeatureLayer;
import com.skyline.teapi.I3DMLFeatureLayers;
import com.skyline.teapi.IFeature;
import com.skyline.teapi.IFeatures;
import com.skyline.teapi.IGeometry;
import com.skyline.teapi.IMeshLayer;
import com.skyline.teapi.IPoint;
import com.skyline.teapi.IPosition;
import com.skyline.teapi.ISGWorld;
import com.skyline.teapi.ITerraExplorerObject;
import com.skyline.teapi.ITerrain3DPolygon;
import com.skyline.teapi.IntersectionType;
import com.skyline.teapi.TEIUnknownHandle;

import java.util.List;

/**
 * Create Author：goldze
 * Create Date：2019/01/29
 * Description：Skyline操作助手
 */

public class SkylineOptHelper {
    public SkylineOptHelper() {
    }

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓移动↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
    //飞到默认兴趣点
    public IPosition flyDefaultPoint() {
        return flyPoint(Config.X, Config.Y, Config.Altitude, Config.Distance, Config.Pitch, Config.Yaw);
    }

    public IPosition flyPoint(double X, double Y) {
        return flyPoint(X, Y, Config.Altitude, Config.Distance, Config.Pitch, Config.Yaw);
    }

    public IPosition flyPoint(double X, double Y, double Altitude) {
        return flyPoint(X, Y, Altitude, Config.Distance, Config.Pitch, Config.Yaw);
    }

    public IPosition flyPoint(double X, double Y, double Altitude, double Distance) {
        return flyPoint(X, Y, Altitude, Distance, Config.Pitch, Config.Yaw);
    }

    public IPosition flyPoint(double X, double Y, double Altitude, double Distance, double Pitch) {
        return flyPoint(X, Y, Altitude, Distance, Pitch, Config.Yaw);
    }

    //飞到指定经纬度高度
    protected IPosition flyPoint(double X, double Y, double Altitude, double Distance, double Pitch, double Yaw) {
        //构建点对象
        IPosition position = ISGWorld.getInstance().getCreator().CreatePosition();
        position.setX(X);
        position.setY(Y);
        position.setAltitudeType(Config.AltitudeType);
        position.setAltitude(Altitude);
        position.setDistance(Distance);
        position.setPitch(Pitch);
        position.setYaw(Yaw);
        //飞操作
        ISGWorld.getInstance().getNavigate()
                .FlyTo(position);
        return position;
    }
    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑移动↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓画盒子↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
    //绘制盒子
    public ITerrain3DPolygon drawBox(String buffer, double ObjectHeight) {
        IGeometry geometry = ISGWorld.getInstance().getCreator().getGeometryCreator().CreateGeometryFromWKT(buffer);
        ITerrain3DPolygon polygon = ISGWorld.getInstance().getCreator().Create3DPolygon(geometry, ObjectHeight, 0x20FF00FF, 0x20FF00FF, AltitudeTypeCode.ATC_TERRAIN_ABSOLUTE, "", "");
        return polygon;
    }
    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑画盒子↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓单体化↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
    //点亮楼栋
    public IFeature lightUp(String PathName, String Code, double X, double Y, double Z) {
        String treeID = ISGWorld.getInstance().getProjectTree().FindItem(PathName);
        ///这里如果没找到，找遍所有的3dml
        if (!TextUtils.isEmpty(treeID)) {
            ITerraExplorerObject iTerra = ISGWorld.getInstance().getProjectTree().GetObject(treeID);
            IMeshLayer meshlayer = iTerra.CastTo(IMeshLayer.class);
            I3DMLFeatureLayers featureLayers = meshlayer.getFeatureLayers();
            I3DMLFeatureLayer featureLayer = ((TEIUnknownHandle) featureLayers.get_Item(0)).CastTo(I3DMLFeatureLayer.class);
            if ("Unclassified".equals(featureLayer.getTreeItem().getName())) {
                featureLayer = ((TEIUnknownHandle) featureLayers.get_Item(1)).CastTo(I3DMLFeatureLayer.class);
            }
            double[] cVerticesArray = {X, Y, Z};

            IPoint point = ISGWorld.getInstance().getCreator().getGeometryCreator().CreatePointGeometry(cVerticesArray);
            IGeometry geometry = point.getSpatialOperator().buffer(10);
            IFeatures features = featureLayer.ExecuteSpatialQuery(geometry, IntersectionType.IT_INTERSECT);
            for (int i = 0; i < features.getCount(); i++) {
                IFeature feature_Box = ((TEIUnknownHandle) features.get_Item(i)).CastTo(IFeature.class);
                if (feature_Box.getFeatureAttributes().GetFeatureAttribute("Code").getValue().equals(Code)) {
                    feature_Box.getTint().setabgrColor(0x99FFFF00);
//                    ISGWorld.getInstance().getNavigate().JumpTo(feature_Box);
                    return feature_Box;
                }
            }
        }
        return null;
    }

    //清除单体化颜色
    public void cleanFeatureColor(List<IFeature> features) {
        if (features == null || features.size() == 0) {
            return;
        }
        for (IFeature feature : features) {
            if (feature != null && feature.getTint() != null) {
                feature.getTint().setabgrColor(0x00000000);//单体化背景色
            }
        }
        features.clear();
    }

    //设置单体化颜色
    public IFeature setFeatureColor(ITerraExplorerObject obj) {
        IFeature featureBox = obj.CastTo(IFeature.class);        //得到单体化对象
        //  featureBox.getTint().setabgrColor(0x99FFFF00);//单体化背景色
        return featureBox;
    }
    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑单体化↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/

    //清除Item，动态加载的对象Id
    public void deleteItems(List<String> itemIds) {
        if (itemIds == null || itemIds.size() == 0) {
            return;
        }
        for (String itemId : itemIds) {
            //删除树里面的id
            ISGWorld.getInstance().getProjectTree().DeleteItem(itemId);
        }
        itemIds.clear();
    }
}

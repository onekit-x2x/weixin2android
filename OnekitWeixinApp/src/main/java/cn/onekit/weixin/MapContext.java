package cn.onekit.weixin;

import android.view.View;

import java.util.Map;


public abstract class MapContext<MV extends View,M>  {

    protected cn.onekit.weixin.app.Map _onekit_map;

    public abstract MV getMapView();
    public abstract M getMap();
    public MapContext(cn.onekit.weixin.app.Map onekit_map){
        _onekit_map=onekit_map;
    }

    //获取地图当前中心经纬度
    public abstract void getCenterLocation(Map obj);

    //获取地图当前视野范围经纬度
    public abstract void getRegion(Map obj);

    //获取地图当前的旋转角
    public abstract void getRotate(Map obj);

    //获取地图当前的缩放级别
    public abstract void getScale(Map obj);

    //获取地图当前的倾斜角
    public abstract void getSkew(Map obj);

    //缩放视野展示所有经纬度
    public abstract void includePoints(Map obj);

    //将地图中心移置当前定位点, show-location 为true
    public abstract void moveToLocation(Map obj);

    //设置地图中心点偏移
    public abstract void setCenterOffset(Map obj);

    //平移marker，带动画
    public abstract void translateMarker(Map obj);



}

package cn.onekit.weixin.app.core.map;

import android.view.MotionEvent;
import android.view.View;

import com.tencent.map.geolocation.TencentLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WeixinMap<MV extends View,M> {

    public boolean isLoaded = false;
    public Map _todo = new HashMap();
    private MV _mapView;
    public final MV getMapView() {
        return _mapView;
    }
    protected cn.onekit.weixin.app.Map _onekit_map;
    ////////////////////构造/////////////////////
    protected WeixinMap(cn.onekit.weixin.app.Map onekit_map, MV mapView){
        _onekit_map = onekit_map;
        if(    this._mapView!=null){
            onekit_map.removeView(_mapView);
            _mapView=null;
        }
        this._mapView = mapView;
        onekit_map.addView(mapView);
        _init();

    }
    protected abstract void _init();
    public abstract M getMap();
    ////////////////////属性/////////////////////
    protected double temp_latitude = 0;
    protected double temp_longitude = 0;

    public abstract float getScale();
    public abstract void setScale(float scale);

    public abstract float getRotate();
    public abstract void setRotate(float rotate);

    public abstract float getSkew();
    public abstract void setSkew(float skew);

    public abstract double getLongitude();
    public abstract void setLongitude(double longitude);

    public abstract double getLatitude();
    public abstract void setLatitude(double latitude);

    public Map<Integer, Object> map_markers = new HashMap<Integer, Object>();

    public abstract void setMarkers(List<Map> markers);

    public List map_polyline = new ArrayList();
    public abstract void setPolyline(List<Map> polyline);

    public List map_circles = new ArrayList();
    public abstract void setCircles(List<Map> circles);

    public List map_controls = new ArrayList();
    public abstract void setControls(List<Map> controls);

    public abstract void setIncludePoints(List<Map> includePoints);

//    protected boolean showLocation = false;
    public abstract boolean getShowLocation();
    public abstract void setShowLocation(boolean showLocation);

    public List map_polygons = new ArrayList();
    public abstract void setPolygons(List<Map> polygons);

    protected Map subkey;
    public abstract Map getSubkey();
    public abstract void setSubkey(Map subkey);

    public abstract int getLayerStyle();
    public abstract void setLayerStyle(int layerStyle);


    public abstract boolean getShowCompass();
    public abstract void setShowCompass(boolean showCompass);

    public abstract boolean getShowScale();
    public abstract void setShowScale(boolean showScale);

    public abstract boolean getEnableOverlooking();
    public abstract void setEnableOverlooking(boolean enableOverlooking);

    public abstract boolean getEnableZoom();
    public abstract void setEnableZoom(boolean enableZoom);

    public abstract boolean getEnableScroll();
    public abstract void setEnableScroll(boolean enableScroll);

    public abstract boolean getEnableRotate();
    public abstract void setEnableRotate(boolean enableRotate);

    public abstract boolean getEnableSatellite();
    public abstract void setEnableSatellite(boolean enableSatellite);

    public abstract boolean getEnableTraffic();
    public abstract void setEnableTraffic(boolean enableTraffic);

    public abstract Map getSetting();
    public abstract void setSetting(Map setting);

    public abstract void setMyLocation(TencentLocation location);

    /////////////////////////////////////////////
    //是否手指操作
    protected boolean isGesture = false;

    public abstract void onStart();

    public abstract void onResume();

    public abstract void onPause();

    public abstract void onStop();

    public abstract void onRestart();

    public abstract void onDestroy();

    public abstract void dispatchTouchEvent(MotionEvent event);

}

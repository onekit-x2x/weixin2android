package cn.onekit.weixin.app.core.map.tencent;
import android.os.Looper;
import android.util.Log;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.tencentmap.mapsdk.map.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.Projection;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import java.util.List;
import java.util.Map;

import cn.onekit.js.Dict;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsString;
import cn.onekit.js.core.function;
import cn.onekit.weixin.MapContext;
import cn.onekit.weixin.core.res.wx_fail;

public  class MapContext_Tencent2D extends MapContext<MapView,TencentMap> {

    private TencentMap _tencentMap;
    private Projection _projection;

    private float[]  _offset;

    @Override
    public MapView getMapView() {
        return ((WeixinMap_Tencent2D)_onekit_map._weixinMap).getMapView();
    }
    @Override
    public TencentMap getMap() {
        return getMapView().getMap();
    }
    public MapContext_Tencent2D(cn.onekit.weixin.app.Map map){
        super(map);
        _init();
    }

    private void _init(){
        _tencentMap = getMap();
        _projection = getMapView().getProjection();
        _offset = new float[]{0.5f, 0.5f};

    }

    //获取地图当前中心经纬度
    public void getCenterLocation(Map obj){
        LatLng center = _tencentMap.getMapCenter();

        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            Dict result = new Dict(){{
                put("longitude", new JsNumber(center.getLongitude()));
                put("latitude", new JsNumber(center.getLatitude()));
            }};
            if(success != null){
                success.invoke(result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("reLaunch :fail");
          //  res.errMsg = "reLaunch :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //获取地图当前视野范围经纬度
    public void getRegion(Map obj){
        LatLngBounds bounds = _projection.getVisibleRegion().getLatLngBounds();

        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            Dict result = new Dict(){{
                put("southwest",new Dict(){{
                    put("latitude",new JsNumber( bounds.getSouthwest().getLatitude()));
                    put("longitude", new JsNumber(bounds.getSouthwest().getLongitude()));
                }});
                put("northeast",new Dict(){{
                    put("latitude",new JsNumber( bounds.getNortheast().getLatitude()));
                    put("longitude", new JsNumber(bounds.getNortheast().getLongitude()));
                }});
            }};
            if(success != null){
                success.invoke(result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("reLaunch :fail");
//            res.errMsg = "reLaunch :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //获取地图当前的旋转角
    public void getRotate(Map obj){

        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            Dict result = new Dict(){{
                put("rotate",new JsNumber( 0));
            }};
            if(success != null){
                success.invoke(result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("reLaunch :fail");
//            res.errMsg = "reLaunch :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //获取地图当前的缩放级别
    public void getScale(Map obj){
        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            Dict result = new Dict(){{
                put("scale",new JsNumber( getMap().getZoomLevel()));
            }};
            if(success != null){
                success.invoke(result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("reLaunch :fail");
//            res.errMsg = "reLaunch :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //获取地图当前的倾斜角
    public void getSkew(Map obj){
        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            Dict result = new Dict(){{
                put("skew",new JsNumber(0));
            }};
            if(success != null){
                success.invoke(result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("reLaunch :fail");
//            res.errMsg = "reLaunch :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //缩放视野展示所有经纬度
    public void includePoints(Map obj){
        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            LatLngBounds bounds = _projection.getVisibleRegion().getLatLngBounds();
            Log.d("bounds", "includePoints: " + bounds.toString());
            for(Map point : (List<Map>)obj.get("points")){
                bounds = bounds.including(new LatLng((double)point.get("latitude"), (double)point.get("longitude")));
            }
            int padding = obj.containsKey("padding") ? Integer.parseInt(obj.get("padding").toString()) : 3;
            Looper.prepare();
            _tencentMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
            Looper.loop();

            Dict result = new Dict(){{
                put("errMsg",new JsString( "includePoints:ok"));
            }};
            if(success != null){
                success.invoke(result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("reLaunch :fail");
//            res.errMsg = "reLaunch :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }

    }

    //将地图中心移置当前定位点, show-location 为true
    public void moveToLocation(Map obj){


        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
           if(!_onekit_map.getShowLocation()){
                throw new Exception("showLocation : false");
            }
            Looper.prepare();
            _tencentMap.animateTo(new LatLng((double)obj.get("latitude"),(double)obj.get("longitude")));
            Looper.loop();
            Dict result = new Dict(){{
                put("errMsg", new JsString("moveToLocation:ok"));
            }};
            if(success != null){
                success.invoke(result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("reLaunch :fail");
//            res.errMsg = "moveToLocation:fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }

    }

    //设置地图中心点偏移
    public void setCenterOffset(Map obj){

        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            float[] offset = (float[])obj.get("offset");
            LatLngBounds bounds = _projection.getVisibleRegion().getLatLngBounds();
            LatLng northEast =  bounds.getNortheast();
            LatLng southWest =  bounds.getSouthwest();
            double lat = _doOffset(southWest.getLatitude(),northEast.getLatitude(),1, offset);
            double lng = _doOffset(northEast.getLongitude(),southWest.getLongitude(),0, offset);
            _offset = offset;
            Log.d("ssssssssss", "setCenterOffset: " + lat + ", "+ lng);

            Looper.prepare();
            _tencentMap.animateTo(new LatLng(lat,lng));
//        _tencentMap.setCenter(new LatLng(lat, lng));
            Looper.loop();

            Dict result = new Dict(){{
                put("errMsg", new JsString("setCenterOffset:ok"));
            }};
            if(success != null){
                success.invoke(result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("moveToLocation:fail");
//            res.errMsg = "moveToLocation:fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }

    }
    private double _doOffset(double v1, double v2, int index, float[] offset){
        double o = offset[index]-_offset[index]+0.5f;
        return v1+(v2-v1)*o;
    }

    //平移marker，带动画
    public void translateMarker(Map obj){

        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            Marker theMarker = (Marker) _onekit_map._weixinMap.map_markers.get(obj.get("markerId"));
            for(Object object_key : obj.keySet()){
                Object object_value = obj.get(object_key);
                switch(object_key.toString()){
                    case "markerId":
                        break;
                    case "destination":
                        theMarker.setPosition(new LatLng((double)((Map)obj.get("destination")).get("latitude"), (double)((Map)obj.get("destination")).get("longitude")));
                        break;
                    case "autoRotate":
                        break;
                    case "rotate":
                        if(object_value != null){
                            theMarker.setRotation(Float.parseFloat(object_value.toString()));
                        }
                        break;
                    case "duration":
                        break;
                    default:
                        break;
                }
            }

            Dict result = new Dict(){{
                put("errMsg",new JsString( "translateMarker:ok"));
            }};

            //动画结束回调
            if(obj.containsKey("animationEnd")){
                ((function)obj.get("animationEnd")).invoke();
            }
            if(success != null){
                success.invoke(result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("translateMarker:fail");
//            res.errMsg = "translateMarker:fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }

    }

}

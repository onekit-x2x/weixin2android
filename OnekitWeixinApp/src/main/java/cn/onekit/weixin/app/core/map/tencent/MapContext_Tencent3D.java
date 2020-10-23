package cn.onekit.weixin.app.core.map.tencent;

import android.util.Log;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.Projection;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.LatLngBounds;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;

import java.util.List;
import java.util.Map;

import cn.onekit.js.JsObject;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsString;
import cn.onekit.js.core.function;
import cn.onekit.weixin.MapContext;
import cn.onekit.weixin.core.res.wx_fail;

public  class MapContext_Tencent3D extends MapContext<MapView,TencentMap> {

    private TencentMap _tencentMap;
    private Projection _projection;
    private float[]  _offset;

    @Override
    public MapView getMapView() {
//        return ((WeixinMap_Tencent3D)_onekit_map._weixinMap).getMapView();
        return (MapView)_onekit_map._weixinMap.getMapView();
    }

    @Override
    public TencentMap getMap() {
        return (TencentMap)_onekit_map._weixinMap.getMap();
    }

    public MapContext_Tencent3D(cn.onekit.weixin.app.Map map){
        super(map);
        _init();
    }
    private void _init(){
        _tencentMap = getMap();
        _projection = _tencentMap.getProjection();
        _offset = new float[]{0.5f,0.5f};
    }

    //获取地图当前中心经纬度
    public void getCenterLocation(Map obj){
        CameraPosition cameraPosition = _tencentMap.getCameraPosition();

        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            JsObject result = new JsObject(){{
                put("longitude", new JsNumber(cameraPosition.target.latitude));
                put("latitude",new JsNumber( cameraPosition.target.longitude));
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

    //获取地图当前视野范围经纬度
    public void getRegion(Map obj){
        LatLngBounds bounds = _projection.getVisibleRegion().latLngBounds;

        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            JsObject result = new JsObject(){{
                put("southwest",new JsObject(){{
                    put("latitude", new JsNumber(bounds.getSouthWest().getLatitude()));
                    put("longitude",new JsNumber( bounds.getSouthWest().getLongitude()));
                }});
                put("northeast",new JsObject(){{
                    put("latitude",  new JsNumber(bounds.getNorthEast().getLatitude()));
                    put("longitude",  new JsNumber(bounds.getNorthEast().getLongitude()));
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
        CameraPosition cameraPosition = _tencentMap.getCameraPosition();
        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            JsObject result = new JsObject(){{
                put("rotate",  new JsNumber(cameraPosition.bearing));
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
        CameraPosition cameraPosition = _tencentMap.getCameraPosition();
        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            JsObject result = new JsObject(){{
                put("scale",  new JsNumber(cameraPosition.zoom));
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
        CameraPosition cameraPosition = _tencentMap.getCameraPosition();
        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            JsObject result = new JsObject(){{
                put("scale",  new JsNumber(cameraPosition.zoom));
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
            LatLngBounds bounds = _projection.getVisibleRegion().latLngBounds;
            Log.d("bounds", "setIncludePoints: " + bounds);
            for(Map point : (List<Map>)obj.get("points")){
                bounds = bounds.including(new LatLng( Double.parseDouble(point.get("latitude").toString()), Double.parseDouble(point.get("longitude").toString()) ));
            }
            int padding = obj.containsKey("padding") ? Integer.parseInt(obj.get("padding").toString()) : 0;
            _tencentMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,padding));
            JsObject result = new JsObject(){{
                put("errMsg",  new JsString("includePoints:ok"));
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
            CameraPosition cameraPosition = _tencentMap.getCameraPosition();
            Log.d("cameraPosition", "moveToLocation: "+ cameraPosition);
            CameraUpdate cameraSigma = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(Double.parseDouble(obj.get("latitude").toString()), Double.parseDouble(obj.get("longitude").toString())), //新的中心点坐标
                    cameraPosition.zoom,  //新的缩放级别
                    cameraPosition.tilt, //俯仰角 0~45° (垂直地图时为0)
                    cameraPosition.bearing)); //偏航角 0~360° (正北方为0)
            _tencentMap.moveCamera(cameraSigma);

            JsObject result = new JsObject(){{
                put("errMsg",  new JsString("moveToLocation:ok"));
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
    public  void setCenterOffset(Map obj){
        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            float[] offset = (float[])obj.get("offset");
            _tencentMap.setCameraCenterProportion(offset[0], offset[1]);
            JsObject result = new JsObject(){{
                put("errMsg",  new JsString("setCenterOffset:ok"));
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

    //平移marker，带动画
    public void translateMarker(Map obj){
        function success = obj.get("success") != null ? (function) obj.get("success") : null;
        function complete = obj.get("complete") != null ? (function) obj.get("complete") : null;
        function fail = obj.get("fail") != null ? (function) obj.get("fail") : null;
        try{
            Map markers = _onekit_map._weixinMap.map_markers;
            Marker theMarker = null;
            if(markers.containsKey(obj.get("markerId"))){
                theMarker = (Marker)markers.get(obj.get("markerId"));
            }else{
                throw new Exception();
            }
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

            JsObject result = new JsObject(){{
                put("errMsg",  new JsString("translateMarker:ok"));
            }};
            if(success != null){
                success.invoke( result);
            }
            if(complete != null){
                complete.invoke(result);
            }
        }catch(Exception e){
            e.printStackTrace();
            wx_fail res = new wx_fail("reLaunch :fail");
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

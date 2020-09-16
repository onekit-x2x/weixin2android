package cn.onekit.weixin.app.core.map.tencent;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.Projection;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMapOptions;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.Circle;
import com.tencent.tencentmap.mapsdk.maps.model.CircleOptions;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.LatLngBounds;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.Polygon;
import com.tencent.tencentmap.mapsdk.maps.model.PolygonOptions;
import com.tencent.tencentmap.mapsdk.maps.model.Polyline;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.onekit.js.Dict;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsString;
import cn.onekit.w3c.Event;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.app.core.map.WeixinMap;

import static thekit.android.Android.dp2px;

public class WeixinMap_Tencent3D extends WeixinMap<MapView,TencentMap> {

    private TencentMap _tencentMap;
    private UiSettings _uiSettings;
    private Projection _projection;
    private long _time;

    public WeixinMap_Tencent3D(cn.onekit.weixin.app.Map _onekit_map) {
        super(_onekit_map, new MapView(_onekit_map.getContext()));
    }
    protected void _init(){
        _time = new Date().getTime();
        _tencentMap = getMap();
        _projection = _tencentMap.getProjection();
        _uiSettings = _tencentMap.getUiSettings();

        _tencentMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(new LatLng(_onekit_map.getLatitude(), _onekit_map.getLongitude()),
                        _onekit_map.getScale(),
                        _onekit_map.getSkew(),
                        _onekit_map.getRotate())));

        //地图加载完成
        _tencentMap.setOnMapLoadedCallback(() -> {
            isLoaded = true;
            _projection = _tencentMap.getProjection();
            _dispatchEvent("updatetap", new Dict(){{
                put("type",  new JsString("updated"));
            }});

            _todo.forEach((key, value) -> {

                switch(key.toString()){
                    case "includePoints":
                        setIncludePoints((List<Map>)value);
                        break;
                    case "setCenterOffset":
                        break;
                }

            });
            _todo.clear();
        });
        //bindmarkerta事件
        _tencentMap.setOnMarkerClickListener(marker -> {
            Map tag = (Map)marker.getTag();
            if(tag.containsKey("markerId")){
                _dispatchEvent("markertap", new Dict(){{
                    put("detail", new Dict());
                    put("markerId",  new JsNumber(Integer.parseInt(tag.get("markerId").toString())));
                    put("type", new JsString( "markertap"));
                }});
            }
            if((boolean)tag.get("hasWindow")){
                if(marker.isInfoWindowShown()){
                   marker.hideInfoWindow();
                }else{
                    marker.showInfoWindow();
                }
            }

            return true;
        });

        //bindcallouttap事件
        _tencentMap.setOnInfoWindowClickListener(new TencentMap.OnInfoWindowClickListener(){

            @Override
            public void onInfoWindowClick(Marker marker) {
                Map tag = (Map)marker.getTag();
                if(tag.containsKey("markerId")){
                    _dispatchEvent("callouttap", new Dict(){{
                        put("detail",new Dict());
                        put("markerId", new JsNumber(Integer.parseInt(tag.get("markerId").toString())));
                        put("type", new JsString("callouttap"));
                    }});
                }
            }

            @Override
            public void onInfoWindowClickLocation(int i, int i1, int i2, int i3) {

            }
        });

        //bindpoitap事件
        _tencentMap.setOnMapPoiClickListener(mapPoi -> {
            Log.d("mapPoi", "_init: " + mapPoi.getName());
            _dispatchEvent("poitap", new Dict(){{
                put("detail", new Dict(){{
                    put("longitude",  new JsNumber(mapPoi.getLatitude()));
                    put("latitude",new JsNumber( mapPoi.getLongitude()));
                    put("name",  new JsString(mapPoi.getName()));
                }});
                put("type", new JsString("poitap"));
            }});
        });

        //bindtap事件
        _tencentMap.setOnMapClickListener(latLng -> {
            Log.d("latLng", "onMapClick: " + latLng);
            _dispatchEvent("tap", new Dict(){{
                put("detail", new Dict(){{
                    put("longitude", new JsNumber(latLng.getLongitude()));
                    put("latitude", new JsNumber(latLng.getLongitude()));
                }});
                put("type", new JsString("tap"));
            }});

        });

        //bindregionchangetap事件
        _tencentMap.setOnCameraChangeListener(new TencentMap.OnCameraChangeListener() {
            private Float _zoom_temp = null;
            // 视图变化中
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if(_zoom_temp == null){
                    _zoom_temp = cameraPosition.zoom;
                    _dispatchEvent("regionchange", new Dict() {{
                        if(isGesture){
                            put("causedBy",new JsString( "gesture"));
                        }else{
                            put("causedBy", new JsString("update"));
                        }
                        put("detail", new Dict() {{
                            put("gesture", null);
                            put("type",new JsString( "begin"));
                        }});
                        put("type", new JsString("begin"));
                    }});
                }

            }
            // 视图变化完成
            @Override
            public void onCameraChangeFinished(CameraPosition cameraPosition) {
                _onekit_map.latitude = cameraPosition.target.latitude;
                _onekit_map.longitude = cameraPosition.target.longitude;
                _onekit_map.scale = cameraPosition.zoom;
                _onekit_map.skew = cameraPosition.tilt;
                _onekit_map.rotate = cameraPosition.bearing;
                float zoom = cameraPosition.zoom;
                Log.d("sssssssss", "onCameraChangeFinished: _zoom_temp:" + _zoom_temp + ", zoom:"+ zoom);
                Dict result = new Dict(){{
                    put("detail",new Dict(){{
                        put("gesture", null);
                        put("type", new JsString("end"));
                        put("rotate",new JsNumber( 0));
                        put("skew", new JsNumber( 0));
                    }});
                    put("timeStamp",new JsNumber( new Date().getTime()));
                    put("type", new JsString("end"));
                }};
                if(!isGesture){
                    result.put("causedBy", new JsString("update"));
                }else if(_zoom_temp == zoom){
                    result.put("causedBy", new JsString("drag"));
                }else{
                    result.put("causedBy", new JsString("scale"));
                }
                _dispatchEvent("regionchange", result);
                _zoom_temp = null;

            }

        });

    }

    @Override
    public TencentMap getMap() {
        if(_tencentMap == null){
            TencentMapOptions tencentMapOptions = new TencentMapOptions();
            tencentMapOptions.setMultipleInfoWindowEnable(true);//显示多个气泡
            _tencentMap = getMapView().getMap(tencentMapOptions);
        }
        return _tencentMap;

    }

    @Override
    public double getLongitude() {
        return  _tencentMap.getCameraPosition().target.longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        if(this.temp_longitude!=longitude) {
            this.temp_longitude=longitude;
            longitudeChanged=true;
        }
            setLatLng();
    }

    @Override
    public double getLatitude() {
        return _tencentMap.getCameraPosition().target.latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        if(this.temp_latitude!=latitude) {
            this.temp_latitude=latitude;
            latitudeChanged=true;
        }
        setLatLng();
    }

    private boolean latitudeChanged = false;
    private boolean longitudeChanged = false;
    private void setLatLng(){
        if(!latitudeChanged && !longitudeChanged){
            return;
        }
            _tencentMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                    new CameraPosition(new LatLng(this.temp_latitude, this.temp_longitude),
                            _onekit_map.getScale(),
                            _onekit_map.getSkew(),
                            _onekit_map.getRotate())));
        latitudeChanged=false;
        longitudeChanged=false;
    }

    @Override
    public float getScale() {
        return _tencentMap.getCameraPosition().zoom;
    }

    @Override
    public void setScale(float scale) {
        _tencentMap.moveCamera(CameraUpdateFactory.zoomTo(scale));
    }
    private Map<String, TextView> _callouts = new HashMap<String, TextView>();
    @Override
    public void setMarkers(List<Map> markers) {
        if(markers == null){
            return ;
        }
        map_markers.forEach((i,item) -> {
            ((Marker)item).remove();
        });

        for(Map marker_item : markers){
            Integer markerId = marker_item.containsKey("id") ? Integer.parseInt(marker_item.get("id").toString()) : null;

            LatLng markerPosition = new LatLng((Double) marker_item.get("latitude"),(Double) marker_item.get("longitude"));
            MarkerOptions markerOptions = new MarkerOptions(markerPosition);
            //存储markerId ， hasWindow等
            Map tag = new HashMap();

            //title , callou共用
            boolean hasWindow = false;
            String display = "BYCLICK";
            TextView calloutInfoWindow = new TextView(_onekit_map.getContext());
            calloutInfoWindow.setBackgroundResource(R.drawable.map_callout);
            GradientDrawable drawable = (GradientDrawable) calloutInfoWindow.getBackground();

            for(Object marker_key : marker_item.keySet()){
                Object marker_value = marker_item.get(marker_key);
                switch(marker_key.toString()){
                    case "id":
                        markerId = Integer.parseInt(marker_value.toString());
                        tag.put("markerId", markerId);
                        break;
                    case "title":
                        String title = marker_value == null ? "" : marker_value.toString();
//                        markerOptions.title(title);
                        calloutInfoWindow.setText(title);
                        hasWindow = true;
                        break;
                    case "zIndex":
                        markerOptions.level(Integer.parseInt(marker_value.toString()));
                        break;
                    case "iconPath":
                        break;
                    case "rotate":
                        markerOptions.rotation(marker_value == null ? 0f : Float.parseFloat(marker_value.toString()));
                        break;
                    case "alpha":
                        markerOptions.alpha(marker_value == null ? 1f : Float.parseFloat(marker_value.toString()));
                        break;
                    case "width":
                        break;
                    case "height":
                        break;
                    case "callout":

                        break;
                    case "label":
                        break;
                    case "anchor":
                        Map anchor= (Map)marker_value;
                        markerOptions.anchor(Float.parseFloat(anchor.getOrDefault("x", 0).toString()), Float.parseFloat(anchor.getOrDefault("y", 0).toString()));
                        break;
                    case "aria-label":
                        tag.put("aria-label", marker_value);
                        break;
                    default :
                        break;
                }
            }
            Marker marker = _tencentMap.addMarker(markerOptions);
            Log.d("marker", "setMarkers: "+ marker.getLevel());
            if(marker_item.containsKey("iconPath") && marker_item.get("iconPath") != null){
                try {
//                    Bitmap bitmap = Bitmap.createBitmap(ASSET.loadImage(marker_item.get("iconPath").toString(), true), 0, 0, dp2px(_tencentMap.getContext(), width), dp2px(_tencentMap.getContext(), height));
//                    marker.setIcon(new BitmapDescriptor(ASSET.loadImage(marker_item.get("iconPath").toString())));
                    marker.setIcon(BitmapDescriptorFactory.fromAsset(marker_item.get("iconPath").toString()));
                }catch(Exception e){
                    Log.d("---------------", "setMarkers: imageLoad-error");
                    e.getStackTrace();
                }
            }
            if(marker_item.containsKey("callout")){
                hasWindow = true;
                Map callout = (Map)marker_item.getOrDefault("callout", new HashMap().toString());
                int borderWidth= 0;
                String borderColor="#000000";
                for(Object callout_key : callout.keySet()){
                    Object callout_value = callout.get(callout_key);
                    switch(callout_key.toString()){
                        case "content":
                            calloutInfoWindow.setText(callout_value == null ? "" : callout_value.toString());
                            break;
                        case "color":
                            calloutInfoWindow.setTextColor(Color.parseColor((String)callout_value));
                            break;
                        case "fontSize":
                            calloutInfoWindow.setTextSize(Float.parseFloat(callout_value.toString()));
                            break;
                        case "borderRadius":
                            drawable.setCornerRadius(Float.parseFloat(callout_value.toString()));
                            break;
                        case "borderWidth":
                            borderWidth = Integer.parseInt(callout_value.toString());
                            break;
                        case "borderColor":
                            borderColor = (String)callout_value;
                            drawable.setColor(Color.parseColor(borderColor));
                            break;
                        case "bgColor":
                            calloutInfoWindow.setBackgroundColor(Color.parseColor((String)callout_value));
                            break;
                        case "padding":
                            int pading=dp2px(Float.parseFloat(callout_value.toString()));
                            calloutInfoWindow.setPadding(pading,pading,pading,pading);
                            break;
                        case "display":
                            display = callout_value.toString();
                            break;
                        case "textAlign":
                            break;
                        default:
                            break;
                    }
                }
                drawable.setStroke(dp2px(borderWidth),Color.parseColor(borderColor));
                calloutInfoWindow.setBackground(drawable);

                _callouts.put(marker.getId(), calloutInfoWindow);
                _tencentMap.setInfoWindowAdapter(new TencentMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return _callouts.get(marker.getId());
                    }
                    @Override
                    public View getInfoContents(Marker marker) {
                        return null;
                    }
                });
                if(display == "ALWAYS"){
                    marker.showInfoWindow();
                }
            }
            tag.put("hasWindow", hasWindow);
            marker.setTag(tag);
            if(markerId != null){
                this.map_markers.put(markerId, marker);
            }
        }
        _dispatchEvent("updatetap", new Dict(){{
            put("type",new JsString(  "updated"));
        }});
    }

    @Override
    public void setPolyline(List<Map> params) {
        if (params == null) {
            return;
        }
        this.map_polyline.forEach(item -> {
            ((Polyline)item).remove();
        });

        for (Map polyline_item : params) {
            PolylineOptions polylineOptions = new PolylineOptions();
            for(Object polyline_key : polyline_item.keySet()){
                Object polyline_value = polyline_item.get(polyline_key);
                switch(polyline_key.toString()){
                    case "points":
                        List<LatLng> latLngs = new ArrayList<LatLng>();
                        ((java.util.ArrayList)polyline_value).forEach(point -> {
                            latLngs.add(new LatLng((double)((Map)point).get("latitude"), (double)((Map)point).get("longitude")));
                        });
                        polylineOptions.addAll(latLngs);
                        break;
                    case "color":
                        polylineOptions.color(Color.parseColor(polyline_value == null ? "#00000000" : (String)polyline_value));
                        break;
                    case "width":
                            polylineOptions.width(dp2px( Float.parseFloat(polyline_value.toString())));
                        break;
                    case "dottedLine":
                        //元素数量必须是偶数个，每对元素分别表示虚线中实线区域的长度，以及空白区域的长度（单位px)
                        if(Boolean.parseBoolean(polyline_value.toString())){
                            polylineOptions.pattern(new ArrayList<Integer>(){{
                                add(20);
                                add(10);
                            }});
                        }
//                        polylineOptions.lineType(PolylineOptions.LineType.LINE_TYPE_IMAGEINARYLINE);
                        break;
                    case "arrowLine":
                        if(Boolean.parseBoolean(polyline_value.toString())){
                            polylineOptions.arrowSpacing(20);
                            if(polyline_item.containsKey("arrowIconPath")){
                                polylineOptions.arrowTexture(BitmapDescriptorFactory.fromAsset(polyline_item.get("arrowIconPath").toString()));
                            }
                        }
                        break;
                    case "arrowIconPath":
                        break;
                    case "borderColor":
                        polylineOptions.borderColor(Color.parseColor(polyline_value == null ? "#00000000" : (String)polyline_value));
                        break;
                    case "borderWidth":
                        polylineOptions.borderWidth(polyline_value == null ? 1f : Float.parseFloat(polyline_value.toString()));
                        break;
                    default:
                        break;
                }
            }
            Polyline polyline = _tencentMap.addPolyline(polylineOptions);
            this.map_polyline.add(polyline);
        }
        _dispatchEvent("updatetap", new Dict(){{
            put("type", new JsString( "updated"));
        }});
    }

    @Override
    public void setCircles(List<Map> circles) {
        if(circles == null){
            return;
        }
        this.map_circles.forEach(item -> {
            ((Circle)item).remove();
        });
        for(Object item :circles){
            Map circles_item = (Map)item;
            LatLng latLng=new LatLng((Double) (circles_item).get("latitude"),(Double) ( circles_item).get("longitude"));
            String color= circles_item.get("color")==null?"#00000000":(String)( circles_item).get("color");
            String fillColor= circles_item.get("fillColor")==null?"00000000":(String) circles_item.get("fillColor");
            String radius= circles_item.get("radius")==null?"0": circles_item.get("radius").toString();
            String strokeWidth = circles_item.get("strokeWidth")==null?"0": circles_item.get("strokeWidth").toString();
            Circle circle = _tencentMap.addCircle(new CircleOptions().
                    center(latLng).
                    radius(dp2px(Float.valueOf(radius))).
                    fillColor(Color.parseColor(fillColor)).
                    strokeColor(Color.parseColor(color)).
                    strokeWidth(dp2px(Float.valueOf(strokeWidth))));
            this.map_circles.add(circle);
        }
        _dispatchEvent("updatetap", new Dict(){{
            put("type", new JsString( "updated"));
        }});
    }

    @Override
    public void setControls(List<Map> controls) {

    }


    @Override
    public void setIncludePoints(List<Map> includePoints) {
        if(!isLoaded){
            _todo.put("includePoints", includePoints);
            return ;
        }
        LatLngBounds bounds = _projection.getVisibleRegion().latLngBounds;
        for(Object item : includePoints){
            Map point = (Map)item;
            bounds = bounds.including(new LatLng( Double.parseDouble(point.get("latitude").toString()), Double.parseDouble(point.get("longitude").toString()) ));
        }
        _tencentMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,0));
    }

    //显示当前定位
    @Override
    public boolean getShowLocation() {
        return false;
    }
    @Override
    public void setShowLocation(boolean showLocation) {
        if(locationMarker != null){
            locationMarker.remove();
            locationMarker = null;
        }
    }

    private Marker locationMarker;
    @Override
    public void setMyLocation(TencentLocation location) {
        if(locationMarker == null){
            locationMarker = _tencentMap.addMarker(new MarkerOptions(new LatLng(location.getLatitude(), location.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromAsset("mylocation.png"))
                    .rotation(location.getBearing())

            );
        }else{
            locationMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
            locationMarker.setRotation(location.getBearing());
        }
    }

    @Override
    public void setPolygons(List<Map> polygons) {
        if (polygons == null) {
            return;
        }
        this.map_polygons.forEach(item -> {
            ((Polygon)item).remove();
        });
        for( Map polygon_item : polygons) {
            PolygonOptions polygonOptions = new PolygonOptions();
            for (Object polygon_key : polygon_item.keySet()) {
                Object polygon_value = polygon_item.get(polygon_key);
                switch (polygon_key.toString()) {
                    case "points":
                        java.util.ArrayList<Map> points = ((java.util.ArrayList) polygon_value);
                        LatLng[] latLngs = new LatLng[points.size()];
                        int i = 0;
                        for(Map point : points){
                            latLngs[i++] = new LatLng((double)point.get("latitude"), (double)point.get("longitude"));
                        }
                        polygonOptions.add(latLngs);
                        break;
                    case "strokeWidth":
                        polygonOptions.strokeWidth(polygon_value == null ? 1f : Float.parseFloat(polygon_value.toString()));
                        break;
                    case "strokeColor":
                        polygonOptions.strokeColor(Color.parseColor(polygon_value == null ? "#00000000" : polygon_value.toString()));
                        break;
                    case "fillColor":
                        polygonOptions.fillColor(Color.parseColor(polygon_value == null ? "#00000000" : polygon_value.toString()));
                        break;
                    case "zIndex":
                        polygonOptions.zIndex(((polygon_value == null ? 0 : Integer.parseInt(polygon_value.toString()))));
                        break;
                    default:
                        break;
                }
            }
            this.map_polygons.add(_tencentMap.addPolygon(polygonOptions));
        }
        _dispatchEvent("updatetap", new Dict(){{
            put("type",new JsString( "updated"));
        }});


    }

    @Override
    public Map getSubkey() {
        return subkey;
    }

    @Override
    public void setSubkey(Map subkey) {
        this.subkey = subkey;
    }

    @Override
    public int getLayerStyle() {
        return 0;
    }

    @Override
    public void setLayerStyle(int layerStyle) {

    }

    @Override
    public float getRotate() {
        return _tencentMap.getCameraPosition().bearing;
    }

    @Override
    public void setRotate(float rotate) {
        CameraPosition cameraPosition = _tencentMap.getCameraPosition();
        _tencentMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(
                    new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude),
                        cameraPosition.zoom,
                        cameraPosition.tilt,
                        rotate
        )));
    }

    @Override
    public float getSkew() {
        return _tencentMap.getCameraPosition().tilt;
    }

    @Override
    public void setSkew(float skew) {
        CameraPosition cameraPosition = _tencentMap.getCameraPosition();
        _tencentMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(
                        new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude),
                        cameraPosition.zoom,
                        skew,
                        cameraPosition.bearing
                )));
    }



    //指南针
    @Override
    public boolean getShowCompass() {
        return _uiSettings.isCompassEnabled();
    }

    @Override
    public void setShowCompass(boolean showCompass) {
        _uiSettings.setCompassEnabled(showCompass);
    }

    //比例尺
    @Override
    public boolean getShowScale() {
        return _uiSettings.isScaleViewEnabled();
    }
    @Override
    public void setShowScale(boolean showScale) {
        _uiSettings.setScaleViewEnabled(showScale);
    }

    //倾斜支持
    @Override
    public boolean getEnableOverlooking() {
        return _uiSettings.	isTiltGesturesEnabled();
    }
    @Override
    public void setEnableOverlooking(boolean enableOverlooking) {
        _uiSettings.setTiltGesturesEnabled(enableOverlooking);
    }

    //缩放支持
    @Override
    public boolean getEnableZoom() {
        return _uiSettings.isZoomGesturesEnabled();
    }
    @Override
    public void setEnableZoom(boolean enableZoom) {
        _uiSettings.setZoomGesturesEnabled(enableZoom);
    }

    //拖动支持
    @Override
    public boolean getEnableScroll() {
        return _uiSettings.isScrollGesturesEnabled();
    }
    @Override
    public void setEnableScroll(boolean enableScroll) {
        _uiSettings.setScrollGesturesEnabled(enableScroll);
    }

    //旋转支持
    @Override
    public boolean getEnableRotate() {
        return _uiSettings.isRotateGesturesEnabled();
    }
    @Override
    public void setEnableRotate(boolean enableRotate) {
        Log.d("enableRotate", "setEnableRotate: "+enableRotate);
        _uiSettings.setRotateGesturesEnabled(enableRotate);
    }

    //卫星图
    @Override
    public boolean getEnableSatellite() {
        return _tencentMap.getMapType() == TencentMap.MAP_TYPE_SATELLITE;
    }
    @Override
    public void setEnableSatellite(boolean enableSatellite) {
        if(enableSatellite){
            _tencentMap.setMapType(TencentMap.MAP_TYPE_SATELLITE);
        }else{
            _tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);
        }
    }

    //实时路况
    @Override
    public boolean getEnableTraffic() {
        return _tencentMap.isTrafficEnabled();
    }
    @Override
    public void setEnableTraffic(boolean enableTraffic) {
        _tencentMap.setTrafficEnabled(enableTraffic);
    }

    @Override
    public Map getSetting() {
        return null;
    }
    @Override
    public void setSetting(Map setting) {

    }


    @Override
    public void onStart() {
        getMapView().onStart();
    }

    @Override
    public void onResume() {
        getMapView().onResume();
    }

    @Override
    public void onPause() {
        getMapView().onPause();
    }

    @Override
    public void onStop() {
        getMapView().onStop();
    }

    @Override
    public void onRestart() {
        getMapView().onRestart();
    }

    @Override
    public void onDestroy() {
        getMapView().onDestroy();
    }

    @Override
    public void dispatchTouchEvent(MotionEvent event){
        if(event.getAction() == 2){
            //手指按下
            isGesture = true;
        }
    }
    /////////////////////////////////////////////////////
    private void _dispatchEvent(String name, Dict result){
        if(name == "updatetap"){
            result.put("timeStamp",new JsNumber( new Date().getTime() - _time));
        }else{
            result.put("timeStamp", new JsNumber( new Date().getTime()));
        }
        Event event = new Event(name, result,_onekit_map,_onekit_map,0);
        Log.d(name, "_dispatchEvent: "+result.toString());
        _onekit_map.dispatchEvent(event);
    }


//    class DemoLocationSource implements LocationSource, TencentLocationListener {
//   private Context mContext;
//   private OnLocationChangedListener mChangedListener;
//   private TencentLocationManager locationManager;
//   private TencentLocationRequest locationRequest;
//
//           public DemoLocationSource(Context context) {
//    // TODO Auto-generated constructor stub
//    mContext = context;
//    locationManager = TencentLocationManager.getInstance(mContext);
//    locationRequest = TencentLocationRequest.create();
//    locationRequest.setInterval(2000);
//   }
//
//   @Override
//   public void onLocationChanged(TencentLocation arg0, int arg1,
//                                 String arg2) {
//    // TODO Auto-generated method stub
//    if (arg1 == TencentLocation.ERROR_OK && mChangedListener != null) {
//     Log.e("maplocation", "location: " + arg0.getCity() + " " + arg0.getProvider());
//     Location location = new Location(arg0.getProvider());
//     location.setLatitude(arg0.getLatitude());
//     location.setLongitude(arg0.getLongitude());
//     location.setAccuracy(arg0.getAccuracy());
//     mChangedListener.onLocationChanged(location);
//    }
//   }
//
//   @Override
//   public void onStatusUpdate(String arg0, int arg1, String arg2) {
//    // TODO Auto-generated method stub
//
//   }
//
//   @Override
//   public void activate(OnLocationChangedListener arg0) {
//    // TODO Auto-generated method stub
//    mChangedListener = arg0;
//    int err = locationManager.requestLocationUpdates(locationRequest, this);
//    switch (err) {
//    case 1:
//        Log.d("errMsg","设备缺少使用腾讯定位服务需要的基本条件");
//     break;
//    case 2:
//        Log.d("errMsg","manifest 中配置的 key 不正确");
//     break;
//    case 3:
//        Log.d("errMsg","自动加载libtencentloc.so失败");
//     break;
//
//    default:
//     break;
//    }
//   }
//
//@Override
//public void deactivate() {
//// TODO Auto-generated method stub
//locationManager.removeUpdates(this);
//mContext = null;
//locationManager = null;
//locationRequest = null;
//mChangedListener = null;
//}
//
//public void onPause() {
//locationManager.removeUpdates(this);
//}
//
//public void onResume() {
//locationManager.requestLocationUpdates(locationRequest, this);
//}
//}

}

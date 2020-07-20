package cn.onekit.weixin.app.core.map.tencent;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationUtils;
import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.raster.model.Polygon;
import com.tencent.mapsdk.raster.model.PolygonOptions;
import com.tencent.mapsdk.raster.model.Polyline;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.Projection;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.tencent.tencentmap.mapsdk.map.UiSettings;
import com.tencent.tencentmap.mapsdk.map.CameraUpdateFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.onekit.ASSET;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsString;
import cn.onekit.w3c.Event;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.app.core.map.WeixinMap;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.onekit.Android.dp2px;

public class WeixinMap_Tencent2D extends WeixinMap<MapView,TencentMap> {

    private TencentMap _tencentMap;
    private UiSettings _uiSettings;
    private long _time;
    private Projection _projection;

    public WeixinMap_Tencent2D(cn.onekit.weixin.app.Map onekit_map) {
        super(onekit_map,new MapView(onekit_map.getContext()));
        _init();
    }

    protected void  _init(){
        _tencentMap = getMap();
        _uiSettings = getMapView().getUiSettings();
        _projection = getMapView().getProjection();

        _tencentMap.setCenter(new LatLng(_onekit_map.getLatitude(), _onekit_map.getLongitude()));
        _tencentMap.setZoom((int)_onekit_map.getScale());//5-19
        _time = new Date().getTime();

        //地图点击事件，包含点击poi
        _tencentMap.setOnMapClickListener(latLng -> {

            String url = String.format("https://www.onekitwx.com/weixin2android/webservice/geocoder?location=%s,%s&key=GU3BZ-TOVHF-CEPJJ-JXSXM-M4B6O-PABGV&get_poi=1", latLng.getLatitude(),latLng.getLongitude());

            OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
            Request request = new Request.Builder().url(url)
                    .get().build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("Fail");
                }

                @Override
                public void onResponse(Call call, Response response) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        _getAddress(latLng, jsonObject.getJSONObject("result"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });


        });

        //maeker点击事件
        _tencentMap.setOnMarkerClickListener(marker -> {
            Log.d("---------onMarkerClick,", "onMarkerClick: "+marker.getTag());
            Map tag = (HashMap)marker.getTag();
            if(tag.containsKey("markerId")){
                Dict result = new Dict(){{
                    put("detail", new Dict());
                    put("markerId", new JsNumber(Integer.parseInt((String)tag.get("markerId"))));
                    put("type", new JsString("markertap"));
                }};
                _dispatchEvent("markertap", result);
            }
            if((boolean)tag.get("hasWindow") && !marker.isInfoWindowShown()){
                marker.showInfoWindow();
            }else{
                marker.hideInfoWindow();
            }
            return false;
        });
        //气泡点击事件
        _tencentMap.setOnInfoWindowClickListener(marker -> {
            Map tag = (Map)marker.getTag();
            if(tag.containsKey("markerId")){
                _dispatchEvent("callouttap", new Dict(){{
                    put("detail",new Dict());
                    put("markerId", new JsNumber(Integer.parseInt((String)tag.get("markerId"))));
                    put("type",new JsString("callouttap"));
                }});
            }
        });

        //视野变化监听
        _tencentMap.setOnMapCameraChangeListener(new TencentMap.OnMapCameraChangeListener() {
            private Float _zoom_temp = null;
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if(_zoom_temp == null){
                    _zoom_temp = cameraPosition.getZoom();
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

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                _onekit_map.latitude = cameraPosition.getTarget().getLatitude();
                _onekit_map.longitude = cameraPosition.getTarget().getLongitude();
                _onekit_map.scale = cameraPosition.getZoom();
                _onekit_map.skew = 0;
                _onekit_map.rotate = 0;

                Log.d("=================================", "getZoomLevel: "+_tencentMap.getZoomLevel());
                Dict result = new Dict(){{
                    put("detail",new Dict(){{
                        put("gesture", null);
                        put("type", new JsString("end"));
                        put("rotate", new JsNumber(0));
                        put("skew",new JsNumber( 0));
                    }});
                    put("timeStamp",new JsNumber(new Date().getTime()));
                    put("type",new JsString( "end"));
                }};
                if(!isGesture){
                    result.put("causedBy", new JsString("update"));
                }else if(_zoom_temp == cameraPosition.getZoom()){
                    result.put("causedBy",new JsString( "drag"));
                }else{
                    result.put("causedBy",new JsString( "scale"));
                }
                _dispatchEvent("regionchange", result);
                _zoom_temp = null;
            }
        });

        //地图加载监听
        _tencentMap.setOnMapLoadedListener(() -> {
            isLoaded = true;
            _dispatchEvent("updatedtap", new Dict(){{
                put("type",new JsString( "updated"));
            }});
            _todo.forEach((key, value) -> {
                if(key == "includePoints"){
                    setIncludePoints((List<Map>)value);
                }

            });
            _todo.clear();

        });


    }


    @Override
    public TencentMap getMap() {
//        if(_tencentMap == null){
//            _tencentMap = getMapView().getMap();
//        }
        return  getMapView().getMap();//_tencentMap;
    }

    private Dict _poitapHandler(Dict before_result, double distance, LatLng clickPoi, double check_lat, double check_lng, String name){
        double check_distance = TencentLocationUtils.distanceBetween(clickPoi.getLatitude(),clickPoi.getLongitude(),check_lat, check_lng);
        Log.d("--------------------------------", "poitap: "+check_distance + "," + name);
        if(check_distance <= distance){
            Dict result = new Dict(){{
                put("result", new Dict(){{
                    put("detail", new Dict(){{
                        put("latitude", new JsNumber(check_lat));
                        put("longitude", new JsNumber(check_lng));
                        put("name", new JsString(name));
                    }});
                    put("timeStamp", new JsNumber(new Date().getTime()));

                }});
                put("distance",new JsNumber( check_distance));
            }};
            if(before_result != null && check_distance > Double.parseDouble(before_result.get("distance").toString())){
                result = before_result;
            }
            return result;
        }
        return before_result;
    }
    private double _calcDistance(int now_zoom){
        double result = 0;
        switch(now_zoom){
            case 5:
                result = 2200000;
                break;
            case 6:
                result = 40000;
                break;
            case 7:
                result = 25000;
                break;
            case 8:
                result = 10000;
                break;
            case 9:
                result = 12000;
                break;
            case 10:
                result = 10000;
                break;
            case 11:
                result = 8000;
                break;
            case 12:
                result = 3000;
                break;
            case 13:
                result = 300;
                break;
            case 14:
                result = 200;
                break;
            case 15:
                result = 150;
                break;
            case 16:
                result = 150;
                break;
            case 17:
                result = 100;
                break;
            case 18:
                result = 50;
                break;
            case 19:
                result = 50;
                break;
        };
        return result;
    }
    //从resources中的raw 文件夹中获取文件并读取数据
    private String _readGeoinfo(){
        InputStream inputStream = null;
        InputStreamReader isReader = null;
        String result = "";
        try {
            //获取文件中的内容
            inputStream = _onekit_map.getResources().openRawResource(R.raw.geoinfo);
            //将文件中的字节转换为字符
            isReader = new InputStreamReader(inputStream, "UTF-8");
            //使用bufferReader去读取字符
            BufferedReader reader = new BufferedReader(isReader);
            String out = "";
            while ((out = reader.readLine()) != null) {
                result += out;
                Log.d("==================reading", "_readGeoinfo: " + out);
            }
            Log.d("---------------over", "_readGeoinfo: "+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                isReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private void _getAddress(LatLng clickPoi, JSONObject data) throws Exception{
        int now_zoom = _tencentMap.getZoomLevel();
        Dict result = null;
        double temp_dis = 999999999;
        JSONObject poi = null;
        JSONObject bossPoi = null;
        Log.d("--------------------------data", "_getAddress: data"+data.length());
        JSONArray pois = data.getJSONArray("pois");
        Log.d("--------------------------data", "_getAddress: pois"+pois.length());

        for(int i = 0 ; i < pois.length() ; i ++){
            Log.d("-----------------------------", "_getAddress:i "+i);
            JSONObject item = pois.getJSONObject(i);
            double item_dis = item.getDouble("_distance");
            if(item_dis == 0){
                bossPoi = item;
            }else  if(item_dis < temp_dis){
                poi  = item;
                temp_dis = item_dis;
            }
        }
        Log.d("--------------------------poi", " poi"+poi);
        Log.d("--------------------------bossPoi", "bossPoi"+bossPoi);

        String province = data.getJSONObject("address_component").getString("province");
        String city = data.getJSONObject("address_component").getString("city");
        String district = data.getJSONObject("address_component").getString("district");
        String street = data.getJSONObject("address_component").getString("street");
        JSONObject town = data.getJSONObject("address_reference").getJSONObject("town");
        double distance = _calcDistance(now_zoom);
        Log.d("-----------------------------distance", "distance: "+distance);
//        Log.d("-------------------------------------------_readGeoinfo()", "_readGeoinfo(): "+_readGeoinfo());
//        JSONArray geoinfo = new JSONArray(_readGeoinfo());
//        JSONObject province_info = null;
//        for(int i = 0 ; i  < geoinfo.length() ; i++ ){
//            JSONObject item = geoinfo.getJSONObject(i);
//            if(item.getString("name") == province){
//                province_info = item;
//            }
//        }

        switch(now_zoom){
            case 5:
                //省份
                result = _poitapHandler(result, distance, clickPoi, data.getJSONObject("ad_info").getJSONObject("location").getDouble("lat"), data.getJSONObject("ad_info").getJSONObject("location").getDouble("lng"), province);
                break;
            case 6:
            case 7:
            case 8:
                //市区
                result =  new Dict(){{
                    put("result", new Dict(){{
                        put("detail", new Dict(){{
                            put("latitude",new JsNumber( clickPoi.getLatitude()));
                            put("longitude", new JsNumber(clickPoi.getLongitude()));
                            put("name", new JsString(city));
                        }});
                    }});
                }};
                break;
            case 9:
                //市区,区/县
                result =  new Dict(){{
                    put("result", new Dict(){{
                        put("detail", new Dict(){{
                            put("latitude", new JsNumber(clickPoi.getLatitude()));
                            put("longitude",new JsNumber( clickPoi.getLongitude()));
                            put("name", new JsString(district));
                        }});
                    }});
                }};
                break;
            case 10:
            case 11:
                //市区,区/县,镇/乡
                result = _poitapHandler(result, distance, clickPoi, town.getJSONObject("location").getDouble("lat"), town.getJSONObject("location").getDouble("lng"), town.getString("title") );
                if(result == null){
                    result = new Dict(){{
                        put("result", new Dict(){{
                            put("detail", new Dict(){{
                                put("latitude", new JsNumber(clickPoi.getLatitude()));
                                put("longitude", new JsNumber(clickPoi.getLongitude()));
                                put("name", new JsString(district));
                            }});
                        }});
                    }};
                }

                break;
            case 12:
            case 13:
                //区/县， 乡/镇， boss点
                //区/县
                result = new Dict(){{
                    put("result", new Dict(){{
                        put("detail", new Dict(){{
                            put("latitude",new JsNumber( clickPoi.getLatitude()));
                            put("longitude", new JsNumber(clickPoi.getLongitude()));
                            put("name", new JsString(district));
                        }});
                    }});
                    put("distance", new JsNumber(3000));
                }};
                if(result != null){
                    break;
                }
            case 14:
                // 乡/镇， boss点, 街道
                result = _poitapHandler(result, distance, clickPoi, town.getJSONObject("location").getDouble("lat"), town.getJSONObject("location").getDouble("lng"), town.getString("title"));
                if(  result == null && bossPoi != null){
                    result = _poitapHandler(result, distance, clickPoi, bossPoi.getJSONObject("location").getDouble("lat"), bossPoi.getJSONObject("location").getDouble("lng"),bossPoi.getString("title"));
                }
                break;
            case 15:
                //只显示boss点
                if(bossPoi != null){
                    result = _poitapHandler(result, distance, clickPoi, bossPoi.getJSONObject("location").getDouble("lat"), bossPoi.getJSONObject("location").getDouble("lng"),bossPoi.getString("title"));
                    if(result != null){
                        break;
                    }
                }
                result = _poitapHandler(result, distance, clickPoi, poi.getJSONObject("location").getDouble("lat"), poi.getJSONObject("location").getDouble("lng"),poi.getString("title"));
                break;
            case 16:
            case 17:
            case 18:
            case 19:
                //poi, bossPoi, 街道
                result = _poitapHandler(result, distance, clickPoi, poi.getJSONObject("location").getDouble("lat"), poi.getJSONObject("location").getDouble("lng"),poi.getString("title"));
                if(bossPoi != null){
                    result = _poitapHandler(result, distance, clickPoi, bossPoi.getJSONObject("location").getDouble("lat"), bossPoi.getJSONObject("location").getDouble("lng"),bossPoi.getString("title"));
                }
                break;
            default :
                break;
        }
        if(result == null){
            _dispatchEvent("tap", new Dict(){{
                put("detail", new Dict(){{
                    put("longitude",new JsNumber(clickPoi.getLongitude()));
                    put("latitude", new JsNumber(clickPoi.getLongitude()));
                }});
                put("type",new JsString( "tap"));
            }});
        }else{
            _dispatchEvent("poitap", (Dict)result.get("result"));
        }

    }
    @Override
    public double getLongitude() {
        return  _tencentMap.getMapCenter().getLongitude();
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
        return _tencentMap.getMapCenter().getLatitude();
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
        _tencentMap.setCenter(new LatLng(this.temp_latitude, this.temp_longitude));
        latitudeChanged=false;
        longitudeChanged=false;
    }

    @Override
    public float getScale() {
        return  _tencentMap.getZoomLevel();
    }

    @Override
    public void setScale(float scale) {
        _tencentMap.setZoom((int)scale);
    }


    private Map<String, TextView> _callouts = new HashMap<String, TextView>();
    @Override
    public void setMarkers(List<Map> params) {
        if (params == null) {
            return;
        }
        this.map_markers.forEach((i,item) -> {
              ((Marker)item).remove();
          });
        for (Map marker_item : params) {
            Integer markerId = marker_item.containsKey("id") ? Integer.parseInt(marker_item.get("id").toString()) : null;
            boolean hasWindow = false;
            LatLng markerPosition = new LatLng((Double) marker_item.get("latitude"),(Double) marker_item.get("longitude"));
            Map tag = new HashMap();
            MarkerOptions markerOptions = new MarkerOptions().position(markerPosition);

            String display = "BYCLICK";
            TextView calloutInfoWindow =  new TextView(_onekit_map.getContext());
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
                        markerOptions.title(marker_value == null ? "" : marker_value.toString());
                        break;
                    case "zIndex":
                        break;
                    case "iconPath":
                        break;
                    case "rotate":
//                        float rotate= (float)marker_item.getOrDefault("rotate", 0f);
                        markerOptions.rotation(marker_value == null ? 0f : Float.parseFloat(marker_value.toString()));
                        break;
                    case "alpha":
//                        float alpha = (float)marker_item.getOrDefault("alpha",1f);
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
                        Map anchor= (Map)marker_value;//marker_item.get("anchor")==null?new Map(){{put("x",0.5);put("y",1);}}:(Map) marker_item.get("anchor");
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
            if(marker_item.containsKey("iconPath") && marker_item.get("iconPath") != null){
                try {
//                    Bitmap bitmap = Bitmap.createBitmap(ASSET.loadImage(marker_item.get("iconPath").toString(), true), 0, 0, dp2px(_tencentMap.getContext(), width), dp2px(_tencentMap.getContext(), height));
                    marker.setIcon(new BitmapDescriptor(ASSET.loadImage(marker_item.get("iconPath").toString())));
                }catch(Exception e){
                    Log.d("---------------", "setMarkers: imageLoad-error");
                    e.getStackTrace();
                }
            }

//            if (((Map) marker_item).get("iconPath")!=null){
//                Bitmap bitmap=Bitmap.createBitmap(BitmapDescriptorFactory.fromAsset((String)marker_item.get("iconPath")).getBitmap()
//                        ,0,0,dp2px(_tencentMap.getContext(),width),dp2px(_tencentMap.getContext(),height));
//                BitmapDescriptor b=new BitmapDescriptor(bitmap);
//                marker.setIcon(b);
//            }
            if(marker_item.containsKey("callout")){
                Map callout = (Map)marker_item.getOrDefault("callout", new HashMap().toString());
                int borderWidth= 0;
                String borderColor="#000000";
                hasWindow = true;
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
                            display = (String)callout_value;
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
                    public void onInfoWindowDettached(Marker marker, View view) {
                        Log.d("-------------", "onInfoWindowDettached: ");
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
            put("type", new JsString("updated"));
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
                        polylineOptions.setDottedLine(polyline_value == null ? false : (boolean)polyline_value);
                        break;
                    case "arrowLine":
                        break;
                    case "arrowIconPath":
                        break;
                    case "borderColor":
                        polylineOptions.edgeColor(Color.parseColor(polyline_value == null ? "#00000000" : (String)polyline_value));
                        break;
                    case "borderWidth":
                        polylineOptions.edgeWidth(polyline_value == null ? 1f : Float.parseFloat(polyline_value.toString()));
                        break;
                    default:
                        break;
                }
            }
            Polyline p = _tencentMap.addPolyline(polylineOptions);
            this.map_polyline.add(p);
        }
        _dispatchEvent("updatetap", new Dict(){{
            put("type", new JsString("updated"));
        }});
    }

    @Override
    public void setCircles(List<Map> params) {
        if (params == null) {
            return;
        }
        this.map_circles.forEach(item -> {
            ((Circle)item).remove();
        });

        for(Object circles:params){
            LatLng latLng=new LatLng((Double) ((Map) circles).get("latitude"),(Double) ((Map) circles).get("longitude"));
            String color=((Map) circles).get("color")==null?"#00000000":(String)((Map) circles).get("color");
            String fillColor=((Map) circles).get("fillColor")==null?"00000000":(String)((Map) circles).get("fillColor");
            String radius=((Map) circles).get("radius")==null?"0":((Map) circles).get("radius").toString();
            String strokeWidth =((Map) circles).get("strokeWidth")==null?"0":((Map) circles).get("strokeWidth").toString();
            Circle cir = _tencentMap.addCircle(new CircleOptions().
                    center(latLng).
                    radius(dp2px(Float.valueOf(radius))).
                    fillColor(Color.parseColor(fillColor)).
                    strokeColor(Color.parseColor(color)).
                    strokeWidth(dp2px(Float.valueOf(strokeWidth))));
            this.map_circles.add(cir);
        }

        _dispatchEvent("updatetap", new Dict(){{
            put("type", new JsString("updated"));
        }});
    }

    @Override
    public void setControls(List<Map> params) {

    }

    @Override
    public void setIncludePoints(List<Map> includePoints) {
        if(!isLoaded){
            _todo.put("includePoints", includePoints);
            return ;
        }
        LatLngBounds bounds = _projection.getVisibleRegion().getLatLngBounds();
        for(Object item : includePoints){
            Map point = (Map)item;
            bounds = bounds.including(new LatLng( Double.parseDouble(point.get("latitude").toString()), Double.parseDouble(point.get("longitude").toString()) ));
        }
//        Looper.prepare();
        _tencentMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
//        Looper.loop();
    }

    @Override
    public boolean getShowLocation() {
        return false;
    }

    @Override
    public void setShowLocation(boolean showLocation) {
        if(!showLocation && locationMarker != null){
            locationMarker.remove();
            locationMarker = null;
        }
    }
    private Marker locationMarker = null;
    @Override
    public void setMyLocation(TencentLocation location ) {
        if(locationMarker == null){
            locationMarker = _tencentMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromAsset("mylocation.png"))
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .rotation(location.getBearing())
            );
        }else{
            locationMarker.setRotation(location.getBearing());
            locationMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
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
                        polygonOptions.zIndex(((polygon_value == null ? 0f : Float.parseFloat(polygon_value.toString()))));
                        break;
                    default:
                        break;
                }
            }
            Polygon pol = _tencentMap.addPolygon(polygonOptions);
        }
        _dispatchEvent("updatetap", new Dict(){{
            put("type", new JsString("updated"));
        }});
    }

    @Override
    public Map getSubkey() {
        return null;
    }

    @Override
    public void setSubkey(Map subkey) {

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
        return 0;
    }

    @Override
    public void setRotate(float rotate) {

    }

    @Override
    public float getSkew() {
        return 0;
    }

    @Override
    public void setSkew(float skew) {

    }


    @Override
    public boolean getShowCompass() {
        return _uiSettings.isScaleControlsEnabled();
    }

    @Override
    public void setShowCompass(boolean showCompass) {

    }

    @Override
    public boolean getShowScale() {
        return false;
    }

    @Override
    public void setShowScale(boolean showScale) {
        _uiSettings.setScaleControlsEnabled(showScale);
    }

    @Override
    public boolean getEnableOverlooking() {
        return false;
    }

    @Override
    public void setEnableOverlooking(boolean enableOverlooking) {

    }

    @Override
    public boolean getEnableZoom() {
        return false;
    }

    @Override
    public void setEnableZoom(boolean enableZoom) {
        _uiSettings.setZoomGesturesEnabled(enableZoom);
    }

    @Override
    public boolean getEnableScroll() {
        return false;
    }

    @Override
    public void setEnableScroll(boolean enableScroll) {
        _uiSettings.setScrollGesturesEnabled(enableScroll);
    }

    @Override
    public boolean getEnableRotate() {
        return false;
    }

    @Override
    public void setEnableRotate(boolean enableRotate) {

    }

    @Override
    public boolean getEnableSatellite() {
        return false;
    }

    @Override
    public void setEnableSatellite(boolean enableSatellite) {
        _tencentMap.setSatelliteEnabled(enableSatellite);
    }

    @Override
    public boolean getEnableTraffic() {
        return false;
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
        getMapView().onCreate(null);
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
            result.put("timeStamp", new JsNumber(new Date().getTime() - _time));
        }else{
            result.put("timeStamp", new JsNumber(new Date().getTime()));
        }
        Event event = new Event(name, result,_onekit_map,_onekit_map,0);
        Log.d(name, "_dispatchEvent: "+result.toString());
        _onekit_map.dispatchEvent(event);
    }
    

}

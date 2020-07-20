package cn.onekit.weixin.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import cn.onekit.js.Array;
import cn.onekit.weixin.app.core.WeixinElement;
import cn.onekit.weixin.app.core.map.WeixinMap;
import cn.onekit.weixin.app.core.map.tencent.WeixinMap_Tencent2D;
import cn.onekit.weixin.app.core.map.tencent.WeixinMap_Tencent3D;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class Map extends WeixinElement implements TencentLocationListener {

    public WeixinMap _weixinMap;
    public TencentLocation tencentLocation = null;
    //public com.tencent.tencentmap.mapsdk.maps.MapView map3dView;

    private Boolean enable3D = null;
    public boolean getEnable3D(){
        return enable3D;
    }
    public void setEnable3D(boolean enable3D){
        if(this.enable3D !=null && this.enable3D==enable3D){
            return;
        }
        this.enable3D = enable3D;
        if(this.enable3D){
            _weixinMap = new WeixinMap_Tencent3D(this);

        }else{
            _weixinMap = new WeixinMap_Tencent2D(this);
        }
    }
    ////////////////////
    public Map(Context context) {
        super(context);
        _init();
    }
    public Map(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init();
    }
    private void _init(){
        setEnable3D(true);
        //开启定位监听器
        _initLocation();
    }

    @SuppressLint("WrongConstant")
    private void _initLocation(){
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(1000)
                .setRequestLevel(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                ;//.setAllowCache(true);
        TencentLocationManager locationManager = TencentLocationManager.getInstance(getContext());
        int error = locationManager.requestLocationUpdates(TencentLocationRequest.create().setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME).setInterval(1000).setAllowDirection(true), this);
        if (error == 0) {
            Log.d("this", "注册位置监听器成功！");
        } else {
            Log.d("this", "注册位置监听器失败！");
        }
        //权限检查
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            if (checkSelfPermission(getContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED)
                requestPermissions((Activity)getContext(),permissions, 0);
        }
    }
    //定位sdk
    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        // do your work
        if (TencentLocation.ERROR_OK == error) {
            // 定位成功
            tencentLocation = location;
            Log.d("showLocation", "showLocation: " + showLocation);
            Log.d("tencentLocation", "onLocationChanged: " + tencentLocation);
            if(this.showLocation){
                _weixinMap.setMyLocation(tencentLocation);
            }
        }

    }

    @Override
    public void onStatusUpdate(String name, int status, String desc) {
        // do your work
        Log.d("-------------------------------2", "onLocationChanged: "+name);
        Log.d("-------------------------------2", "onLocationChanged: "+status);
        Log.d("-------------------------------2", "onLocationChanged: "+desc);
    }

    //////////////// 属性///////////////
//    protected double latitude = 23.10229;
//    protected double longitude = 113.3245211;
//    protected float scale = 16;
    //    protected float skew = 0;
//    protected float rotate = 0;

    public double longitude = 113.3245211;
    public double latitude = 23.10229;
    public float scale = 16;
    public float rotate = 0;
    public float skew = 0;
    private boolean showCompass = false;
    private boolean showScale = false;
    private boolean enableOverlooking = false;
    private boolean enableScroll = false;
    private boolean enableRotate = false;
    private boolean scroll = false;
    private boolean enableZoom = false;
    private boolean enableTraffic = false;
    private boolean enableSatellite = false;
    private boolean showLocation = false;
    private Array markers;
    private Array polyline;
    private Array polygons;
    private Array circles;
    private Array controls;
    private Array includePoints;
    private String subkey;
    private int layerStyle;
    private Map setting;

    public float getRotate() {
        return rotate;
    }
    public void setRotate(Integer rotate) {
        this.rotate = rotate;
    }
    public float getSkew() {
        return skew;
    }

    public void setSkew(Integer skew) {
        this.skew = skew;
    }

    public void setScroll(Boolean scroll) {
        this.scroll = scroll;
    }

    public Boolean getShowLocation() {
        return showLocation;
    }

    public void setShowLocation(Boolean showLocation) {
        this.showLocation = showLocation;
    }

    public String getSubkey() {
        return subkey;
    }

    public void setSubkey(String subkey) {
        this.subkey = subkey;
    }

    public int getLayerStyle() {
        return layerStyle;
    }

    public void setLayerStyle(int layerStyle) {
        this.layerStyle = layerStyle;
    }

    public Map getSetting() {
        return setting;
    }

    public void setSetting(Map setting) {
        this.setting = setting;
    }

    //拖动支持
    public void setScroll(boolean scroll){
        _weixinMap.setEnableScroll(scroll);
    }
    public boolean getScroll(){
        return scroll;
    }
    //////////////////////
    //缩放支持
    public void setEnableZoom(boolean zoom){
        enableZoom = zoom;
        _weixinMap.setEnableZoom(enableZoom);
    }
    public boolean getEnableZoom(){
        return enableZoom;
    }
    //////////////////////
    //实时路况支持
    public void setEnableTraffic(boolean traffic){
        enableTraffic=traffic;
        _weixinMap.setEnableTraffic(enableTraffic);
    }
    public boolean getEnableTraffic(){
        return enableTraffic;
    }
    ///////////////////////
    //卫星图支持
    public void setEnableSatellite(boolean satellite){
        enableSatellite=satellite;
        _weixinMap.setEnableSatellite(enableSatellite);
    }
    public boolean getEnableSatellite(){
        return enableSatellite;
    }

    // 中心经度
    public void setLongitude(double longitude) {
        this.longitude = longitude;
        _weixinMap.setLongitude(this.longitude);
    }
    public double getLongitude() {
        return longitude;
    }

    // 中心纬度
    public void setLatitude(double latitude) {
        this.latitude = latitude;
        _weixinMap.setLatitude(this.latitude);
    }
    public double getLatitude() {
        return latitude;
    }

    // 缩放级别
    public void setScale(int scale) {
        this.scale = scale;
        _weixinMap.setScale(this.scale);
    }
    public float getScale() {
        return scale;
    }

    // 标记点
    public void setMarkers(Array markers)  {
        this.markers = markers;
        _weixinMap.map_markers.clear();
        _weixinMap.setMarkers(markers);
    }
    public Array getMarkers(){
        return markers;
    }

    public void setPolyline(Array polyline){
        this.polyline = polyline;
        _weixinMap.map_polyline.clear();
        _weixinMap.setPolyline(polyline);
    }
    public Array getPolyline(){
        return polyline;
    }
    /////////////
    public void setPolygons(Array polygons){
        this.polygons = polygons;
        _weixinMap.map_polygons.clear();
        _weixinMap.setPolygons(polygons);
    }
    public Array getPolygons(){
        return polygons;
    }
    ////////////////
    public void setCircles(Array circle){
        this.circles = circle;
        _weixinMap.map_circles.clear();
        _weixinMap.setCircles(circle);
    }
    public Array getCircles(){
        return circles;
    }

    public Array getControls() {
        return controls;
    }
    public void setControls(Array controls) {
        this.controls = controls;
        _weixinMap.map_controls.clear();
        _weixinMap.setControls(controls);
    }

    public Array getIncludePoints() {
        return includePoints;
    }
    public void setIncludePoints(Array includePoints) {
        this.includePoints = includePoints;
        _weixinMap.setIncludePoints(includePoints);
    }

    public boolean getShowCompass() {
        return showCompass;
    }

    public void setShowCompass(boolean showCompass) {
        this.showCompass = showCompass;
        _weixinMap.setShowCompass(showCompass);
    }

    public boolean getShowScale() {
        return showScale;
    }

    public void setShowScale(boolean showScale) {
        this.showScale = showScale;
        _weixinMap.setShowScale(showScale);
    }

    public boolean getEnableOverlooking() {
        return enableOverlooking;
    }

    public void setEnableOverlooking(boolean enableOverlooking) {
        this.enableOverlooking = enableOverlooking;
        _weixinMap.setEnableOverlooking(enableOverlooking);
    }

    public boolean getEnableScroll() {
        return enableScroll;
    }

    public void setEnableScroll(boolean enableScroll) {
        this.enableScroll = enableScroll;
        _weixinMap.setEnableScroll(enableScroll);
    }

    public boolean getEnableRotate() {
        return enableRotate;
    }

    public void setEnableRotate(boolean enableRotate) {
        this.enableRotate = enableRotate;
        _weixinMap.setEnableRotate(enableRotate);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if(event.getAction() == 2){
            //手指按下
            _weixinMap.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

    //////////////////////////生命周期/////////////////////////////
    public void onStart(Bundle bundle) {
        _weixinMap.onStart();
    }

    public void onResume() {
        _weixinMap.onResume();
    }

    public void onPause() {
        _weixinMap.onPause();
    }

    public void onStop() {
        _weixinMap.onStop();
    }
    public void onRestart() {
        _weixinMap.onRestart();
    }

    public void onDestroy() {
        _weixinMap.onDestroy();
    }


}

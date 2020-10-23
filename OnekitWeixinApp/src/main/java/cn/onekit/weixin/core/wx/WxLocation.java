package cn.onekit.weixin.core.wx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.util.Map;

import cn.onekit.thekit.Android;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsObject_;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.app.core.WeixinPage;
import cn.onekit.weixin.core.location.ChooseLocation;
import cn.onekit.weixin.core.location.LocationActivity;
import cn.onekit.weixin.core.res.wx_fail;


public class WxLocation extends WxLive {
    private function mapCallback;

    public void getLocation(final Map OBJECT) {
        String type = OBJECT.get("type") != null ? (String) OBJECT.get("type") : "gcj02";
        final Boolean altitude = OBJECT.get("altitude") != null ? (boolean) OBJECT.get("altitude") : false;
        final function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        final function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        final function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

        TencentLocationRequest request = TencentLocationRequest.create();
        TencentLocationManager locationManager = TencentLocationManager.getInstance(Android.context);
        if (type.equals("wgs84")) {
            locationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_WGS84);
        } else if (type.equals("gcj02")) {
            locationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        }
        locationManager.requestLocationUpdates(request, new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation location, int error, String s) {
                if (error == TencentLocation.ERROR_OK) {
                    float  altitude_value = 0.0f;
                    if (altitude == true) {
                        altitude_value = (float) location.getAltitude();//高度
                    }
                    JsObject res = new JsObject();
//                    res.latitude = (float) location.getLatitude();
//                    res.longitude = (float) location.getLongitude();
//                    res.speed = location.getSpeed();
//                    res.accuracy = location.getAccuracy();
//                    res.verticalAccuracy = 0;
//                    res.horizontalAccuracy = -1;
//                    res.errMsg = Android.context.getResources().getString(R.string.wx_getLocation_success);//"getLocation: ok";

                    if (success != null) {
                        success.invoke(res);
                    }
                    if (complete != null) {
                        complete.invoke(res);
                    }
                } else {
                    wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getLocation_fail));
//                    res.errMsg = Android.context.getResources().getString(R.string.wx_getLocation_fail);//"getLocation: fail";
                    if (fail != null) {
                        fail.invoke(res);
                    }
                    if (complete != null) {
                        complete.invoke(res);
                    }
                }
            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {
            }
        });
    }

    public void chooseLocation(final Map OBJECT) {
        final function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        final function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            IntentChooseLocation(new function() {
                @Override
                public JsObject_ invoke(JsObject_... arguments) {
                    JsObject result = (JsObject)arguments[0];
                    JsObject res = new JsObject();
//                    res.errMsg = String.valueOf(R.string.wx_chooseLocation_success);// ((OBJECT) result).get("errMsg").toString();
//                    res.address = (result).get("address").toString();
//                    res.latitude = (float) (result).get("latitude");
//                    res.longitude = (float) (result).get("longitude");
//                    res.name = (result).get("name").toString();
                    if (success != null) {
                        success.invoke(res);
                    }
                    if (complete != null) {
                        complete.invoke(res);
                    }
                    return null;
                }
//                @Override
//                public JsObject invoke(JsObject result) {
//                    wx_chooseLocation res = new wx_chooseLocation();
//                    res.errMsg = String.valueOf(R.string.wx_chooseLocation_success);// ((OBJECT) result).get("errMsg").toString();
//                    res.address = ((Dict) result).get("address").toString();
//                    res.latitude = (float) ((Dict) result).get("latitude");
//                    res.longitude = (float) ((Dict) result).get("longitude");
//                    res.name = ((Dict) result).get("name").toString();
//                    if (success != null) {
//                        success.invoke(res);
//                    }
//                    if (complete != null) {
//                        complete.invoke(res);
//                    }
//                    return null;
//                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_chooseLocation_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_chooseLocation_fail);//"chooseLocation: fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void openLocation(Map OBJECT) {
        final function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        final function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            IntentopenLocation(OBJECT, new function() {
                @Override
                public JsObject_ invoke(JsObject_... arguments) {
                    JsObject res = new JsObject();
//                    res.errMsg = String.valueOf(R.string.wx_openLocation_success);//((OBJECT) result).get("errMsg").toString();
                    if (success != null) {
                        success.invoke(res);
                    }
                    if (complete != null) {
                        complete.invoke(res);
                    }
                    return null;
                }
//                @Override
//                public JsObject invoke(JsObject result) {
//                    wx_openLocation res = new wx_openLocation();
////                    res.errMsg = String.valueOf(R.string.wx_openLocation_success);//((OBJECT) result).get("errMsg").toString();
//                    if (success != null) {
//                        success.invoke(res);
//                    }
//                    if (complete != null) {
//                        complete.invoke(res);
//                    }
//                    return null;
//                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_openLocation_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_openLocation_fail);//"openLocation: fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }


    private void IntentopenLocation(Map OBJECT, function callback) { //不需要返回数据
        mapCallback = callback;
        double latitude = OBJECT.get("latitude") != null ? (double) ((Float) OBJECT.get("latitude")) : 0.0;
        double longitude = OBJECT.get("longitude") != null ? (double) ((Float) OBJECT.get("longitude")) : 0.0;
        float scale = OBJECT.get("scale") != null ? (float) OBJECT.get("scale") : 0;
        String name = OBJECT.get("name") != null ? ((String) OBJECT.get("name")).trim() : null;
        String address = OBJECT.get("address") != null ? ((String) OBJECT.get("address")).trim() : null;
        Intent intent = new Intent(Android.context, LocationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putFloat("scale", scale);
        bundle.putString("name", name);
        bundle.putString("address", address);
        intent.putExtras(bundle);
        ((Activity)Android.context).startActivityForResult(intent, WeixinPage.location);
    }

    private void IntentChooseLocation(function callback) {
        mapCallback = callback;
        Intent intent = new Intent(Android.context, ChooseLocation.class);
        ((Activity)Android.context).startActivityForResult(intent, WeixinPage.chooseLocation);
    }


    public void ACTION1Openlocation(int resultCode, final Intent data) {
        if (resultCode == 1111) {
            mapCallback.invoke(new JsObject() {{
                //  put("errMsg", data.getStringExtra("errMsg"));
            }});
        }
    }

    public void ACTION1ChooseLocation(int resultCode, final Intent data) {
        if (resultCode == 1000) {
            /*
            mapCallback.invoke(new Dict() {{
                put("name", data.getStringExtra("title"));
                put("address", data.getStringExtra("address"));
                put("latitude", data.getStringExtra("lat"));
                put("longitude", data.getStringExtra("lng"));
                // put("errMsg", "chooseLocation: ok");
            }});*/
        }
    }
}

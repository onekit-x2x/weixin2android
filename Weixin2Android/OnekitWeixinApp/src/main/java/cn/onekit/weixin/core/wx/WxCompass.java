package cn.onekit.weixin.core.wx;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

import static android.content.Context.SENSOR_SERVICE;


public class WxCompass extends WxClipboard {
    private SensorManager manager;
    private SensorEventListener listener;
    private function callback;

    public void onCompassChange(function OBJECT) {
        callback = OBJECT;
    }

    public void startCompass(final Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            manager = (SensorManager) Android.context.getSystemService(SENSOR_SERVICE);
            listener = new SensorEventListener() {
                @Override
                public void onSensorChanged(final SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                        Dict res = new Dict();
//                        res.direction = event.values[0];
//                        res.errMsg = Android.context.getResources().getString(R.string.wx_onCompassChange_success);
                        if (callback != null) {
                            callback.invoke(res);
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };
            manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
            Dict res = new Dict();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_startCompass_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_startCompass_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_startCompass_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void stopCompass(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            manager.unregisterListener(listener);
            Dict res = new Dict();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_stopCompass_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_stopCompass_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_stopCompass_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }
}


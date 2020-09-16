package cn.onekit.weixin.core.wx;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxAccelerometer  {
    private  function callback;

    public  void onAccelerometerChange(function OBJECT) {
        callback = OBJECT;
    }

    public  void startAccelerometer(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            startAccelerometer(new function() {
                @Override
                public JsObject invoke(JsObject... arguments) {
                    if (callback != null) {
                    }
                    return null;
                }

//                @Override
//                public JsObject invoke(final SensorEvent result) {
//                    if (callback != null) {
//                        wx_onAccelerometerChange res = new wx_onAccelerometerChange();
//                        res.x = -result.values[0];
//                        res.y = -result.values[1];
//                        res.z = -result.values[2];
//                        callback.invoke(res);
//                    }
//                    return null;
//                }
            });
            Dict result = new Dict();
//            result.errMsg = Android.context.getResources().getString(R.string.wx_startAccelerometer_success);
            if (success != null) {
                success.invoke(result);
            }
            if (complete != null) {
                complete.invoke(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail result = new wx_fail(Android.context.getResources().getString(R.string.wx_startAccelerometer_fail));
//            result.errMsg = Android.context.getResources().getString(R.string.wx_startAccelerometer_fail);
            if (fail != null) {
                fail.invoke(result);
            }
            if (complete != null) {
                complete.invoke(result);
            }
        }
    }

    public  void stopAccelerometer(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            Stop();
            Dict res = new Dict();
//            result.errMsg = Android.context.getResources().getString(R.string.wx_stopAccelerometer_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail result = new wx_fail(Android.context.getResources().getString(R.string.wx_stopAccelerometer_fail));
//            result.errMsg = Android.context.getResources().getString(R.string.wx_stopAccelerometer_fail);
            if (fail != null) {
                fail.invoke(result);
            }
            if (complete != null) {
                complete.invoke(result);
            }
        }
    }

    private  SensorManager sensorManager;
    private  function eventCallback;
    private  android.hardware.SensorEventListener SensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                eventCallback.invoke(new Dict());
               // eventCallback.invoke(event);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private  void startAccelerometer(function Callback) {
        eventCallback = Callback;
        sensorManager = (SensorManager) Android.context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.registerListener(
                    SensorEventListener, sensorManager.getDefaultSensor(
                            Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private  void Stop() {
        sensorManager.unregisterListener(SensorEventListener);
    }
}


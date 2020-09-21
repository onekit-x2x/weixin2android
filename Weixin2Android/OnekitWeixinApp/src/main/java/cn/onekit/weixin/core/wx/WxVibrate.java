package cn.onekit.weixin.core.wx;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxVibrate extends WxUpdate {

    public void vibrateLong(Map OBJECT) {

        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

        try {
            vibrate(Android.context, 400);
            JsObject res = new JsObject();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_vibrateLong_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_vibrateLong_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_vibrateLong_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void vibrateShort(Map OBJECT) {

        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

        try {
            vibrate(Android.context, 15);
            JsObject res = new JsObject();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_vibrateShort_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_vibrateShort_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_vibrateShort_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //震动milliseconds毫秒
    private void vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

}

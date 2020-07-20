package cn.onekit.weixin.core.wx;


import android.app.Activity;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

import cn.onekit.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxScreen extends WxScanCode {

    public void setScreenBrightness(Map OBJECT) {
        float value = OBJECT.get("value") != null ? (float) ((float) OBJECT.get("value")) : 0.5f;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        float brightness = value * 255;
        Window window = ((Activity)Android.context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        try {
            if (brightness == -1) {
                Settings.System.putInt(Android.context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) brightness);
            } else {
                lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
            }
            window.setAttributes(lp);
            Dict res = new Dict();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_setScreenBrightness_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_setScreenBrightness_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_setScreenBrightness_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void getScreenBrightness(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        try {
            int systemBrightness = Settings.System.getInt(Android.context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            Dict res = new Dict();
//            res.value = (float) systemBrightness / 255;
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getScreenBrightness_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getScreenBrightness_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getScreenBrightness_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void setKeepScreenOn(HashMap OBJECT) {
        boolean keepScreenOn = OBJECT.get("keepScreenOn") != null ? (boolean) OBJECT.get("keepScreenOn") : false;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        try {
            if (keepScreenOn) {
                ((Activity)Android.context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            Dict res = new Dict();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_setKeepScreenOn_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_setKeepScreenOn_fail));
//            res.errMsg =Android.context.getResources().getString(R.string.wx_setKeepScreenOn_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }
}

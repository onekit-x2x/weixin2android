package cn.onekit.weixin.core.wx;

import android.content.Intent;
import android.content.IntentFilter;

import java.util.Map;

import cn.onekit.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxBattery extends WxBasic {
    public void getBatteryInfo(Map object) {
        function success = (object.get("success") != null) ? (function) object.get("success") : null;
        function fail = (object.get("fail") != null) ? (function) object.get("fail") : null;
        function complete = (object.get("complete") != null) ? (function) object.get("complete") : null;
        try {
            String level = registerBattery();
            Dict res = new Dict();//(Android.context.getResources().getString(R.string.wx_getBatteryInfo_success),false);
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getBatteryInfo_success);
//            res.level = level;
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getBatteryInfo_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getBatteryInfo_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public String getBatteryInfoSync() {
        String battery = registerBattery();

        return battery;
    }

    private String registerBattery() {
        Intent batteryInfoIntent = Android.context
                .registerReceiver(null,
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryInfoIntent.getIntExtra("level", 0);//电量（0-100）
        return level + "";
    }
}

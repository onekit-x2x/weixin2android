package cn.onekit.weixin.core.wx;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;

import java.util.Locale;
import java.util.Map;

import cn.onekit.thekit.Android;
import cn.onekit.js.JsBoolean;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsString;
import cn.onekit.js.core.function;
import cn.onekit.weixin.SystemInfo;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxSystem extends WxSubscribe {
    public  void getSystemInfo(Map JsObject) {
        function success = JsObject.get("success") != null ? (function) JsObject.get("success") : null;
        function complete = JsObject.get("complete") != null ? (function) JsObject.get("complete") : null;
        function fail = JsObject.get("fail") != null ? (function) JsObject.get("fail") : null;
        try {
            cn.onekit.js.JsObject res = new JsObject();
//            res.brand = getbrand();
//            res.model = getSystemModel();
//            res.pixelRatio = getDensity();
//            res.screenWidth = getwidth();
//            res.screenHeight = getheight();
//            res.windowWidth = getwidth();
//            res.windowHeight = getwindowHeight();
//            res.language = getSystemLanguage();
//            res.version = getVersion();
//            res.platform = " Android";
//            res.system = " Android " + getSystemVersion();
//            res.fontSizeSetting = getFontSize();
//            res.SDKVersion = getVersionName();
//            res.benchmarkLevel = "";
//            res.battery = battery();
//            res.wifiSignal = wifiSignal();
//            res.errMsg = Android.application().getResources().getString(R.string.wx_getSystemInfo_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.application().getResources().getString(R.string.wx_getSystemInfo_fail));
//            res.errMsg = Android.application().getResources().getString(R.string.wx_getSystemInfo_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }


    public SystemInfo getSystemInfoSync() {
        SystemInfo res = new SystemInfo();
       /* res.brand = getbrand();
        res.model = getSystemModel();
        res.pixelRatio = getDensity();
        res.screenWidth = getwidth();
        res.screenHeight = getheight();
        res.windowWidth = getwidth();
        res.windowHeight = getwindowHeight();
        res.language = getSystemLanguage();
        res.version = getVersion();
        res.platform = "Android";
        res.system = "Android " + getSystemVersion();
        res.fontSizeSetting = getFontSize();
        res.SDKVersion = getVersionName();
        res.benchmarkLevel = "";
        res.battery = battery();
        res.wifiSignal = wifiSignal();*/
        return res;
    }


    public JsBoolean canIUse(JsString path) {
        return new JsBoolean(true);
    }


    /**
     * 获取手机电量
     */
    private  String battery;

    private  String battery() {
      /*  WxBattery.getBatteryInfo(new HashMap() {{
            put("success", new function<wx_getBatteryInfo>() {
                @Override
                public void invoke(wx_getBatteryInfo res) {
                    battery = res.level;
                }
            });
        }});
        return battery;*/
      return null;
    }

    /**
     * 获取WiFi信号强度
     */

    private  String wifiSignal() {
        WifiManager mWifiManager = (WifiManager) Android.application().getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo;
        mWifiInfo = mWifiManager.getConnectionInfo();
        final int signalStrength = mWifiInfo.getRssi() + 100;
        return signalStrength + "";
    }

    /**
     * 获取手机型号
     */
    private  String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 屏幕宽度
     */

    private  int getwidth() {
        Resources resources = Android.application().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return (dm.widthPixels / (int) getDensity());
    }

    /**
     * 屏幕高度
     */

    private  int getheight() {
        Resources resources = Android.application().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return (dm.heightPixels / (int) getDensity());
    }

    /**
     * 屏幕密度
     */
    private  float getDensity() {
        Resources resources = Android.application().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    private  String getSystemLanguage() {

        return Locale.getDefault().getLanguage();
    }

    /**
     * 可使用窗口高度
     */
    private  int getwindowHeight() {
        int height = getheight() - getstate();
        return (height / (int) getDensity());
    }

    /**
     * 获取状态栏高度
     */
    private  int getstate() {
        int statusBarHeight1 = -1;
        int resourceId = Android.application().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = Android.application().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 获取app版本号
     */
    private  int getVersion() {
        try {
            PackageManager manager = Android.application().getPackageManager();
            PackageInfo info = manager.getPackageInfo(Android.application().getPackageName(), 0);
            return info.versionCode;//版本号
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取app版本名称
     */
    private  String getVersionName() {
        try {
            PackageManager manager = Android.application().getPackageManager();
            PackageInfo info = manager.getPackageInfo(Android.application().getPackageName(), 0);
            return info.versionName;//版本号
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    private  String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取高宽
     */
    public Point getWeidthAndHeigh() {
        return new Point(getwidth(), getheight());
    }

    /**
     * 获取手机品牌
     */

    private  String getbrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获得默认字体的大小
     */

    private  float getFontSize() {
        Paint sPaint = new Paint();
        return sPaint.getTextSize();
    }
}

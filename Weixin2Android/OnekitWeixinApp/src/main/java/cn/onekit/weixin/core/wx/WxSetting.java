package cn.onekit.weixin.core.wx;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxSetting extends WxScreen {
    public void openSetting(Map OBJECT) {
        function success = (OBJECT.get("success") == null) ? (function) OBJECT.get("success") : null;
        function fail = (OBJECT.get("fail") == null) ? (function) OBJECT.get("fail") : null;
        function complete = (OBJECT.get("complete") == null) ? (function) OBJECT.get("complete") : null;
        try {
            toSelfSetting(Android.context);
            JsObject res = new JsObject();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_openSetting_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_openSetting_fail));
//            res.errMsg =Android.context.getResources().getString(R.string.wx_openSetting_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void getSetting(Map OBJECT) {
    }

    public void toSelfSetting(Context context) {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);
    }
}


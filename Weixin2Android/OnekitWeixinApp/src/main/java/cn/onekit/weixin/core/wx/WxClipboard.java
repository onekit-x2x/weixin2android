package cn.onekit.weixin.core.wx;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import java.util.Map;

import cn.onekit.thekit.Android;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxClipboard extends WxCard {
    private ClipboardManager cm;
    public void setClipboardData(Map OBJECT) {
        String data = OBJECT.get("data") != null ? (String) OBJECT.get("data") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            //获取剪贴板管理器：
            cm = (ClipboardManager) Android.context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText(null, data);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            JsObject res = new JsObject();//Android.context.getResources().getString(R.string.wx_setClipboardData_success));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_setClipboardData_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_setClipboardData_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_setClipboardData_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void getClipboardData(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            JsObject res = new JsObject();//(cm.getPrimaryClip().getItemAt(0).getText().toString());
//            res.data = cm.getPrimaryClip().getItemAt(0).getText().toString();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getClipboardData_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getClipboardData_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getClipboardData_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }
}


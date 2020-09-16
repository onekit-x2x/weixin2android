package cn.onekit.weixin.core.wx;

import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;

import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.Array;
import cn.onekit.js.ArrayBuffer;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.nfc.NfcService;
import cn.onekit.weixin.core.res.wx_fail;

public class WxNFC extends WxNavigationBar {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private String errMsg;
    private int errCode;

    public void getHCEState(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
    }

    public void startHCE(Map OBJECT) {
        Array aid_list = OBJECT.get("aid_list") != null ? (Array) OBJECT.get("aid_list") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;

        nfcAdapter = NfcAdapter.getDefaultAdapter(Android.context);
        nfcAdapter = NfcAdapter.getDefaultAdapter(Android.context);
        if (nfcAdapter != null) {
            //系统支持nfc
            boolean enabled = nfcAdapter.isEnabled();  //判断是否打开nfc
            if (enabled) {
                //打开nfc
                PackageManager packageManager = Android.context.getPackageManager();
                boolean supportHce = packageManager
                        .hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION);
                if (supportHce) {
                    //支持hce

                } else {
                    //不支持hce
                    wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_startHCE_fail));
//                    res.errMsg = Android.context.getResources().getString(R.string.wx_startHCE_fail);// "startHCE:fail";
//                    res.errCode = 13002;
                    if (fail != null) {
                        fail.invoke(res);
                    }
                    if (complete != null) {
                        complete.invoke(res);
                    }
                }
            } else {
                //未打开nfc
                wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_startHCE_fail));
//                res.errMsg = Android.context.getResources().getString(R.string.wx_startHCE_fail);//"startHCE:fail";
//                res.errCode = 13001;
                if (fail != null) {
                    fail.invoke(res);
                }
                if (complete != null) {
                    complete.invoke(res);
                }
            }
        } else {
            //系统不支持nfc
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_startHCE_fail));
//            res.errCode = 13000;
//            res.errMsg = Android.context.getResources().getString(R.string.wx_startHCE_fail);//"startHCE:fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void stopHCE(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;

    }

    public void onHCEMessage(function callback) {
        NfcService.callback = callback;
    }

    public void sendHCEMessage(Map OBJECT) {
        ArrayBuffer data =  (ArrayBuffer) OBJECT.get("data") ;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        NfcService.data = data._data;
    }
}


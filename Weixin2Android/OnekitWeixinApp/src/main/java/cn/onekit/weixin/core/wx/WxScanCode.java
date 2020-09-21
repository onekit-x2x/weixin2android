package cn.onekit.weixin.core.wx;

import android.app.Activity;
import android.content.Intent;

import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.JsArray;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.app.core.WeixinPage;
import cn.onekit.weixin.core.res.wx_fail;
import cn.onekit.weixin.core.zxing.android.CaptureActivity;

import static android.app.Activity.RESULT_OK;


public class WxScanCode extends WxRoute {
    private function success;
    private function complete;
    private function fail;

    public void scanCode(Map OBJECT) {
        boolean onlyFromCamera = OBJECT.get("onlyFromCamera") != null ? Boolean.valueOf(OBJECT.get("onlyFromCamera").toString()) : false;
        JsArray scanType = OBJECT.get("scanType") != null ? (JsArray) OBJECT.get("scanType") : null;
        success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        Intent intent = new Intent();
        intent.putExtra("onlyFromCamera", onlyFromCamera);
        intent.setClass(Android.context, CaptureActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            ((Activity)Android.context).startActivityForResult(intent, WeixinPage.SCANNIN_GREQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_scanCode_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_scanCode_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void SCANNIN_GREQUEST_CODE(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final String content = data.getStringExtra("codedContent");
            //   Bitmap bitmap = data.getParcelableExtra("codedBitmap");//图片
            //显示扫描到的内容
            boolean isSuccess = data.getBooleanExtra("isSuccess", false);
            if (isSuccess) {
                final String scanType;
                if (content.substring(0, 4).equals("http")) {
                    scanType = "QR_CODE";//二维码
                } else {
                    scanType = "EAN_13";//条形码
                }
                String path = "";
                JsObject res = new JsObject();
//                wx_scanCode res = new wx_scanCode(content, scanType, "utf-8", path, Android.context.getResources().getString(R.string.wx_scanCode_success));
//                res.charSet = "utf-8";
//                res.result = content;
//                res.scanType = scanType;
//                res.errMsg = Android.context.getResources().getString(R.string.wx_scanCode_success);//"scanCode :ok";
                if (success != null) {
                    success.invoke(res);
                }
                if (complete != null) {
                    complete.invoke(res);
                }
            } else {
                wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_scanCode_fail));
//                res.errMsg = Android.context.getResources().getString(R.string.wx_scanCode_fail);//"scanCode: fail";
                if (fail != null) {
                    fail.invoke(res);
                }
                if (complete != null) {
                    complete.invoke(res);
                }
            }
        }
    }
}


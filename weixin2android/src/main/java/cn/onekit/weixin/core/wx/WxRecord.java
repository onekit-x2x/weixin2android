package cn.onekit.weixin.core.wx;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Map;

import cn.onekit.thekit.Android;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.RecorderManager;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxRecord extends WxPullDownRefresh {
    // 语音操作对象
    private MediaRecorder mRecorder = null;
    private function success;
    private function complete;
    private String fileName;
    private int Count;

    //开始录音
    public void startRecord(Map OBJECT) {
        initPermission();
        success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;

        fileName = Android.context.getExternalCacheDir().getAbsolutePath() + "/" + "record.silk";//存放临时缓存数据
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置录音的输入源(麦克)
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//设置音频格式
            mRecorder.setOutputFile(fileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);////设置音频编码
        }
        try {
            mRecorder.prepare();
            StartRecord(); //开始录音

        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_startRecord_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_startRecord_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    private void StartRecord() {
        if (mRecorder != null) {
            mRecorder.start(); //开始录音
            Count = 0;
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (Count == 60) {
                        mRecorder.stop();
                        mRecorder.release();
                        mRecorder = null;
                        JsObject res = new JsObject();
//                        res.errMsg = Android.context.getResources().getString(R.string.wx_startRecord_success);
//                        res.tempFilePath = OnekitWeixin.androidPath2tempPath(fileName);
                        if (success != null) {
                            success.invoke(res);
                        }
                        if (complete != null) {
                            complete.invoke(res);
                        }
                    }
                    Count++;
                    handler.postDelayed(this, 1000);//每一秒刷新一次
                }
            };
            runnable.run();
        }
    }
    //停止录音

    public void stopRecord(Map OBJECT) {
        if (OBJECT == null) {
            return;
        }
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        JsObject res = new JsObject();
//        res.errMsg = Android.context.getResources().getString(R.string.wx_stopRecord_success);
        if (success != null) {
            success.invoke(res);
        }
        if (complete != null) {
            complete.invoke(res);
        }
        if (mRecorder == null) {
            return;
        } else if (mRecorder != null) {
            Count = 60;
        } else {
            wx_fail failres = new wx_fail(Android.context.getResources().getString(R.string.wx_stopRecord_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_stopRecord_fail);
            if (fail != null) {
                fail.invoke(failres);
            }
            if (complete != null) {
                complete.invoke(failres);
            }
        }
    }

    private void initPermission() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                checkSelfPermission(Android.context, android.Manifest.permission.RECORD_AUDIO)) {
        } else {
            //提示用户开户权限音频
            String[] perms = {"android.permission.RECORD_AUDIO"};
            ActivityCompat.requestPermissions((Activity) Android.context, perms, 1);
        }
    }
    public RecorderManager getRecorderManager(){
        return null;
    }
}

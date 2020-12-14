package cn.onekit.weixin;

import cn.onekit.js.core.function;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class DownloadTask {
    public OkHttpClient okHttpClient;
    public Call call;
    public function onProgressUpdate;
    public void onProgressUpdate(function callback){
         onProgressUpdate = callback;
    }
    public void abort(){
        call.cancel();
    }
    public void offHeadersReceived(function callback){

    };
    public void offProgressUpdate(function callback){

    };
    public void onHeadersReceived(function callback){

    };
}

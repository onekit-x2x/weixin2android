package cn.onekit.weixin;

import cn.onekit.js.core.function;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by zhangjin on 2017/12/25.
 */

public class RequestTask {
    public OkHttpClient okHttpClient;
    public Call call;
    public void abort(){
        call.cancel();
    }
    public void offHeadersReceived(function callback){

    }
    public void onHeadersReceived(function callback){

    }

}

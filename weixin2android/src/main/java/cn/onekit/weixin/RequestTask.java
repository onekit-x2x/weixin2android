package cn.onekit.weixin;

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
}

package cn.onekit.weixin.core.wx;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsString;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.function;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WxOpenInterface extends WxNFC {
    public void login(Dict obj){

    }

public boolean share_menuItem = true;
    private String the_share_title = null;
    private String the_share_url = null;
    private String the_share_imgurl = null;

    private final int RETURN_LOGIN = 1;
    private final int RETURN_GETUSERINFO = 2;

    String code;
    String access_token;
    String openid;

    private final String App_id = "wx664178aa785b1628";
    private final String App_security = "2a327a4a0b5af5c22e34475e8e7ec2ee";
    private IWXAPI api;
    private function success;

    public void login(Map obj) {
        success = (function) obj.get("success");
        //注册小程序
        api = WXAPIFactory.createWXAPI(Android.context, App_id, true);
        api.registerApp(App_id);

        //if (!api.isWXAppInstalled()) {
        //    Toast.makeText(Android.context,"您还未安装微信客户端",Toast.LENGTH_SHORT).show();
        //     return;
        //  }else{
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
        // }
    }

    public void checkSession(Map obj) {

    }

    public void getUserInfo(Map obj) {
        final function success = (function) obj.get("success");
        final function fail = (function) obj.get("fail");
        final function complete = (function) obj.get("complete");
        //
        final String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx664178aa785b1628&secret=89e3c1844c5a6ea8663faf9f7c48279e&code=" + code + "&grant_type=authorization_code";

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("bili", "get请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();

                try {
                    JSONObject json = new JSONObject(str);
                    if(json.getInt("errcode")!=0){
                        throw new Error(json.getString("errmsg"));
                    }
                    access_token = json.getString("access_token");
                    openid = json.getString("openid");
                  //  handler.sendEmptyMessage(2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void share(Map obj) {
/*        api = WXAPIFactory.createWXAPI(Android.context, App_id,true);
        api.registerApp(App_id);

        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl = "http://www.qq.com";
        miniProgram.userName = "gh_0b1ceeb1e2a3";
        miniProgram.path = "OpenInterface/share/onShareAppMessag/onShareAppMessagee";

        WXMediaMessage msg = new WXMediaMessage(miniProgram);
        msg.title = "小程序标题";
        msg.description = "小程序描述信息";

        Bitmap bmp = new BitmapFactory().decodeResource(Android.context.getResources(), R.drawable.more);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,50,50,true);

        bmp.recycle();


        msg.thumbData = bmpToByteArray(thumbBmp);  // 设置缩略图

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);*/

        showShare();
    }

    private void showShare() {

    }


    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private byte[] bmpToByteArray(Bitmap bmp) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    private void getUserMesg() {

        //获取用户信息部分
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();

        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        Request request = builder
                .get()
                .url(path)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("bili", "get请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("bili", "get请求成功2");
                String str = response.body().string();
                Log.e("bili", str);

                try {
                    JSONObject json = new JSONObject(str);

                    String name = json.getString("nickname");
                    int sex = json.getInt("sex");
                    String lang = json.getString("language");

                    Log.e("bili", name);
                    Log.e("bili", String.valueOf(sex));
                    Log.e("bili", lang);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RETURN_LOGIN:
                    code = msg.getData().getString("code");
                    break;
                case RETURN_GETUSERINFO:
                    getUserMesg();
                    break;
                default:
                    break;
            }
        }
    };


    public void showShareMenu() {
        share_menuItem = true;
    }

    public void hideShareMenu() {
        share_menuItem = false;
    }

    public void updateShareMenu(Map obj) {

    }

    public void getShareInfo(Map obj) {

    }

    public void onShareAppMessage(Map obj) {
        the_share_title = (String) obj.get("title");
        the_share_url = (String) obj.get("path");
        the_share_imgurl = (String) obj.get("imageUrl");
    }


    //支付功能
    public void requestPayment(Map OBJECT) {
        final String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//        final String url = "https://www.onekit.cn/api2/zhifu/payfee.php";
        api = WXAPIFactory.createWXAPI(Android.context, App_id, true);
        api.registerApp(App_id);

        request(new Dict() {{
            put("url", new JsString(url));
            put("data", new JsString(""));
            put("method", new JsString("POST"));
            put("dataType", new JsString("json"));
            put("success", new function() {
                @Override
                public JsObject invoke(JsObject... arguments) {
                    JsObject res = arguments[0];
                    Log.e("code", String.valueOf(res));
//                    Log.e("headers", String.valueOf(res.get("headers")));
//                    Log.e("data", String.valueOf(res.get("data")));

                    return null;
                }
//                @Override
//                public JsObject invoke(JsObject res) {
//                    Log.e("code", String.valueOf(res));
////                    Log.e("headers", String.valueOf(res.get("headers")));
////                    Log.e("data", String.valueOf(res.get("data")));
//                    return null;
//                }
            });

        }});
    }


}


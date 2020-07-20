package demo.chen;

import android.util.Log;
import android.view.ViewGroup;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;

import cn.onekit.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.JSON;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.vue.Vue;
import cn.onekit.weixin.app.core.WeixinPage;
import demo.chen.wxapi.WXEntryActivity;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class onekit_MainActivity extends WeixinPage{

    private IWXAPI api;

    @Override
    public void onekit_wxml(ViewGroup ui, JsObject data, Vue vue) {

    }

    @Override
    public void onekit_js() {
        onekit_MainActivity that = this;
        Page(new Options(){{
            put("onLoad", new function(){
                @Override
                public JsObject invoke(JsObject... arguments) {
//                    wx.login(new HashMap(){{
//                        put("success", new function(){
//                            @Override
//                            public JsObject invoke(JsObject... arguments) {
//                                wx_login res = (wx_login) arguments[0];
//                                console.log(res);
//
//
//
//                                return null;
//                            }
//                        });
//                    }});








                    /*
                    wx.setStorage(new HashMap<String, JsObject>(){{
                        put("key", "keya");
                        put("data", "valuea");
                        put("success", new function(){
                            @Override
                            public JsObject invoke(JsObject... arguments){
                                console.warn("---------------------------------set");
                                return null;
                            }
                        });
                    }});
                    console.warn(wx.getStorageSync("keya"));
                    wx.removeStorage(new HashMap(){{
                        put("key", "keya");
                        put("success", new function(){
                            @Override
                            public JsObject invoke(JsObject... arguments) {
                                wx_removeStorage res = (wx_removeStorage)arguments[0];
                                console.warn(res.errMsg);

                                return null;
                            }
                        });
                    }});
                    wx.removeStorageSync("keya2");
                    wx.getStorage(new HashMap<String, JsObject>(){{
                        put("key", "keya");
                        put("success", new function(){
                            @Override
                            public JsObject invoke(JsObject... arguments) {
                                wx_getStorage res = (wx_getStorage)arguments[0];
                                console.warn("-----------------------------------get:" +  res.data);

                                return null;
                            }
                        });
                    }});
//                    wx.clearStorage(new HashMap(){{
//                        put("success", new function(){
//                            @Override
//                            public JsObject invoke(JsObject... arguments) {
//                                wx_clearStorage res = (wx_clearStorage)arguments[0];
//                                console.warn(res.errMsg);
//                                return null;
//                            }
//                        });
//                    }});
                    wx.clearStorageSync();
//                    wx.getStorageInfo(new HashMap(){{
//                        put("success", new function(){
//                            @Override
//                            public JsObject invoke(JsObject... arguments) {
//                                wx_getStorageInfo res = (wx_getStorageInfo)arguments[0];
//                                console.warn(res.keys);
//                                console.warn(res.currentSize);
//                                console.warn(res.limitSize);
//                                return null;
//                            }
//                        });
//                    }});
                    console.warn(wx.getStorageInfoSync().toString());

//                    wx.setStorageSync("key1",123);
//                    console.warn(wx.getStorageSync("key1"));
//
//                    wx.setStorageSync("key2",12.3);
//                    console.warn(wx.getStorageSync("key2"));
//
//                    wx.setStorageSync("key3",true);
//                    console.warn(wx.getStorageSync("key3"));
//
//                    wx.setStorageSync("key4",15.5f);
//                    console.warn(wx.getStorageSync("key4"));
//
//                    wx.setStorageSync("key5","123");
//                    console.warn(wx.getStorageSync("key5"));
//
//                    java.util.Map map = new HashMap<String, JsObject>(){{
//                        put("ss", "sss");
//                    }};
//                    wx.setStorageSync("key6",map);
//                    console.warn(wx.getStorageSync("key6"));
//
//                    java.util.List list = new ArrayList(){{
//                        add("value");
//                        add(1);
//                    }};
//                    wx.setStorageSync("key7",list);
//                    console.warn(wx.getStorageSync("key7"));

//                    Dict dict = new Dict(){{
//                        put("ss", "sss");
//                    }};
//                    wx.setStorageSync("key8",dict);
//                    console.warn(wx.getStorageSync("key8"));
*/
                    // 通过WXAPIFactory工厂，获取IWXAPI的实例
//                    api = WXAPIFactory.createWXAPI(that, WXEntryActivity.APP_ID, false);
                    api = WXAPIFactory.createWXAPI(Android.context, null);
                    api.registerApp(WXEntryActivity.APP_ID);
//                    //拉起微信登录
//                    SendAuth.Req req = new SendAuth.Req();
//                    //授权域 获取用户个人信息则填写snsapi_userinfo
//                    req.scope = "snsapi_userinfo";
//                    //用于保持请求和回调的状态 可以任意填写
//                    req.state = "test_login";
//                    api.sendReq(req);

                    //微信支付
                    toPay();


                    return null;
                }
            });
        }});
    }

    private void login(){
        //拉起微信登录
        SendAuth.Req req = new SendAuth.Req();
        //授权域 获取用户个人信息则填写snsapi_userinfo
        req.scope = "snsapi_userinfo";
        //用于保持请求和回调的状态 可以任意填写
        req.state = "test_login";
        api.sendReq(req);
    }

   // public static final String APP_ID = "wx5a2a5fb5cc45b5e2";
    // IWXAPI 是第三方app和微信通信的openapi接口


    private void toPay(){
        //1. 请求服务端，获取下单数据
//        String url = "http://192.168.22.67/right/android/unifiedorder";
        String url = "https://www.onekitwx.com/weixin/right/android/unifiedorder";
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.d("sssssssssssssssssssssssssssss1", "run: ");
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.同步调用会阻塞主线程,这边在子线程进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Log.d("sssssssssssssssssssssssssssss2", "run: ");
                    //同步调用,返回Response,会抛出IO异常
                    Response response = call.execute();
                    String string =  response.invoke().string();
                    Log.d("sssssssssssssssssssssssssssss3", "run: " + string);
                    Dict data = (Dict) JSON.parse(string);

                    //2. 拿到数据调拉起微信支付
                    String appId = data.get("appid").stringValue();
                    String partnerId = data.get("partnerid").stringValue();
                    String prepayId = data.get("prepayid").stringValue();
                    String nonceStr = data.get("noncestr").stringValue();
                    String timeStamp = data.get("timestamp").stringValue();
                    String packageValue = data.get("package").stringValue();
                    String sign =  data.get("sign").stringValue();


                    payHandler(appId, partnerId, prepayId, nonceStr, timeStamp, packageValue,sign);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("ssssssssssssssss5", "run: ");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ssssssssssssssss6", "run: ");
                }
            }
        }).start();
    }

    /**
     *
     * @param partnerId 商户号
     * @param prepayId 预支付订单号
     * @param nonceStr 签名随机字符串
     * @param timeStamp 签名时间戳
     * @param sign 签名
     */
    private void payHandler(String appId, String partnerId, String prepayId, String nonceStr, String timeStamp, String packageValue,String sign) {
        Log.d("sssssssssssssssssssssssssssss4", "run: ");
//        api = WXAPIFactory.createWXAPI(this, appId, false); //初始化微信api
//        api = WXAPIFactory.createWXAPI(this, null); //初始化微信api
//        api.registerApp(appId); //注册appid appid可以在开发平台获取


        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = appId;
                request.partnerId = partnerId;
                request.prepayId = prepayId;
                request.packageValue = packageValue;
                request.nonceStr = nonceStr;
                request.timeStamp = timeStamp;
                request.sign = sign;
                api.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
        Log.d("dddddddddddddffff", "payHandler: " );
    }



/*

    ViewGroup root(){
        return findViewById(R.id.root);
    }
    private Map map;
    private MapContext mapContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Page(new HashMap() {
            {
                put("onLoad", new function() {
                    @Override
                    public JsObject invoke(JsObject... arguments) {
                        setContentView(R.layout.main_activity);
                        // Page(new )
                        map = new Map(MainActivity.this);
                        mapContext = wx.createMapContext(map);
                        root().addView(map);


                        map.setLatitude(23.10229);
                        map.setLongitude(113.3245211);
                        map.setScale(16);
                        map.setEnableRotate(true);
                        map.setEnableSatellite(false);
                        map.setEnableScroll(true);
                        map.setEnableZoom(true);
                        map.setEnableTraffic(true);
                        map.setEnableOverlooking(true);
                        map.setShowCompass(true);
                        map.setShowLocation(false);

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                //上下文测试
                                mapContext.getRegion(new HashMap());
                                mapContext.getCenterLocation(new HashMap());
                                mapContext.getScale(new HashMap());
                                mapContext.getSkew(new HashMap());
                                mapContext.getRotate(new HashMap());

                                mapContext.moveToLocation(new HashMap(){{
                                    put("latitude", map.tencentLocation.getLatitude());
                                    put("longitude", map.tencentLocation.getLongitude());
                                }});

//                              mapContext.setCenterOffset(new float[]{0.5f,1f});

//                               mapContext.includePoints(new HashMap(){{
//                                    put("points", new ArrayList(){{
//                                        add(new HashMap(){{
//                                            put("latitude", 23.10229 );
//                                            put("longitude", 113.3245211);
//                                        }});
//                                        add(new HashMap(){{
//                                            put("latitude", 23.10229 + 0.01);
//                                            put("longitude", 113.3245211 + 0.01);
//                                        }});
//                                    }});
//                                }});

//                                mapContext.translateMarker(new HashMap(){{
//                                    put("markerId",2);
//                                    put("destination", new HashMap<String, Double>(){{
//                                        put("latitude", 23.10229);
//                                        put("longitude", 113.3245211 + 0.001) ;
//                                    }});
//                                }});


                            }
                        }, 3000);

//                        Log.d("----------------------------", "getScale: " + map.getScale());
                        map.setMarkers(new ArrayList() {{
                            add(new HashMap() {{
                                put("id", 1);
                                put("latitude", 23.10229);
                                put("longitude", 113.3245211);
                                put("width", 50);
                                put("height", 50);
                                put("title", "marker_title");
                                put("rotate", 0f);
                                put("alpha", 0.7f);
                                put("anchor", new HashMap() {{
                                    put("x", 0.5f);
                                    put("y", 1f);
                                }});
                                put("aria-label", "aria-label");
                                put("callout", new HashMap() {{
                                    put("content", "西山壹号院");
                                    put("color", "#1C1818");
                                    put("fontSize", "16");
                                    put("borderRadius", "50");
                                    put("borderWidth", 5);
                                    put("borderColor", "#ED1111");
                                    put("bgColor", "#7C7272");
                                    put("padding", 2);
                                    put("display", "ALWAYS");
                                    put("textAlign", "center");
                                }});
                                put("label", new HashMap() {{
                                    put("content", "marker_label");
                                }});
                            }});
                            add(new HashMap() {{
                                put("id", 2);
                                put("latitude", 23.099994);
                                put("longitude", 113.324520 + 0.005);
                                    put("title", "marker_title2");
                                    put("iconPath", "image/location.png");
                                    put("rotate", 0f);
                                    put("alpha", 0.7f);
                                    put("anchor", new HashMap() {{
                                        put("x", 0.5f);
                                        put("y", 1f);
                                    }});
                                    put("aria-label", "aria-label");
                                    put("callout", new HashMap() {{
                                    put("content", "西山壹号院2");
                                    put("color", "#1C1818");
                                    put("fontSize", "16");
                                    put("borderRadius", "50");
                                    put("borderWidth", 5);
                                    put("borderColor", "#ED1111");
                                    put("bgColor", "#7C7272");
                                    put("padding", 2);
                                    put("display", "BYCLICK");
                                    put("textAlign", "center");
                                }});
                            }});
                        }});
//
                        map.setPolyline(new ArrayList() {{
                            add(new HashMap() {{
                                put("points", new ArrayList() {{
                                    add(new HashMap() {{
                                        put("latitude", 23.10229);
                                        put("longitude", 113.3245211);
                                    }});
                                    add(new HashMap() {{
                                        put("latitude", 23.10229 + 0.01);
                                        put("longitude", 113.3245211 + 0.01);
                                    }});
                                }});
                                put("color", "#ED1111");
                                put("width", 5);
                                put("dottedLine", true);
                                put("arrowLine", true);
                                put("arrowIconPath", "image/location.png");
                                put("borderColor", "#7C7272");
                                put("borderWidth", 5);
                            }});
                        }});

//                        map.setPolygons(new ArrayList() {{
//                            add(new HashMap() {{
//                                put("points", new ArrayList() {{
//                                    add(new HashMap() {{
//                                        put("latitude", 23.099994 + 0.005);
//                                        put("longitude", 113.324520);
//                                    }});
//                                    add(new HashMap() {{
//                                        put("latitude", 23.099994 - 0.005);
//                                        put("longitude", 113.324520);
//                                    }});
//                                    add(new HashMap() {{
//                                        put("latitude", 23.099994);
//                                        put("longitude", 113.324520 - 0.005);
//                                    }});
//                                }});
//                                put("strokeWidth", 5);
//                                put("strokeColor", "#7C7272");
//                                put("fillColor", "#ED1111");
//                                put("zIndex", 1);
//                            }});
//                        }});

                        map.setCircles(new ArrayList() {{
                            add(new HashMap() {{
                                put("latitude", 23.099994 + 0.008);
                                put("longitude", 113.324520);
                                put("color", "#7C7272");
//                                put("fillColor", "#ED1111");
                                put("fillColor", "#5B9FFFFF");
                                put("radius", 50);
                                put("strokeWidth", 3);
                            }});
                        }});

//                        map.setIncludePoints(new ArrayList(){{
//                            add(new HashMap(){{
//                                put("latitude", 23.10229);
//                                put("longitude", 113.3245211);
//                            }});
//                            add(new HashMap(){{
//                                put("latitude", 23.10229 + 0.01);
//                                put("longitude", 113.3245211 + 0.01);
//                            }});
//                        }});

//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                map.setIncludePoints(new ArrayList(){{
//                                    add(new HashMap(){{
//                                        put("latitude", 23.10229);
//                                        put("longitude", 113.3245211);
//                                    }});
//                                    add(new HashMap(){{
//                                        put("latitude", 23.10229 + 0.01);
//                                        put("longitude", 113.3245211 + 0.01);
//                                    }});
//                                }});
//                            }
//                        }, 5000);

                        return null;
                    }
                });

            }});

        }
    @Override
    protected void onStart() {
        super.onStart();
        map.onStart(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        map.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        map.onStop();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        map.onRestart();
    }

    @Override
    protected void onDestroy() {
        map.onDestroy();
        super.onDestroy();
    }
*/

}

package cn.onekit.weixin.app.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import thekit.android.Android;
import cn.onekit.OneKit;
import cn.onekit.css.core.CssLayoutParams;
import cn.onekit.css.core.OnekitCSS;
import cn.onekit.js.Dict;
import cn.onekit.js.JsString;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.vue.Vue;
import cn.onekit.weixin.EventChannel;
import cn.onekit.weixin.PageObject;
import cn.onekit.weixin.WX;
import cn.onekit.weixin.app.Page;

public abstract class WeixinPage extends Activity implements WeixinFile  {
    private void update() {
        //LOG LOG = new LOG();
        page.removeAllViews();
        //LOG.add("removeAllViews");
        Dict data = (Dict)onekit.get("data");
        onekit_wxml(page, data,new Vue(data));
        //LOG.add("onekit_wxml");
        OnekitCSS.share.run(page, new String[]{"/" + url + ".wxss"});
        //LOG.add("OnekitCSS.share.run");
    }

    protected WX wx;
    //////////////////////////////////
    Map<String,Class<WeixinTemplate>> imports = new HashMap();
    public void Import(String src) {
        src = src.substring(0,src.lastIndexOf("/"));
        src = OneKit.fixPath(url,src);
        src = OneKit.url2class(this,src);
        try {
            Class classes = Class.forName(src);
            for(Class<WeixinTemplate> clazz : classes.getClasses()){
                imports.put(clazz.getSimpleName(),clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void Template(String is,Dict data,ViewGroup parent) {
        try {
            WeixinTemplate template = imports.get(is).getConstructor(Context.class).newInstance(this);
            template.onekit_wxml(parent,data,new Vue(data));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        };
    }

    protected Dict onekit = new Dict();
    //
    static Map<Integer, EventChannel> allEventChannel = new HashMap();

    public abstract void onekit_wxml(final ViewGroup ui,  final JsObject data,final Vue vue);

    protected Page page;

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        Android.context = this;
        wx = new WX();
        ////////////////////////
        page = new Page(this);
        setContentView(page);
        page.setLayoutParams(new CssLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        Bundle extras = getIntent().getExtras();
        OneKit.currentUrl = url = OneKit.class2url(this, this.getClass().getName(), extras);
        pages.add(new PageObject(url));
        onekit_js();
         update();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (onekit == null) {
            console.error(new JsString(String.format("Page \"%s\" has not been registered yet.", url)));
        }
        if (onekit.containsKey("onReady")) {
            ((function) onekit.get("onReady")).invoke();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        OneKit.currentUrl = url;
        if (onekit.containsKey("onShow")) {
            ((function) onekit.get("onShow")).invoke();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (onekit.containsKey("onHide")) {
            onekit.get("onHide").invoke();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode>0){
//            setResult(--resultCode);
//            finish();
//        }
//    }

    protected void Page(Dict obj) {
        onekit = obj;
        if(!onekit.containsKey("data")){
            onekit.put("data",new Dict());
        }
        onekit.set("setData",new function(){
            @Override
            public JsObject invoke(JsObject... arguments) {
                Dict data = (Dict) arguments[0];
                for (String key : data.keySet()) {
                    onekit.put(key, data.get(key));
                }
                ((WeixinPage) Android.context).update();
                return null;
            }
        });
        onekit.put("getOpenerEventChannel", new cn.onekit.js.core.function() {
            public EventChannel block(JsObject... arguments) {
                int channelID = getIntent().getIntExtra("onekit_channelID", 0);
                return EventChannel.eventChannels.get(channelID);
            }
        });
        if (onekit.containsKey("onLoad")) {
            Bundle extras = getIntent().getExtras();
            Dict query = new Dict();
            if (extras != null) {
                for (String key : extras.keySet()) {
                    if (key.startsWith("onekit_")) {
                        continue;
                    }
                    query.put(key, new JsString(extras.getString(key)));
                }
            }
            onekit.get("onLoad").invoke(query);
        }
    }

    public static final int VideoView = 1;
    public static final int IMAGE_CAPTURE = 100;
    public static final int IMAGE_CAPTURE_PATH = 101;
    public static final int VIDEO_CAPTURE = 200;
    public static final int GET_CONTENT = 300;
    public static final int GET_CONTENT_PATH = 301;
    public static final int GET_MINI = 400;
    public static final int GET_URI = 500;
    public static final int SCANNIN_GREQUEST_CODE = 202;
    public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 203;
    public static final int location = 204;
    public static final int chooseLocation = 205;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case chooseLocation: {
                this.wx.ACTION1ChooseLocation(resultCode, data);
            }
            break;
            case location: {
                this.wx.ACTION1Openlocation(resultCode, data);
            }
            break;
//            case SCANNIN_GREQUEST_CODE: {
//                ScanCode.SCANNIN_GREQUEST_CODE(resultCode, data);
//            }
//            break;
//            case CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE: {
//                Video.CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE(resultCode, data);
//            }
//            break;
//            case VideoView: {
//                Video.VideoView(resultCode, data);
//            }
//            break;
            default:
                break;
        }
    }

 protected abstract    void onekit_js();
}

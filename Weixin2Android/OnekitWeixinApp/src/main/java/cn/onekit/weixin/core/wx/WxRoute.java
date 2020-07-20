package cn.onekit.weixin.core.wx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TabHost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.onekit.Android;
import cn.onekit.TheKit;
import cn.onekit.js.Dict;
import cn.onekit.js.core.Onekit_JS;
import cn.onekit.js.core.function;
import cn.onekit.weixin.EventChannel;
import cn.onekit.weixin.app.core.Onekit_Weixin_App;
import cn.onekit.weixin.app.core.page.TabsActivity;
import cn.onekit.weixin.core.Onekit_Weixin;
import cn.onekit.weixin.core.res.wx_fail;

public class WxRoute extends WxRedPackage {


    //////////////////////////////////

    public  void switchTab(Map OBJECT) {
        String url = OBJECT.get("url") != null ? (String) OBJECT.get("url") : null;
        url = TheKit.fixPath(TheKit.currentUrl,url);
         url = url.split("\\?")[0];

        try {
            JSONArray tabs=   Onekit_Weixin.APP_JSON.getJSONObject("tabBar").getJSONArray("list");
            for(int t=0;t<tabs.length();t++){
                JSONObject tab = tabs.getJSONObject(t);
                        if(tab.getString("pagePath").equals(url)){
                            final String tag=String.valueOf(t);
                            Handler handler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    TabsActivity.current.getTabHost().setCurrentTabByTag(tag);
                                }
                            };
                            handler.sendEmptyMessageDelayed(0, 0);
                         //   MainActivity.current.getTabHost().setCurrentTabByTag(String.valueOf(t));
                         //   setCurrentTabByTag(MainActivity.current.getTabHost(),t);
                        }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
void setCurrentTabByTag(TabHost tabHost,int tag) {

    try {
        Field idcurrent = tabHost.getClass()
                .getDeclaredField("mCurrentTab");
        idcurrent.setAccessible(true);
        idcurrent.setInt(tabHost, tag);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void reLaunch(Map OBJECT) {
        String url = OBJECT.get("url") != null ? (String) OBJECT.get("url") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            url = TheKit.fixPath(TheKit.currentUrl,url);
            Intent intent = Onekit_Weixin_App.initIntent(application, url,0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent);
            //
            Dict res = new Dict();
            if (success != null) {
                success.invoke(res);
            }
            if (success != null) {
                complete.invoke(res);
            }
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail("reLaunch :fail");
//            res.errMsg = "reLaunch :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void redirectTo(Map OBJECT) {
        String url = OBJECT.get("url") != null ? (String) OBJECT.get("url") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

        try {
            url = TheKit.fixPath( TheKit.currentUrl,url);
            Intent intent = Onekit_Weixin_App.initIntent(application, url,0);
            application.startActivity(intent);
            Dict res = new Dict();
            ((Activity) Android.context).finish();
//            res.errMsg = "redirectTo :Ok";
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail("redirectTo :fail");
//            res.errMsg = "redirectTo :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void navigateTo(Map OBJECT) {
        String url = OBJECT.get("url") != null ? (String) OBJECT.get("url") : null;
        Map<String, function> events = OBJECT.get("events") != null ? (Map) OBJECT.get("events") : new HashMap();
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

        try {
            int channelID = new Random().nextInt();
            url = TheKit.fixPath(TheKit.currentUrl,url);
            Intent intent = Onekit_Weixin_App.initIntent(application, url,channelID);
            //new EventChannel(channelID, -channelID);
            EventChannel resEventChannel = new EventChannel(-channelID, channelID);
            for (Map.Entry<String, function> entry : events.entrySet()) {
                resEventChannel.on(entry.getKey(), entry.getValue());
            }
            ((Activity)Android.context).startActivityForResult(intent,0);
            @SuppressLint("HandlerLeak")
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Dict res = new Dict();
//                    res.errMsg = "navigateTo :ok";
//                    res.eventChannel = resEventChannel;
                    if (success != null) {
                        success.invoke(res);
                    }
                    if (complete != null) {
                        complete.invoke(res);
                    }
                }
            };
            handler.sendEmptyMessageDelayed(0,0);

        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail("navigateTo :fail");
//            res.errMsg = "navigateTo :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void navigateBack(Dict OBJECT) {
        if(OBJECT==null){OBJECT=new Dict();}
        int delta = OBJECT.get("delta") != null ?  Onekit_JS.number(OBJECT.get("delta"),0,0).intValue():1;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

        try {
            delta--;
            if(delta>0) {
                ((Activity)Android.context).setResult(delta);
                ((Activity) Android.context).finish();
            }
            //
            Dict res = new Dict();
//            res.errMsg = "navigateBack :ok";
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail("navigateBack :fail");
//            res.errMsg = "navigateBack :fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

}

package cn.onekit.weixin.app.core;

import android.app.Application;

import cn.onekit.thekit.Android;
import cn.onekit.core.OneKit;
import cn.onekit.css.core.OnekitCSS;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsString;
import cn.onekit.js.JsObject_;
import cn.onekit.weixin.WX;

public abstract class WeixinApplication extends Application implements WeixinFile, JsObject_ {

    public WeixinApplication(){
        super();
    }
    public JsObject THIS;
    public void set(JsObject_ key, JsObject_ value){
        THIS.set(key,value);
    }
   /* public JsObject get(JsObject key){
        return OnekitJS.object2value(THIS.get(key));
    }*/

    protected void App(JsObject OBJECT) {
        THIS = OBJECT;
    }

    protected WX wx ;
    @Override
    public void onCreate() {
        super.onCreate();
        Android.context = this;
        OneKit.currentUrl = "/";
        OnekitCSS.share = new OnekitCSS(Android.SIZE(),"miniprogram/");
        OnekitCSS.share.init(new String[]{"/onekit.wxss","/app.wxss"});
         wx = new WX();
        onekit_js();
    }

   protected abstract   void onekit_js();

    @Override
    public JsObject_ get(JsObject_ key) {
        return THIS.get(key);
    }

    @Override
    public JsString ToString() {
        return null;
    }

    @Override
    public String toLocaleString(JsString locales, JsObject_ options) {
        return null;
    }

    @Override
    public JsObject_ invoke(JsObject_... params) {
        return null;
    }
}

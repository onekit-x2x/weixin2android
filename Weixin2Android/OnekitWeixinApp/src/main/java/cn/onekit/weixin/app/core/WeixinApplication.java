package cn.onekit.weixin.app.core;

import android.app.Application;

import thekit.android.Android;
import cn.onekit.OneKit;
import cn.onekit.css.core.OnekitCSS;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsString;
import cn.onekit.js.core.JsObject;
import cn.onekit.weixin.WX;

public abstract class WeixinApplication extends Application implements WeixinFile,JsObject {

    public WeixinApplication(){
        super();
    }
    public Dict THIS;
    public void set(JsObject key,JsObject value){
        THIS.set(key,value);
    }
   /* public JsObject get(JsObject key){
        return OnekitJS.object2value(THIS.get(key));
    }*/

    protected void App(Dict OBJECT) {
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
    public JsObject get(JsObject key) {
        return THIS.get(key);
    }

    @Override
    public JsString ToString() {
        return null;
    }

    @Override
    public String toLocaleString(JsString locales, JsObject options) {
        return null;
    }

    @Override
    public JsObject invoke(JsObject... params) {
        return null;
    }
}

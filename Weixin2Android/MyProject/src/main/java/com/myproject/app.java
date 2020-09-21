package com.myproject;

import cn.onekit.js.*;
import cn.onekit.js.core.*;
import cn.onekit.weixin.app.core.WeixinApplication;

public class app extends WeixinApplication {
    @Override
    public void onekit_js() {
        App(new JsObject(){{
            put("onLaunch",new function(){ public JsObject_ invoke(JsObject_... arguments){
                JsObject_ logs = Onekit_JS.or(wx.getStorageSync(new JsString("logs")),new JsArray(){{}});
                logs.get("unshift").invoke(Date.now());
                wx.setStorageSync(new JsString("logs"),logs);
                wx.login(new JsObject(){{
                    put("success",new function(){ public JsObject_ invoke(JsObject_... arguments){
                        JsObject_ res = arguments[0];
                        return null;
                    }});
                }});
                wx.getSetting(new JsObject(){{
                    put("success",new function(){ public JsObject_ invoke(JsObject_... arguments){
                        JsObject_ res = arguments[0];
                        if(Onekit_JS.is(res.get("authSetting").get(new JsString("scope.userInfo")))){
                            wx.getUserInfo(new JsObject(){{
                                put("success",new function(){ public JsObject_ invoke(JsObject_... arguments){
                                    JsObject_ res = arguments[0];
                                    this.get("globalData").set( "userInfo", res.get("userInfo"));
                                    if(Onekit_JS.is(this.get("userInfoReadyCallback"))){
                                        this.get("userInfoReadyCallback").invoke(res);
                                    }
                                    return null;
                                }});
                            }});
                        }
                        return null;
                    }});
                }});
                return null;
            }});
            put("globalData",new JsObject(){{
                put("userInfo",null);
            }});
        }});


    }
}
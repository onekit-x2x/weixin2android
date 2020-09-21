package com.myproject;

import android.view.ViewGroup;

import cn.onekit.js.*;
import cn.onekit.js.core.*;
import cn.onekit.vue.*;
import cn.onekit.w3c.*;
import cn.onekit.weixin.app.*;
import cn.onekit.weixin.app.core.*;

public class onekit_pages_index_index extends WeixinPage {

    @Override
    public void onekit_wxml(ViewGroup ui, JsObject_ data, Vue vue) {
        View ui_0 = new View(ui.getContext());
        ui.addView(ui_0);
        ui_0.setClassName("container");
        //
        {
            View ui_0_0 = new View(ui.getContext());
            ui_0.addView(ui_0_0);
            ui_0_0.setClassName("userinfo");
            //
            if (!Onekit_JS.is(data.get("hasUserInfo")) && Onekit_JS.is(data.get("canIUse"))) {
                Button ui_0_0_0 = new Button(this);
                ui_0_0.addView(ui_0_0_0);
                ui_0_0_0.setOpenType("getUserInfo");
                ui_0_0_0.setClassName("userinfo");
                ui_0_0_0.addEventListener("getuserinfo", event -> {
                    onekit.get("getUserInfo").invoke(event);
                    return false;
                });
                //
                LITERAL ui_0_0_0_0 = new LITERAL(this);
                ui_0_0_0.addView(ui_0_0_0_0);
                ui_0_0_0_0.setText(" 获取头像昵称 ");
            } else {

                Image node_0_0_1 = new Image(this);
                node_0_0_1.addView(node_0_0_1);
                node_0_0_1.setClassName("userinfo-avatar");
                node_0_0_1.setSrc(data.get("userInfo").get("avatarUrl"));
                node_0_0_1.setMode("cover");
                node_0_0_1.addEventListener("tap", event -> {
                    onekit.get("bindViewTap").invoke(event);
                    return false;
                });
                //
                Text node_0_0_1_2 = new Text(this);
                node_0_0_1.addView(node_0_0_1_2);
                node_0_0_1_2.setClassName("userinfo-nickname");
                {
                    LITERAL node_0_0_1_2_0 = new LITERAL(this);
                    node_0_0_1_2.addView(node_0_0_1_2_0);
                    node_0_0_1_2_0.setValue(data.get("userInfo").get("nickName"));
                }
            }

            View ui_0_1 = new View(this);
            ui_0.addView(ui_0_1);
            ui_0_1.setClassName("usermotto");

            {
                Text ui_0_1_0 = new Text(this);
                ui_0_1.addView(ui_0_1_0);
                ui_0_1_0.setClassName("user-motto");
                {
                    LITERAL ui_0_1_0_0 = new LITERAL(this);
                    ui_0_1_0.addView(ui_0_1_0_0);
                    ui_0_1_0_0.setValue(data.get("motto"));
                }
            }
        }
    }

     JsObject_ app;
    @Override
    public void onekit_js() {
        app = getApp();
        Page(new JsObject(){{
            put("data",new JsObject(){{
                put("motto",new JsString("Hello World"));
                put("userInfo",new JsObject(){{
                }});
                put("hasUserInfo",new JsBoolean(false));
                put("canIUse",wx.canIUse(new JsString("button.open-type.getUserInfo")));
            }});
            put("bindViewTap",new function(){ public JsObject_ invoke(JsObject_... arguments){
                wx.navigateTo(new JsObject(){{
                    put("url",new JsString("../logs/logs"));
                }});
                return null;
            }});
            put("onLoad",new function(){ public JsObject_ invoke(JsObject_... arguments){
                if(Onekit_JS.is(app.get("globalData").get("userInfo"))){
                    onekit.get("setData").invoke(new JsObject(){{
                        put("userInfo",app.get("globalData").get("userInfo"));
                        put("hasUserInfo",new JsBoolean(true));
                    }});
                }
                else if(Onekit_JS.is(onekit.get("data").get("canIUse"))){
                    app.set( "userInfoReadyCallback", new function(){ public JsObject_ invoke(JsObject_... arguments){
                        final JsObject_ res = arguments[0];
                        onekit.get("setData").invoke(new JsObject(){{
                            put("userInfo",res.get("userInfo"));
                            put("hasUserInfo",new JsBoolean(true));
                        }});
                        return null;
                    }});
                }
                else {
                    wx.getUserInfo(new JsObject(){{
                        put("success",new function(){ public JsObject_ invoke(JsObject_... arguments){
                            final JsObject_ res = arguments[0];
                            app.get("globalData").set( "userInfo", res.get("userInfo"));
                            onekit.get("setData").invoke(new JsObject(){{
                                put("userInfo",res.get("userInfo"));
                                put("hasUserInfo",new JsBoolean(true));
                            }});
                            return null;
                        }});
                    }});
                };
                return null;
            }});
            put("getUserInfo",new function(){ public JsObject_ invoke(JsObject_... arguments){
                final JsObject_ e = arguments[0];
                console.get("log").invoke(e);
                app.get("globalData").set( "userInfo", e.get("detail").get("userInfo"));
                onekit.get("setData").invoke(new JsObject(){{
                    put("userInfo",e.get("detail").get("userInfo"));
                    put("hasUserInfo",new JsBoolean(true));
                }});
                return null;
            }});
        }});


    }
}

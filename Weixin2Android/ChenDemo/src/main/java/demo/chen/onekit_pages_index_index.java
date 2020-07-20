package demo.chen;

import android.view.ViewGroup;

import cn.onekit.js.Dict;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.OnekitJS;
import cn.onekit.js.core.function;
import cn.onekit.vue.Vue;
import cn.onekit.w3c.Event;
import cn.onekit.w3c.LITERAL;
import cn.onekit.weixin.app.WeixinApplication;
import cn.onekit.weixin.app.Button;
import cn.onekit.weixin.app.Image;
import cn.onekit.weixin.app.Text;
import cn.onekit.weixin.app.View;
import cn.onekit.weixin.app.core.WeixinPage;
import cn.onekit.weixin.core.res.wx_getUserInfo;

public class onekit_pages_index_index extends WeixinPage {

    @Override
    public void onekit_js() {
        final WeixinApplication app = getApp();
        Page(new Options() {{
            put("data", new Dict() {{
                put("motto", "Hello World");
                put(" userInfo", new Dict());
                put("hasUserInfo", false);
                put("canIUse", wx.canIUse("button.open-type.getUserInfo"));
            }});
            put("bindViewTap", new function() {
                @Override
                public JsObject invoke(JsObject... arguments) {
                    wx.navigateTo(new Dict() {{
                        put("url", "../logs/logs");
                    }});
                    return false;
                }
            });
            put("onLoad", new function() {
                @Override
                public JsObject invoke(JsObject... arguments) {
                    if (OnekitJS.is(app.get("globalData.userInfo"))) {
                        THIS.setData(new Dict() {{
                            put("userInfo", app.get("globalData").get("userInfo"));
                            put("hasUserInfo", true);
                        }});
                    } else if (OnekitJS.is(THIS.get("data").get("canIUse"))) {
                        // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                        // 所以此处加入 callback 以防止这种情况
                        app.set("userInfoReadyCallback", new function() {
                            @Override
                            public JsObject invoke(JsObject... arguments) {
                                wx_getUserInfo res = (wx_getUserInfo) arguments[0];
                                THIS.setData(new Dict() {{
                                    put("userInfo", res.get("userInfo"));
                                    put("hasUserInfo", true);
                                }});
                                return null;
                            }
                        });
                    } else {
                        // 在没有 open-type=getUserInfo 版本的兼容处理
                        wx.getUserInfo(new Dict() {{
                            put("success", new function() {
                                @Override
                                public JsObject invoke(JsObject... arguments) {
                                    wx_getUserInfo res = (wx_getUserInfo) arguments[0];
                                    THIS.setData(new Dict() {{
                                        put("userInfo", res.get("userInfo"));
                                        put("hasUserInfo", true);
                                    }});
                                    return null;
                                }
                            });
                        }});
                    }
                    return null;
                }
            });
            put("getUserInfo", new function() {
                @Override
                public JsObject invoke(JsObject... arguments) {
                    Event e = (Event) arguments[0];
                    console.log(e);
                    app.get("globalData").sot("userInfo", e.getDetail().get("userInfo"));
                    THIS.setData(new Dict() {{
                        put("userInfo", e.getDetail().get("userInfo"));
                        put("hasUserInfo", true);
                    }});
                    return null;
                }
            });
        }});
    }

    @Override
    public void onekit_wxml(ViewGroup ui, JsObject data, Vue vue) {
        View ui_0 = new View(ui.getContext());
        ui.addView(ui_0);
        ui_0.setClassName("container");
        //
        {
            View ui_0_0 = new View(ui.getContext());
            ui_0.addView(ui_0_0);
            ui_0_0.setClassName("userinfo");
            //
            if (!OnekitJS.is(data.get("hasUserInfo")) && OnekitJS.is(data.get("canIUse"))) {
                Button ui_0_0_0 = new Button(this);
                ui_0_0.addView(ui_0_0_0);
                ui_0_0_0.setOpenType("getUserInfo");
                ui_0_0_0.setClassName("userinfo");
                ui_0_0_0.addEventListener("getuserinfo", event -> {
                    THIS.get("getUserInfo").function().invoke(event);
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
                    THIS.get("bindViewTap").function().invoke(event);
                    return false;
                });
                //
                Text node_0_0_1_2 = new Text(this);
                node_0_0_1.addView(node_0_0_1_2);
                node_0_0_1_2.setClassName("userinfo-nickname");
                {
                    LITERAL node_0_0_1_2_0 = new LITERAL(this);
                    node_0_0_1_2.addView(node_0_0_1_2_0);
                    node_0_0_1_2_0.setText(data.get("userInfo").get("nickName").stringValue());
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
                    ui_0_1_0_0.setText(data.get("motto").stringValue());
                }
            }
        }
    }
}

package com.myproject;

import android.view.ViewGroup;

import cn.onekit.js.*;
import cn.onekit.js.core.*;
import cn.onekit.vue.*;
import cn.onekit.w3c.*;
import cn.onekit.weixin.app.*;
import cn.onekit.weixin.app.core.*;
import thekit.ACTION1;

public class onekit_pages_logs_logs extends WeixinPage {

    @Override
    public void onekit_js() {
        /*
        final Dict util = new onekit_utils_util().exports;
        Page(new Dict() {{
            put("data", new Dict() {{
                put("logs", new Array());
            }});
            put("onLoad", new function() {
                @Override
                public JsObject invoke(JsObject... arguments) {
                    THIS.get("setData").invoke(new Dict() {{
                        put("logs", OnekitJS.or((Array) wx.getStorageSync("logs"), new Array()).get("map").invoke(new function() {
                            @Override
                            public JsObject invoke(JsObject... arguments) {
                                JsNumber log = (JsNumber) arguments[0];
                                return util.get("formatTime").invoke(new Date(log));
                            }
                        }));
                    }});
                    return null;
                }
            });
        }});*/
        JsObject_ util = new onekit_utils_util().exports;
        Page(new JsObject(){{
            put("data",new JsObject(){{
                put("logs",new JsArray(){{}});
            }});
            put("onLoad",new function(){ public JsObject_ invoke(JsObject_... arguments){
                this.get("setData").invoke(new JsObject(){{
                    put("logs", Onekit_JS.or(wx.getStorageSync(new JsString("logs")),new JsArray(){{}}).get("map").invoke(new function(){ public JsObject_ invoke(JsObject_... arguments){
                        JsObject_ log = arguments[0];
                        return util.get("formatTime").invoke(new Date(log));
                    }}));
                }});
                return null;
            }});
        }});

    }

    @Override

    public void onekit_wxml(ViewGroup ui, JsObject_ data, Vue vue) {

        View ui_0 = new View(this);
        ui.addView(ui_0);
        ui_0.setClassName("container log-list");
        vue.For(data.get("logs"), null, "index", "log", new ACTION1<JsObject_>() {
            @Override
            public void invoke(JsObject_ data) {

                Text ui_0_0 = new Text(ui.getContext());
                ui_0.addView(ui_0_0);
                ui_0_0.setClassName("log-item");
                //
                {
                    LITERAL ui_0_0_0 = new LITERAL(ui.getContext());
                    ui_0_0.addView(ui_0_0_0);
                    ui_0_0_0.setText(String.format("%s. %s", Onekit_JS.plus(data.get("index") , new JsNumber(1)), data.get("log")));

                }
            }
        });
    }
}
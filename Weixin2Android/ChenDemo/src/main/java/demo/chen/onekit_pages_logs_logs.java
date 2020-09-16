package demo.chen;

import android.view.ViewGroup;

import thekit.ACTION1;
import cn.onekit.js.Array;
import cn.onekit.js.Date;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.OnekitJS;
import cn.onekit.js.core.function;
import cn.onekit.vue.Vue;
import cn.onekit.w3c.Element;
import cn.onekit.w3c.LITERAL;
import cn.onekit.weixin.app.Text;
import cn.onekit.weixin.app.View;
import cn.onekit.weixin.app.core.WeixinPage;

public class onekit_pages_logs_logs extends WeixinPage {

    @Override
    public void onekit_js() {
        Element a;
        final Dict util = new onekit_utils_util().exports;
        Page(new Options() {{
            put("data", new Dict() {{
                put("logs", new Array());
            }});
            put("onLoad", new function() {
                @Override
                public JsObject invoke(JsObject... arguments) {
                    THIS.setData(new Dict() {{
                        put("logs", OnekitJS.or((Array) wx.getStorageSync("logs"), new Array()).map(new function<String, JsObject>() {
                            @Override
                            public String body(JsObject... arguments) {
                                Long log = (Long) arguments[0];
                                return util.get("formatTime").function().invoke(new Date(log));
                            }
                        }));
                    }});
                    return null;
                }
            });
        }});
    }

    @Override

    public void onekit_wxml(ViewGroup ui, JsObject data, Vue vue) {

        View ui_0 = new View(this);
        ui.addView(ui_0);
        ui_0.setClassName("container log-list");
        vue.For(data.get("logs"), null, "index", "log", new ACTION1() {
            @Override
            public void invoke(JsObject data) {

                Text ui_0_0 = new Text(ui.getContext());
                ui_0.addView(ui_0_0);
                ui_0_0.setClassName("log-item");
                //
                {
                    LITERAL ui_0_0_0 = new LITERAL(ui.getContext());
                    ui_0_0.addView(ui_0_0_0);
                    ui_0_0_0.setText(String.format("%s. %s", data.get("index").intValue() + 1, data.get("log").stringValue()));

                }
            }
        });
    }
}
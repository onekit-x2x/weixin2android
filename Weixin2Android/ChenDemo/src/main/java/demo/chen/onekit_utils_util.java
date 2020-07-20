package demo.chen;

import cn.onekit.js.Array;
import cn.onekit.js.Date;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsString;
import cn.onekit.js.core.OnekitJS;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.core.WeixinModule;

public class onekit_utils_util extends WeixinModule {

    @Override
    public void onekit_js() {

        final function<String, JsObject> formatTime = new function() {
            @Override
            public JsObject invoke(JsObject... arguments) {
                Date date = (Date) arguments[0];
                final int year = date.getFullYear();
                final int month = date.getMonth() + 1;
                final int day = date.getDate();
                final int hour = date.getHours();
                final int minute = date.getMinutes();
                final int second = date.getSeconds();

                return new Array() {{
                    add(year);
                    add(month);
                    add(day);
                }}.map(get("ormatNumber").function()).join('/') + ' ' + new Array() {{add(hour);add(minute); add(second);}}.
                        map(get("ormatNumber").function()).join(':');

            }
        };
        sot("formatTime",formatTime);

        final function<String, JsObject> formatNumber = new function() {
            @Override
            public JsObject invoke(JsObject... arguments) {
                Integer n = (int) arguments[0];
                String n_2 = n.toString();
                return OnekitJS.is(JsString.get(n_2, 1)) ? n : "0" + n;
            }
        };
        sot("formatNumber",formatNumber);

        exports=  new Dict() {{
            put("formatTime", formatTime);
        }};
    }
}
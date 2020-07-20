package cn.onekit.weixin.core.wx;

import cn.onekit.TheKit;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsString;

import static cn.onekit.TheKit.launchPath;

public class WxLifeCycle extends WxKeyboard {

    public Dict getLaunchOptionsSync() {
        Dict result = new Dict() {
            {
                put("path", new JsString(launchPath(application)));
                put("query", new Dict());
                put("scene", new JsNumber(1001));
                put("shareTicket", new JsString(null));
                put("referrerInfo", new Dict());
            }
        };
        return result;
    }

    public Dict getEnterOptionsSync() {
        Dict result = new Dict() {
            {
                put("path", new JsString( TheKit.currentUrl));
                put("query", new Dict());
                put("scene", new JsNumber(1001));
                put("shareTicket", new JsString(null));
                put("referrerInfo", new Dict());
            }
        };
        return result;
    }
}

package cn.onekit.weixin.core.wx;

import cn.onekit.core.OneKit;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsString;

import static cn.onekit.core.OneKit.launchPath;

public class WxLifeCycle extends WxKeyboard {

    public JsObject getLaunchOptionsSync() {
        JsObject result = new JsObject() {
            {
                put("path", new JsString(launchPath(application)));
                put("query", new JsObject());
                put("scene", new JsNumber(1001));
                put("shareTicket", new JsString(null));
                put("referrerInfo", new JsObject());
            }
        };
        return result;
    }

    public JsObject getEnterOptionsSync() {
        JsObject result = new JsObject() {
            {
                put("path", new JsString( OneKit.currentUrl));
                put("query", new JsObject());
                put("scene", new JsNumber(1001));
                put("shareTicket", new JsString(null));
                put("referrerInfo", new JsObject());
            }
        };
        return result;
    }
}

package cn.onekit.weixin.core.res;


import cn.onekit.js.Dict;
import cn.onekit.js.core.JsString;
import cn.onekit.js.core.JsObject;

public class wx_fail extends Dict {
    public wx_fail(String errMsg) {
        put("errMsg", new JsString( errMsg));
    }

    @Override
    public JsObject get(JsObject key) {
        return null;
    }

    @Override
    public void set(JsObject key, JsObject value) {

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

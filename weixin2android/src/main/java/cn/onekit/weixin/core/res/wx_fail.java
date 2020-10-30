package cn.onekit.weixin.core.res;


import cn.onekit.js.JsObject;
import cn.onekit.js.JsString;
import cn.onekit.js.JsObject_;

public class wx_fail extends JsObject {
    public wx_fail(String errMsg) {
        put("errMsg", new JsString( errMsg));
    }

    @Override
    public JsObject_ get(JsObject_ key) {
        return null;
    }

    @Override
    public void set(JsObject_ key, JsObject_ value) {

    }

    @Override
    public JsString ToString() {
        return null;
    }

    @Override
    public String toLocaleString(JsString locales, JsObject_ options) {
        return null;
    }

    @Override
    public JsObject_ invoke(JsObject_... params) {
        return null;
    }
}

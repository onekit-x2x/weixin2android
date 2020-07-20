package cn.onekit.js;

import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.JsString;

public class Error extends java.lang.Error implements JsObject {

    public Error(JsObject message) {
        super(message.toString());
    }
    public Error() {
        super();
    }

    public String toSource(){
        return null;
    }

    @Override
    public JsObject get(String key) {
        return null;
    }

    @Override
    public JsObject get(JsObject key) {
        return null;
    }

    @Override
    public void set(String key, JsObject value) {

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

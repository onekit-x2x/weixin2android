package cn.onekit.js;

import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.JsString;

public class WeakSet implements JsObject {
    ////////////
    public Dict add(JsObject value){
        return null;
    }
    public boolean delete(JsObject value){
        return true;
    }
    public boolean has(JsObject value){
        return true;
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

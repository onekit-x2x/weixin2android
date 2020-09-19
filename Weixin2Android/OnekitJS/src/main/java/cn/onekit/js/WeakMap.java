package cn.onekit.js;

public class WeakMap implements JsObject {
///////////////////////
    public boolean delete(String key){
        return true;
    }
    public boolean has(String key){
        return true;
    }
    public Dict set(JsObject key, Dict value){
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

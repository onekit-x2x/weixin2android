package cn.onekit.js;

import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.JsString;

public class WebAssembly implements JsObject {
    public static Dict compile(Array bufferSource){
        return null;
    }
    public static Dict compileStreaming(Dict source){
        return null;
    }
    public static Dict instantiate(Array bufferSource, Dict importObject ){
        return null;
    }
    public static Dict instantiateStreaming(Dict Parameters, Dict importObject){
        return null;
    }
    public static boolean validate(Dict bufferSource){
        return true;
    }

    @Override
    public JsObject get(String key) {
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

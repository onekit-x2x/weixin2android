package cn.onekit.js;

import cn.onekit.js.core.JsObject;

public class TypeError extends Error {
    public String columnNumber;
    public String fileName;
    public Integer lineNumber;
    public String message;
    public String name;
    public Dict stack;
    ////////////////////
    public String toSource(){
        return null;
    }
    public String toString(){
        return null;
    }

    @Override
    public JsObject get(JsObject key) {
        return null;
    }
}

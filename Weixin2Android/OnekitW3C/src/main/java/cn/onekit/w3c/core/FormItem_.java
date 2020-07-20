package cn.onekit.w3c.core;

import cn.onekit.js.core.JsObject;

public interface FormItem_<T extends JsObject> {

    void setName(String name);

    String getName();

    //
    void setValue(T value);

    T getValue();

    ///////////////////////////
    public abstract void reset();
}

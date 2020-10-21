package cn.onekit.vue;

import thekit.ACTION1;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsObject_;

public class Vue {
    JsObject_ data;

    public Vue(JsObject_ data) {
        this.data = data;
    }

    public void For(JsObject_ forItems, String forKey, String forIndex, String forItem, ACTION1 callback) {
        Iterable<JsObject_> items = (Iterable) forItems;
        if (items == null) {
            return;
        }
        int index = 0;
        JsObject DATA = (JsObject) data;
        for (JsObject_ item : items) {
            DATA.put(forItem, item);
            DATA.put(forIndex, new JsNumber(index));
            callback.invoke(DATA);
            index++;
        }
    }
}

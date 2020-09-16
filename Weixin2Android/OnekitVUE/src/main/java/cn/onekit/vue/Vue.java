package cn.onekit.vue;

import thekit.ACTION1;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsObject;

public class Vue {
    JsObject data;

    public Vue(JsObject data) {
        this.data = data;
    }

    public void For(JsObject forItems, String forKey, String forIndex, String forItem, ACTION1 callback) {
        Iterable<JsObject> items = (Iterable) forItems;
        if (items == null) {
            return;
        }
        int index = 0;
        Dict DATA = (Dict) data;
        for (JsObject item : items) {
            DATA.put(forItem, item);
            DATA.put(forIndex, new JsNumber(index));
            callback.invoke(DATA);
            index++;
        }
    }
}

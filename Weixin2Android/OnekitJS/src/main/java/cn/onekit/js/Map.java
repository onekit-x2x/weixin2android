package cn.onekit.js;

import java.util.HashMap;

import cn.onekit.js.core.Iterator;
import cn.onekit.js.core.function;

public class Map implements JsObject {


    private java.util.Map<JsObject,JsObject> _THIS = new HashMap<>();

    public int getSize() {
        return _THIS.size();
    }

    /////////////////////////////
    public void clear() {
        _THIS.clear();
    }

    public boolean delete(JsObject key) {
        for (Dict.Entry<JsObject,JsObject> entry :_THIS.entrySet()){
            if(entry.getKey().hashCode()==key.hashCode()){
                _THIS.remove(entry.getKey());
                return true;
            }
        }
        return false;
    }

    public Iterator entries() {
        return new Iterator(_THIS.entrySet().iterator()){

            @Override
            public Object getValue(Object value) {
                Dict.Entry<JsObject,JsObject> entry= (Dict.Entry) value;
                return new Array(){{add(entry.getKey());add(entry.getValue());}};
            }
        };
    }

    public   void forEach(function callback, JsObject thisArg) {
        callback.thisArg = thisArg;
        for (Dict.Entry<JsObject,JsObject> entry : _THIS.entrySet()) {
            callback.invoke(entry.getValue(), entry.getKey(), this);
        }
    }
    public void forEach(function  callback ) {
        forEach(callback, null);
    }

    @Override
    public JsObject get(String key) {
        return null;
    }

    public JsObject get(JsObject key) {
        return _THIS.get(key);
    }

    @Override
    public void set(String key, JsObject value) {

    }

    public JsBoolean has(JsObject key) {
         for (Dict.Entry entry :_THIS.entrySet()){
             if(entry.getKey().hashCode()==key.hashCode()){
                 return new JsBoolean(true);
             }
         }
         return new JsBoolean(false);
    }

    public Iterator keys() {
        return new Iterator<JsObject>(_THIS.entrySet().iterator()) {

            @Override
            public JsObject getValue(Object value) {
                Dict.Entry<JsObject,JsObject> entry = (Dict.Entry<JsObject,JsObject>) value;
                return entry.getKey();
            }
        };
    }

    public void set(JsObject key, JsObject value) {
        _THIS.put(key ,value);
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

    public Iterator values() {
        return new Iterator(_THIS.entrySet().iterator()) {


            @Override
            public JsObject getValue(Object value) {
                Dict.Entry<JsObject,JsObject> entry = (Dict.Entry<JsObject,JsObject> ) value;
                return entry.getValue();
            }
        };
    }

}

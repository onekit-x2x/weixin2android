package cn.onekit.js.core;

import java.lang.reflect.Field;

import cn.onekit.js.Dict;

public interface JsObject {
    default  JsObject get(String key){
        try {
            if(this instanceof Dict){
                Dict dict = ((Dict)this);
                return dict.get(key);
            }else {
                Class clazz = this.getClass();
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                return (JsObject) field.get(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     default JsObject get(JsObject key){
         return get(((JsString)key).THIS);
     }
    default JsObject get(int key){
        return null;
    }
    default  void set(String key,JsObject value){
        if(this instanceof Dict) {
            Dict dict = ((Dict)this);
             dict.put(key,value);
        }else {
            try {
                Class clazz = this.getClass();
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(this, value);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    default void set(JsObject key,JsObject value){
        set(((JsString)key).THIS,value);
    }
/*
    default void sot(JsObject key, JsObject value) {
        try {
            if (this instanceof Dict) {
                Dict dict = (Dict) this;
                if (value == null) {
                    dict.put(key, null);
                } else if (value instanceof JsObject) {
                    dict.put(key, value);
                } else {
                    dict.put(key, new NUMBER(value));
                }

            } else  if (this instanceof Array) {
                int index = OnekitJS.number(key).intValue();
                Array array = (Array) this;
                if (value == null) {
                    array.set(index, null);
                } else if (value instanceof JsObject) {
                    array.set(index, value);
                } else {
                    array.set(index, new NUMBER(value));
                }

            } else if (this instanceof Map) {
                Map map = (Map) this;
                map.put(key, value);
            } else {
                throw new Exception(this.toString());
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
    default JsObject get(JsObject key) {
        throw new Error("please use xxx.get('key')!");
    }

    default JsObject get(JsObject key) {
        try {
            JsObject value;
             if (this instanceof Map) {
                Map map = (Map) this;
                value=map.get(key);
            } else  if (this instanceof List) {
                int index = OnekitJS.number(key).intValue();
                ArrayList list = (ArrayList)this;
                value=list.get(index);
            }else {
                String key_ = (String) key;
                Class clazz = this.getClass();
                Field field = clazz.getDeclaredField(key_);
                field.setAccessible(true);
                value = field.get(this);
            }
            return OnekitJS.object2value(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
*/
     JsString ToString();/*{
        Class clazz = this.getClass();
        StringBuilder result = new StringBuilder();
        result.append("{");
        int i = 0;
        for (Field field : clazz.getDeclaredFields()) {
            try {
                if (i++ > 0) {
                    result.append(",");
                }
                Object temp = field.get(this);
                Object value;
                if (temp == null) {
                    value = null;
                } else if (temp instanceof String) {
                    value = String.format("\"%s\"", temp);
                } else {
                    value = temp.toString();
                }
                result.append(String.format("\"%s\":%s", field.getName(),value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.append("}");
        return new STRING(result.toString());
    }*/

    default String toLocaleString(JsString locales, JsObject options){
        return toString();
    }

    default  JsObject invoke(JsObject... params){
        return null;
    }

}

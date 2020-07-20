package cn.onekit.js;

import cn.onekit.js.core.JsObject;

public class Reflect  {
    ////////////////////////
    public static JsObject apply(Dict target, Dict thisArgument, Dict argumentsList){
        return null;
    }
    public static JsObject construct(Dict target, Dict argumentsList, Dict newTarget ){
        return null;
    }
    public static boolean defineProperty(Dict target, String  propertyKey, String attributes){
        return true;
    }
    public static boolean deleteProperty(Dict target, String  propertyKey){
        return true;
    }
    public static JsObject get(Dict target, String  propertyKey, Dict receiver){
        return null;
    }
    public static JsObject getOwnPropertyDescriptor(Dict target, String propertyKey){
        return null;
    }
    public static JsObject getPrototypeOf(Dict target){
        return null;
    }
    public static boolean has(Dict target, String propertyKey){
        return true;
    }
    public static boolean isExtensible(Dict target){
        return true;
    }
    public static Dict ownKeys(Dict target){
        return  null;
    }
    public static boolean preventExtensions(Dict target){
        return true;
    }
    public static boolean set(Dict target, String propertyKey, String value, Dict receiver){
        return true;
    }
    public static boolean setPrototypeOf(Dict target, Dict prototype){
        return true;
    }

}

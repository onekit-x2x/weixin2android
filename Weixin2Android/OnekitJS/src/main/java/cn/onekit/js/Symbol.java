package cn.onekit.js;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Symbol implements JsObject {

    static List _Symbols = new ArrayList();
    JsObject _description;
    public Symbol(JsObject description){
        _description=description;
        String result = String.format("__%s_%d_%d__", description, (int) java.lang.Math.floor(java.lang.Math.random() * 1e9), _Symbols.size() + 1);
        _Symbols.add(result);
    }
    public Symbol(){
        this(new JsNumber(new Random().nextInt()));
    }
    public static boolean asyncIterator;
    public static boolean hasInstance;
    public static Dict isConcatSpreadable;
    public static Array iterator ;
    public static String match;
    public static Dict matchAll;
    public final String description = null;
    public static String  replace;
    public static Dict search;
    public static boolean species;
    public static String split;
    public static Dict toPrimitive;
    public static String toStringTag;
    public static Dict unscopables;
    //////////////////////////////
    public static Dict For(String key){
        return null;
    }
    public static String keyFor(Dict sym){
        return null;
    }
    public String toSource(){
        return  null;
    }
    public String toString(){
        return String.format("Symbol(%s)",_description);
    }
    public Dict valueOf(){
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

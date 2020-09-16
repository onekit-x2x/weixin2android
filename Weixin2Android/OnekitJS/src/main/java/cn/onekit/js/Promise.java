package cn.onekit.js;

import cn.onekit.js.core.*;

public class Promise implements JsObject {
    function _callback;

    @Override
    public String toString() {
        return "[obj Promise]";
    }

    ///////////////////////////////
    public Promise(function callback) {
        _callback = callback;
    }

    //////////////////////////////////
    public static Promise all(Array iterable) {
        return null;
        /*return new Promise((resolve, reject) -> {
            Array result = new Array();
            for (JsObject item : iterable) {
                if (item instanceof Promise) {
                    Promise promise = (Promise) item;
                    //promise.then(new cn.onekit.js.core.function() {
                   //     @Override
                   //     public JsObject invoke(JsObject... arguments) {
                  //          JsObject arg=arguments[0];
                  //           result.add(arg);
                 //            return null;
                 //       }
                //    });
                } else {
                    result.add(item);
                }
            }
      // reject).invoke(result);
            return null;
        });*/
    }

    public static Promise all(String iterable) {
        return null;
    }

    public static Promise allSettled(Array iterable) {
        return null;
    }

    public static Promise allSettled(String iterable) {
        return null;
    }

    public Promise Catch(function onRejected) {
        return null;
    }

    public Promise Finally(function onFinally) {
        return null;
    }

    public Promise then(cn.onekit.js.core.function onFulfilled, cn.onekit.js.core.function onRejected) {
        _callback.invoke(onFulfilled, onRejected);
        return this;
    }

    public Promise then(cn.onekit.js.core.function onFulfilled) {
        return then(onFulfilled, null);
    }

    public static Promise race(Array iterable) {
        return null;
    }

    public static Promise race(String iterable) {
        return null;
    }

    public static Promise reject(Error reason) {
        return null;//new Promise((resolve, reject) -> (()invoke(reason));
    }

    public static Promise resolve(JsObject value) {
        return new Promise(new function(){

            @Override
            public JsObject invoke(JsObject... arguments) {
                try {
                    function resolve = (function) arguments[0];
                    resolve.invoke(value);
                }catch (Exception e){
                    e.printStackTrace();
                    function reject = (function) arguments[1];
                    reject.invoke(value);
                }
                return null;
            }
        });
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

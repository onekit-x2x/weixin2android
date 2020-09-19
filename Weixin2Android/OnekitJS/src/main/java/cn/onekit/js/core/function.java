package cn.onekit.js.core;

import java.lang.reflect.Method;

import cn.onekit.js.JsObject;
import cn.onekit.js.JsString;

public  class function implements JsObject {
     JsObject obj;
     Method method;
     public JsObject thisArg;
     public function(){

     }

     public function(Class clazz, String methodName, Class< JsObject>... types) {
          try {
               method = clazz.getMethod(methodName,types);
          } catch (NoSuchMethodException e) {
               e.printStackTrace();
          }
     }

     public function(JsObject obj, String methodName, Class< JsObject>... types) {
          this(obj.getClass(), methodName,types);
          this.obj = obj;
     }
     public JsObject invoke(JsObject... arguments) {
          try{
               return (JsObject) method.invoke(obj, arguments);
          } catch (Exception e) {
               e.printStackTrace();
               return null;
          }
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
}

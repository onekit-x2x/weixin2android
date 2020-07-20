package cn.onekit.js.core;

import java.lang.reflect.Method;

public  class function implements JsObject {
     JsObject object;
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

     public function(JsObject object, String methodName, Class< JsObject>... types) {
          this(object.getClass(), methodName,types);
          this.object = object;
     }
     public JsObject invoke(JsObject... arguments) {
          try{
               return (JsObject) method.invoke(object, arguments);
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

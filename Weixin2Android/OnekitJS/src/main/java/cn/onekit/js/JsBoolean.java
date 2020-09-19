package cn.onekit.js;

public class JsBoolean implements JsObject {
    public boolean THIS;
    public JsBoolean(boolean value) {
       THIS = value;
    }
    public JsBoolean(JsObject value) {
        if (value == null) {
            THIS= false;
        }
        if (value instanceof JsString) {
            String s = value.toString();
            THIS= s.length() > 0;
        } else if (value instanceof JsBoolean) {
            THIS= ((JsBoolean) value).THIS;
        } else if (value instanceof JsNumber) {
            THIS= ((JsNumber)value).THIS.doubleValue() != 0;
        } else {
            THIS= true;
        }
    }
    public static String toString(java.lang.Boolean THIS) {
        return THIS.toString();
    }
    public static JsBoolean valueof(JsObject value) {
        return new JsBoolean(value);
    }
    public JsString ToString() {
        return new JsString(String.valueOf(THIS));
    }

    @Override
    public String toLocaleString(JsString locales, JsObject options) {
        return null;
    }

    @Override
    public JsObject invoke(JsObject... params) {
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
}
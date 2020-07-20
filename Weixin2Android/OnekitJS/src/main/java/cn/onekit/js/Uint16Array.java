package cn.onekit.js;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.function;

public class Uint16Array extends TypedArray{
    public final static JsNumber BYTES_PER_ELEMENT=new JsNumber(2);
    public final static String name = "Uint16Array";

    public <TA extends TypedArray> Uint16Array(JsObject length) {
        super(Uint16Array.class, length);
    }

    public <TA extends TypedArray> Uint16Array(TA typedArray) {
        super(typedArray);
    }

    public <TA extends TypedArray> Uint16Array(JsObject buffer, JsObject byteOffset, JsObject length) {
        super(Uint16Array.class, buffer, byteOffset, length);
    }

    public <TA extends TypedArray> Uint16Array(JsObject buffer, JsObject byteOffset) {
        super(Uint16Array.class, buffer, byteOffset);
    }


    public <TA extends TypedArray> Uint16Array(Array array) {
        super(Uint16Array.class, array);
    }

    //////////////////////////////////
    public static  Uint16Array from(Set source, function mapFn, JsObject thisArg) {
        return _from(Uint16Array.class, source, mapFn, thisArg);
    }

    public static Uint16Array from(Set source, function mapFn) {
        return from(source, mapFn, null);
    }

    public static Uint16Array from(Set source) {
        return from(source, null);
    }

    //
    public static  Uint16Array from(Array source, function mapFn, JsObject thisArg) {
        return _from(Uint16Array.class, source, mapFn, thisArg);
    }

    public static Uint16Array from(Array source, function mapFn) {
        return from(source, mapFn, null);
    }

    public static Uint16Array from(Array source) {
        return from(source, null);
    }

    //
    public static  Uint16Array from(JsObject source, JsObject mapFn, JsObject thisArg) {
        return _from(Uint16Array.class, source, mapFn, thisArg);
    }

    public static Uint16Array from(JsObject source, JsObject mapFn) {
        return from(source, mapFn, null);
    }

    public static Uint16Array from(JsObject source) {
        return from(source, null);
    }

    public static Uint16Array of(JsObject... elements) {
        return _of(Uint16Array.class, elements);
    }
}

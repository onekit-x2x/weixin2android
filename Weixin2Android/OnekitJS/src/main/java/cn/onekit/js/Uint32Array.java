package cn.onekit.js;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.function;

public class Uint32Array extends TypedArray{
    public final static JsNumber BYTES_PER_ELEMENT=new JsNumber(4);
    public final static String name = "Uint32Array";

    public <TA extends TypedArray> Uint32Array(JsObject length) {
        super(Uint32Array.class, length);
    }

    public <TA extends TypedArray> Uint32Array(TA typedArray) {
        super(typedArray);
    }

    public <TA extends TypedArray> Uint32Array(JsObject buffer, JsObject byteOffset, JsObject length) {
        super(Uint32Array.class, buffer, byteOffset, length);
    }

    public <TA extends TypedArray> Uint32Array(JsObject buffer, JsObject byteOffset) {
        super(Uint32Array.class, buffer, byteOffset);
    }

    public <TA extends TypedArray> Uint32Array(Array array) {
        super(Uint32Array.class, array);
    }

    //////////////////////////////////
    public static  Uint32Array from(Set source, function mapFn, JsObject thisArg) {
        return _from(Uint32Array.class, source, mapFn, thisArg);
    }

    public static Uint32Array from(Set source, function mapFn) {
        return from(source, mapFn, null);
    }

    public static Uint32Array from(Set source) {
        return from(source, null);
    }

    //
    public static  Uint32Array from(Array source, function mapFn, JsObject thisArg) {
        return _from(Uint32Array.class, source, mapFn, thisArg);
    }

    public static Uint32Array from(Array source, function mapFn) {
        return from(source, mapFn, null);
    }

    public static Uint32Array from(Array source) {
        return from(source, null);
    }

    //
    public static  Uint32Array from(JsObject source, JsObject mapFn, JsObject thisArg) {
        return _from(Uint32Array.class, source, mapFn, thisArg);
    }

    public static Uint32Array from(JsObject source, JsObject mapFn) {
        return from(source, mapFn, null);
    }

    public static Uint32Array from(JsObject source) {
        return from(source, null);
    }

    public static Uint32Array of(JsObject... elements) {
        return _of(Uint32Array.class, elements);
    }


}

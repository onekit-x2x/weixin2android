package cn.onekit.js;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.function;

public class Uint8Array extends TypedArray{
    public final static JsNumber BYTES_PER_ELEMENT=new JsNumber(1);
    public final static String name = "Uint8Array";

    public <TA extends TypedArray> Uint8Array(JsNumber length) {
        super(Uint8Array.class, length);
    }

    public <TA extends TypedArray> Uint8Array(TA typedArray) {
        super(typedArray);
    }

    public <TA extends TypedArray> Uint8Array(JsObject buffer, JsObject byteOffset, JsObject length) {
        super(Uint8Array.class, buffer, byteOffset, length);
    }

    public <TA extends TypedArray> Uint8Array(JsObject buffer, JsObject byteOffset) {
        super(Uint8Array.class, buffer, byteOffset);
    }

    public <TA extends TypedArray> Uint8Array(JsObject buffer) {
        super(Uint8Array.class, buffer);
    }

    public <TA extends TypedArray> Uint8Array(Array array) {
        super(Uint8Array.class, array);
    }

    //////////////////////////////////
    public static  Uint8Array from(Set source, function mapFn, JsObject thisArg) {
        return _from(Uint8Array.class, source, mapFn, thisArg);
    }
    public static Uint8Array of(JsObject... elements) {
        return _of(Uint8Array.class, elements);
    }
}
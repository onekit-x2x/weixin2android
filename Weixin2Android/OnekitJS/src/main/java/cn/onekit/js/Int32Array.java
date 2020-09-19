package cn.onekit.js;

import cn.onekit.js.core.function;

public class Int32Array extends TypedArray{
    public final static int BYTES_PER_ELEMENT=4;
    public final static String name="Int32Array";
    public Int32Array(JsObject length) {
        super(Int32Array.class,length);
    }

    public Int32Array(TypedArray typedArray) {
        super(typedArray);
    }

    public Int32Array(JsObject buffer, JsObject byteOffset, JsObject length) {
        super(Int32Array.class,buffer, byteOffset, length);
    }

    public Int32Array(JsObject buffer, JsObject byteOffset) {
        super(Int32Array.class,buffer, byteOffset);
    }


    public Int32Array(Array array) {
        super(Int32Array.class,array);
    }
    //////////////////////////////////
    public static  Int32Array from(Set source, function mapFn, JsObject thisArg) {
        return _from(Int32Array.class, source, mapFn, thisArg);
    }
    public static Int32Array of(JsObject... elements) {
        return _of(Int32Array.class, elements);
    }
}

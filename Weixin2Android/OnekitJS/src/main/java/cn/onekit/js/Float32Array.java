package cn.onekit.js;

import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.function;

public class Float32Array extends TypedArray<Double> {
    public final static JsNumber BYTES_PER_ELEMENT=new JsNumber(4);
    public final static String name="Float32Array";
    public Float32Array(JsObject length) {
        super(Float32Array.class,length);
    }

    public Float32Array(TypedArray<Double> typedArray) {
        super(typedArray);
    }

    public Float32Array(JsObject buffer, JsObject byteOffset, JsObject length) {
        super(Float32Array.class,buffer, byteOffset, length);
    }

    public Float32Array(JsObject buffer, JsObject byteOffset) {
        super(Float32Array.class,buffer, byteOffset);
    }


    public Float32Array(Array array) {
        super(Float32Array.class,array);
    }
    //////////////////////////////////
    public static  Float32Array from(Set source, function mapFn, JsObject thisArg) {
        return _from(Float32Array.class, source, mapFn, thisArg);
    }
    public static Float32Array of(JsObject... elements) {
        return _of(Float32Array.class, elements);
    }
}

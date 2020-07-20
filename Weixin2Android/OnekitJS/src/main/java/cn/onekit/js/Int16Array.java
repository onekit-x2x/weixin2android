package cn.onekit.js;

import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.function;

public class Int16Array extends TypedArray{
    public final static JsNumber BYTES_PER_ELEMENT=new JsNumber(2);
    public final static String name="Int16Array";
    public Int16Array(JsObject length) {
        super(Int16Array.class,length);
    }

    public Int16Array(TypedArray typedArray) {
        super(typedArray);
    }

    public Int16Array(JsObject buffer, JsObject byteOffset, JsObject length) {
        super(Int16Array.class,buffer, byteOffset, length);
    }

    public Int16Array(JsObject buffer, JsObject byteOffset) {
        super(Int16Array.class,buffer, byteOffset);
    }

    public Int16Array(Array array) {
        super(Int16Array.class,array);
    }
    //////////////////////////////////
    public static  Int16Array from(Set source, function mapFn, JsObject thisArg) {
        return _from(Int16Array.class, source, mapFn, thisArg);
    }
    public static Int16Array of(JsObject... elements) {
        return _of(Int16Array.class, elements);
    }
}

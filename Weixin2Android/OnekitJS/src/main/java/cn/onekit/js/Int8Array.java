package cn.onekit.js;

public class Int8Array extends TypedArray{
    public final static JsNumber BYTES_PER_ELEMENT=new JsNumber(1);
    public final static String name="Int8Array";
    public Int8Array(JsObject length) {
        super(Int8Array.class,length);
    }


    public Int8Array(JsObject buffer, JsObject byteOffset, JsObject length) {
        super(Int8Array.class,buffer, byteOffset, length);
    }

    public Int8Array(JsObject buffer, JsObject byteOffset) {
        super(Int8Array.class,buffer, byteOffset);
    }


    public Int8Array(Array array) {
        super(Int8Array.class,array);
    }
    //////////////////////////////////
    public static  Int8Array from(JsObject source, JsObject mapFn, JsObject thisArg) {
        return _from(Int8Array.class, source, mapFn, thisArg);
    }


    public static Int8Array of(JsObject... elements) {
        return _of(Int8Array.class, elements);
    }

}

package cn.onekit.js;

import cn.onekit.js.core.Onekit_JS;

/**
 * Created by zhangjin on 2017/10/30.
 */

public class DataView implements JsObject {
    private final int _byteLength;
    private ArrayBuffer _buffer;
    private int _byteOffset;

    ////////////////////////////////////////////////////
    public DataView(JsObject buffer, JsObject byteOffset, JsObject byteLength) {
        _byteOffset = Onekit_JS.number(byteOffset,0,0).intValue();
        _byteLength = Onekit_JS.number(byteLength,0,0).intValue();
        _buffer = (ArrayBuffer)buffer;
    }

    public DataView(JsObject buffer, JsObject byteOffset) {
        this(buffer, byteOffset, new JsNumber(((JsNumber)((ArrayBuffer)buffer).getByteLength()).THIS.intValue() - ((JsNumber)byteOffset).THIS.intValue()));
    }

    public DataView(JsObject buffer) {
        this(buffer, new JsNumber(0));
    }

    //////////////////////////////////////////////

    public ArrayBuffer getBuffer() {
        return _buffer;
    }

    public JsObject getByteLength() {
        return new JsNumber(_byteLength);
    }

    public JsObject getByteOffset() {
        return new JsNumber(_byteOffset);
    }
    ////////////////////////////////////////////////


    public JsObject getFloat32(JsObject byteOffset, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        return new JsNumber((float) _get(byteOffset, "Float32", 4, ((JsBoolean)littleEndian).THIS));
    }
 

    public JsObject getFloat64(JsObject byteOffset, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        return new JsNumber((double)_get(byteOffset, "Float64", 8, ((JsBoolean)littleEndian).THIS));
    }

    public JsObject getInt16(JsObject byteOffset, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        return new JsNumber( (short)_get(byteOffset, "Int16", 2, ((JsBoolean)littleEndian).THIS));
    }
    

    public JsObject getInt32(JsObject byteOffset, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        return (JsObject) _get(byteOffset, "Int32", 4, ((JsBoolean)littleEndian).THIS);
    }

    public JsObject getInt8(JsObject byteOffset) {
        return new JsNumber( (byte)_get(byteOffset, "Int8", 1, false));
    }

    public JsObject getUint16(JsObject byteOffset, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        return new JsNumber( (short)_get(byteOffset, "Uint16", 2, ((JsBoolean)littleEndian).THIS));
    }
    public JsObject getUint32(JsObject byteOffset, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        return new JsNumber((long) _get(byteOffset, "Uint32", 4, ((JsBoolean)littleEndian).THIS));
    }

    public JsObject getUint8(JsObject byteOffset) {
        return new JsNumber((short) _get(byteOffset, "Uint8", 1, false));
    }

    public void setFloat32(JsObject byteOffset, JsObject value, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        _set(byteOffset, value, "Float32", 4, ((JsBoolean)littleEndian).THIS);
    }

    public void setFloat64(JsObject byteOffset, JsObject value, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        _set(byteOffset, value, "Float64", 8, ((JsBoolean)littleEndian).THIS);
    }


    public void setInt16(JsObject byteOffset, JsObject value, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        _set(byteOffset, value, "Int16", 2, ((JsBoolean)littleEndian).THIS);
    }

    public void setInt32(JsObject byteOffset, JsObject value, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        _set(byteOffset, value, "Int32", 4, ((JsBoolean)littleEndian).THIS);
    }

    public void setInt8(JsObject byteOffset, JsObject value) {
        
        _set(byteOffset, value, "Int8", 1, false);
    }

    

    public void setUint16(JsObject byteOffset, JsObject value, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        _set(byteOffset, value, "Uint16", 2, ((JsBoolean)littleEndian).THIS);
    }
    public void setUint32(JsObject byteOffset, JsObject value, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        _set(byteOffset, value, "Uint32", 4, ((JsBoolean)littleEndian).THIS);
    }

    public void setUint8(JsObject byteOffset, JsObject value, JsObject littleEndian) {
        if(littleEndian==null){
            littleEndian = new JsBoolean(false) ;
        }
        _set(byteOffset, value, "Uint8", 1, ((JsBoolean)littleEndian).THIS);
    }

    private Object _get(JsObject byteOffset, String type, int BYTES_PER_ELEMENT, boolean littleEndian) {
        
        return Onekit_JS.bytes2number(_buffer._data, type, BYTES_PER_ELEMENT,((JsNumber)getByteOffset()).THIS.intValue()+ ((JsNumber)byteOffset).THIS.intValue());
    }

    private <T extends Number>  void _set(JsObject byteOffset,JsObject value, String type, int BYTES_PER_ELEMENT, boolean littleEndian) {
        Onekit_JS.number2bytes(_buffer._data, type, BYTES_PER_ELEMENT,_byteOffset+ ((JsNumber)byteOffset).THIS.intValue(), value);
    }

    @Override
    public JsObject get(JsObject key) {
        return null;
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

    @Override
    public JsObject invoke(JsObject... params) {
        return null;
    }
}



















































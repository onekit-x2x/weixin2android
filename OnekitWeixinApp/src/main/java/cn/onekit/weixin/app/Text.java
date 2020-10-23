package cn.onekit.weixin.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import cn.onekit.css.CSSStyleDeclaration;
import cn.onekit.weixin.app.core.WeixinElement;

public class Text extends WeixinElement {
    public Text(Context context) {
        super(context);
        _init( context);
    }

    public Text(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WX);
        for (int i = 0; i < array.getIndexCount(); i++) {
            int index = array.getIndex(i);
            if (index == R.styleable.WX_SELECTABLE) {
                selectable(array.getBoolean(index,false));
            }else  if (index == R.styleable.WX_SPACE) {
                setSpace(array.getString(index));
            }else  if (index == R.styleable.WX_DECODE) {
                setDecode(array.getBoolean(index,false));
            }else {

            }
        }
        array.recycle();
    }
    public  void _init(Context context){
        super._init(context);
        selectable(false);
        setSpace("false");
        setDecode(false);
    }

    boolean _selectable;
    public boolean selectable() {
        return _selectable;
    }
    public void selectable(boolean selectable) {
        this._selectable = selectable;
    }

    String _space;
    public String getSpace() {
        return _space;
    }
    public void setSpace(String space) {
        this._space = space;
    }

    boolean _decode;
    public boolean getDecode() {
        return _decode;
    }
    public void setDecode(boolean decode) {
        this._decode = decode;
    }

}

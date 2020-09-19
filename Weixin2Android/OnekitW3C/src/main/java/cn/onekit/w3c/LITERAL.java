package cn.onekit.w3c;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import cn.onekit.LITERAL_;
import cn.onekit.js.JsString;
import cn.onekit.js.JsObject;


public class LITERAL extends TextView implements Node, LITERAL_ {



    AttributeSet _attrs;

    ///////////////////////////////////////////////
    public LITERAL(Context context) {
        super(context);
        _init(context);
        //
        //
        _init2(context);
    }
    public LITERAL(Context context,AttributeSet attrs) {
        super(context,attrs);
        _init(context);
        //
        //
        _init2(context);
    }

    public  void _init(Context context) {

    }

    public   void _init2(Context context) {

    }

    @Override
    public TextView view() {
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
         super.dispatchTouchEvent(event);
        return false;
    }

    public void setValue(JsObject text) {
        this.setText(((JsString)text).THIS);
    }

    public JsObject getValue() {
        return new JsString((String) this.getText());
    }
}

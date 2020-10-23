package cn.onekit.weixin.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import cn.onekit.js.JsObject;
import cn.onekit.w3c.Event;
import cn.onekit.w3c.core.FormItem_;
import cn.onekit.weixin.app.core.WeixinElement;

public class Form extends WeixinElement {

    public Form(Context context) {
        super(context);
        _init();
    }
    public Form(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private void _init() {

    }

    public void submit() {
        try {
            final JsObject data = new JsObject();
            _formData(this, data);
            dispatchEvent(new  Event("submit",new JsObject() {{
                put("value", data);
            }},this,this,0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void _formData(ViewGroup parent, JsObject data)  {

            for (int i = 0; i < parent.getChildCount(); i++) {
                View view = this.getChildAt(i);
                if (view instanceof FormItem_) {
                    FormItem_ formItem = (FormItem_) view;
                    if(formItem.getName()!=null) {
                        data.put(formItem.getName(), formItem.getValue());
                    }
                }else if(view instanceof ViewGroup){
                    _formData((ViewGroup) view,data);
                }

            }


    }

    public void reset() {
        try {
                _resetData(this);
            dispatchEvent(new  Event("reset",null,this,this,0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void _resetData(ViewGroup parent) {

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = this.getChildAt(i);
            if (view instanceof FormItem_) {
                FormItem_ formItem = (FormItem_) view;
                if(formItem.getName()!=null) {
                    formItem.reset();
                }
            }else if(view instanceof ViewGroup){
                _resetData((ViewGroup) view);
            }

        }


    }
}

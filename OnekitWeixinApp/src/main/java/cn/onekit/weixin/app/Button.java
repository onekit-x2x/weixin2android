package cn.onekit.weixin.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;

import cn.onekit.weixin.app.core.WeixinElement;

public class Button extends WeixinElement {


    public Button(Context context) {
        super(context);
        _init();
    }
    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //
    private String _openType;
    public void setOpenType(String openType){
        _openType = openType;
    }
    public String getOpenType(){
        return _openType;
    }
    //
    private String _formType;
    public void setFormType(String formType){
        _formType=formType;
    }
    public String getFormType(){
        return _formType;
    }
    //
    //
    private void _init() {
        this.setOnClickListener(v -> {
            if(getOpenType()!=null) {
                switch (getOpenType()) {
                    case "contact":
                        ;
                        break;
                    case "share":
                        ;
                        break;
                    case "getPhoneNumber":
                        ;
                        break;
                    case "getUserInfo":
                        wx.getUserInfo(new HashMap(){{

                        }});
                        break;
                    case "launchApp":
                        ;
                        break;
                    case "openSetting":
                        ;
                        break;
                    case "feedback":
                        ;
                        break;
                    default:
                        break;
                }
                return;
            }
            if (getFormType()!= null) {
                View parent = (View) Button.this.getParent();

                while (parent != null && !(parent instanceof Form)) {
                    parent = (View) parent.getParent();
                }
                if (parent != null) {
                    Form form = (Form) parent;
                    switch (getFormType()) {
                        case "submit":
                            form.submit();
                            break;
                        case "reset":
                            form.reset();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }



}

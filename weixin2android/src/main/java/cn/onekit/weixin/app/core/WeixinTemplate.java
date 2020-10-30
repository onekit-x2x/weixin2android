package cn.onekit.weixin.app.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import cn.onekit.js.JsObject_;
import cn.onekit.w3c.Template;

public abstract class WeixinTemplate extends Template {
    public WeixinTemplate(Context context) {
        super(context);
    }

    public WeixinTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public abstract void onekit_wxml(final ViewGroup ui, final JsObject_ data, final Vue vue);
}

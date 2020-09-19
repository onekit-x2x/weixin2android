package cn.onekit.weixin.app;

import android.content.Context;
import android.util.AttributeSet;

import cn.onekit.js.Dict;
import cn.onekit.js.JsString;
import cn.onekit.weixin.app.core.WeixinElement;

public class Navigator extends WeixinElement {
    public Navigator(Context context) {
        super(context);
        _init();
    }

    public Navigator(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    void _init() {
        this.setOnClickListener(v -> {
            wx.navigateTo(new Dict() {{
                put("url", new JsString(getUrl()));
            }});
        });
    }

    private String getUrl() {
        return src;
    }

    String src;
    public void setSrc(String src) {
        this.src = src;
    }

    String openType;
    public void setOpenType(String openType) {
        this.openType=openType;
    }

}

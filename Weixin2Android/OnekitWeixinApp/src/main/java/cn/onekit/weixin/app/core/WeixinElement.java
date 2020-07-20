package cn.onekit.weixin.app.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.HashMap;

import cn.onekit.js.Dict;
import cn.onekit.js.core.JsNumber;
import cn.onekit.w3c.Element;
import cn.onekit.w3c.Event;
import cn.onekit.w3c.EventListener;
import cn.onekit.weixin.WX;

public abstract class WeixinElement extends Element {
    public WeixinElement(Context context) {
        super(context);
        _init();
    }

    public WeixinElement(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    protected WX wx;

    void _init() {
        wx = ((WeixinPage) getContext()).wx;
        //
       /* setOnClickListener(v -> {
            if(allTypeListeners.containsKey(this.hashCode())) {
                HashMap<String, HashMap<Integer, EventListener>> typeListeners = allTypeListeners.get(this.hashCode());
                HashMap<Integer, EventListener> listeners = typeListeners.get("tap");
                for (EventListener listener : listeners.values()) {
                    listener.handleEvent(new Event("tap", new Dict() {{
                        put("x", 0);
                        put("y", 0);
                    }}, this, this, 9527));
                }
            }
        });*/

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (allTypeListeners.containsKey(this.hashCode())) {
                    HashMap<String, HashMap<Integer, EventListener>> typeListeners = allTypeListeners.get(this.hashCode());
                    HashMap<Integer, EventListener> listeners = typeListeners.get("tap");
                    if(listeners!=null) {
                        for (EventListener listener : listeners.values()) {
                            listener.handleEvent(new Event("tap", new Dict() {{
                                put("x", new JsNumber(0));
                                put("y", new JsNumber( 0));
                            }}, this, this, 9527));
                        }
                    }
                }
                break;
            default:break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setHoverClass(String hoverClass) {
    }
}

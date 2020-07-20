package cn.onekit.css.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.onekit.LITERAL_;
import cn.onekit.css.CSSStyleDeclaration;

public class CssLayoutParams extends FrameLayout.LayoutParams {




    /////////////////
    public String id,className,style;
    public CSSStyleDeclaration computedStyle = new CSSStyleDeclaration();

    public BeforeAfter before,after;
    public Float measuredWidth,measuredHeight;
    public float x,y;//,w,h;
    public Float mt,mb,ml,mr;

    public CssLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public CssLayoutParams(int width, int height) {
        super(width, height);
    }

    public CssLayoutParams(int width, int height, int gravity) {
        super(width, height, gravity);
    }

    public CssLayoutParams(ViewGroup.LayoutParams source) {
        super(source);
    }

    public CssLayoutParams(ViewGroup.MarginLayoutParams source) {
        super(source);
    }

    public CssLayoutParams(FrameLayout.LayoutParams source) {
        super(source);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s %s",x,y,measuredWidth,measuredHeight,mt,mb,ml,mr);
    }
}

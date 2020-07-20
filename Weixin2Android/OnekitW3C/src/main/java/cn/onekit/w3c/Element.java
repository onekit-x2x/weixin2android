package cn.onekit.w3c;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Date;

import cn.onekit.css.core.CssLayoutParams;

public abstract class Element extends FrameLayout implements Node {
    private int mCounter;
    private boolean isReleased;
    private boolean isMoved;
    private float mLastMotionX,mLastMotionY;
    private float TOUCH_SLOP;
    private long time1;
    ////////////////////////

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CssLayoutParams(0,0);
    }


    //


    public void setClassName(String className) {
        ViewGroup.LayoutParams layoutParams =  getLayoutParams();
        CssLayoutParams cssLayoutParams;
        if(layoutParams instanceof CssLayoutParams){
            cssLayoutParams = (CssLayoutParams) layoutParams;
        }else {
            cssLayoutParams = new CssLayoutParams(0,0);
        }
        cssLayoutParams.className = className;
     //   setLayoutParams(cssLayoutParams);
    }

    public String getClassName() {
        ViewGroup.LayoutParams layoutParams =  getLayoutParams();
        if(layoutParams instanceof CssLayoutParams){
            return ( (CssLayoutParams) layoutParams).className;
        }else {
           return "";
        }
    }
    public void setStyle(String style) {

        ViewGroup.LayoutParams layoutParams =  getLayoutParams();
        CssLayoutParams cssLayoutParams;
        if(layoutParams instanceof CssLayoutParams){
            cssLayoutParams = (CssLayoutParams) layoutParams;
        }else {
            cssLayoutParams = new CssLayoutParams(0,0);
        }
        cssLayoutParams.style = style;
      //  setLayoutParams(cssLayoutParams);
    }

    public String getStyle() {
        ViewGroup.LayoutParams layoutParams =  getLayoutParams();
        if(layoutParams instanceof CssLayoutParams){
            return ( (CssLayoutParams) layoutParams).style;
        }else {
            return "";
        }
    }


    public void setID(String id) {
        ViewGroup.LayoutParams layoutParams =  getLayoutParams();
        CssLayoutParams cssLayoutParams;
        if(layoutParams instanceof CssLayoutParams){
            cssLayoutParams = (CssLayoutParams) layoutParams;
        }else {
            cssLayoutParams = new CssLayoutParams(0,0);
        }
        cssLayoutParams.id = id;
        //  setLayoutParams(cssLayoutParams);
    }

    public String getID() {
        ViewGroup.LayoutParams layoutParams =  getLayoutParams();
        if(layoutParams instanceof CssLayoutParams){
            return ( (CssLayoutParams) layoutParams).id;
        }else {
            return "";
        }
    }
    public Element(Context context) {
        super(context);
        _init(context);
    }

    public Element(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init(context);
    }


    protected void _init(Context context)

    {

    }
    protected void _init2(Context context) {

    }
}

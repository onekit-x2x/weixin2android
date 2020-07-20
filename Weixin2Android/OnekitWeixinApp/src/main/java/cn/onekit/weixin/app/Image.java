package cn.onekit.weixin.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;

import cn.onekit.ASSET;
import cn.onekit.FUNC1;
import cn.onekit.TheKit;
import cn.onekit.js.core.JsString;
import cn.onekit.js.core.JsObject;
import cn.onekit.weixin.app.core.WeixinElement;

public class Image extends WeixinElement {
    private ImageView imageView;

    public Image(Context context) {
        super(context);
        _init( context);
        _init2(context);
    }

    public Image(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init( context);
        _init2(context);
    }
    public void _init(Context context) {
        imageView = new ImageView(context);
        imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        addView(imageView);
    }
    public  void _init2(Context context) {
        setMode("scaleToFill");
        lazy_load(false);
    }
    JsString _src;

    public JsString getSrc() {
        return _src;
    }

    public void setSrc(JsObject src) {
        this._src = (JsString) src;
        try {
            imageView.setImageBitmap(ASSET.loadImage("miniprogram/"+ TheKit.fixPath(TheKit.currentUrl,_src.THIS)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String _mode;

    public String getMode() {
        return _mode;
    }

    public void setMode(String mode) {
        this._mode = mode;
    }

    boolean _lazy_load;

    public boolean lazyoad() {
        return _lazy_load;
    }

    public void lazy_load(boolean lazy_load) {
        this._lazy_load = lazy_load;
    }

    FUNC1 binderror;
    FUNC1 bindload;
}

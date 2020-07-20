package cn.onekit.weixin.app.core;

import cn.onekit.js.core.JsClass;
import cn.onekit.weixin.WX;

public abstract class WeixinClass extends JsClass implements WeixinFile {

    protected WX wx;

    protected WeixinClass() {
        super();
        wx = new WX();
    }
}

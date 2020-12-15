package cn.onekit.weixin;

import cn.onekit.js.JsBoolean;
import cn.onekit.js.JsObject;
import cn.onekit.weixin.app.Page;

public  class RealtimeLogManager {
     public void info(JsObject... args){};

     public void warn(JsObject... args){};

     public void error(JsObject... args){};

     public void setFilterMsg(JsObject msg){};
     public void addFilterMsg(JsObject msg){};
     public void in(Page pageInstance){};

}

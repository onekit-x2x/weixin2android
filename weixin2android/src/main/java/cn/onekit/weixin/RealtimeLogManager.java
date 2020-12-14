package cn.onekit.weixin;

import cn.onekit.js.JsObject_;
import cn.onekit.weixin.app.Page;

public  class RealtimeLogManager {
     public void info(JsObject_... args){};

     public void warn(JsObject_... args){};

     public void error(JsObject_... args){};

     public void setFilterMsg(JsObject_ msg){};
     public void addFilterMsg(JsObject_ msg){};
     public void in(Page pageInstance){};
}

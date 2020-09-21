package com.myproject;

import cn.onekit.js.*;
import cn.onekit.js.core.*;
import cn.onekit.weixin.app.core.*;

public class onekit_utils_util extends WeixinModule {

    JsObject_ formatTime;
    JsObject_ formatNumber;
    @Override
    public void onekit_js() {
        formatTime = new function(){ public JsObject_ invoke(JsObject_... arguments){
            JsObject_ date = arguments[0];
            JsObject_ year = date.get("getFullYear").invoke();
            JsObject_ month = Onekit_JS.plus(date.get("getMonth").invoke(),new JsNumber(1.0d));
            JsObject_ day = date.get("getDate").invoke();
            JsObject_ hour = date.get("getHours").invoke();
            JsObject_ minute = date.get("getMinutes").invoke();
            JsObject_ second = date.get("getSeconds").invoke();
            return Onekit_JS.plus(Onekit_JS.plus(new JsArray(){{  add(year);
                add(month);
                add(day);
            }}.get("map").invoke(formatNumber).get("join").invoke(new JsString("/")),new JsString(" ")),new JsArray(){{  add(hour);
                add(minute);
                add(second);
            }}.get("map").invoke(formatNumber).get("join").invoke(new JsString(":")));
        }};
         formatNumber = new function(){ public JsObject_ invoke(JsObject_... arguments){
            JsObject_ n = arguments[0];
            n = n.get("toString").invoke();
            return Onekit_JS.is(n.get(new JsNumber(1.0d)))?n: Onekit_JS.plus(new JsString("0"),n);
        }};
        module.exports = new JsObject(){{
            put("formatTime",formatTime);
        }};


    }
}
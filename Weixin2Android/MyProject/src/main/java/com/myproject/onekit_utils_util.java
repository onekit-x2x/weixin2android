package com.myproject;

import cn.onekit.js.*;
import cn.onekit.js.core.*;
import cn.onekit.weixin.app.core.*;

public class onekit_utils_util extends WeixinModule {

    JsObject formatTime;
    JsObject formatNumber;
    @Override
    public void onekit_js() {
        formatTime = new function(){ public JsObject invoke(JsObject... arguments){
            JsObject date = arguments[0];
            JsObject year = date.get("getFullYear").invoke();
            JsObject month = Onekit_JS.plus(date.get("getMonth").invoke(),new JsNumber(1.0d));
            JsObject day = date.get("getDate").invoke();
            JsObject hour = date.get("getHours").invoke();
            JsObject minute = date.get("getMinutes").invoke();
            JsObject second = date.get("getSeconds").invoke();
            return Onekit_JS.plus(Onekit_JS.plus(new Array(){{  add(year);
                add(month);
                add(day);
            }}.get("map").invoke(formatNumber).get("join").invoke(new JsString("/")),new JsString(" ")),new Array(){{  add(hour);
                add(minute);
                add(second);
            }}.get("map").invoke(formatNumber).get("join").invoke(new JsString(":")));
        }};
         formatNumber = new function(){ public JsObject invoke(JsObject... arguments){
            JsObject n = arguments[0];
            n = n.get("toString").invoke();
            return Onekit_JS.is(n.get(new JsNumber(1.0d)))?n: Onekit_JS.plus(new JsString("0"),n);
        }};
        module.exports = new Dict(){{
            put("formatTime",formatTime);
        }};


    }
}
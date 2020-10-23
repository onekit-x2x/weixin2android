package cn.onekit.weixin.core.wx;

import java.util.Map;

import cn.onekit.js.core.function;

public class WxAddress extends WxAD {
    public void chooseAddress(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
    }
}

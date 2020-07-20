package cn.onekit.weixin;


import cn.onekit.js.core.JsString;
import cn.onekit.js.core.JsObject;

public class iBeacon implements JsObject{
    String uuid;
    String major;
    String minor;
    int proximity;
    double accuracy;
    int rssi;

    @Override
    public JsString ToString() {
        return null;
    }
}

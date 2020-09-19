package cn.onekit.weixin;


import cn.onekit.js.JsString;
import cn.onekit.js.JsObject;

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

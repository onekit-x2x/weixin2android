package cn.onekit.weixin;


import cn.onekit.js.JsString;
import cn.onekit.js.JsObject_;

public class IBeacon implements JsObject_ {
    String uuid;
    String major;
    String minor;
    int proximity;
    double accuracy;
    int rssi;

    public String getUuid() {
        return uuid;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public int getProximity() {
        return proximity;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getRssi() {
        return rssi;
    }

    @Override
    public JsString ToString() {
        return null;
    }
}

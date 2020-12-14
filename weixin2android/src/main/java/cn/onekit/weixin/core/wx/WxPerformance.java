package cn.onekit.weixin.core.wx;

import cn.onekit.js.JsObject_;

public class WxPerformance extends WxPayment {

    public WxPerformance getPerformance() {
        WxPerformance performance = new WxPerformance();
        return performance;
    }

    public void triggerGC() {
        System.gc();
    }

    public int now() {
        int i = (int) System.nanoTime() / 10000;
        return i;
    }
    public void reportPerformance(JsObject_ id, JsObject_ value, JsObject_ dimensions){

    }

}

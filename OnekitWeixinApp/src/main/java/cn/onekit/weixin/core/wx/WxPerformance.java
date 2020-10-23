package cn.onekit.weixin.core.wx;

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
}

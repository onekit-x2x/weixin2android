package cn.onekit.weixin.core.wx;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.onekit.js.core.function;

public class WxWindow extends WxWiFi {
    HashMap<Integer, Timer> timeouts = new HashMap();
    HashMap<Integer, Timer> intervals = new HashMap();

    public WxWindow() {

    }

    public void onWindowResize(function callback) {

    }

    public void offWindowResize(function callback) {

    }

    public function requestAnimationFrame;
    Thread _thread;

    public void requestAnimationFrame(final function callback) {
        if (callback == null) {
            _thread.stop();
            _thread = null;
            requestAnimationFrame = null;
        }
        _thread = new Thread(new Runnable() {
            @Override
            public void run() {

                requestAnimationFrame = callback;

            }
        });
        _thread.start();
    }

    public int setTimeout(final function callback, Float timeout) {


        if (timeout == null) {
            timeout = 0.0f;
        }
        final Timer timer = new Timer();
        final int timerID = timer.hashCode();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                timeouts.remove(timerID);
                callback.invoke();
            }
        }, (int) (float) timeout, (int) (float) timeout);
        timeouts.put(timerID, timer);
        return timerID;
    }

    public int setInterval(final function callback, Float interval) {
        if (interval == null) {
            interval = 0.0f;
        }
        Timer timer = new Timer();
        int timerID = timer.hashCode();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                callback.invoke();

            }
        }, (int) (float) interval, (int) (float) interval);
        intervals.put(timerID, timer);
        return timerID;
    }

    public void clearTimeout(int timerID) {
        Timer timeout = timeouts.get(timerID);
        timeout.cancel();
        timeouts.remove((int)timerID);
    }

    public void clearInterval(int timerID) {
        Timer interval = intervals.get(timerID);
        interval.cancel();
        intervals.remove((int)timerID);
    }

    float _innerWidth, _innerHeight;

    public float innerWidth() {
        return _innerWidth;
    }

    public float innerHeight() {
        return _innerHeight;
    }

    public void setWindowSize(Map OBJECT){

    }
    public void onWindowResize(Map OBJECT){

    }
    public void offWindowResize(Map OBJECT){

    }

}

package cn.onekit.weixin;

import cn.onekit.js.JsBoolean;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;

public class InnerAudioContext {
    private JsObject src;
    private JsObject startTime;
    private JsBoolean autoplay;
    private JsBoolean loop;
    private JsBoolean obeyMuteSwitch;
    private JsObject volume;
    private JsObject playbackRate;
    private JsObject duration;
    private JsObject currentTime;
    private JsBoolean paused;
    private JsObject buffered;

    public JsObject getSrc() {
        return src;
    }

    public JsObject getStartTime() {
        return startTime;
    }

    public JsBoolean getAutoplay() {
        return autoplay;
    }

    public JsBoolean getLoop() {
        return loop;
    }

    public JsBoolean getObeyMuteSwitch() {
        return obeyMuteSwitch;
    }

    public JsObject getVolume() {
        return volume;
    }

    public JsObject getPlaybackRate() {
        return playbackRate;
    }

    public JsObject getDuration() {
        return duration;
    }

    public JsObject getCurrentTime() {
        return currentTime;
    }

    public JsBoolean getPaused() {
        return paused;
    }

    public JsObject getBuffered() {
        return buffered;
    }

    public void destroy(){

    }
    public void offCanplay(function callback){

    }
    public void offEnded(function callback){

    }
    public void offError(function callback){

    }
    public void offPause(function callback){

    }
    public void offPlay(function callback){

    }
    public void offSeeked(function callback){

    }
    public void offSeeking(function callback){

    }
    public void offStop(function callback){

    }
    public void offTimeUpdate(function callback){

    }
    public void offWaiting(function callback){

    }
    public void onCanplay(function callback){

    }
    public void onEnded(function callback){

    }
    public void onError(function callback){

    }
    public void onPause(function callback){

    }

    public void onPlay(function callback){

    }
    public void onSeeked(function callback){

    }
    public void onSeeking(function callback){

    }
    public void onStop(function callback){

    }
    public void onTimeUpdate(function callback){

    }
    public void onWaiting(function callback){

    }
    public void pause(){

    }
    public void play(){

    }
    public void seek(JsObject position){

    }
    public void stop(){

    }








}

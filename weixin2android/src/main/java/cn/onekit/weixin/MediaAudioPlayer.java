package cn.onekit.weixin;

import cn.onekit.js.JsObject;
import cn.onekit.js.Promise;

public class MediaAudioPlayer {
    private JsObject volume;

    public JsObject getVolume() {
        return volume;
    }

    public Promise addAudioSource(VideoDecoder source){
        return null;
    }

    public Promise destroy(){
        return null;
    }
    public Promise removeAudioSource(VideoDecoder source){
        return null;
    }
    public Promise start(){
        return null;
    }
    public Promise stop(){
        return null;
    }
}

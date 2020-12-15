package cn.onekit.weixin;


import cn.onekit.js.JsObject;
import cn.onekit.js.Map;

public class MediaContainer {
    public void addTrack(MediaTrack track){

    }

    private class MediaTrack {
        private JsObject kind;
        private JsObject duration;
        private JsObject volume;

        public JsObject getKind() {
            return kind;
        }

        public JsObject getDuration() {
            return duration;
        }

        public JsObject getVolume() {
            return volume;
        }
    }

    public void destroy(){

    }
    public void export(){

    }
    public void extractDataSource(Map OBJECT){

    }
    public void removeTrack(MediaTrack track){

    }
}

package cn.onekit.weixin;



import cn.onekit.js.JsArray;
import cn.onekit.js.JsObject;
import cn.onekit.js.Map;
import cn.onekit.js.core.function;

public class Performance {
    public PerformanceObserver createObserver(function callback){
        return null;
    }

    private class PerformanceObserver {
        private JsArray supportedEntryTypes;

        public JsArray getSupportedEntryTypes() {
            return supportedEntryTypes;
        }

        public void disconnect(){

        }
        public void observe(Map options){

        }
    }
    public JsArray getEntries(){

        return null;
    }
    public JsArray getEntriesByName(JsObject name, JsObject entryType){

        return null;
    }
    public JsArray getEntriesByType(JsObject entryType){

        return null;
    }
    public void setBufferSize(JsObject size){


    }
}

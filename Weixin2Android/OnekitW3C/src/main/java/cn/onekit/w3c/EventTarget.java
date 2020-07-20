package cn.onekit.w3c;

import java.util.HashMap;

public interface EventTarget  {


     HashMap<Integer, HashMap<String, HashMap<Integer, EventListener>>> allTypeListeners = new HashMap();

    //void addEventListener(String type, EventListener listener, Map<String,Boolean> options);
    default   void addEventListener(String type, EventListener listener, boolean useCapture) {
        if(!allTypeListeners.containsKey(this.hashCode())){
            allTypeListeners.put(this.hashCode(), new HashMap());
        }
        HashMap<String, HashMap<Integer, EventListener>> typeListeners = allTypeListeners.get(this.hashCode());
        if (!typeListeners.containsKey(type)) {
            typeListeners.put(type, new HashMap());
        }
        typeListeners.get(type).put(listener.hashCode(), listener);
    }
    default   void addEventListener(String type, EventListener listener) {
        addEventListener(type,listener,false);
    }
    //void removeEventListener(String type, EventListener listener, Map<String,Boolean> options);
    default void removeEventListener(String type, EventListener listener, boolean useCapture) {
        HashMap<String, HashMap<Integer, EventListener>> typeListeners = allTypeListeners.get(this.hashCode());
        typeListeners.get(type).remove(listener.hashCode());
    }

    default void removeEventListener(String type, EventListener listener) {
        removeEventListener(type, listener, false);
    }

    default boolean dispatchEvent(Event event) {
        HashMap<String, HashMap<Integer, EventListener>> typeListeners = allTypeListeners.get(this.hashCode());
        if (!typeListeners.containsKey("change")) {
            return false;
        }
        for (EventListener listener : typeListeners.get("change").values()) {
            listener.handleEvent(event);
        }
        return false;
    }
}

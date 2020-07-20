package cn.onekit.js;

import cn.onekit.js.core.JsObject;

public class InteralError extends Error {
    public InteralError(JsObject message){
        super(message);
    }
    public InteralError(){
        super();
    }
}

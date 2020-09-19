package cn.onekit.js;

public class InteralError extends Error {
    public InteralError(JsObject message){
        super(message);
    }
    public InteralError(){
        super();
    }
}

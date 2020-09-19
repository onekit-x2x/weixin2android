package cn.onekit.js;

public class ReferenceError extends Error {
    public ReferenceError(JsObject message) {
        super(message);
    }
    public ReferenceError() {
        super();
    }
}

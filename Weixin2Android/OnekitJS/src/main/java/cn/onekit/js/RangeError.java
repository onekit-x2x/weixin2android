package cn.onekit.js;

public class RangeError extends Error {
    public RangeError(JsObject message) {
        super(message);
    }
    public RangeError() {
        super();
    }
}
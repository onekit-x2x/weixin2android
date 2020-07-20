package cn.onekit.js;

import cn.onekit.js.core.JsObject;

public class RangeError extends Error {
    public RangeError(JsObject message) {
        super(message);
    }
    public RangeError() {
        super();
    }
}
package cn.onekit.js;

import cn.onekit.js.core.JsObject;

public class ReferenceError extends Error {
    public ReferenceError(JsObject message) {
        super(message);
    }
    public ReferenceError() {
        super();
    }
}

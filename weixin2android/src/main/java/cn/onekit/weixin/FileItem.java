package cn.onekit.weixin;

import cn.onekit.js.JsString;
import cn.onekit.js.JsObject_;

public class FileItem implements JsObject_ {
    public String filePath;
    public int createTime;
    public int size;

    @Override
    public JsString ToString() {
        return null;
    }
}

package cn.onekit.js.core;


import cn.onekit.js.Dict;

public abstract class JsModule extends Dict implements JsFile {
   public JsModule module = this;
   public Dict exports;

   protected abstract void onekit_js();

}

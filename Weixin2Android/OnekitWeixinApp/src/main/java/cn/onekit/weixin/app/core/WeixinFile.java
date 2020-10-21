package cn.onekit.weixin.app.core;

import java.util.ArrayList;
import java.util.List;

import cn.onekit.thekit.Android;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.JsFile;
import cn.onekit.weixin.PageObject;


public interface WeixinFile extends JsFile {


     ///////////////////////////////////////////
     default JsObject getApp() {
          WeixinApplication app = (WeixinApplication) Android.application();
          return  app.THIS;
     }
     //
     List<PageObject> pages = new ArrayList();

     default List<PageObject> getCurrentPages() {
          return pages;
     }

     //////////////////////////////////////////////
}

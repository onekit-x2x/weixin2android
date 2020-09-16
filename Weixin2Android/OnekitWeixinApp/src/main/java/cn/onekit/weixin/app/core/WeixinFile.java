package cn.onekit.weixin.app.core;

import java.util.ArrayList;
import java.util.List;

import thekit.android.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsFile;
import cn.onekit.weixin.PageObject;


public interface WeixinFile extends JsFile {


     ///////////////////////////////////////////
     default Dict getApp() {
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

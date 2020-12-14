package demo.weixin;

import android.view.ViewGroup;

import cn.onekit.js.JsObject_;
import cn.onekit.js.JsString;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.Switch;
import cn.onekit.weixin.app.core.Vue;
import cn.onekit.weixin.app.core.WeixinPage;

public class onekit_pages_index_index extends WeixinPage {


    @Override
    public void onekit_wxml(ViewGroup ui, cn.onekit.js.JsObject_ data, Vue vue) {
        //Switch switch1 = new Switch(ui.getContext());
      //  ui.addView(switch1);
        //switch1.setStyle("width:100px;height:100px;");
    }

    @Override
    protected void onekit_js() {
wx.showToast(new JsObject(){{
    put("title",new JsString("Hello,world!"));
    put("success",new function(){
        @Override
        public JsObject_ invoke(Object... arguments) {
            JsObject_ res = (JsObject_)arguments[0];
            console.warn(new JsString("[wx.showToast success]"),res);
            return null;
        }
    });
    put("fail",new function(){
        @Override
        public JsObject_ invoke(Object... arguments) {
            JsObject_ res = (JsObject_)arguments[0];
            console.warn(new JsString("[wx.showToast fail]"),res);
            return null;
        }
    });
}});
    }
}

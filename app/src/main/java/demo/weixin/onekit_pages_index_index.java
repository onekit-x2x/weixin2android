package demo.weixin;

import android.view.ViewGroup;

import cn.onekit.weixin.app.Switch;
import cn.onekit.weixin.app.core.Vue;
import cn.onekit.weixin.app.core.WeixinPage;

public class onekit_pages_index_index extends WeixinPage {


    @Override
    public void onekit_wxml(ViewGroup ui, cn.onekit.js.JsObject_ data, Vue vue) {
        Switch switch1 = new Switch(ui.getContext());
        ui.addView(switch1);
        //switch1.setStyle("width:100px;height:100px;");
    }

    @Override
    protected void onekit_js() {

    }
}

package demo.css;

import android.content.Context;
import android.view.ViewGroup;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.Onekit_JS;
import cn.onekit.vue.Vue;
import cn.onekit.w3c.LITERAL;
import cn.onekit.weixin.app.Image;
import cn.onekit.weixin.app.Navigator;
import cn.onekit.weixin.app.View;
import cn.onekit.weixin.app.core.WeixinTemplate;

public abstract class onekit_page_common {

    public static class head extends WeixinTemplate {

        public head(Context context) {
            super(context);
        }

        public void onekit_wxml(final ViewGroup ui, final JsObject data, final Vue vue) {

            View ui_0 = new View(ui.getContext());
            ui.addView(ui_0);
            ui_0.setClassName("page-head");
//
            {
                View ui_0_0 = new View(ui.getContext());
                ui_0.addView(ui_0_0);
                ui_0_0.setClassName("page-head-title");
                {
                    LITERAL ui_0_0_0 = new LITERAL(ui.getContext());
                    ui_0_0.addView(ui_0_0_0);
                    ui_0_0_0.setText(data.get("title").stringValue());
                }
                View ui_0_1 = new View(ui.getContext());
                ui_0.addView(ui_0_1);
                ui_0_1.setClassName("page-head-line");
                if (Onekit_JS.is(data.get("desc"))) {
                    View ui_0_2 = new View(ui.getContext());
                    ui_0.addView(ui_0_2);
                    ui_0_2.setClassName("page-head-desc");
                    {
                        LITERAL ui_0_2_0 = new LITERAL(ui.getContext());
                        ui_0_2.addView(ui_0_2_0);
                        ui_0_2_0.setText(data.get("desc").stringValue());
                    }
                }
            }
        }
    }

    public static class foot extends WeixinTemplate {

        public foot(Context context) {
            super(context);
        }

        public void onekit_wxml(final ViewGroup ui, final JsObject data, final Vue vue) {
            Navigator ui_0 = new Navigator(ui.getContext());
            ui.addView(ui_0);
            ui_0.setClassName("page-foot");
            ui_0.setOpenType("switchTab");
            ui_0.setSrc("/page/component/index");
            ui_0.setHoverClass("none");
            //
            {
                Image ui_0_0 = new Image(ui.getContext());
                ui_0.addView(ui_0_0);
                ui_0.setClassName("icon-foot");
                ui_0.setSrc("../../../../image/icon_foot.png");
            }
        }
    }
}
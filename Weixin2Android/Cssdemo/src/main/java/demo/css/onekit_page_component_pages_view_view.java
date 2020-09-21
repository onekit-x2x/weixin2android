package demo.css;

import android.view.ViewGroup;

import cn.onekit.js.JsObject;
import cn.onekit.js.JsObject_;
import cn.onekit.vue.Vue;
import cn.onekit.w3c.LITERAL;
import cn.onekit.weixin.app.Text;
import cn.onekit.weixin.app.View;
import cn.onekit.weixin.app.core.WeixinPage;

public class onekit_page_component_pages_view_view extends WeixinPage {

    public void onekit_js() {
Page(new Options(){{

}});
    }


    public void onekit_wxml(final ViewGroup ui, final JsObject_ data, final Vue vue) {
        Import("../../../common/head.wxml");
        Import("../../../common/foot.wxml");
        View ui_0 = new View(ui.getContext());
        ui.addView(ui_0);
        ui_0.setClassName("container");
        //
        {
            Template("head", new JsObject() {{
                put("title", "view");
            }}, ui_0);
            //
            View ui_0_1 = new View(ui.getContext());
            ui_0.addView(ui_0_1);
            ui_0_1.setClassName("page-body");
            //

            {
                View ui_0_1_0 = new View(ui.getContext());
                ui_0_1.addView(ui_0_1_0);
                ui_0_1_0.setClassName("page-section");
                //
                {
                    View ui_0_1_0_0 = new View(ui.getContext());
                    ui_0_1_0.addView(ui_0_1_0_0);
                    ui_0_1_0_0.setClassName("page-section-title");
                    //
                    {
                        Text ui_0_1_0_0_0 = new Text(ui.getContext());
                        ui_0_1_0_0.addView(ui_0_1_0_0_0);
                        //
                       {
                            LITERAL ui_0_1_0_0_0_0 = new LITERAL(ui.getContext());
                            ui_0_1_0_0_0.addView(ui_0_1_0_0_0_0);
                            ui_0_1_0_0_0_0.setText("flex-direction: row\\n横向布局");
                            //
                        }
                    }
                    //
                    View ui_0_1_0_1 = new View(ui.getContext());
                    ui_0_1_0.addView(ui_0_1_0_1);
                    ui_0_1_0_1.setClassName("page-section-spacing");
                    //
                    {
                        View ui_0_1_0_1_0 = new View(ui.getContext());
                        ui_0_1_0_1.addView(ui_0_1_0_1_0);
                        ui_0_1_0_1_0.setClassName("flex-wrp");
                        ui_0_1_0_1_0.setStyle("flex-direction:row;");
                        {
                            View ui_0_1_0_1_0_0 = new View(ui.getContext());
                            ui_0_1_0_1_0.addView(ui_0_1_0_1_0_0);
                            ui_0_1_0_1_0_0.setClassName("flex-item demo-text-1");
                            //
                            View ui_0_1_0_1_0_1 = new View(ui.getContext());
                            ui_0_1_0_1_0.addView(ui_0_1_0_1_0_1);
                            ui_0_1_0_1_0_1.setClassName("flex-item demo-text-2");
                            //
                            View ui_0_1_0_1_0_2 = new View(ui.getContext());
                            ui_0_1_0_1_0.addView(ui_0_1_0_1_0_2);
                            ui_0_1_0_1_0_2.setClassName("flex-item demo-text-3");
                        }
                    }
                }

                //
                View ui_0_1_1 = new View(ui.getContext());
                ui_0_1.addView(ui_0_1_1);
                ui_0_1_1.setClassName("page-section");
                //
                {
                    View ui_0_1_1_0 = new View(ui.getContext());
                    ui_0_1_1.addView(ui_0_1_1_0);
                    ui_0_1_1_0.setClassName("page-section-title");
                    //
                    {
                        Text ui_0_1_1_0_0 = new Text(ui.getContext());
                        ui_0_1_1_0.addView(ui_0_1_1_0_0);
                        //
                        {
                            LITERAL ui_0_1_1_0_0_0 = new LITERAL(ui.getContext());
                            ui_0_1_1_0_0.addView(ui_0_1_1_0_0_0);
                            ui_0_1_1_0_0_0.setText("flex-direction: column\\n纵向布局");
                            //
                        }
                    }
                    //
                    View ui_0_1_1_1 = new View(ui.getContext());
                    ui_0_1_1.addView(ui_0_1_1_1);
                    ui_0_1_1_1.setClassName("page-section-spacing");
                    //
                    {
                        View ui_0_1_1_1_0 = new View(ui.getContext());
                        ui_0_1_1_1.addView(ui_0_1_1_1_0);
                        ui_0_1_1_1_0.setClassName("flex-wrp");
                        ui_0_1_1_1_0.setStyle("flex-direction:column;");
                        {
                            View ui_0_1_1_1_0_0 = new View(ui.getContext());
                            ui_0_1_1_1_0.addView(ui_0_1_1_1_0_0);
                            ui_0_1_1_1_0_0.setClassName("flex-item flex-item-V demo-text-1");
                            //
                            View ui_0_1_1_1_0_1 = new View(ui.getContext());
                            ui_0_1_1_1_0.addView(ui_0_1_1_1_0_1);
                            ui_0_1_1_1_0_1.setClassName("flex-item flex-item-V demo-text-2");
                            //
                            View ui_0_1_1_1_0_2 = new View(ui.getContext());
                            ui_0_1_1_1_0.addView(ui_0_1_1_1_0_2);
                            ui_0_1_1_1_0_2.setClassName("flex-item flex-item-V demo-text-3");
                        }
                    }
                }
            }
            //
           Template("foot", null,ui_0);
        }
    }
}

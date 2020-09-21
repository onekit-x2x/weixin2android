package demo.css;
import android.graphics.Color;
import android.view.ViewGroup;

import cn.onekit.css.core.CssLayoutParams;
import cn.onekit.js.*;
import cn.onekit.js.core.*;
import cn.onekit.vue.*;
import cn.onekit.w3c.*;
import cn.onekit.weixin.app.*;
import cn.onekit.weixin.app.core.*;

public class onekit_page_component_index extends WeixinPage {


    public void onekit_js() {

        Page(new Options() {
            {
                put("onShow", new function() {

                    public JsObject_ invoke(JsObject_... arguments) {
                        wx.reportAnalytics("enter_home_programmatically", new JsObject());
                        return null;
                    }
                });
                put("onShareAppMessage", new function() {

                    public JsObject_ invoke(JsObject_... arguments) {
                        return new JsObject() {{
                            put("title", "小程序官方组件展示");
                            put("path", "page/component/index");
                        }};
                    }
                });


                put("data", new JsObject() {
                    {
                        put("list", new JsArray() {
                            {
                                add(new JsObject() {
                                    {
                                        put("id", "view");
                                        put("name", "视图容器");
                                        put("open", true);
                                        put("pages", new JsArray() {{
                                                    add("view");
                                         add("scroll-view");
                                                   /*      add("swiper");
                                                    add("movable-view");
                                                    add("cover-view");*/
                                                }}
                                        );
                                    }
                                });
                                /*
                                add(new Dict() {
                                    {
                                        put("id", "content");
                                        put("name", "基础内容");
                                        put("open", false);
                                        put("pages", new Array() {{
                                            add("text");

                                            add("icon");

                                            add("progress");

                                            add("rich-text");
                                        }});
                                    }
                                });
                                add(new Dict() {
                                    {
                                        put("id", "form");
                                        put("name", "表单组件");
                                        put("open", false);
                                        put("pages", new Array() {{
                                                    add("button");
                                                    add("checkbox");
                                                    add("form");
                                                    add("input");
                                                    add("label");
                                                    add("picker");
                                                    add("picker-view");
                                                    add("radio");
                                                    add("slider");
                                                    add("switch");
                                                    add("textarea");
                                                    add("editor");
                                                }}
                                        );
                                    }
                                });
                                add(new Dict() {
                                    {
                                        put("id", "nav");
                                        put("name", "导航");
                                        put("open", false);
                                        put("pages", new Array() {{
                                            add("navigator");
                                        }});
                                    }
                                });
                                add(new Dict() {
                                    {
                                        put("id", "media");
                                        put("name", "媒体组件");
                                        put("open", false);
                                        put("pages", new Array() {{
                                            add("image");
                                            add("audio");
                                            add("video");
                                            add("camera");
                                        }});
                                    }
                                });
                                add(new Dict() {
                                    {
                                        put("id", "map");
                                        put("name", "地图");
                                        put("open", false);
                                        put("pages", new Array() {{
                                            add("map");
                                        }});
                                    }
                                });
                                add(new Dict() {
                                    {
                                        put("id", "canvas");
                                        put("name", "画布");
                                        put("open", false);
                                        put("pages", new Array() {{
                                                    add("canvas");
                                                }}
                                        );
                                    }
                                });
                                add(new Dict() {
                                    {
                                        put("id", "open");
                                        put("name", "开放能力");
                                        put("open", false);
                                        put("pages", new Array() {{
                                                    add("ad");
                                                    add("open-data");
                                                    add("web-view");
                                                }}
                                        );
                                    }
                                });*/
                            }
                        });
                    }
                });
                put("kindToggle", new function() {

                    public JsObject_ invoke(JsObject_... arguments) {
                        Event e = (Event) arguments[0];
                        final String id = e.getCurrentTarget().getID();
                        final JsArray list = THIS.get("data").get("list");
                        for (int i = 0, len = list.getLength(); i < len; ++i) {
                            if (list.get(i).get("id").stringValue().equals(id)) {
                                list.get(i).sot("open", !Onekit_JS.is(list.get(i).get("open")));
                            } else {
                                list.get(i).sot("open", false);
                            }
                        }
                        THIS.setData(new JsObject() {{
                            put("list", list);
                        }});
                        wx.reportAnalytics("click_view_programmatically", new JsObject());
                        return null;
                    }
                });


            }
        });
        /*
        Class System = OnekitJS.Import("../../lib/System.js");
        Class Character = OnekitJS.Import("../../lib/Character.js");
        Class Arrays = OnekitJS.Import("../../lib/Arrays.js");

        function bytes2arraybuffer = new function() {
            @Override
            public JsObject invoke(JsObject... arguments) {
                Array bytes = (Array) arguments[0];
                return new Uint8Array(bytes).getBuffer();
            }
        };
        function arraybuffer2bytes = new function() {
            @Override
            public JsObject invoke(JsObject... arguments) {
                ArrayBuffer buffer = (ArrayBuffer) arguments[0];

                Array bytes = new Array();
                Uint8Array typedArray = new Uint8Array(buffer);
                for (int item : typedArray) {
                    bytes.push(item);
                }
                return bytes;
            }
        };
        function hexStringToByteArray = new function() {
            @Override
            public JsObject invoke(JsObject... arguments) {
                try {
                    String s = (String) arguments[0];

                    int len = STRING.getLength(s);
                    if (len % 2 == 1) {
                        throw new Error("指令字符串长度必须为偶数 !!!");
                    }
                    Array data = new Array(len / 2);
                    for (int i = 0; i < len; i += 2) {
                        data.set(i / 2, (int) (Character.getMethod("digit",String.class,Integer.class).invoke(null, STRING.get(s, i), 16)) << 4
                                + (int) (Character.getMethod("digit",String.class,Integer.class).invoke(null, STRING.get(s, i + 1), 16)));
                    }
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Error(e.getMessage());
                }
            }
        };
        setOverride("String", "format", new function() {
            @Override
            public JsObject invoke(JsObject... arguments) {
                String format = (String) arguments[0];
                String str = (String) arguments[1];
                format = format.toLowerCase();
                str = str.toString();
                String result;
                switch (format) {
                    case "%02x":
                        result = STRING.toString(str, 16);
                        while (STRING.getLength(result) < 2) {
                            result = "0" + result;
                        }
                        return result;
                    default:
                        throw new Error(format);
                }
                // return null;
            }
        });
        function buildSelectApdu = new function() {
            @Override
            public JsObject invoke(JsObject... arguments) {
                String aid = (String) arguments[0];
                String HEADER = "00A40400";
                String str = getOverride("String", "format").invoke("%02X", STRING.getLength(aid) / 2).stringValue();
                console.log(HEADER, str, aid);
                return hexStringToByteArray.invoke(HEADER + str + aid);
            }
        };
        function concatArrays = new function() {
            @Override
            public JsObject invoke(JsObject... arguments) {
                try {
                    Array first = (Array) arguments[0];
                    Array rest = (Array) arguments[1];

                    int totalLength = first.getLength();
                    for (Array array : new Array<Array>() {{
                        add(rest);
                    }}) {
                        totalLength += array.getLength();
                    }
                    console.log(first, totalLength);
                    Array result = (Array) Arrays.getMethod("copyOf").invoke(null, first, totalLength);
                    int offset = first.getLength();
                    for (Array array : new Array<Array>() {{
                        add(rest);
                    }}) {
                        System.getMethod("arraycopy").invoke(null, array, 0, result, offset, array.getLength());
                        offset += array.getLength();
                    }
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        setPrototype("String", "getBytes", new function() {
            @Override
            public JsObject invoke(JsObject... arguments) {
                String str = thisArg.toString();
                Array bufView = new Array();
                for (int i = 0, strLen = STRING.getLength(str); i < strLen; i++) {
                    bufView.push(STRING.charCodeAt(str, i));
                }
                return bufView;
            }
        });

        Array SELECT_OK = (Array) hexStringToByteArray.invoke("1000");
        Array UNKNOWN_ERROR = (Array) hexStringToByteArray.invoke("0000");


        Page(new Options() {
            {
                put("processCommandApdu", new function() {
                    @Override
                    public JsObject invoke(JsObject... arguments) {
                        Array bytes = (Array) arguments[0];

                        wx.sendHCEMessage(new Dict() {{
                            put("data", bytes2arraybuffer.invoke(bytes));
                            put("success", new function() {
                                @Override
                                public JsObject invoke(JsObject... arguments) {
                                    wx_sendHCEMessage res = (wx_sendHCEMessage) arguments[0];
                                    console.log("sendHCEMessage.success", res);
                                    return null;
                                }
                            });
                            put("fail", new function() {
                                @Override
                                public JsObject invoke(JsObject... arguments) {
                                    wx_fail res = (wx_fail) arguments[0];

                                    console.log("sendHCEMessage.fail", res);
                                    return null;
                                }
                            });
                        }});
                        return null;
                    }
                });
                put("onLoad", new function() {
                    @Override
                    public JsObject invoke(JsObject... arguments) {
                        Dict that = THIS;
                        wx.getHCEState(new Dict() {{
                            put("fail", new function() {
                                @Override
                                public JsObject invoke(JsObject... arguments) {
                                    wx_fail res = (wx_fail) arguments[0];
                                    console.log("getHCEState.fail", res);
                                    return null;
                                }
                            });
                            put("success", new function() {
                                @Override
                                public JsObject invoke(JsObject... arguments) {
                                    wx_getHCEState res = (wx_getHCEState) arguments[0];
                                    console.log("getHCEState.success", res);
                                    wx.startHCE(new Dict() {{
                                        put("aid_list", new Array() {{
                                            add("F123466666");
                                        }});
                                        put("success", new function() {
                                            @Override
                                            public JsObject invoke(JsObject... arguments) {
                                                wx_startHCE res = (wx_startHCE) arguments[0];
                                                console.log("startHCE.success", res);
                                                wx.onHCEMessage(new function() {
                                                    @Override
                                                    public JsObject invoke(JsObject... arguments) {
                                                        try {
                                                            wx_onHCEMessage res = (wx_onHCEMessage) arguments[0];
                                                            console.log("onHCEMessage", res);
                                                            Array commandApdu = (Array) arraybuffer2bytes.invoke(res.getData());
                                                            console.log("commandApdu", commandApdu);
                                                            if (res.getMessageType() == 1) {
                                                                String AID = "F123466666";

                                                                // 将指令转换成 byte[]
                                                                Array selectAPDU = (Array) buildSelectApdu.invoke(AID);
                                                                console.log("selectAPDU", selectAPDU);
                                                                if (OnekitJS.is(Arrays.getMethod("equals").invoke(null, selectAPDU, commandApdu))) {
                                                                    // 直接模拟返回16位卡号
                                                                    String account = "6222222200000001";

                                                                    // 获取卡号 byte[]
                                                                    Array accountBytes = (Array) getPrototype("String","getBytes",account).invoke();
                                                                    console.log("accountBytes", accountBytes);
                                                                    // 处理欲返回的响应数据
                                                                    console.log("SELECT_OK", SELECT_OK);
                                                                    Array result = (Array) concatArrays.invoke(accountBytes, SELECT_OK);
                                                                    console.log("result", result);
                                                                    that.get("processCommandApdu").function().invoke(result);
                                                                } else {
                                                                    that.get("processCommandApdu").function().invoke(UNKNOWN_ERROR);
                                                                }
                                                            } else {
                                                                throw new Error(res);
                                                            }
                                                            return null;
                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                        return null;
                                                    }
                                                });

                                                return null;
                                            }
                                        });
                                        put("fail", new function() {
                                            @Override
                                            public JsObject invoke(JsObject... arguments) {
                                                wx_fail res = (wx_fail) arguments[0];
                                                console.log("startHCE.fail", res);
                                                return null;
                                            }
                                        });

                                    }});
                                    return null;
                                }
                            });

                        }});
                        return null;
                    }
                });
            }
        });*/
    }


    public void onekit_wxml(final ViewGroup ui, final JsObject_ data, final Vue vue) {

        View ui_0 = new View(ui.getContext());
        ui.addView(ui_0);
        ui_0.setClassName("index");
        //
        {
            View ui_0_0 = new View(ui.getContext());
            ui_0.addView(ui_0_0);
            ui_0_0.setClassName("index-hd");
            //
            {
                Image ui_0_0_0 = new Image(ui.getContext());
                ui_0_0.addView(ui_0_0_0);
                ui_0_0_0.setClassName("index-logo");
                ui_0_0_0.setSrc("resources/kind/logo.png");
                //
                View ui_0_0_1 = new View(ui.getContext());
                ui_0_0.addView(ui_0_0_1);
                ui_0_0_1.setClassName("index-desc");
                //
                {
                    LITERAL ui_0_0_1_0 = new LITERAL(ui.getContext());
                    ui_0_0_1.addView(ui_0_0_1_0);
                    ui_0_0_1_0.setText("以下将展示小程序官方组件能力，组件样式仅供参考，开发者可根据自身需求自定义组件样式，具体属性参数详见");
                    //
                    Navigator ui_0_0_1_1 = new Navigator(ui.getContext());
                    ui_0_0_1.addView(ui_0_0_1_1);
                    ui_0_0_1_1.setSrc("pages/doc-web-view/doc-web-view");
                    ui_0_0_1_1.setClassName("agree__link");
                    //
                    {
                        LITERAL ui_0_0_1_1_0 = new LITERAL(ui.getContext());
                        ui_0_0_1_1.addView(ui_0_0_1_1_0);
                        ui_0_0_1_1_0.setText("小程序开发文档");
                    }
                    //
                    LITERAL ui_0_0_1_2 = new LITERAL(ui.getContext());
                    ui_0_0_1.addView(ui_0_0_1_2);
                    ui_0_0_1_2.setText("。");
                }
            }
            //
            View ui_0_1 = new View(ui.getContext());
            ui_0.addView(ui_0_1);
            ui_0_1.setClassName("index-bd");
            //
            {
                View ui_0_1_0 = new View(ui.getContext());
                ui_0_1.addView(ui_0_1_0);
                ui_0_1_0.setClassName("kind-list");
                //
                vue.For(data.get("list"), "item.id", "index", "item", new ACTION1() {
                    @Override
                    public void invoke(JsObject_ data) {

                        View ui_0_1_0_0 = new View(ui.getContext());
                        ui_0_1_0.addView(ui_0_1_0_0);
                        ui_0_1_0_0.setClassName("kind-list-item");
                        {
                            View ui_0_1_0_0_0 = new View(ui.getContext());
                            ui_0_1_0_0.addView(ui_0_1_0_0_0);
                            ui_0_1_0_0_0.setID(data.get("item").get("id").stringValue());
                            ui_0_1_0_0_0.setClassName("kind-list-item-hd " + (Onekit_JS.is(data.get("item").get("open")) ? "kind-list-item-hd-show" : ""));
                            ui_0_1_0_0_0.addEventListener("tap", event -> {
                                ((function) THIS.get("kindToggle")).invoke(event);
                                return false;
                            });
                            //
                            {
                                View ui_0_1_0_0_0_0 = new View(ui.getContext());
                                ui_0_1_0_0_0.addView(ui_0_1_0_0_0_0);
                                ui_0_1_0_0_0_0.setClassName("kind-list-text");
                                {

                                    LITERAL ui_0_1_0_0_0_0_0 = new LITERAL(ui.getContext());
                                    ui_0_1_0_0_0_0.addView(ui_0_1_0_0_0_0_0);
                                    ui_0_1_0_0_0_0_0.setText(data.get("item").get("name").stringValue());
                                    //
                                }
                                Image ui_0_1_0_0_0_1 = new Image(ui.getContext());
                                ui_0_1_0_0_0.addView(ui_0_1_0_0_0_1);
                                ui_0_1_0_0_0_1.setClassName("kind-list-img");
                                ui_0_1_0_0_0_1.setSrc("resources/kind/" + data.get("item").get("id").stringValue() + ".png");
                            }
                            //

                            View ui_0_1_0_0_1 = new View(ui.getContext());
                            ui_0_1_0_0.addView(ui_0_1_0_0_1);
                            ui_0_1_0_0_1.setClassName("kind-list-item-bd " + (Onekit_JS.is(data.get("item").get("open")) ? "kind-list-item-bd-show" : ""));
                            {
                                View ui_0_1_0_0_1_0 = new View(ui.getContext());
                                ui_0_1_0_0_1.addView(ui_0_1_0_0_1_0);
                                ui_0_1_0_0_1_0.setClassName("navigator-box " + (Onekit_JS.is(data.get("item").get("open")) ? "navigator-box-show" : ""));
                                vue.For(data.get("item").get("pages"), "*item", "index", "page", new ACTION1() {
                                    @Override
                                    public void invoke(JsObject_ data) {

                                        Navigator ui_0_1_0_0_1_0_0 = new Navigator(ui.getContext());
                                        ui_0_1_0_0_1_0.addView(ui_0_1_0_0_1_0_0);
                                        ui_0_1_0_0_1_0_0.setClassName("navigator");
                                        ui_0_1_0_0_1_0_0.setSrc("pages/" + data.get("page").stringValue() + "/" + data.get("page").stringValue());
                                        {
                                            View ui_0_1_0_0_1_0_0_0 = new View(ui.getContext());
                                            ui_0_1_0_0_1_0_0.addView(ui_0_1_0_0_1_0_0_0);
                                            ui_0_1_0_0_1_0_0_0.setClassName("navigator-text");
                                            {
                                                LITERAL ui_0_1_0_0_1_0_0_0_0 = new LITERAL(ui.getContext());
                                                ui_0_1_0_0_1_0_0_0.addView(ui_0_1_0_0_1_0_0_0_0);
                                                ui_0_1_0_0_1_0_0_0_0.setText(data.get("page").stringValue());
                                            }
                                        }
                                        View ui_0_1_0_0_1_0_0_1 = new View(ui.getContext());
                                        ui_0_1_0_0_1_0_0.addView(ui_0_1_0_0_1_0_0_1);
                                        ui_0_1_0_0_1_0_0_1.setClassName("navigator-arrow");
                                    }
                                });
                            }
                        }
                    }
                });
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewGroup ui_0 = (ViewGroup) page.getChildAt(0);
        ViewGroup ui_0_1 = (ViewGroup) ui_0.getChildAt(1);
        ViewGroup ui_0_1_0 = (ViewGroup) ui_0_1.getChildAt(0);
        ViewGroup ui_0_1_0_0 = (ViewGroup) ui_0_1_0.getChildAt(0);
        ViewGroup ui_0_1_0_0_1 = (ViewGroup) ui_0_1_0_0.getChildAt(1);
        ViewGroup ui_0_1_0_0_1_0 = (ViewGroup) ui_0_1_0_0_1.getChildAt(0);
        Navigator ui_0_1_0_0_1_0_1 = (Navigator) ui_0_1_0_0_1_0.getChildAt(1);
        ViewGroup ui_0_1_0_0_1_0_1_1 = (ViewGroup) ui_0_1_0_0_1_0_1.getChildAt(1);
        CssLayoutParams layoutParams = (CssLayoutParams) ui_0_1_0_0_1_0_1_1.getLayoutParams();
        CssLayoutParams lp = (CssLayoutParams) layoutParams.after.getLayoutParams();
        layoutParams.after.setBackgroundColor(Color.RED);
        String style = lp.computedStyle.toString();
    }
}
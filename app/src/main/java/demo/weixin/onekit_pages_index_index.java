package demo.weixin;

import android.view.ViewGroup;

import cn.onekit.js.ArrayBuffer;
import cn.onekit.js.JsNumber;
import cn.onekit.js.
import cn.onekit.js.JsObject;
import cn.onekit.js.JsObject_;
import cn.onekit.js.JsString;
import cn.onekit.js.core.function;
import cn.onekit.thekit.ACTION1;
import cn.onekit.weixin.UDPSocket;
import cn.onekit.weixin.app.core.Vue;
import cn.onekit.weixin.app.core.WeixinPage;

public class onekit_pages_index_index extends WeixinPage {


    @Override
    public void onekit_wxml(ViewGroup ui, cn.onekit.js.JsObject_ data, Vue vue) {
        //Switch switch1 = new Switch(ui.getContext());
        //  ui.addView(switch1);
        //switch1.setStyle("width:100px;height:100px;");

        vue.For(data.get("items"), null, "index", "item", new ACTION1(){
            @Override
            public void invoke(Object Item) {

            }
        });
    }

    @Override
    protected void onekit_js() {
        Page(new JsObject() {{
            put("onLoad", new function() {
                @Override
                public JsObject_ invoke(JsObject_... params) {
                  /*  final UDPSocket udpConnect = wx.createUDPSocket();

                    final function closeCallback = new function() {
                        @Override
                        public JsObject_ invoke(JsObject_... params) {
                            final JsObject_ res = (JsObject_) params[0];
                            console.warn(new JsString("关闭"), res);
                            return null;
                        }
                    };

                    final function errorCallback = new function() {
                        @Override
                        public JsObject_ invoke(JsObject_... params) {
                            final JsObject_ res = (JsObject_) params[0];
                            console.warn(new JsString("错误"), res);
                            return null;
                        }
                    };

                    final function listeningCallback = new function() {
                        @Override
                        public JsObject_ invoke(JsObject_... params) {
                            final JsObject_ res = (JsObject_) params[0];
                            console.warn(new JsString("监听"), res);

                            udpConnect.send(new JsObject(){{
                                JsString jsString = new JsString("hello, how are you");
                                put("address",new JsString("192.168.22.173"));
                                put("port",new JsNumber(9527));
                                put("message",jsString);
                                put("offset",new JsNumber(1));
                                //put("length",jsString.getLength());
                            }});
                            return null;
                        }

                    };

                    final function messageCallback = new function() {
                        @Override
                        public JsObject_ invoke(JsObject_... params) {
                            final JsObject_ res = (JsObject_) params[0];
                            console.warn(new JsString("收到消息"), res);

                            udpConnect.close();
                            return null;
                        }
                    };

                    udpConnect.onListening(listeningCallback);
                    udpConnect.onError(errorCallback);
                    udpConnect.onMessage(messageCallback);
                    udpConnect.onClose(closeCallback);

                    udpConnect.bind();
                    return null;
                }
            });*/
        }});
    }
}

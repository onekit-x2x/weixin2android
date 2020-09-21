package cn.onekit.weixin.core.wx;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.JsArray;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxWebSocket extends WxVoice {
    WebSocketClient socketClient;

    public void connectSocket(Map Dict) {
        String url = (String) Dict.get("url");
        JsObject header = (JsObject) Dict.get("header");
        String method = (String) Dict.get("method");
        JsArray protocols = (JsArray) Dict.get("protocols");
        function success = (function) Dict.get("success");
        function fail = (function) Dict.get("fail");
        function complete = (function) Dict.get("complete");
        //

        try {
            socketClient = new WebSocketClient(URI.create(url)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
//if(onSocketOpen!=null){onSocketOpen.invoke();}ssss
                }

                @Override
                public void onMessage(String message) {

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {

                }

                @Override
                public void onError(Exception ex) {

                }
            };
            socketClient.connect();
            JsObject res = new JsObject();
//            res.errMsg = Android.context.getResources().getString(cn.onekit.weixin.R.string.wx_connectSocket_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_connectSocket_fail));
//            res.errMsg = Android.context.getResources().getString(cn.onekit.weixin.R.string.wx_connectSocket_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    function onSocketOpen;

    public void onSocketOpen(function callback) {
        onSocketOpen = callback;
    }

    function onSocketError;

    public void onSocketError(function callback) {
        onSocketError = callback;
    }

    public void sendSocketMessage(Map Dict) {
        String data = null;

        if (Dict.containsKey("data")) {
            data = ((String) Dict.get("data")).trim();
        }
        //发消息
        sendMessage(data);
    }

    function onSocketMessage;

    public void onSocketMessage(function callback) {
        onSocketMessage = callback;
    }

    public void closeSocket(Map Dict) {

    }

    function onSocketClose;

    public void onSocketClose(function callback) {
        onSocketClose = callback;
    }


    //发送message
    private void sendMessage(String msg) {

    }


}


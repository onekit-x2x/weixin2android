package cn.onekit.weixin;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

import cn.onekit.js.ArrayBuffer;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsObject_;
import cn.onekit.js.JsString;
import cn.onekit.js.core.function;


@SuppressWarnings("unused")
public class UDPSocket  {

    private static DatagramSocket socket ;
    private function messageCallback,listeningCallback,errorCallback, closeCallback;

    public JsObject_ bind(JsObject_ port) {
        try {
            if(port!=null) {
                socket = new DatagramSocket(((JsNumber) port).THIS.intValue());
            }else{
                socket = new DatagramSocket();
            }
            if(listeningCallback!=null) {
                listeningCallback.invoke(new JsObject());
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final byte[] msgRcv = new byte[1024]; //接收消息
                    final  DatagramPacket packetRcv = new DatagramPacket(msgRcv, msgRcv.length);
                    while (!socket.isClosed()) {
                        try {
                            socket.receive(packetRcv);
                            if(messageCallback!=null) {
                                String RcvMsg = new String(packetRcv.getData(), packetRcv.getOffset(), packetRcv.getLength());
                                Log.e("===============",RcvMsg);
                                        /*
                                messageCallback.invoke(new JsObject(){{
                                    put("remoteInfo",new JsObject(){{
                                        put("address",new JsString(packetRcv.getAddress().getHostAddress()));
                                        put("family",new JsString(""));
                                        put("port",new JsNumber(socket.getPort()));
                                        put("size",new JsNumber(packetRcv.getLength()));
                                    }});
                                    put("message",new ArrayBuffer(packetRcv.getData()));
                                }});*/
                            }
                        } catch (IOException e) {
                            e.printStackTrace();

                            if(errorCallback!=null) {
                                errorCallback.invoke(new JsObject(){{
                                    put("errMsg",new JsString(e.getMessage()));
                                }});
                            }
                        }
                    }
                }
            }).start();
            return new JsNumber(socket.getLocalPort());
        }catch (Exception e){
            e.printStackTrace();
            if(errorCallback!=null) {
                errorCallback.invoke(new JsObject(){{
                    put("errMsg",new JsString(e.getMessage()));
                }});
            }
            return null;
        }
    }
    public JsObject_ bind() {
        return bind(null);
    }

    public void close() {
        socket.disconnect();
        if(closeCallback!=null) {
            closeCallback.invoke(new JsObject());
        }
        socket.close();
    }

    public void offError(function callback) {
        this.errorCallback = null;
    }

    public void offClose(function callback) {
        this.closeCallback = null;
    }

    ;

    public void offListening(function callback) {
        this.listeningCallback = null;
    }

    ;

    public void offMessage(function callback) {
       this.messageCallback = null;
    }

    ;

    public void onClose(function callback) {
        this.closeCallback = callback;
    }

    ;

    public void onError(function callback) {
        this.errorCallback = callback;
    }

    ;

    public void onListening(function callback) {
        this.listeningCallback = callback;
    }

    ;

    public void onMessage(function callback) {
        this.messageCallback = callback;
    }

    public void send(Map OBJECT) {
        new Thread(){
            @Override
            public void run() {
                try {
                    final String address = ((JsString) OBJECT.get("address")).THIS;
                    final int port = ((JsNumber) OBJECT.get("port")).THIS.intValue();
                    final byte[] message = OBJECT.get("message") instanceof JsString ?
                            ((JsString) OBJECT.get("message")).THIS.getBytes() :
                            ((ArrayBuffer) OBJECT.get("message"))._data;
                    final int offset = OBJECT.get("offset") != null ? ((JsNumber) OBJECT.get("offset")).THIS.intValue() : 0;
                    final int length = OBJECT.get("length") != null ? ((JsNumber) OBJECT.get("length")).THIS.intValue() : message.length-offset;
                    //////////////////////////////////////
                    DatagramPacket packetSend = new DatagramPacket(message, offset, length, InetAddress.getByName(address), port);
                    socket.send(packetSend);
                } catch (Exception e) {
                    e.printStackTrace();
                    if(errorCallback!=null) {
                        errorCallback.invoke(new JsObject(){{
                            put("errMsg",new JsString(e.getMessage()));
                        }});
                    }
                }
            }
        }.start();

    }

}

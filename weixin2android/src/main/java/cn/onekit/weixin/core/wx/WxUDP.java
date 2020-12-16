package cn.onekit.weixin.core.wx;

import cn.onekit.weixin.UDPSocket;

public class WxUDP extends WxTopBar{
    public UDPSocket createUDPSocket(){

        return new UDPSocket();
    }

}

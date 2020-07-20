package demo.chen;

import cn.onekit.js.Array;
import cn.onekit.js.Date;
import cn.onekit.js.Dict;
import cn.onekit.js.core.OnekitJS;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.WeixinApplication;
import cn.onekit.weixin.core.res.wx_getSetting;
import cn.onekit.weixin.core.res.wx_getUserInfo;
import cn.onekit.weixin.core.res.wx_login;

public class app extends WeixinApplication {
    @Override
    public void onekit_js() {

        App(new Dict() {
            {

                put("onLaunch", new function() {
                    @Override
                    public JsObject invoke(JsObject... arguments) {

                        // 展示本地存储能力
                        Array<Long> logs = (Array<Long>) OnekitJS.or(wx.getStorageSync("logs"), new Array<Long>());
                        logs.unshift(Date.now());
                        wx.setStorageSync("logs", logs);

                        // 登录
                        wx.login(new Dict() {{
                            put("success", new function() {
                                @Override
                                public JsObject invoke(JsObject... arguments) {
                                    wx_login res = (wx_login) arguments[0];
                                    return null;
                                }
                            });
                        }});
                        // 获取用户信息
                        wx.getSetting(new Dict() {
                            {
                                put("success", new function() {
                                    @Override
                                    public JsObject invoke(JsObject... arguments) {
                                        wx_getSetting res = (wx_getSetting) arguments[0];
                                        if (OnekitJS.is(res.getAuthSetting().get("scope.userInfo"))) {
                                            // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                                            wx.getUserInfo(new Dict() {{
                                                put("success", new function() {
                                                    @Override
                                                    public JsObject invoke(JsObject... arguments) {
                                                        wx_getUserInfo res = (wx_getUserInfo) arguments[0];
                                                        // 可以将 res 发送给后台解码出 unionId
                                                        this.get("globalData").sot("userInfo", res.getUserInfo());

                                                        // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                                                        // 所以此处加入 callback 以防止这种情况
                                                        if (OnekitJS.is(get("userInfoReadyCallback"))) {
                                                            ((function) get("userInfoReadyCallback")).invoke(res);
                                                        }
                                                        return null;
                                                    }

                                                });
                                            }});

                                        }
                                        return null;
                                    }
                                });
                            }
                        });
                        return null;
                    }
                });
                put("globalData", new Dict() {{
                    put("userInfo", null);
                }});
            }
        });
    }
}
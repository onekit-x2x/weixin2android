package cn.onekit.weixin.core.wx;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.JsArray;
import cn.onekit.js.JsBoolean;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsString;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxWiFi extends WxWeRun {
    // 定义WifiManager对象
    private WifiManager mWifiManager = (WifiManager) Android.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
    // 扫描出的网络连接列表
    private List<ScanResult> mWifiList;
    // 网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    // 定义一个WifiLock
    WifiManager.WifiLock mWifiLock;
    String BSSID;
    String SSID;
    String password = null;
    boolean secure;
    int signalStrength;
    final SharedPreferences preferences = Android.context.getSharedPreferences("wifi_password", Context.MODE_PRIVATE);
    function callback;
    //错误代码
    //ok 正常
    private int errcode_OK = 0;
    //not init 未先调用startWifi接口
    private int errcode_0 = 12000;
    //system not support 当前系统不支持相关能力
    private int errcode_1 = 12001;
    //password error Wi-Fi 密码错误
    private int errcode_2 = 12002;
    //connection timeout 连接超时
    private int errcode_3 = 12003;
    //duplicate request 重复连接 Wi-Fi
    private int errcode_4 = 12004;
    //wifi not turned on Android特有，未打开 Wi-Fi 开关
    private int errcode_5 = 12005;
    //gps not turned on Android特有，未打开 GPS 定位开关
    private int errcode_6 = 12006;
    //user denied 用户拒绝授权链接 Wi-Fi
    private int errcode_7 = 12007;
    //invalid SSID 无效SSID
    private int errcode_8 = 12008;
    //system config err 系统运营商配置拒绝连接 Wi-Fi
    private int errcode_9 = 12009;
    //system internal error 系统其他错误，需要在errmsg打印具体的错误原因
    private int errcode_10 = 12010;
    //weapp in background 应用在后台无法配置 Wi-Fi
    private int errcode_11 = 12011;

    //startWifi开关
    private boolean switchStart = false;


    @SuppressLint("WrongConstant")
    public void startWifi(Map Dict) {
        if (!(mWifiManager.getWifiState() == 4)) {
            switchStart = true;
            JsObject res = new JsObject();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_startWifi_success);
//            res.errCode = errcode_OK;
            if (Dict.containsKey("success")) {
                function success = (function) Dict.get("success");
                success.invoke(res);
            }
            if (Dict.containsKey("complete")) {
                function complete = (function) Dict.get("complete");
                complete.invoke(res);
            }
        } else {
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_startWifi_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_startWifi_fail);
//            res.errCode = errcode_4;
            if (Dict.containsKey("fail")) {
                function fail = (function) Dict.get("fail");
                fail.invoke(res);
            }
            if (Dict.containsKey("complete")) {
                function complete = (function) Dict.get("complete");
                complete.invoke(res);
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void stopWifi(Map Dict) {
        if (switchStart == true) {
            if (!(mWifiManager.getWifiState() == 4)) {
                JsObject res = new JsObject();
//                res.errMsg = Android.context.getResources().getString(R.string.wx_stopWifi_success);
//                res.errCode = errcode_OK;
                if (Dict.containsKey("success")) {
                    function success = (function) Dict.get("success");
                    success.invoke(res);
                }
                if (Dict.containsKey("complete")) {
                    function complete = (function) Dict.get("complete");
                    complete.invoke(res);
                }
            } else {
                wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_stopWifi_fail));
//                res.errCode = errcode_4;
//                res.errMsg = Android.context.getResources().getString(R.string.wx_stopWifi_fail);
                if (Dict.containsKey("fail")) {
                    function fail = (function) Dict.get("fail");
                    fail.invoke(res);
                }
                if (Dict.containsKey("complete")) {
                    function complete = (function) Dict.get("complete");
                    complete.invoke(res);
                }
            }
        } else {
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_stopWifi_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_stopWifi_fail);
//            res.errCode = errcode_0;
            if (Dict.containsKey("fail")) {
                function fail = (function) Dict.get("fail");
                fail.invoke(res);
            }
            if (Dict.containsKey("complete")) {
                function complete = (function) Dict.get("complete");
                complete.invoke(res);
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void connectWifi(Map Dict) {
        if (Dict.containsKey("SSID")) {
            SSID = (String) Dict.get("SSID");
        }
        if (Dict.containsKey("BSSID")) {
            BSSID = (String) Dict.get("BSSID");
        }
        if (Dict.containsKey("password"))
            password = (String) Dict.get("password");

        if (switchStart) {
            if (mWifiManager.getWifiState() == 2 || mWifiManager.getWifiState() == 3) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SSID, password);   //保存密码
                editor.commit();
                boolean b = addNetwork(CreateWifiInfo(SSID, password, 3));
                if (b) {

                    if (Dict.containsKey("success")) {
                        function success = (function) Dict.get("success");
                        JsObject res = new JsObject();
//                        res.errMsg =Android.context.getResources().getString(R.string.wx_connectWifi_success);
                     /*   res.errCode = errcode_OK;
                        res.wifi = new Dict() {{
                            put("signalStrenth", 100 + mWifiInfo.getRssi());
                            put("secure", getCipherType(Android.context, SSID));
                            put("BSSID", BSSID);
                            put("SSID", SSID);
                        }};*/
                        success.invoke(res);
                    }
                    if (Dict.containsKey("complete")) {
                        function complete = (function) Dict.get("complete");
                        JsObject res = new JsObject();
//                        res.errCode = errcode_OK;
//                        res.errMsg = Android.context.getResources().getString(R.string.wx_connectWifi_fail);
                       /* res.wifi = new Dict() {{
                            put("signalStrenth", 100 + mWifiInfo.getRssi());
                            put("secure", getCipherType(Android.context, SSID));
                            put("BSSID", BSSID);
                            put("SSID", SSID);
                        }};*/
                        complete.invoke(res);
                    }
                } else {
                    wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_connectWifi_success));
//                    res.errCode = errcode_3;
//                    res.errMsg = Android.context.getResources().getString(R.string.wx_connectWifi_fail);
                    if (Dict.containsKey("fail")) {
                        function fail = (function) Dict.get("fail");
                        fail.invoke(res);
                    }
                    if (Dict.containsKey("complete")) {
                        function complete = (function) Dict.get("complete");
                        complete.invoke(res);
                    }
                }
            } else {
                wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_connectWifi_fail));
//                res.errMsg =Android.context.getResources().getString(R.string.wx_connectWifi_fail);
//                res.errCode = errcode_5;
                if (Dict.containsKey("fail")) {
                    function fail = (function) Dict.get("fail");
                    fail.invoke(res);
                }
                if (Dict.containsKey("complete")) {
                    function complete = (function) Dict.get("complete");
                    complete.invoke(res);
                }
            }
        } else {
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_connectWifi_fail));
//            res.errCode = errcode_0;
//            res.errMsg = Android.context.getResources().getString(R.string.wx_connectWifi_fail);
            if (Dict.containsKey("fail")) {
                function fail = (function) Dict.get("fail");
                fail.invoke(res);
            }
            if (Dict.containsKey("complete")) {
                function complete = (function) Dict.get("complete");
                complete.invoke(res);
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void getWifiList(Map Dict) {
        if (switchStart) {

            if (mWifiManager.getWifiState() == 2 || mWifiManager.getWifiState() == 3) {
                JsObject res = new JsObject();
//                res.errCode = errcode_OK;
//                res.errMsg = Android.context.getResources().getString(R.string.wx_getWifiList_success);
                if (Dict.containsKey("success")) {
                    function success = (function) Dict.get("success");
                    success.invoke(res);
                }
                if (Dict.containsKey("complete")) {
                    function complete = (function) Dict.get("complete");
                    complete.invoke(res);
                }
            } else {
                wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getWifiList_fail));
//                res.errMsg = Android.context.getResources().getString(R.string.wx_getWifiList_fail);
//                res.errCode = errcode_5;
                if (Dict.containsKey("fail")) {
                    function fail = (function) Dict.get("fail");
                    fail.invoke(res);
                }
                if (Dict.containsKey("complete")) {
                    function complete = (function) Dict.get("complete");
                    complete.invoke(res);
                }
            }
        } else {
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getWifiList_fail));
//            res.errCode = errcode_0;
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getWifiList_fail);
                if (Dict.containsKey("fail")) {
                function fail = (function) Dict.get("fail");
                fail.invoke(res);
            }
            if (Dict.containsKey("complete")) {
                function complete = (function) Dict.get("complete");
                complete.invoke(res);
            }
        }
    }

    public void onGetWifiList(function OBJECT) {
//        final Array wifiList = new Array();
//        for (int i = 0;i<mWifiManager.getScanResults().size();i++){
//            cn.onekit.Dict res = new Dict();
//            ScanResult sc =   mWifiManager.getScanResults().get(i);
//            SSID = sc.SSID;
//            BSSID = sc.BSSID;
//            secure = getCipherType(Android.context,SSID);
//            signalStrength = mWifiInfo.getRssi();
//            res.put("SSID",SSID);
//            res.put("BSSID",BSSID);
//            res.put("secure",secure);
//            res.put("signalStrength",signalStrength);
//            wifiList.add(res);
//            Dict.invoke(new Dict(){{
//                put("wifiList",wifiList);
//            }});
//        }
        final JsArray wifiList = new JsArray();
        callback = OBJECT;
        startScan(Android.context);
        mWifiList = getWifi();
        for (ScanResult result : mWifiList) {
            JsObject res = new JsObject();
            String SSID = result.SSID;
            String BSSID = result.BSSID;
            boolean secure = getCipherType(Android.context, SSID);
            int signalStrength = 100 + mWifiInfo.getRssi();
            res.put("SSID", new JsString(SSID));
            res.put("BSSID",new JsString( BSSID));
            res.put("secure",new JsBoolean( secure));
            res.put("signalStrength", new JsNumber(signalStrength));
            wifiList.add(res);
        }
        callback.invoke(new JsObject() {{
            put("wifiList", wifiList);
        }});
    }

    public void setWifiList(Map Dict) {
        function fail = (function) Dict.get("fail");
        wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_setWifiList_fail));
//        res.errCode = errcode_1;
        if (fail != null) {
            fail.invoke(res);
        }
        function complete = (function) Dict.get("complete");
        if (complete != null) {
            complete.invoke(res);
        }
    }

    public void presetWifiList(Map Dict) {
    }

    public void getConnectedWifi(Map Dict) {
        ConnectivityManager manager = (ConnectivityManager) Android.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (switchStart) {

            if (wifiInfo.isConnected()) {

                WifiManager wifiManager = (WifiManager) Android.context.getApplicationContext()
                        .getSystemService(Context.WIFI_SERVICE);
                final String SSID = wifiManager.getConnectionInfo().getSSID();
                final String BSSID = wifiManager.getConnectionInfo().getBSSID();
                final boolean secure = getCipherType(Android.context, SSID);
                final int signalStrength = mWifiInfo.getRssi() + 100;

                JsObject res = new JsObject() {{
                    put("SSID", new JsString(SSID));
                    put("BSSID", new JsString(BSSID));
                    put("secure",new JsBoolean( secure));
                    put("signalStrength",new JsNumber( signalStrength));
                }};
//                res.errCode = errcode_OK;
//                res.errMsg = Android.context.getResources().getString(R.string.wx_getConnectedWifi_success);
//                res.wifi = new Dict() {{
//                    put("SSID", SSID);
//                    put("BSSID", BSSID);
//                    put("secure", secure);
//                    put("signalStrength", signalStrength);
//                }};
                if (Dict.containsKey("success")) {
                    function success = (function) Dict.get("success");
                    success.invoke(res);
                }
                if (Dict.containsKey("complete")) {
                    function complete = (function) Dict.get("complete");
                    complete.invoke(res);
                }
            } else {
                wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getConnectedWifi_fail));
//                res.errMsg = Android.context.getResources().getString(R.string.wx_getConnectedWifi_fail);
//                res.errCode = errcode_5;
                if (Dict.containsKey("fail")) {
                    function fail = (function) Dict.get("fail");
                    fail.invoke(res);
                }
                if (Dict.containsKey("complete")) {
                    function complete = (function) Dict.get("complete");
                    complete.invoke(res);
                }
            }
        } else {
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getConnectedWifi_fail));
//            res.errCode = errcode_0;
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getConnectedWifi_fail);
            if (Dict.containsKey("fail")) {
                function fail = (function) Dict.get("fail");
                fail.invoke(res);
            }
            if (Dict.containsKey("complete")) {
                function complete = (function) Dict.get("complete");
                complete.invoke(res);
            }
        }


    }

    public void onWifiConnected(function OBJECT) {
        callback = OBJECT;
        ConnectivityManager manager = (ConnectivityManager) Android.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        JsObject res;
        if (wifiInfo.isConnected()) {
            WifiManager wifiManager = (WifiManager) Android.context.getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            String  SSID = wifiManager.getConnectionInfo().getSSID();
            String  BSSID = wifiManager.getConnectionInfo().getBSSID();
            boolean  secure = getCipherType(Android.context, SSID);
            int  signalStrength = mWifiInfo.getRssi() + 100;
             res = new JsObject() {{
                put("SSID", new JsString(SSID));
                put("BSSID", new JsString(BSSID));
                put("secure",new JsBoolean( secure));
                put("signalStrength",new JsNumber( signalStrength));
            }};
        }else{
            res = new JsObject();
        }
        callback.invoke(new JsObject() {{
            put("wifi", res);
        }});
    }

    public void onEvaluateWifi(Map Dict) {
    }

    public boolean getCipherType(Context context, String ssid) {
        mWifiList = mWifiManager.getScanResults();

        for (ScanResult scResult : mWifiList) {

            if (!TextUtils.isEmpty(scResult.SSID) && scResult.SSID.equals(ssid)) {
                String capabilities = scResult.capabilities;
                Log.i("river", "capabilities=" + capabilities);

                if (!TextUtils.isEmpty(capabilities)) {

                    if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
                        secure = true;

                    } else if (capabilities.contains("WEP") || capabilities.contains("wep")) {
                        secure = true;
                    } else {
                        secure = false;
                    }
                }
            }
        }
        return secure;
    }

    // 添加一个网络并连接
    public boolean addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        boolean b = mWifiManager.enableNetwork(wcgID, true);
        System.out.println("a--" + wcgID);
        Log.e("bili", "a--" + wcgID);
        Log.e("bili", "b--" + b);
        return b;
    }

//然后是一个实际应用方法，只验证过没有密码的情况：

    public WifiConfiguration CreateWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = IsExsits(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        if (Type == 1) //WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 2) //WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) //WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration IsExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    @SuppressLint("WrongConstant")
    public void startScan(Context context) {
        mWifiManager.startScan();
        // 得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        // 得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
        if (mWifiList == null) {
            if (mWifiManager.getWifiState() == 3) {
                Toast.makeText(context, "当前区域没有无线网络", Toast.LENGTH_SHORT).show();
            } else if (mWifiManager.getWifiState() == 2) {
                Toast.makeText(context, "WiFi正在开启，请稍后重新点击扫描", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "WiFi没有开启，无法扫描", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 得到网络列表
    public List<ScanResult> getWifi() {
        return mWifiList;
    }

    // 断开指定ID的网络
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    //监听wifi状态
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo.isConnected()) {
                WifiManager wifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                String wifiSSID = wifiManager.getConnectionInfo()
                        .getSSID();
                Toast.makeText(context, wifiSSID + "连接成功", Toast.LENGTH_SHORT).show();
            }
        }

    };
}

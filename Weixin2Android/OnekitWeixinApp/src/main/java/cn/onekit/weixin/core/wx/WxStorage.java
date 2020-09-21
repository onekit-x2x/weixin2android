package cn.onekit.weixin.core.wx;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import thekit.android.Android;
import cn.onekit.js.JsArray;
import cn.onekit.js.JsObject;
import cn.onekit.js.JSON;
import cn.onekit.js.JsString;
import cn.onekit.js.JsObject_;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxStorage extends WxSoterAuthentication  {
     Gson gson=new Gson();


    public  void setStorage(JsObject OBJECT) {
        String key =  ((JsString)OBJECT.get("key")).THIS;
        final JsObject_ data = OBJECT.get("data");
        ///////////////////
        _set(OBJECT, key, data);
        ////////////////////
    }

    private  void _set(Map OBJECT, String key, JsObject_ data) {
        try {
            Context context = Android.context;
            SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = spf.edit();
            JsObject ONEKIT = (JsObject) JSON.parse(spf.getString("ONEKIT", "{}"));
            String type = data.getClass().getName();
            ONEKIT.put(key, new JsString(type));
            editor.putString("ONEKIT", ONEKIT.toString());
            editor.putString(key, data.toString());
            editor.apply();
            function successCallback = null;
            function completeCallback = null;
            if (OBJECT.containsKey("success"))
                successCallback = (function) OBJECT.get("success");
            if (OBJECT.containsKey("complete"))
                completeCallback = (function) OBJECT.get("complete");
            JsObject res = new JsObject();
//            res.errMsg =  Android.context.getResources().getString(R.string.wx_setStroage_success);
            if (successCallback != null)
                successCallback.invoke(res);
            if (completeCallback != null)
                completeCallback.invoke(res);
        } catch (Exception e) {
            e.printStackTrace();
            function failCallback = null;
            function completeCallback = null;
            if (OBJECT.containsKey("fail"))
                failCallback = (function) OBJECT.get("fail");
            if (OBJECT.containsKey("complete"))
                completeCallback = (function) OBJECT.get("complete");
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_setStroage_fail));
//            res.errMsg =  Android.context.getResources().getString(R.string.wx_setStroage_fail);
            if (failCallback != null)
                failCallback.invoke(res);
            if (completeCallback != null)
                completeCallback.invoke(res);
        }
    }
    public void setStorageSync(JsObject_ jskey, JsObject_ value) {
        String key = ((JsString)jskey).THIS;
        try {
            Context context = Android.context;
            SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = spf.edit();
            JsObject ONEKIT = (JsObject) JSON.parse(spf.getString("ONEKIT", "{}"));
            String type;
            if (value instanceof List) {
                type = "List";
            } else if (value instanceof Map) {
                type = "Map";
            } else {
                type = value.getClass().getName();
            }
            ONEKIT.put(key, new JsString(type));
            editor.putString("ONEKIT", ONEKIT.toString());
            Log.d("type-----------------------", "setStorageSync: " + type);
            editor.putString(key, value.toString());
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
//            FUNC1 failCallback = null;
//            FUNC1 completeCallback = null;
//            if (OBJECT.containsKey("fail"))
//                failCallback = (FUNC1) OBJECT.get("fail");
//            if (OBJECT.containsKey("complete"))
//                completeCallback = (FUNC1) OBJECT.get("complete");
//            if (failCallback!=null)
//                failCallback.invoke(new Dict(){{
//                    put("fail","fail");
//                }});
//            if (completeCallback!=null)
//                completeCallback.invoke(new Dict(){{
//                    put("complete","调用失败");
//                }});
        }
    }

    public  void getStorage(JsObject OBJECT) {
        JsObject_ key = OBJECT.get("key");
        function successCallback = (function) OBJECT.get("success");

        JsObject_ data = getStorageSync(key);
        JsObject result = new JsObject();
//        result.data = data;
//        result.errMsg =  Android.context.getResources().getString(R.string.wx_getStorage_success);
        //
        function completeCallback = null;
        if (OBJECT.containsKey("complete"))
            completeCallback = (function) OBJECT.get("complete");
        if (successCallback != null)
            successCallback.invoke(result);
        if (completeCallback != null)
            completeCallback.invoke(result);
    }

    public JsObject_ getStorageSync(JsObject_ key) {
        String akey = ((JsString)key).THIS;
            Context context = Android.context;
            SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
            if (!spf.contains(akey)) {
                return null;
            }
            JsObject ONEKIT = (JsObject) JSON.parse(spf.getString("ONEKIT", "{}"));
            String type = ((JsString) ONEKIT.get(key)).THIS;
            String value = spf.getString(akey, null);
            /*switch (type) {
                case "Map":
                    return gson.fromJson(value,Dict.class);
                case "List":
                    return gson.fromJson(value,Array.class);
                case "java.lang.Integer":
                    return gson.fromJson(value,Integer.class);
                case "java.lang.Float":
                    return gson.fromJson(value,Float.class);
                case "java.lang.Double":
                    return gson.fromJson(value,Double.class);
                case "java.lang.Boolean":
                    return gson.fromJson(value,Boolean.class);
                default:
                    return value;
            }*/
        try {
            return gson.fromJson(value, (Type) Class.forName(type));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public  void getStorageInfo(JsObject OBJECT) {
        function success = (function) OBJECT.get("success");
        final JsArray keys = new JsArray();
        try {
            final JsObject data = getStorageInfoSync();
            Set<String> set = data.keySet();
            String path = storagePath();
            Log.e("bili", path);
            final Long total = getTotalMemorySize(path);
//            Log.e("bili",total+"");
            final Long ava = getAvailableMemorySize(path);
//            Log.e("bili",ava+"");
            //  Log.e("bili",set+"");
            for (String in : set) {
                keys.add(new JsString(in));
                //     Log.e("bili",in);
            }
            JsObject res = new JsObject();
//            res.keys = keys;
//            res.currentSize =(total - ava) / 1000000;
//            res.limitSize = total / 1000000;
            if (success != null) {
                success.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JsObject getStorageInfoSync() {
        Context context = Android.context;
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);

        JsObject ONEKIT = (JsObject) JSON.parse(spf.getString("ONEKIT", "{}"));
//        Dict  result = new Dict();
        return ONEKIT;

    }

    public  void removeStorage(Map OBJECT) {
        String key = (String) OBJECT.get("key");

        Context context = Android.context;
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = spf.edit();
        try {

            editor.remove(key);
            editor.apply();
            function success = (function) OBJECT.get("success");
            JsObject res = new JsObject();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_removeStorage_success);
            if (success != null) {
                success.invoke(res);
            }
            function completeCallback = null;
            if (OBJECT.containsKey("complete"))
                completeCallback = (function) OBJECT.get("complete");

            if (completeCallback != null)
                completeCallback.invoke(res);
        } catch (Exception e) {
            e.printStackTrace();
            function fail = (function) OBJECT.get("fail");
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_removeStorage_fail));
//            res.errMsg =Android.context.getResources().getString(R.string.wx_removeStorage_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            function completeCallback = null;
            if (OBJECT.containsKey("complete"))
                completeCallback = (function) OBJECT.get("complete");

            if (completeCallback != null)
                completeCallback.invoke(res);
        }
    }

    public  void removeStorageSync(JsObject_ key) {

        Context context = Android.context;
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = spf.edit();
        try {
            editor.remove(((JsString)key).THIS);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void clearStorage(Map OBJECT) {
        try {
            Context context = Android.context;
            SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = spf.edit();
            editor.clear();
            editor.apply();
            if (OBJECT.get("success") != null) {
                function success = (function) OBJECT.get("success");
                JsObject res = new JsObject();
//                res.errMsg = Android.context.getResources().getString(R.string.wx_clearStorage_success);
                success.invoke(res);
            }
            if (OBJECT.get("complete") != null) {
                function complete = (function) OBJECT.get("complete");
                JsObject res = new JsObject();
//                res.errMsg = Android.context.getResources().getString(R.string.wx_clearStorage_success);
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (OBJECT.get("fail") != null) {
                function fail = (function) OBJECT.get("fail");
                wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_clearStorage_fail));
//                res.errMsg = Android.context.getResources().getString(R.string.wx_clearStorage_fail);
                fail.invoke(res);
            }
            if (OBJECT.get("complete") != null) {
                function complete = (function) OBJECT.get("complete");
                wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_clearStorage_fail));
//                res.errMsg = Android.context.getResources().getString(R.string.wx_clearStorage_fail);
                complete.invoke(res);
            }
        }

    }

    public  void clearStorageSync() {
        try {
            Context context = Android.context;
            SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = spf.edit();
            editor.clear();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public  void removeByKey(JsObject key) {
//        Context context = Android.context;
//        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        try {
//            JSONObject jsonObject = new JSONObject(spf.getString("ONEKIT","{}"));
//            jsonObject.remove(key);
//            editor.putString("ONEKIT",jsonObject.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//            FUNC1 failCallback = null;
//            FUNC1 completeCallback = null;
//            if (OBJECT.containsKey("fail"))
//                failCallback = (FUNC1) OBJECT.get("fail");
//            if (OBJECT.containsKey("complete"))
//                completeCallback = (FUNC1) OBJECT.get("complete");
//            if (failCallback!=null)
//                failCallback.invoke(new Dict(){{
//                    put("errMsg","removeStorage:fail");
//                }});
//            if (completeCallback!=null)
//                completeCallback.invoke(new Dict(){{
//                    put("errMsg","removeStorage:fail");
//                }});
//        }
//        editor.remove(key);
//        editor.commit();
//    }
//
//    public  void removeAll() {
//        Context context = Android.context;
//        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.clear();
//        editor.commit();
//    }


    public  String storagePath() {
        String path = "/data/data/" + Android.context.getPackageName() + "/shared_prefs";
//        String path = "/storage/emulated/0/Android/data/"+ Android.context.getPackageName()+"/shared_prefs";
        return path;
    }

    private   long getTotalMemorySize(String path) {

        StatFs stat = new StatFs(path);

        long blockSize = stat.getBlockSize(); // 每个block 占字节数
        long totalBlocks = stat.getBlockCount(); // block总数

        return totalBlocks * blockSize;

    }

    private   long getAvailableMemorySize(String path) {

        StatFs stat = new StatFs(path);

        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();

        return availableBlocks * blockSize;

    }
}

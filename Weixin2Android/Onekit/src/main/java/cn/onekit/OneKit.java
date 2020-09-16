package cn.onekit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.util.UUID;

import thekit.android.Android;

public class OneKit {
    public static String currentUrl;

    public static void set(Object obj, Object value, String key) {
        try {
            Class clazz = obj.getClass();
            Field filed = clazz.getField(key);
            filed.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object get(Object obj, Object key) {
        try {
            Class clazz = obj.getClass();
            Field field = clazz.getDeclaredField((String) key);
            if (field == null) {
                return null;
            }
            return field.get(obj);
        } catch (Exception e) {
            //Log.e("[get]==========", obj + " " +key);
            return null;
        }
    }

    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static Bundle querystring2extras(String querystring) {
        Bundle extras = new Bundle();
        for (String param : querystring.split("&")) {
            String[] arr = param.split("=");
            extras.putString(arr[0], arr[1]);
        }
        return extras;
    }

    static String extras2querystring(Bundle extras) {
        if (extras == null) {
            return "";
        }
        StringBuilder querystring = new StringBuilder();
        int i = 0;
        for (String key : extras.keySet()) {
            if (key.startsWith("onekit_")) {
                continue;
            }
            if (i++ > 0) {
                querystring.append("&");
            }
            querystring.append(String.format("%s=%s", key, extras.getString(key)));
        }
        return querystring.toString();
    }

    public static Class<? extends Activity> tabsActivityClass;

    public static String class2url(Context context, String clazz) {
        if (tabsActivityClass != null && clazz.equals(tabsActivityClass.getName())) {
            if (context == null) {
                return "";
            }
        }
        clazz = clazz.substring(context.getPackageName().length() + 1);
        return  clazz.substring("onekit_".length()).replace("_", "/");
    }

    public static String class2url(Context context, String clazz, Bundle extras) {
        String url = class2url(context, clazz);
        String querystring = extras2querystring(extras);
        if (querystring.length() > 0) {
            url += "?" + querystring;
        }
        return url;
    }

    public static String url2class(Context context, String url) {
        return context.getPackageName() + ".onekit_" + url.replace("/", "_");
    }

    public static String launchPath(Context context) {
        String name = Android.launchActivityName(context);
        return class2url(context, name.substring(1));
    }

    public static String fixPath(String currentUrl,String url) {
        if (url.startsWith("/")) {
            return url.substring(1);
        }
        ////////////////////
        String folder;
        if (currentUrl.contains("/")) {
            folder = currentUrl.substring(0, currentUrl.lastIndexOf("/") + 1);
            if(folder.startsWith("/")){
                folder = folder.substring(1);
            }
        } else {
            folder = "";
        }
        url = url.trim();
        if (url.startsWith("./")) {
            url = url.substring("./".length());
        }
        while (url.startsWith("../")) {
            folder = folder.substring(0, folder.length() - 1);
            folder = folder.substring(0, folder.lastIndexOf("/") + 1);
            url = url.substring("../".length());
        }

        return folder + url;
    }

    //获取唯一UUID
    public static String createUUID() {
        StringBuilder uuidStr = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            UUID uuid = UUID.randomUUID();
            String str = uuid.toString().replaceAll("-", "");
            uuidStr.append(str);
        }
        return uuidStr.toString();
    }

    public static String createUUIDfileName(String fileName) {
        String uuid = createUUID();
        String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return uuid + ext;
    }

    public static boolean isEmpty(String aString) {
        return aString == null || aString.trim().equals("");
    }
/*
    public static boolean isNaN(Object value) {

        if (value == null) {
            return true;
        }
        if (value instanceof Number) {
            Number number = (Number) value;
            return Double.valueOf(number.doubleValue()).equals(Double.NaN);
        }
        if (value instanceof String) {

            String aString = (String) value;
            if (aString.equals("NaN")) {
                return true;
            }
            if (aString.equals("")) {
                return false;
            }
            int count = 0;
            for (char chr : aString.toCharArray()) {
                if (chr == ' ') {
                    count++;
                }
            }
            if (count == aString.length()) {
                return false;
            }
        }

        if (value instanceof java.lang.Boolean) {
            return false;
        }
        return !isNumber(value);
    }
*/
    public static String firstUppder(String key) {
        return key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    public static String firstLower(String key) {
        return key.substring(0, 1).toLowerCase() + key.substring(1);
    }
/*
    public static boolean isNumber(JsObject value) {
        if (value == null) {
            return false;
        }

        if (value instanceof Number) {
            return !value.equals(Double.NaN);
        }
        try {
            Double.parseDouble(value.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
*/
    public static String toCamelCase(String aString) {
        StringBuilder result = new StringBuilder();
        //
        String[] strings = aString.trim().split("-");
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            if (i > 0) {
                str = firstUppder(str);
            }
            result.append(str);
        }
        //
        return result.toString();
    }

    public static String fromCamelCase(String aString) {
        StringBuilder result = new StringBuilder();
        //
        for (char chr : aString.toCharArray()) {
            if (result.length() > 0) {
                if (chr >= 'A' && chr <= 'Z') {
                    result.append("-");
                }
            }
            result.append(String.valueOf(chr).toLowerCase());
        }
        //
        return result.toString();
    }
}

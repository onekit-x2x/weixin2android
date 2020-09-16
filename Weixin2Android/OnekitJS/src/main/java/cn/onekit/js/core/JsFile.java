package cn.onekit.js.core;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.onekit.js.Array;
import cn.onekit.js.Console;
import cn.onekit.js.Dict;
import cn.onekit.js.Error;
import cn.onekit.js.Map;
import cn.onekit.js.Null;
import cn.onekit.js.Symbol;
import cn.onekit.js.URIError;

public interface JsFile {

    HashMap<Integer,HashMap<String,function>> allOverrides = new HashMap();
    default void setOverride(String clazz,String method,function function){
        if(!allOverrides.containsKey(this.hashCode())){
            allOverrides.put(hashCode(), new HashMap());
        }
        HashMap<String,function> overrides = allOverrides.get(hashCode());
        overrides.put(String.format("%s.%s",clazz,method),function);
    }
    default function getOverride(String clazz,String method){
        return allOverrides.get(hashCode()).get(String.format("%s.%s",clazz,method));
    }
    //////////////////////////////////////
    HashMap<Integer,HashMap<String,function>> allPrototypes = new HashMap();
    default void setPrototype(String clazz,String method,function function){
        if(!allPrototypes.containsKey(this.hashCode())){
            allPrototypes.put(hashCode(), new HashMap());
        }
        HashMap<String,function> prototypes = allOverrides.get(hashCode());
        prototypes.put(String.format("%s.%s",clazz,method),function);
    }
    default function getPrototype(String clazz,String method,JsObject THIS){
        function function =  allPrototypes.get(hashCode()).get(String.format("%s.%s",clazz,method));
        function.thisArg = THIS;
        return function;
    }
    /////////////////////////////////////
    default String onekit_$(String format, Dict args) {
        for (Dict.Entry entry : args.entrySet()) {
            String str = String.format("${%s}", entry.getKey());
            format = format.replace(str, Onekit_JS.toString(entry.getValue()));
        }
        return format;
    }

    ////////////////////////////////////

    default String typeof(JsObject obj) {
        if (obj == null) {
            return "undefined";
        }
        if (obj instanceof Null) {
            return "obj";
        }
        return obj.getClass().getSimpleName();
    }

    JsObject NaN = JsNumber.NaN;
    JsObject undefined = null;
    JsObject Infinity = JsNumber.POSITIVE_INFINITY;
    JsObject Null = new Null();

    /////////////////////////////////////
    default JsObject Number(JsObject value) {
        return JsNumber.Number(value);
    }

    default JsObject Boolean(JsObject value) {
        return new JsBoolean(value);
    }

    default Error Error(JsObject message) {
        return new Error(message);
    }

    default  Array Array(JsObject length) {
        return new Array(length);
    }

    default  Map Map(Array map) {
        Map result = new Map();
        for (JsObject temp : map) {
            Array item  = (Array) temp;
            result.set(item.get(0), item.get(1));
        }
        return result;
    }

    ///////////////////////////////////////////
    default String decodeURI(String url) {
        return URLDecoder.decode(url);
    }

    default String decodeURIComponent(String url) {
        return URLDecoder.decode(url);
    }

    default String encodeURI(String url) {
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new URIError(new JsString("Uncaught URIError: URI malformed"));
        }
    }

    default String encodeURIComponent(String url) {

        try {
            return URLEncoder.encode(url, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            throw new URIError(new JsString("Uncaught URIError: URI malformed"));
        }
    }

    String ascii = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890";

    default String escape(String src) {
        StringBuilder result = new StringBuilder();
        for (char chr : src.toCharArray()) {
            String str = String.valueOf(chr);
            if (ascii.contains(str)) {
                result.append(str);
                continue;
            }
            if ("@*_+-./".contains(str)) {
                result.append(str);
                continue;
            }
            StringBuilder s16 = new StringBuilder(Long.toHexString(chr).toUpperCase());
            if (s16.length() > 2) {
                while (s16.length() < 4) {
                    s16.insert(0, "0");
                }
                s16.insert(0, "%u");
            } else {
                while (s16.length() < 2) {
                    s16.insert(0, "0");
                }
                s16.insert(0, "%");
            }
            result.append(s16);
        }
        return result.toString();
    }

    default JsObject isFinite(JsObject testValue) {
        return JsNumber.isFinite(testValue);

    }

    default JsObject isNaN(JsObject v) {
        return JsNumber.isNaN(v);
/*
        if (v == null) {
            return true;
        }
        if (v instanceof Null) {
            return true;
        }
        Object value = ((Number)v)._object;
        if (value instanceof Number) {
            Number number = (Number) value;
            return Double.valueOf(number.doubleValue()).equals(NUMBER.NaN);
        }
        if (value instanceof String) {

            String string = (String) value;
            if (string.equals("NaN")) {
                return true;
            }
            if (string.equals("")) {
                return false;
            }
            int count = 0;
            for (char chr : string.toCharArray()) {
                if (chr == ' ') {
                    count++;
                }
            }
            if (count == string.length()) {
                return false;
            }
        }

        if (value instanceof java.lang.Boolean) {
            return false;
        }
        return !OnekitJS.isNumber(value);*/
    }

    default Double parseFloat(JsObject string) {
        try {
            Pattern pattern = Pattern.compile("^[+-]?[\\d]+([.][\\d]*)?([Ee][+-]?[\\d]+)?$");
            Matcher matcher = pattern.matcher(string.toString().trim());
            StringBuilder str = new StringBuilder();
            boolean flag = false;
            while (matcher.find()) {
                str.append(matcher.group());
                flag = true;
            }
            if (!flag) {
                pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
                matcher = pattern.matcher(string.toString().trim());
                while (matcher.find()) {
                    str.append(matcher.group());
                }
            }
            return Double.parseDouble(str.toString());
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    default  JsObject parseInt(JsObject string, JsObject radix) {
        try {
            int flag;
            if (radix == null || !Onekit_JS.isNumber(radix)) {
                flag = 10;
            } else {
                flag = Onekit_JS.number(radix,0,0).intValue();
            }
            String str = string.toString().trim();
            Long result;
            if (str.startsWith("0x")) {
                result = new BigInteger(str.substring(2), flag).longValue();
            } else if (str.startsWith("0b")) {
                result = new BigInteger(str.substring(2), flag).longValue();
            } else if (str.startsWith("0o")) {
                result = new BigInteger(str.substring(2), flag).longValue();
            } else {
                result = new BigInteger(str, flag).longValue();
            }
            return  new JsNumber(result);
        } catch (Exception e) {
            return new JsString(NaN.toString());
        }
    }


    default String unescape(String src) {
        StringBuilder tmp = new StringBuilder();
        tmp.ensureCapacity(src.length());
        int lastPos = 0;
        char ch;
        while (lastPos < src.length()) {
            int pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /////////////////////////////////////
      Console console = new Console();
    /////////////////////////////////////


    default Class getClass(JsObject obj) {
        return obj.getClass();
    }


    default Float NUMBER(String string) {
        try {
            return Float.parseFloat(string);
        } catch (Exception e) {
            return null;
        }
    }


    default String String(String string, JsObject... vars) {
        return string;
    }

    //
   /* default String typeOf(JsObject obj) {
        return obj.getClass().getSimpleName().toLowerCase();
    }*/

    ///////////////////////////

    default Symbol Symbol(JsObject description) {
        return new Symbol(description);
    }
    default Symbol Symbol() {
        return new Symbol();
    }


    //
    @SuppressLint("UseSparseArrays")
    HashMap<Long, cn.onekit.js.core.function> _timeouts = new HashMap();

    default long setTimeout(JsObject function, JsObject delay, JsObject... params) {

        long id = new Random().nextLong();
        @SuppressLint("HandlerLeak")
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (_timeouts.containsKey(id)) {
                    _timeouts.get(id).invoke(params);
                }
            }
        };
        handler.sendEmptyMessageDelayed(0, Onekit_JS.number(delay,0,0).longValue());
        _timeouts.put(id, (cn.onekit.js.core.function) function);
        return id;

    }

    default long setTimeout(JsObject function) {
        return setTimeout(function, new JsNumber(0));
    }

    default void clearTimeout(long id) {

        if (!_timeouts.containsKey(id)) {
            return;
        }
        _timeouts.remove(id);
    }

    //////////////////////////////////////////////
    HashMap<Long, Timer> _intervals = new HashMap();

    default long setInterval(JsObject function, JsObject delay, JsObject... params) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                function.invoke(params);
            }
        };

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                handler.sendMessage(message);
            }
        };
        long id = timer.hashCode();
        long delay_ = Onekit_JS.number(delay,0,0).longValue();
        timer.schedule(timerTask, delay_, delay_);
        _intervals.put(id, timer);
        return id;
    }

    default void clearInterval(long id) {

        if (!_intervals.containsKey(id)) {
            return;
        }
        _intervals.get(id).cancel();
        _intervals.remove(id);
    }
}

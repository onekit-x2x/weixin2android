package demo.css;

import cn.onekit.js.core.JsString;
import cn.onekit.weixin.app.core.WeixinClass;

public class onekit_lib_Character extends WeixinClass {

    public static boolean isDight(String str){
        int ch = JsString.charCodeAt(str);
        return ch>= JsString.charCodeAt("0") && ch<= JsString.charCodeAt("9");
    }
    public static Integer digit(String ch,Integer radix){
        int codePoint = JsString.charCodeAt(ch);
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            return -1;
        }
        if (codePoint < 128) {
            // Optimized for ASCII
            int result = -1;
            if (JsString.charCodeAt("0") <= codePoint && codePoint <= JsString.charCodeAt("9")) {
                result = codePoint - JsString.charCodeAt("0");
            } else if (JsString.charCodeAt("a") <= codePoint && codePoint <= JsString.charCodeAt("z")) {
                result = 10 + (codePoint - JsString.charCodeAt("a"));
            } else if (JsString.charCodeAt("A") <= codePoint && codePoint <= JsString.charCodeAt("Z")) {
                result = 10 + (codePoint - JsString.charCodeAt("A"));
            }
            return result < radix ? result : -1;
        }
        throw new Error(ch+" "+radix);
        //return digitImpl(codePoint, radix);
    }
    public static int MIN_RADIX=2;
    public static  int MAX_RADIX = 36;
}

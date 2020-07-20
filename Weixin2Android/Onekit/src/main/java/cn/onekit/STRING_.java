package cn.onekit;

public class STRING_ {
    public static  boolean isEmpty(String string){
        return string==null || string.trim().equals("");
    }
    public static String firstUpper(String s) {
        if (STRING_.isEmpty(s)) {
            return s;
        }
        if (s.length() < 1) {
            return s.toUpperCase();
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String[] split(String string, String str) {
        if(string.indexOf(str)<0){
            return new String[]{ string };
        } else if (string.startsWith(str)) {
            return new String[]{"", string.substring(1)};
        } else {
            return string.split(str);
        }
    }
}

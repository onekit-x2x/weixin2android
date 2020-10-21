package cn.onekit.thekit;

public class STRING_ {
    public static  boolean isEmpty(String aString){
        return aString==null || aString.trim().equals("");
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

    public static String[] split(String aString, String str) {
        if(aString.indexOf(str)<0){
            return new String[]{ aString };
        } else if (aString.startsWith(str)) {
            return new String[]{"", aString.substring(1)};
        } else {
            return aString.split(str);
        }
    }
}

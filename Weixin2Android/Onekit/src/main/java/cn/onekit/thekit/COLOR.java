package cn.onekit.thekit;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;


public class COLOR {

    /**
     * 颜色转字符串
     *
     * @param intColor 颜色
     */
    public static String toString(Integer intColor) {
        if (intColor == 0) {
            return "#00000000";
        }
        String red = Integer.toHexString((intColor & 0xff0000) >> 16);
        String green = Integer.toHexString((intColor & 0x00ff00) >> 8);
        String blue = Integer.toHexString((intColor & 0x0000ff));
        if (red.length() == 1) {
            red = String.format("%1s%2s", 0, red).replace(" ", "");
        }
        if (green.length() == 1) {
            green = String.format("%1s%2s", 0, green).replace(" ", "");
        }
        if (blue.length() == 1) {
            blue = String.format("%1s%2s", 0, blue).replace(" ", "");
        }
        return '#' + red + green + blue;
    }

    /**
     * 字符串转颜色
     *
     * @param cString 字符串
     */
    @SuppressWarnings("static-access")
    public static int parse(String cString) {
        try {
            cString = cString.toLowerCase();
            switch (cString) {
                case "transparent": {
                    cString = "#00000000";
                }
                break;
                case "lightgray": {
                    cString = "#666666";
                }
                break;
                case "lightpink": {
                    cString = "#ffb6c1";
                }
                break;
                case "pink": {
                    cString = "#ffc0cb";
                }
                break;
                case "crimson": {
                    cString = "#dc143c";
                }
                break;
                case "lavenderblush": {
                    cString = "#fff0f5";
                }
                break;
                case "palevioletred": {
                    cString = "#db7093";
                }
                break;
                case "hotpink": {
                    cString = "#ff69b4";
                }
                break;
                case "deeppink": {
                    cString = "#ff1493";
                }
                break;
                case "mediumvioletred": {
                    cString = "#c71585";
                }
                break;
                case "orchid": {
                    cString = "#da70d6";
                }
                break;
                case "thistle": {
                    cString = "#d8bfd8";
                }
                break;
                case "plum": {
                    cString = "#dda0dd";
                }
                break;
                case "violet": {
                    cString = "#ee82ee";
                }
                break;
                case "magenta": {
                    cString = "#ff00ff";
                }
                break;
                case "fuchsia": {
                    cString = "#ff00ff";
                }
                break;
                case "darkmagenta": {
                    cString = "#8b008b";
                }
                break;
                case "purple": {
                    cString = "#800080";
                }
                break;
                case "mediumorchid": {
                    cString = "#ba55d3";
                }
                break;
                case "darkviolet": {
                    cString = "#9400d3";
                }
                break;
                case "darkorchid": {
                    cString = "#9932cc";
                }
                break;
                case "indigo": {
                    cString = "#4b0082";
                }
                break;
                case "blueviolet": {
                    cString = "#8a2be2";
                }
                break;
                case "mediumpurple": {
                    cString = "#9370db";
                }
                break;
                case "mediumslateblue": {
                    cString = "#7b68ee";
                }
                break;
                case "slateblue": {
                    cString = "#6a5acd";
                }
                break;
                case "darkslateblue": {
                    cString = "#483d8b";
                }
                break;
                case "lavender": {
                    cString = "#e6e6fa";
                }
                break;
                case "ghostwhite": {
                    cString = "#f8f8ff";
                }
                break;
                case "blue": {
                    cString = "#0000ff";
                }
                break;
                case "mediumblue": {
                    cString = "#0000cd";
                }
                break;
                case "midnightblue": {
                    cString = "#191970";
                }
                break;
                case "darkblue": {
                    cString = "#00008b";
                }
                break;
                case "navy": {
                    cString = "#000080";
                }
                break;
                case "royalblue": {
                    cString = "#4169e1";
                }
                break;
                case "cornflowerblue": {
                    cString = "#6495ed";
                }
                break;
                case "lightsteelblue": {
                    cString = "#b0c4de";
                }
                break;
                case "lightslategray": {
                    cString = "#778899";
                }
                break;
                case "slategray": {
                    cString = "#708090";
                }
                break;
                case "dodgerblue": {
                    cString = "#1e90ff";
                }
                break;
                case "aliceblue": {
                    cString = "#f0f8ff";
                }
                break;
                case "steelblue": {
                    cString = "#4682b4";
                }
                break;
                case "lightskyblue": {
                    cString = "#87cefa";
                }
                break;
                case "skyblue": {
                    cString = "#87ceeb";
                }
                break;
                case "deepskyblue": {
                    cString = "#00bfff";
                }
                break;
                case "lightblue": {
                    cString = "#add8e6";
                }
                break;
                case "powderblue": {
                    cString = "#b0e0e6";
                }
                break;
                case "cadetblue": {
                    cString = "#5f9ea0";
                }
                break;
                case "azure": {
                    cString = "#f0ffff";
                }
                break;
                case "lightcyan": {
                    cString = "#e0ffff";
                }
                break;
                case "paleturquoise": {
                    cString = "#afeeee";
                }
                break;
                case "cyan": {
                    cString = "#00ffff";
                }
                break;
                case "aqua": {
                    cString = "#00ffff";
                }
                break;
                case "darkturquoise": {
                    cString = "#00ced1";
                }
                break;
                case "darkslategray": {
                    cString = "#2f4f4f";
                }
                break;
                case "darkcyan": {
                    cString = "#008b8b";
                }
                break;
                case "teal": {
                    cString = "#008080";
                }
                break;
                case "mediumturquoise": {
                    cString = "#48d1cc";
                }
                break;
                case "lightseagreen": {
                    cString = "#20b2aa";
                }
                break;
                case "turquoise": {
                    cString = "#40e0d0";
                }
                break;
                case "aquamarine": {
                    cString = "#7fffd4";
                }
                break;
                case "mediumaquamarine": {
                    cString = "#66cdaa";
                }
                break;
                case "mediumspringgreen": {
                    cString = "#00fa9a";
                }
                break;
                case "mintcream": {
                    cString = "#f5fffa";
                }
                break;
                case "springgreen": {
                    cString = "#00ff7f";
                }
                break;
                case "mediumseagreen": {
                    cString = "#3cb371";
                }
                break;
                case "seagree": {
                    cString = "#2e8b57";
                }
                break;
                case "honeydew": {
                    cString = "#f0fff0";
                }
                break;
                case "lightgreen": {
                    cString = "#90ee90";
                }
                break;
                case "palegreen": {
                    cString = "#98fb98";
                }
                break;
                case "darkseagreen": {
                    cString = "#8fbc8f";
                }
                break;
                case "limegreen": {
                    cString = "#32cd32";
                }
                break;
                case "lime": {
                    cString = "#00ff00";
                }
                break;
                case "forestgreen": {
                    cString = "#228b22";
                }
                break;
                case "green": {
                    cString = "#008000";
                }
                break;
                case "darkgreen": {
                    cString = "#006400";
                }
                break;
                case "chartreuse": {
                    cString = "#7fff00";
                }
                break;
                case "lawngreen": {
                    cString = "#7cfc00";
                }
                break;
                case "greenyellow": {
                    cString = "#adff2f";
                }
                break;
                case "darkolivegreen": {
                    cString = "#556b2f";
                }
                break;
                case "yellowgree": {
                    cString = "#9acd32";
                }
                break;
                case "olivedrab": {
                    cString = "#6b8e23";
                }
                break;
                case "beige": {
                    cString = "#f5f5dc";
                }
                break;
                case "lightgoldenrodyellow": {
                    cString = "#fafad2";
                }
                break;
                case "ivory": {
                    cString = "#fffff0";
                }
                break;
                case "lightyellow": {
                    cString = "#ffffe0";
                }
                break;
                case "yellow": {
                    cString = "#ffff00";
                }
                break;
                case "olive": {
                    cString = "#808000";
                }
                break;
                case "darkkhaki": {
                    cString = "#bdb76b";
                }
                break;
                case "lemonchiffon": {
                    cString = "#fffacd";
                }
                break;
                case "palegoldenrod": {
                    cString = "#eee8aa";
                }
                break;
                case "khaki": {
                    cString = "#f0e68c";
                }
                break;
                case "gold": {
                    cString = "#ffd700";
                }
                break;
                case "cornsilk": {
                    cString = "#fff8dc";
                }
                break;
                case "goldenrod": {
                    cString = "#daa520";
                }
                break;
                case "darkgoldenrod": {
                    cString = "#b8860b";
                }
                break;
                case "floralwhite": {
                    cString = "#fffaf0";
                }
                break;
                case "oldlace": {
                    cString = "#fdf5e6";
                }
                break;
                case "wheat": {
                    cString = "#f5deb3";
                }
                break;
                case "moccasin": {
                    cString = "#ffe4b5";
                }
                break;
                case "orange": {
                    cString = "#ffa500";
                }
                break;
                case "papayawhip": {
                    cString = "#ffefd5";
                }
                break;
                case "blanchedalmond": {
                    cString = "#ffebcd";
                }
                break;
                case "navajowhite": {
                    cString = "#ffdead";
                }
                break;
                case "antiquewhite": {
                    cString = "#faebd7";
                }
                break;
                case "tan": {
                    cString = "#d2b48c";
                }
                break;
                case "burlywood": {
                    cString = "#deb887";
                }
                break;
                case "bisque": {
                    cString = "#ffe4c4";
                }
                break;
                case "darkorange": {
                    cString = "#ff8c00";
                }
                break;
                case "linen": {
                    cString = "#faf0e6";
                }
                break;
                case "peru": {
                    cString = "#cd853f";
                }
                break;
                case "peachpuff": {
                    cString = "#ffdab";
                }
                break;
                case "sandybrown": {
                    cString = "#f4a460";
                }
                break;
                case "chocolate": {
                    cString = "#d2691e";
                }
                break;
                case "saddlebrown": {
                    cString = "#8b4513";
                }
                break;
                case "seashell": {
                    cString = "#fff5ee";
                }
                break;
                case "sienna": {
                    cString = "#a0522d";
                }
                break;
                case "lightsalmon": {
                    cString = "#ffa07a";
                }
                break;
                case "coral": {
                    cString = "#ff7f50";
                }
                break;
                case "orangered": {
                    cString = "#ff4500";
                }
                break;
                case "darksalmon": {
                    cString = "#e9967a";
                }
                break;
                case "tomato": {
                    cString = "#ff6347";
                }
                break;
                case "mistyrose": {
                    cString = "#ffe4e1";
                }
                break;
                case "salmon": {
                    cString = "#fa8072";
                }
                break;
                case "snow": {
                    cString = "#fffafa";
                }
                break;
                case "lightcoral": {
                    cString = "#f08080";
                }
                break;
                case "rosybrown": {
                    cString = "#bc8f8f";
                }
                break;
                case "indianred": {
                    cString = "#cd5c5c";
                }
                break;
                case "red": {
                    cString = "#ff0000";
                }
                break;
                case "brown": {
                    cString = "#a52a2a";
                }
                break;
                case "firebrick": {
                    cString = "#b22222";
                }
                break;
                case "darkred": {
                    cString = "#8b0000";
                }
                break;
                case "maroon": {
                    cString = "#800000";
                }
                break;
                case "white": {
                    cString = "#ffffff";
                }
                break;
                case "whitesmoke": {
                    cString = "#f5f5f5";
                }
                break;
                case "gainsboro": {
                    cString = "#dcdcdc";
                }
                break;
                case "lightgrey": {
                    cString = "#d3d3d3";
                }
                break;
                case "silver": {
                    cString = "#c0c0c0";
                }
                break;
                case "darkgray": {
                    cString = "#a9a9a9";
                }
                break;
                case "gray": {
                    cString = "#808080";
                }
                break;
                case "dimgray": {
                    cString = "#696969";
                }
                break;
                case "black": {
                    cString = "#000000";
                }
                break;
                default:
                    break;
            }
            if (cString.startsWith("0X")) cString = cString.substring(2);
            if (cString.startsWith("#")) cString = cString.substring(1);
            if (cString.startsWith("rgb(") && cString.endsWith(")")) {
                String[] array = cString.substring("rgb(".length(), cString.length() - 1).split(",");
                return Color.rgb(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
            }
            if (cString.startsWith("rgba(") && cString.endsWith(")")) {
                String[] array = cString.substring("rgba(".length(), cString.length() - 1).split(",");
                return Color.argb((int)(Double.parseDouble(array[3])*255), Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
            }
            if (cString.length() < 6) {
                StringBuilder temp = new StringBuilder();
                for (int i = 0; i < cString.length(); i++) {
                    String chr = cString.substring(i, i + 1);
                    temp.append(chr).append(chr);
                }
                cString = temp.toString();
            }
            if (cString.length() == 6) {
                cString = "ff" + cString;
            }else{
                cString=cString.substring(6)+cString.substring(0,6);
            }
            cString= "#"+cString;
            return Color.parseColor(cString);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /*
     * 颜色转字符串
     *
     * @param color 颜色
     * */
    /*
    public static String toString(Integer color) {
        if (color == 0) {
            return "#00000000";
        }
        String red = Integer.toHexString((color & 0xff0000) >> 16);
        String green = Integer.toHexString((color & 0x00ff00) >> 8);
        String blue = Integer.toHexString((color & 0x0000ff));
        if (red.length() == 1) {
            red = String.format("%1s%2s", 0, red).replace(" ", "");
        }
        if (green.length() == 1) {
            green = String.format("%1s%2s", 0, green).replace(" ", "");
        }
        if (blue.length() == 1) {
            blue = String.format("%1s%2s", 0, blue).replace(" ", "");
        }
        return '#' + red + green + blue;
    }*/

    /**
     * 字符串转颜色
     *
     * @param str 字符串
     * */
    @SuppressWarnings("static-access")
    public static Integer fromString(String str) {
        if(str==null){
            return -1;
        }
        switch (str){
            case "orange":str="#ff8800";break;
            default:break;
        }
        return Color.parseColor(str);
    }

    /**
     * 颜色转RGB
     * @param color
     * @return
     */
    public static String toRGB(Integer color){
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        return "rgb(" + red + "," + green + "," + blue +")";
    }

    /**
     * 颜色转RGBA
     * @param color
     * @return
     */
    public static String toRGBA(Integer color){
        int alpha = (color & 0xff000000) >> 24;
        int red = (color & 0x00ff0000) >> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue = (color & 0x000000ff);
        return "rgba(" + red + "," + green + "," + blue +","+((alpha+256)%256)/255.0+")";
    }
    public static List toRGBs(Integer color){
        final int red = (color & 0xff0000) >> 16;
        final int green = (color & 0x00ff00) >> 8;
        final int blue = (color & 0x0000ff);
        return new ArrayList(){{
            add(red);
            add(green);
            add(blue);
        }};
    }
    public static List toRGBAs(Integer color){

        final  int alpha = (color & 0xff000000) >> 24;
        final  int red = (color & 0x00ff0000) >> 16;
        final  int green = (color & 0x0000ff00) >> 8;
        final  int blue = (color & 0x000000ff);
        return new ArrayList(){{
            add(red);
            add(green);
            add(blue);
            add((alpha+256)%256);
        }};
    }
    public static Integer fromRGBs(List rgb){
        return Color.rgb((int)rgb.get(0),(int)rgb.get(1),(int)rgb.get(2));
    }
    public static Integer fromRGBAs(List rgb){
        return Color.argb((int)rgb.get(3),(int)rgb.get(0),(int)rgb.get(1),(int)rgb.get(2));
    }
}

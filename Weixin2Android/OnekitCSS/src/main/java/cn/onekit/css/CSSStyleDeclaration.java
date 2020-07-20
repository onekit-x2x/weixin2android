package cn.onekit.css;

import android.util.Log;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import cn.onekit.ARRAY;
import cn.onekit.COLOR;
import cn.onekit.TheKit;
import cn.onekit.STRING_;
import cn.onekit.css.core.OnekitCSS;

/**
 * Created by zhangjin on 2017/8/28.
 */

public class CSSStyleDeclaration {

    public String content;
    String _cssText;

    public String cssText() {
        return _cssText;
    }

    public void cssText(String cssText) {
        _cssText = cssText;
    }

    CSSRule parentRule() {
        return null;
    }

    private List<String> propertyNames;
    private HashMap<String, String> propertyValues;
    private HashMap<String, Integer> propertyPriorities;

    ////////////////////////////
    public String margin;
    public String marginTop;
    public String marginRight;
    public String marginBottom;
    public String marginLeft;
    //
    public String padding;
    public String paddingTop;
    public String paddingRight;
    public String paddingBottom;
    public String paddingLeft;
    //
    public String left;
    public String right;
    public String top;
    public String bottom;
    //
    public String border;
    //
    public String borderRadius;
    public String borderTopLeftRadius;
    public String borderTopRightRadius;
    public String borderBottomLeftRadius;
    public String borderBottomRightRadius;
    //
    public String borderWidth;
    public String borderLeftWidth;
    public String borderRightWidth;
    public String borderTopWidth;
    public String borderBottomWidth;
    //
    public String borderColor;
    public String borderLeftColor;
    public String borderRightColor;
    public String borderTopColor;
    public String borderBottomColor;
    //
    public String borderStyle;
    public String borderLeftStyle;
    public String borderRightStyle;
    public String borderTopStyle;
    public String borderBottomStyle;
    //
    public String font;
    public String fontFamily;
    public String fontSize;
    public String fontWeight;
    public String textSizeAdJust;
    //
    public String background;
    public String backgroundColor;
    //
    public String flexDirection;
    public String flexWrap;
    public String justifyContent;
    public String alignItems;
    public String alignContent;
    public String order;
    public String flexGrow;
    public String flexShrink;
    public String flexBasis;
    public String alignSelf;
    //
    public String zIndex;
    public String width;
    public String height;
    public String lineHeight;
    public String display;
    public String position;
    public String boxSizing;
    public String textAlign;
    public String color;
    public String wordwrap;
    public String minWidth;
    public String minHeight;
    public String textTransform;
    public String textSizeAdjust;
    public String maxWidth;
    public String maxHeight;
    public String whiteSpace;
    public String userSelect;
    public String cursor;
    public String overflow;
    public String overflowX;
    public String overflowY;
    public String transform;
    public String transformOrigin;
    public String tapHighlightColor;
    public String textDecoration;
    public  String pointerEvents;
    public  String willChange;
    public String outline;
    public String textOverflow;
    public String opacity;
    public String transition;

    public CSSStyleDeclaration() {
        this.propertyNames = new ArrayList<String>();
        this.propertyValues = new HashMap();
        this.propertyPriorities = new HashMap();

    }
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(new Date().getTime()+"===============\r\n");
        for (String name : this.propertyNames) {
            string.append(String.format("%s: %s\r\n", name, this.propertyValues.get(name)));
        }
        return string.toString();
    }

    public int length() {
        return this.propertyNames.size();
    }

    public int getPropertyPriority(String property) {
        property = property.toLowerCase();
        if (!this.propertyPriorities.containsKey(property)) {
            return 0;
        }
        return this.propertyPriorities.get(property);
    }

    public String getPropertyValue(String property) {
        property = property.toLowerCase();
        if (!this.propertyValues.containsKey(property)) {
            return null;
        }
        return this.propertyValues.get(property);
    }

    public String item(int index) {
        return this.propertyNames.get(index);
    }

    private void _setValue(String value, String propertyName, int priority) {
        if (STRING_.isEmpty(propertyName)) {
            return;
        }
        propertyName =propertyName.toLowerCase();
        int priority0 = this.getPropertyPriority(propertyName);
        if (priority0 > priority) {
            return;
        }
        if (!propertyNames.contains(propertyName)) {
            this.propertyNames.add(propertyName);
        }
        this.propertyValues.put(propertyName, value);
        this.propertyPriorities.put(propertyName, priority);
        //
        TheKit.set(this, value, TheKit.toCamelCase(propertyName));
    }



    public void setProperty(String key, String css, int priority) {
        key = key.toLowerCase();
        String[] prefixes = new String[]{"-webkit-", "-moz-", "-ms-", "-o-"};
        for (String prefix : prefixes) {
            if (key.startsWith(prefix)) {
                key = key.substring(prefix.length());
                break;
            }
        }
        switch (key) {
            case "margin": {
                String[] margins = fixArray(css);
                this._setValue(margins[0], "margin-top", priority);
                this._setValue(margins[1], "margin-right", priority);
                this._setValue(margins[2], "margin-bottom", priority);
                this._setValue(margins[3], "margin-left", priority);
            }
            break;
            case "padding": {
                String[] paddings = fixArray(css);
                this._setValue(paddings[0], "padding-top", priority);
                this._setValue(paddings[1], "padding-right", priority);
                this._setValue(paddings[2], "padding-bottom", priority);
                this._setValue(paddings[3], "padding-left", priority);
            }
            break;
            case "background": {
                String[] array = STRING_.split(css, " ");
                StringBuilder positon = new StringBuilder();
                for (String tmp : array) {
                    String item = tmp.trim();
                    if (item.equals("inherit")) {
                        Log.e("==========", "background " + css);
                        continue;
                    }
                    if (ARRAY.contains(new String[]{"scroll", "fixed"}, item)) {
                        this._setValue(item, "background-attachment", priority);
                        continue;
                    }
                    if (item.startsWith("url(") && item.endsWith(")")) {
                        this._setValue(item, "background-image", priority);
                        continue;
                    }
                    if (ARRAY.contains(new String[]{"top", "bottom", "left", "right", "center"}, item)) {
                        positon.append(String.format("%s", item));
                        continue;
                    }
                    if (ARRAY.contains(new String[]{"repeat", "repeat-x", "repeat-y", "no-repeat"}, item)) {
                        this._setValue(item, "background-repeat", priority);
                        continue;
                    }
                    if (ARRAY.contains(new String[]{"border-box", "padding-box", "content-box"}, item)) {
                        this._setValue(item, "background-origin", priority);
                        continue;
                    }
                    if (ARRAY.contains(new String[]{"border-box", "padding-box", "content-box"}, item)) {
                        this._setValue(item, "background-clip", priority);
                        continue;
                    }
                    String[] temp = OnekitCSS.share.View_H5.getSize(item, item);
                    if (temp != null && temp[0].equals("px")) {
                        this._setValue(item, "background-size", priority);
                        continue;
                    }
                    Integer color = COLOR.parse(item);
                    if (color != null) {
                        this._setValue(item, "background-color", priority);
                    }
                }
                if (positon.length() > 0) {
                    this._setValue(positon.toString().trim(), "background-position", priority);
                }
            }
            break;
            case "border": {
                this._applyStyle_border("top", css, priority);
                this._applyStyle_border("right", css, priority);
                this._applyStyle_border("bottom", css, priority);
                this._applyStyle_border("left", css, priority);
            }
            break;
            case "border-left": {
                this._applyStyle_border("left", css, priority);
            }
            break;
            case "border-right": {
                this._applyStyle_border("right", css, priority);
            }
            break;
            case "border-top": {
                this._applyStyle_border("top", css, priority);
            }
            break;
            case "borderbottom": {
                this._applyStyle_border("bottom", css, priority);
            }
            break;
            case "border-image": {

                String[] array = STRING_.split(css, " ");
                for (String tmp : array) {
                    String item = tmp.trim();
                    if (item.equals("inherit")) {
                        Log.e("=================", "border-image" + css);
                        continue;
                    }
                    if (ARRAY.contains(new String[]{"stretch", "repeat", "round"}, item)) {
                        this._setValue(item, "border-image-repeat", priority);
                        continue;
                    }
                    if (item.startsWith("url(") && item.endsWith(")")) {
                        this._setValue(item, "border-image-source", priority);
                        continue;
                    }
                    String[] temp = OnekitCSS.share.View_H5.getSize(key, item);
                    if (temp != null && temp[0].equals("px")) {
                        this._setValue(item, "border-image-slice", priority);
                        continue;
                    }
                    Integer color = COLOR.parse(item);
                    if (color != null) {
                        this._setValue(item, "border-image-color", priority);
                    }
                }
            }
            break;
            case "border-radius": {
                String[] csses = fixArray(css);
                this._setValue(csses[0], "border-top-left-radius", priority);
                this._setValue(csses[1], "border-top-right-radius", priority);
                this._setValue(csses[2], "border-bottom-left-radius", priority);
                this._setValue(csses[3], "border-bottom-right-radius", priority);
            }
            break;
            case "border-color": {
                String[] csses = fixArray(css);
                this._setValue(csses[0], "border-top-color", priority);
                this._setValue(csses[1], "border-right-color", priority);
                this._setValue(csses[2], "border-bottom-color", priority);
                this._setValue(csses[3], "border-left-color", priority);
            }
            break;
            case "border-style": {
                String[] csses = fixArray(css);
                this._setValue(csses[0], "border-top-style", priority);
                this._setValue(csses[1], "border-right-style", priority);
                this._setValue(csses[2], "border-bottom-style", priority);
                this._setValue(csses[3], "border-left-style", priority);
            }
            break;
            case "border-width": {
                String[] csses = fixArray(css);
                this._setValue(csses[0], "border-top-width", priority);
                this._setValue(csses[1], "border-right-width", priority);
                this._setValue(csses[2], "border-bottom-width", priority);
                this._setValue(csses[3], "border-left-width", priority);
            }
            break;
            case "flex": {
                if (!css.equals("none")) {
                    String[] array = STRING_.split(css, " ");
                    for (int i = 0; i < array.length; i++) {
                        String tmp = array[i];
                        String item = tmp.trim();
                        switch (i) {
                            case 0:
                                this._setValue(item, "flex-grow", priority);
                                break;
                            case 1:
                                this._setValue(item, "flex-shrink", priority);
                                break;
                            case 2:
                                this._setValue(item, "flex-basis", priority);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            break;
            case "flex-flow": {
                String[] array = STRING_.split(css, " ");
                for (int i = 0; i < array.length; i++) {
                    String tmp = array[i];
                    String item = tmp.trim();
                    switch (i) {
                        case 0:
                            this._setValue(item, "flex-direction", priority);
                            break;
                        case 1:
                            this._setValue(item, "flex-wrap", priority);
                            break;
                        default:
                            break;
                    }
                }

            }
            case "font":
                _applyStyle_font(css, priority);
                break;
            default: {
                this._setValue(css, key, priority);
            }
        }
    }

    String[] fixArray(String string) {
        String[] array = STRING_.split(string, " ");
        switch (array.length) {
            case 1:
                return new String[]{array[0], array[0], array[0], array[0]};
            case 2:
                return new String[]{array[0], array[1], array[0], array[1]};
            case 3:
                return new String[]{array[0], array[1], array[2], array[1]};
            case 4:
                return array;
            default:
                return array;
        }
    }

    public void _applyStyle_border(String border, String css, int priority) {
        String[] array = STRING_.split(css, " ");
        for (String tmp : array) {
            String item = tmp.trim();
            if (ARRAY.contains(new String[]{"none", "hidden", "dotted", "dashed", "solid", "float", "groove", "ridge", "", "inset", "outset", "inherit"}, item)) {
                this._setValue(item, String.format("border-%s-style", border), priority);
                continue;
            }
            String[] temp = OnekitCSS.share.View_H5.getSize(border, item);
            if (temp != null && temp[0].equals("px")) {
                this._setValue(item, String.format("border-%s-width", border), priority);
                continue;
            }
            Integer color = COLOR.parse(item);
            if (color != null) {
                this._setValue(item, String.format("border-%s-color", border), priority);
            }
        }
    }

    public void _applyStyle_font(String css, int priority) {
        String[] array = STRING_.split(css, " ");
        for (String tmp : array) {
            String item = tmp.trim();

            String[] temp = OnekitCSS.share.View_H5.getSize("font-size", item);
            if (temp != null && temp[0].equals("px")) {
                this._setValue(item, "font-size", priority);
                continue;
            }
            this._setValue(item, "font-faminy", priority);
        }
    }

}

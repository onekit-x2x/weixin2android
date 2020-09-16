package cn.onekit.css.core;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import thekit.ARRAY;
import thekit.COLOR;
import cn.onekit.DOM;
import cn.onekit.LITERAL_;
import cn.onekit.OneKit;
import thekit.STRING_;
import cn.onekit.css.CSSStyleDeclaration;

public class View_H5 {

    private OnekitCSS OnekitCSS;

    public View_H5(OnekitCSS OnekitCSS) {
        this.OnekitCSS = OnekitCSS;
    }

    private boolean isQuikeElement(View node) {
        return ARRAY.contains(new String[]{"block", "flex"}, getDisplay(node))
                && ARRAY.contains(new String[]{"static", "relative"}, getPosition(node));
    }


    void initWidth(View node) {

        View parentNode = (View) node.getParent();
        if (getDisplay(node).equalsIgnoreCase("inline") && !getDisplay(parentNode).equalsIgnoreCase("flex")) {
            return;
        }
        CssLayoutParams parentLayoutParams = (CssLayoutParams) parentNode.getLayoutParams();
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        CSSStyleDeclaration style = layoutParams.computedStyle;
        Float measuredWidth = null;
        boolean isQuike = false;
        if (style.width != null) {
            measuredWidth = getSize1("width", node);
        } else if (
                (isQuikeElement(node) && !getDisplay(parentNode).equalsIgnoreCase("flex"))
                        || (getDisplay(parentNode).equalsIgnoreCase("flex")
                        && getFlexDirection(parentNode).contains("column")
                        && getAlignItems(parentNode).equalsIgnoreCase("stretch"))) {
            if (parentLayoutParams.measuredWidth != null) {
                isQuike = true;
                measuredWidth = parentLayoutParams.measuredWidth
                        - getSize2("paddingLeft", parentNode)
                        - getSize2("marginLeft", node)
                        - getSize2("marginRight", node)
                        - getSize2("paddingRight", parentNode);
            }

        }
        if (measuredWidth == null) {
            return;
        }
        if (!isQuike && getBoxSizing(node).equalsIgnoreCase("content-sizing")) {
            measuredWidth += getSize2("paddingLeft", node)
                    + getSize2("paddingRight", node);
        }
        layoutParams.measuredWidth = measuredWidth;
    }

    void initHeight(View node) {
        View parentNode = (View) node.getParent();
        if (getDisplay(node).equalsIgnoreCase("inline") && !getDisplay(parentNode).equalsIgnoreCase("flex")) {
            return;
        }
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        CSSStyleDeclaration style = layoutParams.computedStyle;
        Float measuredHeight = null;
        //
        if (style.height != null) {
            measuredHeight = getSize1("height", node);
        }
        if (measuredHeight == null) {
            return;
        }
        if (getBoxSizing(node).equalsIgnoreCase("content-sizing")) {
            measuredHeight += getSize2("paddingTop", node)
                    + getSize2("paddingBottom", node);
        }
        layoutParams.measuredHeight = measuredHeight;
    }

    void fixWidth(View node, float aw) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.measuredWidth != null) {
            return;
        }
        CSSStyleDeclaration STYLE = layoutParams.computedStyle;
        float measuredWidth = aw;
        if (STYLE.maxWidth != null) {
            Float maxWidth = getSize1("maxWidth", node);
            if (maxWidth != null && maxWidth < measuredWidth) {
                measuredWidth = maxWidth;
            }
        }
        if (STYLE.minWidth != null) {
            Float minWidth = getSize1("minWidth", node);
            if (minWidth != null && minWidth > measuredWidth) {
                measuredWidth = minWidth;
            }
        }
        layoutParams.measuredWidth = measuredWidth
                + getSize2("paddingLeft", node)
                + getSize2("paddingRight", node);
    }

    void fixHeight(View node, float ah) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.measuredHeight != null) {
            return;
        }
        CSSStyleDeclaration STYLE = layoutParams.computedStyle;
        float measuredHeight = ah;
        if (STYLE.maxHeight != null) {
            Float maxHeight = getSize1("maxHeight", node);
            if (maxHeight != null && maxHeight < measuredHeight) {
                measuredHeight = maxHeight;
            }
        }
        if (STYLE.minHeight != null) {
            Float minHeight = getSize1("minHeight", node);
            if (minHeight != null && minHeight > measuredHeight) {
                measuredHeight = minHeight;
            }
        }
        layoutParams.measuredHeight = measuredHeight
                + getSize2("paddingTop", node)
                + getSize2("paddingBottom", node);

    }

    float marginTopBlock(View node, boolean needCompute) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.mt != null) {
            return layoutParams.mt;
        }
        float mt = getSize2("marginTop", node);
        if (mt <= 0) {
            return mt;
        }
        View v = node;
        float mt0 = mt;
        boolean needCompute2 = false;
        CssLayoutParams vLayoutParams = (CssLayoutParams) v.getLayoutParams();
        while (true) {
            boolean a = !DOM.isRoot((View) v.getParent());
            boolean b = getDisplay((View) v.getParent()).equalsIgnoreCase("block");
            int c = DOM.index(((ViewGroup) v.getParent()).getChildAt(0));
            int d = DOM.index(node);
            if (!a || !b || !(c == d)) {
                break;
            }
            mt = Math.max(mt, getSize2("marginTop", v));
            vLayoutParams = (CssLayoutParams) v.getLayoutParams();
            vLayoutParams.mt = 0f;
            mt0 = 0;
            needCompute2 = true;
            v = (View) v.getParent();
        }
        vLayoutParams.mt = mt;
        if (needCompute && needCompute2) {
            layoutParams.measuredWidth = null;
            layoutParams.measuredHeight = null;
            initWidth(v);
            initHeight(v);
            OnekitCSS.View_measure.measure(v);
        }
        return mt0;
    }

    float marginBlockBlock(View node, int index) {
        float marginTop = getSize2("marginTop", node);
        if (index < 1) {
            return marginTop;
        }
        ViewGroup parentNode = (ViewGroup) node.getParent();
        int index2 = 0;
        for (int i = 1; i < parentNode.getChildCount() - 1; i++) {
            View child = (View) parentNode.getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            if (index2 == index - 1) {
                float marginBottom = getSize2("marginBottom", child);
                if (marginTop > marginBottom) {
                    return marginTop - marginBottom;
                } else {
                    return 0;
                }
            }
            index2++;
        }
        return marginTop;
    }

    void computeAbsoluted(View node, boolean reverseH, boolean reverseV) {
        View parent = (View) node.getParent();
        CssLayoutParams parentLayoutParams = (CssLayoutParams) parent.getLayoutParams();
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
       /* if(getPosition(parent).equals("")){
            Log.e("=================","[Absoluted parent]");
        }*/
        float w = layoutParams.measuredWidth;
        float h = layoutParams.measuredHeight;
        //
        float ml = getSize2("marginLeft", node);
        float mr = getSize2("marginRight", node);
        float mt = getSize2("marginTop", node);
        float mb = getSize2("marginBottom", node);
        //
        Float l = getSize1("left", node);
        Float r = getSize1("right", node);
        Float t = getSize1("top", node);
        Float b = getSize1("bottom", node);

        if (l != null) {
            layoutParams.x = l + ml;
            if (r != null) {
                layoutParams.measuredWidth = parentLayoutParams.measuredWidth - l - r;
            }
        } else if (r != null) {
            layoutParams.x = parentLayoutParams.measuredWidth - r - w;
        } else {
            layoutParams.x = reverseH ? (parentLayoutParams.measuredWidth - w - mr) : ml;
        }

        if (t != null) {
            layoutParams.y = t + mt;
            if (b != null) {
                layoutParams.measuredHeight = parentLayoutParams.measuredHeight - t - b;
            }
        } else if (b != null) {
            layoutParams.y = parentLayoutParams.measuredHeight - b - h;
        } else {
            layoutParams.y = reverseV ? (parentLayoutParams.measuredHeight - h - mt) : mb;
        }
    }

    public String[] getSize(String style, String value) {
        // style=style.toLowerCase().replace("-","");
        if (null == value) {
            return ARRAY.contains(new String[]{"marginLeft", "marginRight", "marginTop", "marginBottom"}, style) ? new String[]{"px", 0 + ""} : null;
        }
        value = value.trim();
        if (value.equals("auto")) {
            return null;
        }
        if (value.equals("0")) {
            return new String[]{"px", 0 + ""};
        }
        final String[] units = new String[]{"rpx", "px", "em", "%"};
        for (String item : units) {
            if (!value.endsWith(item)) {
                continue;
            }
            String unit = item;
            float v = Float.parseFloat(value.substring(0, value.length() - unit.length()));
            if (unit.equals("rpx")) {
                unit = "px";
                v = v * OnekitCSS.window.getWidth() / 1500;
            }
            return new String[]{unit, v + ""};
        }
        return null;
    }

    Float getSize1(String style, View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        //  style = style.toLowerCase().replace("-","");
       /* if (ARRAY.contains(new String[]{"float"}, style)) {
            style = style.toUpperCase();
        } else {
            style = style.toLowerCase();
        }*/
        String value = (String) OneKit.get(layoutParams.computedStyle, style);
        String[] temp = getSize(style, value);
        return getSize1_(style, node, temp);
    }

    private Float getSize1_(String style, View node, String[] temp) {
        if (null == temp) {
            if (style.equals("lineHeight")) {
                temp = new String[]{"%", "103"};
            } else if (style.equals("fontSize")) {
                temp = new String[]{"px", String.valueOf(FONT_SIZE)};
            } else  if (style.equals("width")) {
                temp = new String[]{"%", "100"};
            } else {
                return null;
            }
        }
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();

        String unit = temp[0];
        float result = Float.parseFloat(temp[1]);
        switch (unit) {
            case "px": {
                return result;
            }
            case "%": {
                View parentNode;
                CssLayoutParams parentLayoutParams = null;
                if (!DOM.isRoot(node)) {
                    parentNode = (View) node.getParent();
                    parentLayoutParams = (CssLayoutParams) parentNode.getLayoutParams();
                } else {
                    parentNode = null;
                }
                float v;
                if (style.equals("lineHeight")) {
                    if (!DOM.isRoot(node)) {
                        assert parentNode != null;
                        v = getFontSize(parentNode);
                    } else {
                        return getFontSize(node);
                    }
                } else {
                    switch (style) {
                        case "width":
                        case "minWidth":
                        case "maxWidth": {
                            if (!DOM.isRoot(node)) {
                                if (getSize1("width", parentNode) == null && !isQuikeElement(parentNode)) {
                                    return null;
                                }
                                assert parentNode != null;
                                v = parentLayoutParams.measuredWidth
                                        - getSize2("paddingLeft", parentNode)
                                        - getSize2("paddingRight", parentNode);
                            } else if (layoutParams.computedStyle.width != null) {
                                v = OnekitCSS.window.getWidth();
                            } else {
                                return null;
                            }
                            if (layoutParams.measuredWidth != null) {
                                switch (style) {
                                    case "minWidth":
                                        v = Math.max(v, layoutParams.measuredWidth);
                                        break;
                                    case "maxWidth":
                                        v = Math.min(v, layoutParams.measuredWidth);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            break;
                        }
                        case "height":
                        case "minHeight":
                        case "maxHeight": {
                            if (!DOM.isRoot(node)) {
                                assert parentNode != null;
                                if (parentLayoutParams.measuredHeight == null) {
                                    return null;
                                }
                                v = parentLayoutParams.measuredHeight
                                        - getSize2("paddingTop", parentNode)
                                        - getSize2("paddingBottom", parentNode);
                            } else if (layoutParams.computedStyle.height != null) {
                                v = OnekitCSS.window.getHeight();
                            } else {
                                return null;
                            }
                            if (layoutParams.measuredHeight != null) {
                                switch (style) {
                                    case "minHeight":
                                        v = Math.max(v, layoutParams.measuredHeight);
                                        break;
                                    case "maxHeight":
                                        v = Math.min(v, layoutParams.measuredHeight);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        break;

                        case "flexBasis": {
                            if (!DOM.isRoot(node)) {
                                assert parentNode != null;
                                if (parentLayoutParams.measuredWidth == null) {
                                    return null;
                                }
                                v = parentLayoutParams.measuredWidth
                                        - getSize2("paddingLeft", parentNode)
                                        - getSize2("paddingRight", parentNode);
                            } else if (layoutParams.computedStyle.width != null) {
                                v = OnekitCSS.window.getWidth();
                            } else {
                                return null;
                            }
                        }
                        break;
                        case "fontSize": {
                            v = getFontSize(node);
                        }
                        break;
                        case "left":
                        case "right": {
                            assert parentNode != null;
                            v = parentLayoutParams.measuredWidth;
                        }
                        break;
                        case "top":
                        case "bottom": {
                            assert parentNode != null;
                            v = parentLayoutParams.measuredHeight;
                        }
                        break;
                        case "borderTopLeftRadius":
                        case "borderTopRightRadius":
                        case "borderBottomLeftRadius":
                        case "borderBottomRightRadius": {
                            v = Math.min(layoutParams.measuredWidth, layoutParams.measuredHeight);
                        }
                        break;
                        case "selfWidth": {
                            v = layoutParams.measuredWidth;
                        }
                        break;
                        case "selfHeight": {
                            v = layoutParams.measuredHeight;
                        }
                        break;
                        default: {
                            v = 0;
                            Log.e("===========", "[getSize1]" + style);
                        }
                    }
                }
                return (float) (v * result / 100);
            }
            case "em":
                View parentNode = (View) node.getParent();
                float v = getSize2("fontSize", parentNode);
                return (v * result);
            case "":
                switch (style) {
                    case "lineHeight":
                        return result * getFontSize(node);
                    case "":
                        break;
                    default:
                        return result;
                }
            default: {
                Log.e("============", "[getSize1]" + unit);
                return 0f;
            }
        }
    }

    float getSize2(String style, View node) {
        Float size = getSize1(style, node);
        return (size != null ? size : 0);
    }

    float getSize2_(String style, View node, String[] temp) {
        Float size = getSize1_(style, node, temp);
        return (size != null ? size : 0);
    }

    public String getDisplay(View node) {
        if (node instanceof LITERAL_) {
            return "inline";
        }
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        String display = layoutParams.computedStyle.display;
        if (display != null) {
            return display;
        }
        /*String tagName = node.getClass().getSimpleName().toUpperCase();
        if (ARRAY.contains(new String[]{"TEXT"}, tagName)) {
            display = "inline";
        }else if (ARRAY.contains(new String[]{"PAGE","CANVAS","AUDIO","WEB_VIEW","FORM", "VIEW", "TEXTAREA", "BUTTON", "NAVIGATOR", "SCROLL_VIEW", "SWIPER", "SWIPER_ITEM"}, tagName)) {
            display = "block";
        } else if (ARRAY.contains(new String[]{"PICKER_VIEW", "PICKER_VIEW_COLUMN", "CHECKBOX_GROUP", "RADIO_GROUP", "LABEL", "IMAGE", "VIDEO", "ICON", "PROGRESS"}, tagName)) {
            display = "inline-block";
        }else if (node instanceof FORM_ITEM) {
            display = "inline-block";
        } else if (!node.getClass().getName().startsWith("demo.layout.ui.")) {
            display = "inline-block";
        } else {
            Log.e("[getDisplay]", tagName);
            display = "block";
        }
        return display;*/
        return "inline";
    }

    private String getBoxSizing(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.boxSizing != null) {
            return layoutParams.computedStyle.boxSizing;
        }
        return "content-sizing";
    }

    String getPosition(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.position != null) {
            return layoutParams.computedStyle.position;
        }
        return "static";
    }

    String getTextAlign(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.textAlign != null) {
            return layoutParams.computedStyle.textAlign;
        }
        return "start";
    }

    public String getColor(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.color != null) {
            return layoutParams.computedStyle.color;
        }
        return "black";
    }

    String getBackgroundColor(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.backgroundColor != null) {
            return layoutParams.computedStyle.backgroundColor;
        }
        return "transparent";
    }

    String getFlexDirection(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.flexDirection != null) {
            return layoutParams.computedStyle.flexDirection;
        }
        return "row";
    }


    Float getZIndex(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if(layoutParams.computedStyle.zIndex!=null) {
            return Float.parseFloat(layoutParams.computedStyle.zIndex);
        }else{
            return null;
        }
    }

    String getFlexWrap(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.flexWrap != null) {
            return layoutParams.computedStyle.flexWrap;
        }
        return "nowrap";
    }

    String getJustifyContent(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.justifyContent != null) {
            return layoutParams.computedStyle.justifyContent;
        }
        return "flex-start";
    }

    String getAlignItems(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.alignItems != null) {
            return layoutParams.computedStyle.alignItems;
        }
        return "stretch";
    }

    String getAlignContent(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.alignContent != null) {
            return layoutParams.computedStyle.alignContent;
        }
        return "stretch";
    }

    float getOrder(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.order != null) {
            return Float.parseFloat(layoutParams.computedStyle.order);
        }
        return 0;
    }

    float getFlexGrow(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();

        if (layoutParams.computedStyle.flexGrow != null) {
            return Float.parseFloat(layoutParams.computedStyle.flexGrow);
        }

        return 0;
    }

    float getFlexShrink(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.flexShrink != null) {
            return Float.parseFloat(layoutParams.computedStyle.flexShrink);
        }
        return 1;
    }

    Float getFlexBasis(View node) {
        return getSize1("flexBasis", node);
    }

    String getAlignSelf(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.alignSelf != null) {
            return layoutParams.computedStyle.alignSelf;
        }
        return "auto";
    }

    String getBorderStyle(View node, String border) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        String style = String.format("border%sStyle", STRING_.firstUpper(border));
        if (OneKit.get(layoutParams.computedStyle, style) != null) {
            return (String) OneKit.get(layoutParams.computedStyle, style);
        }
        return "none";
    }

    Integer getBorderColor(View node, String border) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        String style = String.format("border%sColor", STRING_.firstUpper(border));
        if (OneKit.get(layoutParams.computedStyle, style) != null) {
            return COLOR.parse((String) OneKit.get(layoutParams.computedStyle, style));
        }
        return Color.TRANSPARENT;
    }

    float getBorderWidth(View node, String border) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        String style = String.format("border%sWidth", STRING_.firstUpper(border));
        if (OneKit.get(layoutParams.computedStyle, style) != null) {
            return getSize2(style, node);
        }
        return 0;
    }

    float getBorderRadius(View node, String border) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        String style = String.format("border%sRadius", STRING_.firstUpper(border));
        if (OneKit.get(layoutParams.computedStyle, style) != null) {
            return getSize2(style, node);
        }
        return 0;
    }

    /*
      Size getTextSize(String text ,Font font,Size box)
    {
        return [text boundingRectWithSize:box
        options:0
        attributes:@{NSFontAttributeName:font} context:nil].size;
    }*/
    static final float FONT_SIZE = 16;

    float getFontSize(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (null != layoutParams.computedStyle.fontSize) {
            return getSize2("fontSize", node);
        }
        return FONT_SIZE;

    }

    float getLineHeight(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        String lineHeight = layoutParams.computedStyle.lineHeight;
        String[] temp = getSize("lineHeight", lineHeight);
        return getSize2_("lineHeight", node, temp);
    }

    String getTextTransform(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.textTransform != null) {
            return layoutParams.computedStyle.textTransform;
        }
        return null;
    }

    String getWhiteSpace(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.whiteSpace != null) {
            return layoutParams.computedStyle.whiteSpace;
        }
        return "normal";
    }

    String getContent(View node) {
        CssLayoutParams layoutParams = (CssLayoutParams) node.getLayoutParams();
        if (layoutParams.computedStyle.content != null) {
            return layoutParams.computedStyle.content;
        }
        return null;
    }
}

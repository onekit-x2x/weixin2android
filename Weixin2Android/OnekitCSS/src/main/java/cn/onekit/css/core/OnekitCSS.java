package cn.onekit.css.core;

import android.util.Size;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


import cn.onekit.thekit.COLOR;
import cn.onekit.core.LITERAL_;
import cn.onekit.core.OneKit;
import cn.onekit.css.CSSRule;
import cn.onekit.css.CSSStyleDeclaration;
import cn.onekit.css.CSSStyleRule;
import cn.onekit.css.CSSStyleSheet;
import cn.onekit.thekit.ARRAY;
import cn.onekit.thekit.STRING_;

import static cn.onekit.thekit.Android.dp2px;

public class OnekitCSS {

    public static OnekitCSS share;
    public Size window;
    static CSS2JSON CSS2JSON = new CSS2JSON();
    public View_H5 View_H5;
    View_CSS View_CSS;
    View_measure View_measure;
    View_layout View_layout;
    List<CSSStyleSheet> globalStyleSheets = new ArrayList();
    String prev;

    public OnekitCSS(Size window, String prev) {
        this.window = window;
        View_H5 = new View_H5(this);
        View_CSS = new View_CSS(this);
        View_measure = new View_measure(this);
        View_layout = new View_layout(this);
        this.prev = prev;
    }

    public void init(String[] urls) {
        for (String url : urls) {
            CSSStyleSheet styleSheet = new CSSStyleSheet(prev, CSS2JSON);
            styleSheet.load(OneKit.currentUrl, url);
            globalStyleSheets.add(styleSheet);

        }
    }

    public void run(View rootView, String[] pageStyleSheetUrls) {
        //LOG LOG =new LOG();
        List<CSSStyleSheet> pageStyleSheets = new ArrayList();
        for (String pageStyleSheetUrl : pageStyleSheetUrls) {
            CSSStyleSheet styleSheet = new CSSStyleSheet(prev, CSS2JSON);
            styleSheet.load(OneKit.currentUrl, pageStyleSheetUrl);
            pageStyleSheets.add(styleSheet);
        }
        //LOG.add("styleSheet.load");
        css_run(rootView, null, pageStyleSheets);
        //LOG.add("css_run");
        css_measure(rootView);
        //LOG.add("css_measure");
        css_layout(rootView);
        //LOG.add("css_layout");
    }

    public void css_measure(View SELF) {
        View_measure.measure(SELF);
    }

    private void css_run(View SELF, CSSStyleDeclaration parentStyle, List<CSSStyleSheet> pageStyleSheets) {
  //   LOG LOG = new LOG(SELF.hashCode()+"");
        CSSStyleDeclaration computedStyle = new CSSStyleDeclaration();
        if (SELF.getLayoutParams() instanceof CssLayoutParams) {
            CssLayoutParams layoutParams = (CssLayoutParams) SELF.getLayoutParams();
            //
            _loadParentStyle(SELF, parentStyle, computedStyle);
            for (CSSStyleSheet styleSheet : globalStyleSheets) {
                _loadStyleSheet(SELF, styleSheet, computedStyle);
            }
            for (CSSStyleSheet styleSheet : pageStyleSheets) {
                _loadStyleSheet(SELF, styleSheet, computedStyle);
            }
            String style = ((CssLayoutParams) SELF.getLayoutParams()).style;
            if (style != null) {
                String[] styles = style.trim().split(";");
                for (String temp : styles) {
                    String[] pair = temp.trim().split(":");
                    if (pair.length < 2) {
                        continue;
                    }
                    String property = pair[0].trim();
                    String value = pair[1].trim();
                    int p = value.indexOf("!important");
                    int priority = 1000;
                    if (p >= 0) {
                        value = value.substring(0, p).trim();
                        priority += 10000;
                    }
                    computedStyle.setProperty(property, value, priority);
                }

            }
            layoutParams.computedStyle = computedStyle;
            SELF.setLayoutParams(layoutParams);

            if (View_H5.getDisplay(SELF).equalsIgnoreCase("none")) {
                return;
            }
        }
       // LOG.add("view");
        if (!(SELF instanceof ViewGroup)) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) SELF;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);


            css_run(child, computedStyle, pageStyleSheets);
        //    LOG.add("view "+i);
        }
    }

    private void _loadStyleSheet(View SELF, CSSStyleSheet styleSheet, CSSStyleDeclaration computedStyle) {
        if (styleSheet == null) {
            return;
        }
        //
        List<CSSRule> cssRules = styleSheet.cssRules();
        for (int r = 0; r < cssRules.size(); r++) {
            CSSStyleRule rule = (CSSStyleRule) cssRules.get(r);
            String selectorText = rule.selectorText();
            if (STRING_.isEmpty(selectorText)) {
                continue;
            }
            int priority = View_CSS.matchSelector(selectorText, rule.style(), SELF);
            if (priority >= 0) {
                _loadStyle(SELF, rule.style(), priority, computedStyle);
            }
        }
    }

    private void _loadParentStyle(View node, CSSStyleDeclaration parentStyle, CSSStyleDeclaration computedStyle) {
        if (null == parentStyle) {
            return;
        }

        for (int i = 0; i < parentStyle.length(); i++) {
            String name = parentStyle.item(i);
            if (name.startsWith("font")
                    || ARRAY.contains(new String[]{"color", "text-align", "line-height"}, name)) {
                String css = parentStyle.getPropertyValue(name);
                int priority = parentStyle.getPropertyPriority(name);
                computedStyle.setProperty(name, css, priority);
            }
        }

    }

    static void _loadStyle(View SELF, CSSStyleDeclaration style, int priority, CSSStyleDeclaration computedStyle) {
        if (style == null) {
            return;
        }
        for (int i = 0; i < style.length(); i++) {
            String name = style.item(i);
            String value = style.getPropertyValue(name);
            computedStyle.setProperty(name, value, priority);
        }
    }

    public CSSStyleDeclaration getComputedStyle(View node) {
        return ((CssLayoutParams) node.getLayoutParams()).computedStyle;
    }

    public void css_layout(View view) {

        View_layout.h5css(view);
        if (!(view instanceof ViewGroup)) {
            return;
        }
        ViewGroup SELF = (ViewGroup) view;
        for (int i = 0; i < SELF.getChildCount(); i++) {
            View child = SELF.getChildAt(i);
            if (!(child.getLayoutParams() instanceof CssLayoutParams)) {
                continue;
            }
            if (View_H5.getDisplay(child).equalsIgnoreCase("none")) {
                continue;
            }
            CssLayoutParams childLayoutParams = (CssLayoutParams) child.getLayoutParams();
            if (child instanceof LITERAL_) {
                LITERAL_ literal = (LITERAL_) child;
                literal.view().setTextColor(COLOR.parse(View_H5.getColor(SELF)));
                childLayoutParams.leftMargin = dp2px(childLayoutParams.x);
                childLayoutParams.topMargin = dp2px(childLayoutParams.y);
                childLayoutParams.width = dp2px(childLayoutParams.measuredWidth);
                childLayoutParams.height = dp2px(childLayoutParams.measuredHeight);
                child.setLayoutParams(childLayoutParams);
                continue;
            }
            if (child instanceof BeforeAfter) {
                BeforeAfter beforeAfter = (BeforeAfter) child;
                beforeAfter.setTextColor(COLOR.parse(View_H5.getColor(SELF)));
            }
            View node = (View) child;
            childLayoutParams.leftMargin = dp2px(childLayoutParams.x);
            childLayoutParams.topMargin = dp2px(childLayoutParams.y);
            childLayoutParams.width = dp2px(childLayoutParams.measuredWidth);
            childLayoutParams.height = dp2px(childLayoutParams.measuredHeight);
            //Log.e("[CSS]"+new Date().getTime()+" "+child.getClass().getSimpleName(),childLayoutParams.leftMargin+" "+childLayoutParams.topMargin+" "+childLayoutParams.width+" "+childLayoutParams.height);

            child.setLayoutParams(childLayoutParams);
            css_layout(node);
        }
    }
}

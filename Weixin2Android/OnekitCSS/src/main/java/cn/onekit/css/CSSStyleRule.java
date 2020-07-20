package cn.onekit.css;


public class CSSStyleRule extends CSSRule {
    String _selectorText;

    public String selectorText() {
        return _selectorText;
    }

    public void selectorText(String selectorText) {
        _selectorText = selectorText;
    }

    CSSStyleDeclaration _style;

    public CSSStyleDeclaration style() {
        return _style;
    }

    public CSSStyleRule() {
        _style = new CSSStyleDeclaration();
    }
}

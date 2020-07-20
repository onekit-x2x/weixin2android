package cn.onekit.css;

public abstract class CSSRule {
    String _cssText;
   public String cssText(){
       return _cssText;
   }
    public  void cssText(String cssText){
       _cssText=cssText;
    }
   public CSSRule parentRule(){
        return null;
   }
    CSSStyleSheet parentStyleSheet(){
       return null;
    }
}

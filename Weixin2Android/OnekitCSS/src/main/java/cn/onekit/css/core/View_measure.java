package cn.onekit.css.core;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.onekit.DOM;
import cn.onekit.LITERAL_;

import static thekit.android.Android.dp2px;
import static thekit.android.Android.px2dp;

public class View_measure {
    private OnekitCSS OnekitCSS;
    public View_measure(OnekitCSS OnekitCSS){
        this.OnekitCSS=OnekitCSS;
    }
    public  void measure(View SELF) {
        CssLayoutParams layoutParams = (CssLayoutParams) SELF.getLayoutParams();
        if ("none".equals(OnekitCSS.View_H5.getDisplay(SELF))) {
            layoutParams.measuredWidth=null;
            layoutParams.measuredHeight=null;
            return;
        }

        if (SELF instanceof BeforeAfter ){
            if(OnekitCSS.View_H5.getDisplay(SELF).equalsIgnoreCase("inline")) {
                BeforeAfter beforeAfter = (BeforeAfter) SELF;
                OnekitCSS.View_measure.literal(SELF, beforeAfter);
            }
            return;
        }
        if (DOM.isRoot(SELF)) {
            layoutParams.measuredWidth=px2dp(OnekitCSS.window.getWidth());
            layoutParams.measuredHeight=null;
        }
        if(layoutParams.before!=null && layoutParams.before.getParent()==null){
            if(SELF instanceof ViewGroup){
                layoutParams.before.run();
                ((ViewGroup) SELF).addView(layoutParams.before,0);
            }
        }
        if(layoutParams.after!=null&& layoutParams.after.getParent()==null){
            if(SELF instanceof ViewGroup){
                layoutParams.after.run();
                ((ViewGroup) SELF).addView(layoutParams.after);
            }
        }
        String DISPLAY = OnekitCSS.View_H5.getDisplay(SELF);
        //
        if (DISPLAY.equals("flex")) {
            String flexDirection = OnekitCSS.View_H5.getFlexDirection(SELF);
            switch (flexDirection) {
                case "row":
                    new View_flexH(OnekitCSS).measure( SELF, false);
                    break;
                case "row-reverse":
                    new View_flexH(OnekitCSS).measure( SELF, true);
                    break;
                case "column":
                    new View_flexV(OnekitCSS).measure( SELF, false);
                    break;
                case "column-reverse":
                    new View_flexV(OnekitCSS).measure( SELF, true);
                    break;
                default:
                    break;
            }
        } else {
            new View_normal(OnekitCSS).measure( SELF);
        }
    }

    public  void literal(View parent, TextView literal) {
        String text = (String) literal.getText();
        CssLayoutParams layoutParams = (CssLayoutParams) literal.getLayoutParams();
        //
        float fontSize = OnekitCSS.View_H5.getFontSize(parent);
        literal.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize);
        //
        String whiteSpace = OnekitCSS.View_H5.getWhiteSpace(parent);
        if(whiteSpace.equals("nowrap")){
            literal.setSingleLine(true);
        }else{
            literal.setSingleLine(false);
        }
        //
        String textAlign = OnekitCSS.View_H5.getTextAlign(parent);
        switch (textAlign){
            case "left":
            case "start": literal.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);break;
            case "center": literal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);break;
            case "right":
            case "end": literal.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);break;
            default:
                Log.e("=====================",textAlign);break;
        }
        //
        if(text!=null) {
            text = text .replace("\\n","\r\n");
            String textTransform = OnekitCSS.View_H5.getTextTransform(parent);
            if (textTransform != null) {
                switch (textTransform) {
                    case "uppercase":
                        text = text.toUpperCase();
                        break;
                    case "lowercase":
                        text = text.toLowerCase();
                        break;
                    case "capitalize":
//                     literal.setText(((String)literal.getText()).toLowerCase().replace(/\b([\w|']+)\b/g, FUNC1 (word) {
//                      return word.replace(word.charAt(0), word.charAt(0).toUpperCase());
//                      });
//                    break;
                    default:
                        break;
                }
            }
            literal.setText(text);
        }
        float k = OnekitCSS.View_H5.getLineHeight(parent)/ OnekitCSS.View_H5.getFontSize(parent);
        literal.setLineSpacing(0,k);
        View measureView = parent;
        CssLayoutParams parentLayoutParams = (CssLayoutParams)measureView.getLayoutParams();
        while(parentLayoutParams.measuredWidth==null){
            measureView = (View)measureView.getParent();
            parentLayoutParams = (CssLayoutParams)measureView.getLayoutParams();
        }
        int maxWidth = dp2px(parentLayoutParams.measuredWidth);
        int widthSpec = View.MeasureSpec.makeMeasureSpec(maxWidth, View.MeasureSpec.AT_MOST);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        literal.measure(widthSpec, heightSpec);
        float w = px2dp(literal.getMeasuredWidth());
        float h = px2dp(literal.getMeasuredHeight());
        /*if(literal.getLineCount()==1){
            h*=k;
        }*/
        //Log.e("============",w+" "+h);
        layoutParams.measuredWidth= w;
        layoutParams.measuredHeight=h;
    }
}

package cn.onekit.css.core;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.onekit.thekit.ARRAY;
import cn.onekit.core.DOM;
import cn.onekit.core.LITERAL_;
import cn.onekit.thekit.STRING_;
import cn.onekit.core.OneKit;
import cn.onekit.css.CSSStyleDeclaration;

public class View_CSS {
    OnekitCSS OnekitCSS;
View_CSS(OnekitCSS OnekitCSS){
    this.OnekitCSS=OnekitCSS;
}
    public  int matchSelector(String selectorText, CSSStyleDeclaration style, View SELF) {
        if (STRING_.isEmpty(selectorText)) {
            return -1;
        }
        if (selectorText.trim().equals("*")) {
            return 0;
        }
        String[] allSelectors = STRING_.split(selectorText, ",");
        for (String s : allSelectors) {
            String allSelector = s.trim();
            while (allSelector.contains(": ")){
                allSelector = allSelector.replace(": ",":");
            }
            int priority = checkBrotherSelector(allSelector, style, SELF);
            if (priority >= 0) {
                return priority;
            }
        }

        return -1;
    }
    private   int checkBrotherSelector(String aString, CSSStyleDeclaration style, View SELF) {
        String flag;
        if (aString.indexOf(">") > 0) {
            flag = ">";
            aString = aString.replaceAll(">", " ");
        } else if (aString.indexOf("+") > 0) {
            flag = "+";
            aString = aString.replace("+", " ");
        } else if (aString.indexOf("~") > 0) {
            flag = "~";
            aString = aString.replace("~", " ");
        }else{
            flag = " ";
        }
        //
        String[] brothers;
        if (aString.indexOf(" ") > 0) {
            brothers = STRING_.split(aString," ");
        } else {
            brothers = new String[]{aString};
        }
        //
       List<String> temp = new ArrayList<>();
        for (String brother : brothers) {
            if (brother==null) {
                continue;
            }
            temp.add(brother);
        }
        brothers = temp.toArray(new String[0]);
        //
        View v = SELF;
        int result = 0;
        int matchs = 0;
        for (int b = brothers.length - 1; b >= 0; b--) {
            String brother = brothers[b];
            int priority = checkSelector( style,brother,v);
            if (priority < 0) {
                if (b<brothers.length-1 && ARRAY.contains(new String[]{" ", "~"},flag)) {
                    while (priority < 0) {
                        switch (flag) {
                            case " ":
                                v = (View) v.getParent();
                                break;
                            case "~":
                                do {
                                    v = (View) ((ViewGroup) v.getParent()).getChildAt(DOM.index(v) - 1);
                                } while (v!=null && v instanceof LITERAL_) ;
                                break;
                            default:
                                return -1;

                        }
                        if (v==null || DOM.isRoot(v)) {
                            return -1;
                        }
                        priority = checkSelector( style,brother,v);
                        if (priority >= 0) {
                            break;
                        }
                    }
                    if(priority<0){
                        return priority;
                    }
                }else{
                    return priority;
                }
                return priority;
            }
            result += priority;
            matchs++;


            if (v==null || DOM.isRoot(v)) {
                break;
            }

            switch (flag) {
                case ">":
                case " ":
                    v = (View)v.getParent();
                    break;
                case "+":
                case "~":
                    do {
                        v = (View) ((ViewGroup)v.getParent()).getChildAt(DOM.index(v) - 1);
                    } while (v!=null && v instanceof LITERAL_);
                    break;
                default:
                    v = null;
                    break;

            }
            if (v==null || DOM.isRoot(v)) {
                break;
            }
        }
        if(matchs<brothers.length){
            return -1;
        }else {
            return result;
        }
    }

    private   int checkSelector(CSSStyleDeclaration style, String filter, View SELF) {
        /*filter = filter.replace("::",":");
        String[] filter_beforeafter = filter.split("::");
        String beforeafter = null;
        if (filter_beforeafter.length > 1) {
            beforeafter = filter_beforeafter[1].trim();
        }
        filter = filter_beforeafter[0].trim();
        int result = 0;
        if (filter.endsWith("]")) {
            String attr = filter.substring(filter.lastIndexOf("[") + 1, filter.lastIndexOf("]") - 1);
            result += checkAttributeSelector(attr, SELF);
            if (result < 0) {
                return -1;
            }
            filter = filter.substring(0, filter.lastIndexOf("["));
        }*/
        filter = filter.replace("::",":");
        String[] element_pseudos = filter.split(":");
        int priority = checkClassSelector(element_pseudos[0], SELF);
        if (priority < 0) {
            return priority;
        }
        for(int i=1;i<element_pseudos.length;i++){
            String  pseudo=element_pseudos[i];
            if(!pseudo.equals("before") && !pseudo.equals("after")) {
                priority = checkPseudoElementSelector(style, pseudo, SELF);
                if(priority<0){
                    return priority;
                }
            }else{
                setBeforeafter(pseudo,style,SELF,priority);
                return -1;
            }
        }
       return 10;
    }
    private    int checkPseudoElementSelector( CSSStyleDeclaration style,String filter,View SELF) {

        ViewGroup parentNode;
        /// if(SELF.getParent() instanceof CssELEMENT_){
        parentNode = (ViewGroup) SELF.getParent();
        ///}else{
        //      parentNode=null;
        // }
        int priority=10;
        View child;
        int i, N, n;
        if (filter.equalsIgnoreCase("only-child")) {

            n = 0;
            for (i = 0; i < parentNode.getChildCount(); i++) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
                /*
                if (child instanceof CssBeforeAfter_) {
                    continue;
                }*/
                n++;
            }
            if (n == 1) {
                return priority;
            } else {
                return -1;
            }
        } else if (filter.equalsIgnoreCase("first-child")) {
            if (parentNode == null) {
                return -1;
            }
            n = 0;
            for (i = 0; i < parentNode.getChildCount(); i++) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
                /*
                if (child instanceof CssBeforeAfter_) {
                    continue;
                }*/
                if (DOM.index(((View) child)) == DOM.index(SELF)) {
                    return priority;
                } else {
                    return -1;
                }
            }
        } else if (filter.equalsIgnoreCase("only-of-type")) {
            if (parentNode == null) {
                return -1;
            }
            n = 0;
            for (i = 0; i < parentNode.getChildCount(); i++) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
               /* if (child instanceof CssBeforeAfter_) {
                    continue;
                }*/
                if (child.getClass().getName().equalsIgnoreCase(SELF.getClass().getSimpleName())) {
                    n++;
                }
            }
            if (n == 1) {
                return priority;
            } else {
                return -1;
            }
        } else if (filter.equalsIgnoreCase("last-child")) {
            if (parentNode == null) {
                return -1;
            }
            for (i = parentNode.getChildCount() - 1; i >= 0; i--) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
              /*  if (child instanceof CssBeforeAfter_) {
                    continue;
                }*/
                if (DOM.index(SELF) == DOM.index(((View) child))) {
                    return priority;
                } else {
                    return -1;
                }
            }
        } else if (filter.startsWith("nth-child(")) {
            if (parentNode == null) {
                return -1;
            }
            N = Integer.parseInt(filter.substring("nth-child(".length(), filter.length() - 1));
            n = 0;
            for (i = 0; i < parentNode.getChildCount(); i++) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
                /*
                if (child instanceof CssBeforeAfter_) {
                    continue;
                }*/
                n++;
                if (DOM.index(SELF) == DOM.index(((View) child))) {
                    if (n == N) {
                        return priority;
                    } else {
                        return -1;
                    }
                }
            }
        } else if (filter.startsWith("nth-last-child(")) {
            if (parentNode == null) {
                return -1;
            }
            N = Integer.parseInt(filter.substring("nth-last-child(".length(), filter.length() - 1));
            n = 0;
            for (i = parentNode.getChildCount() - 1; i >= 0; i--) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
                /*if (child instanceof CssBeforeAfter_) {
                    continue;
                }*/
                n++;
                if (DOM.index(SELF) == DOM.index(((View) child))) {
                    if (n == N) {
                        return priority;
                    } else {
                        return -1;
                    }
                }
            }
        } else if (filter.startsWith("nth-of-type(")) {
            if (parentNode == null) {
                return -1;
            }
            N = Integer.parseInt(filter.substring("nth-of-type(".length(), filter.length() - 1));
            n = 0;
            for (i = 0; i < parentNode.getChildCount(); i++) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
               /* if (child instanceof CssBeforeAfter_) {
                    continue;
                }*/
                if (SELF.getClass().getSimpleName().equalsIgnoreCase(child.getClass().getSimpleName())) {
                    n++;
                    if (DOM.index(SELF) == DOM.index(((View) child))) {
                        if (n == N) {
                            return priority;
                        } else {
                            return -1;
                        }
                    }
                }
            }
        } else if (filter.startsWith("nth-last-of-type(")) {
            if (parentNode == null) {
                return -1;
            }
            N = Integer.parseInt(filter.substring("nth-last-of-type(".length(), filter.length() - 1));
            n = 0;
            for (i = parentNode.getChildCount() - 1; i >= 0; i--) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
                /*
                if (child instanceof CssBeforeAfter_) {
                    continue;
                }*/
                if (SELF.getClass().getSimpleName().equalsIgnoreCase(child.getClass().getSimpleName())) {
                    n++;
                    if (DOM.index(SELF) == DOM.index(((View) child))) {
                        if (n == N) {
                            return priority;
                        } else {
                            return -1;
                        }
                    }
                }
            }
        } else if (filter.equalsIgnoreCase("first-of-type")) {
            if (parentNode == null) {
                return -1;
            }
            for (i = 0; i < parentNode.getChildCount(); i++) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
                /*
                if (child instanceof CssBeforeAfter_) {
                    continue;
                }*/
                if (SELF.getClass().getSimpleName().equalsIgnoreCase(child.getClass().getSimpleName())) {
                    if (DOM.index(SELF) == DOM.index(((View) child))) {
                        return 10;
                    } else {
                        return -1;
                    }
                }
            }
        } else if (filter.equalsIgnoreCase("last-of-type")) {
            if (parentNode == null) {
                return -1;
            }
            for (i = parentNode.getChildCount() - 1; i >= 0; i--) {
                child = parentNode.getChildAt(i);
                if (child instanceof LITERAL_) {
                    continue;
                }
                if (child.getClass().getSimpleName().equalsIgnoreCase(SELF.getClass().getSimpleName())) {
                    if (DOM.index(SELF) == DOM.index(((View) child))) {
                        return priority;
                    } else {
                        return -1;
                    }
                }
            }
        } else if (filter.equalsIgnoreCase("root")) {
            return priority;
        } else if (filter.equalsIgnoreCase("empty")) {
            if (((ViewGroup) SELF).getChildCount() <= 1) {
                return priority;
            } else {
                return -1;
            }

        } else {
            if (filter.startsWith("not(")) {
                filter = filter.substring("not(".length(), filter.lastIndexOf(")"));
                return matchSelector(filter, style, SELF) > 0 ? -1 : priority;
            } else {
                Log.e("==========", "_loadStyleSheet-filter " + filter);
                return priority;
            }
        }
        return -1;
    }
    private    void setBeforeafter(String beforeafter, CSSStyleDeclaration style, View SELF,int priority) {

        switch (beforeafter) {
            case "before":{
                CssLayoutParams beforeLayoutParams;
                if(((CssLayoutParams)SELF.getLayoutParams()).before==null){
                    BeforeAfter before = new BeforeAfter(SELF.getContext());
                    beforeLayoutParams = new CssLayoutParams(0,0);
                    beforeLayoutParams.computedStyle.display = "inline";
                    beforeLayoutParams.computedStyle.width = "auto";
                    beforeLayoutParams.computedStyle.height = "auto";
                    before.setLayoutParams(beforeLayoutParams);
                    ((CssLayoutParams)SELF.getLayoutParams()).before=before;
                }else{
                    beforeLayoutParams=(CssLayoutParams)((CssLayoutParams)SELF.getLayoutParams()).before.getLayoutParams();
                }
                OnekitCSS._loadStyle(SELF,style,priority,beforeLayoutParams.computedStyle);
                return ;}
            case "after":{
                CssLayoutParams afterLayoutParams;
                if(((CssLayoutParams)SELF.getLayoutParams()).after==null){
                    BeforeAfter after = new BeforeAfter(SELF.getContext());
                    afterLayoutParams = new CssLayoutParams(0,0);
                    afterLayoutParams.computedStyle.display = "inline";
                    afterLayoutParams.computedStyle.width = "auto";
                    afterLayoutParams.computedStyle.height = "auto";
                    after.setLayoutParams(afterLayoutParams);
                    ((CssLayoutParams)SELF.getLayoutParams()).after=after;
                }else{
                    afterLayoutParams=(CssLayoutParams)((CssLayoutParams)SELF.getLayoutParams()).after.getLayoutParams();
                }
                OnekitCSS._loadStyle(SELF,style,priority,afterLayoutParams.computedStyle);
                return ;}
            default:
                Log.e("[setBeforeafter]",beforeafter);
                return ;
        }
    }

    private   int checkAttributeSelector(String aString,View SELF) {
      //  if(aString.endsWith("]")) {
       //     aString = aString.substring(0, aString.length()- 1);
      //  }
        String[] attributePair;
        String flag;
        if (aString.indexOf("~=") > 0) {
            flag = "~=";
            attributePair = STRING_.split(aString,"~=");
        } else if (aString.indexOf("|=") > 0) {
            flag = "|=";
            attributePair = STRING_.split(aString,"|=");
        } else  if (aString.indexOf("^=") > 0) {
            flag = "^=";
            attributePair = STRING_.split(aString,"^=");
        }else  if (aString.indexOf("*=") > 0) {
            flag = "*=";
            attributePair = STRING_.split(aString,"*=");
        }else if (aString.indexOf("$=") > 0) {
            flag = "$=";
            attributePair = STRING_.split(aString,"$=");
        }else if (aString.indexOf("=") > 0) {
            flag = "=";
            attributePair = STRING_.split(aString,"=");
        } else{
            flag = "";
            attributePair = new String[]{aString};
        }
        String attributeName = attributePair[0];
        String attribute = (String) OneKit.get(SELF, attributeName);
        if (null==attribute) {
            return -1;
        }
        if (attributePair.length > 1) {
            String attributeValue = attributePair[1];
            attributeValue = attributeValue.substring(1, attributeValue.length() - 1);

            if (attributeValue!=null) {
                boolean isMatch;
                switch (flag) {
                    case "=":
                        isMatch = attribute .equalsIgnoreCase( attributeValue);
                        break;
                    case  "~=":
                    case  "*=":
                        isMatch = attribute.contains(attributeValue);
                        break;
                    case  "^=":
                    case  "|=":
                        isMatch = attribute.startsWith(attributeValue);
                        break;
                    case  "$=":
                        isMatch = attribute.endsWith(attributeValue);
                        break;
                    default:
                        isMatch = false;
                        Log.e("checkAttributeSelector",flag);
                        break;
                }
                if (!isMatch) {
                    return -1;
                }
            } else {
                return -1;
            }
        }
        return 10;
    }
    private     int checkClassSelector(String aString,View SELF) {
       if(!(SELF.getLayoutParams() instanceof CssLayoutParams)){
            return -1;
        }
       View node = SELF;
        int p = aString.indexOf(".");
        if (p >= 0) {
            String className = aString.substring(p + 1);
            if(STRING_.isEmpty(((CssLayoutParams)node.getLayoutParams()).className)){
                return -1;
            }
            String[] classNames = STRING_.split(((CssLayoutParams)node.getLayoutParams()).className," ");
            if (!ARRAY.contains(classNames, className)) {
                return -1;
            }
            if (p > 0) {
                int priority = checkIdSelector(node, p < 0 ? aString : aString.substring(0, p));
                if (priority < 0) {
                    return priority;
                } else {
                    return 10 + priority;
                }
            } else {
                return 10;
            }
        }
        return checkIdSelector(SELF, p < 0 ? aString : aString.substring(0, p));
    }
    private       int checkIdSelector(View SELF, String aString) {
        /*if(!(SELF instanceof CssELEMENT_)){
            return -1;
        }*/
//        View node = SELF;
        CssLayoutParams layoutParams = (CssLayoutParams) SELF.getLayoutParams();
        int p = aString.indexOf("#");
        if (p >= 0) {
            String id = aString.substring(p + 1);
            if (id.equals(layoutParams.id)) {
                int priority = 0;
                if (p >0) {
                    priority = checkElementSelector(aString.substring(0, p),SELF);
                    if(priority<0) {
                        return priority;
                    }
                }
                return priority + 100;
            } else {
                return -1;
            }
        }
        return checkElementSelector( aString,SELF);
    }
    private    int checkElementSelector( String aString,View SELF) {
        if( SELF.getClass().getSimpleName().toLowerCase().equalsIgnoreCase(aString)){
            return 1;
        }else{
            return -1;
        }
    }

}

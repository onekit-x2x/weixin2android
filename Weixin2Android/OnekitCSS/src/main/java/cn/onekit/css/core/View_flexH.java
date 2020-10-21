package cn.onekit.css.core;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import cn.onekit.thekit.ACTION;
import cn.onekit.thekit.ACTION1;
import cn.onekit.core.DOM;
import cn.onekit.core.LITERAL_;


class View_flexH {
    private boolean needAddRow;
    private ArrayList line;
    private float grow = 0, shrink = 0;
    private float _grow, _shrink;
    private float aw, ah, lw, lh, w2, h2;
    private float lmm, mm;
    OnekitCSS OnekitCSS;
    View_flexH(OnekitCSS OnekitCSS){
this.OnekitCSS=OnekitCSS;
    }
    final static int ORDER_MAX =  100000000;
     void measure(View parent, boolean reverse) {
         CssLayoutParams parentLayoutParams = (CssLayoutParams) parent.getLayoutParams();
         float LEFT = OnekitCSS.View_H5.getSize2("paddingLeft", parent);
         float RIGHT = OnekitCSS.View_H5.getSize2("paddingRight", parent);
         float TOP = OnekitCSS.View_H5.getSize2("paddingTop", parent);
         float BOTTOM = OnekitCSS.View_H5.getSize2("paddingBottom", parent);
         aw = 0;
         ah = 0;
         lw = 0;
         lh = 0;
         //
         final ArrayList<ArrayList<Integer>> lines = new ArrayList();
         line = null;
         final ArrayList<Float> lhs = new ArrayList();
         final ArrayList<Float> lws = new ArrayList();
         final ArrayList<Float> lmms = new ArrayList();
         final ArrayList<Float> grows = new ArrayList();
         grow = 0;
         final ArrayList<Float> shrinks = new ArrayList();
         shrink = 0;
         lmm = 0;

         ACTION addLine = new ACTION() {

             @Override
             public void invoke() {
                 if (!needAddRow) {
                     return ;
                 }
                 if (null == line) {
                     return ;
                 }
                 lines.add(line);
                 line = new ArrayList();
                 lws.add(lw);
                 lhs.add(lh);
                 lmms.add(lmm);
                 aw = Math.max(aw, lw);
                 ah += lh;
                 lw = 0;
                 lh = 0;
                 lmm = 0;
                 needAddRow = false;
                 //
                 grows.add(grow);
                 grow = 0;
                 shrinks.add(shrink);
                 shrink = 0;
             }
         };

         ACTION1 addCell = new ACTION1() {

             @Override
             public void invoke(Object index) {
                 grow += _grow;
                 shrink += _shrink;
                 lw += w2;
                 lmm += mm;
                 lh = Math.max(lh, h2);
                 //
                 if (null == line) {
                     line = new ArrayList();
                 }
                 line.add(index);
             }
         };

         //////////////////////////////
         String flexWrap = OnekitCSS.View_H5.getFlexWrap(parent);
         ArrayList<HashMap<String, Float>> orders = new ArrayList();
         ViewGroup viewGroup = parent instanceof ViewGroup?(ViewGroup)parent:null;
         for (int i = 0; i < viewGroup.getChildCount() ; i++) {
             View child = viewGroup.getChildAt(i);
            /* if (!(child instanceof CssNODE_)) {
                 continue;
             }*/
             if (child.getVisibility() == View.GONE) {
                 continue;
             }
             HashMap<String, Float> order = new HashMap();

             order.put("order", (!(child instanceof View) ? OnekitCSS.View_H5.getOrder((View) child) : 0));
             order.put("i", (float) i);
             orders.add(order);
         }
         /*
         if(parentLayoutParams.before!=null && OnekitCSS.View_H5.getContent(parentLayoutParams.before)!=null){
             if(reverse){
                 orders.add(new HashMap(){{
                     put("order",100000000f);
                     put("i",0f);
                 }});
             }else{
                 orders.add(0,new HashMap(){{
                     put("order",-100000000f);
                     put("i",0f);
                 }});
             }
         }
         if(parentLayoutParams.after!=null && OnekitCSS.View_H5.getContent(parentLayoutParams.after)!=null) {
             if (reverse) {
                 orders.add(0, new HashMap() {{
                     put("order", -100000000f);
                     put("i", 0f);
                 }});
             } else {
                 orders.add(new HashMap() {{
                     put("order", 100000000f);
                     put("i", 0f);
                 }});
             }
         }*/
         float childrenHeight = 0;
         if (orders.size() > 0) {

             Collections.sort(orders, new Comparator<Map<String, Float>>() {

                 @Override
                 public int compare(Map<String, Float> a, Map<String, Float> b) {
                     return (int) (a.get("order") - b.get("order"));
                 }
             });
             int index;
             for (int i = 0; i < orders.size(); i++) {
                 index = (int) (float) orders.get(i).get("i");
                 View child = viewGroup.getChildAt(index);
                 CssLayoutParams childLayoutParams = (CssLayoutParams) child.getLayoutParams();
                 if (child instanceof LITERAL_) {
                     LITERAL_ literal = (LITERAL_) child;
                     OnekitCSS.View_measure.literal(parent, literal.view());
                     w2 = childLayoutParams.measuredWidth;
                     h2 = childLayoutParams.measuredHeight;
                     mm = 0;
                     _grow = 0;
                     _shrink = 1;
                     if (!needAddRow) {
                         if (parentLayoutParams.measuredWidth != null && !OnekitCSS.View_H5.getFlexWrap(parent).equalsIgnoreCase("nowrap")) {
                             needAddRow = ((lw + w2) > parentLayoutParams.measuredWidth- LEFT - RIGHT);
                         }
                     }
                     addLine.invoke();
                     addCell.invoke(i);
                         continue;

                 }
                 /*
                 if (!(child instanceof View)) {
                     continue;
                 }*/
                 View node = (View) child;
                 if (OnekitCSS.View_H5.getDisplay(node).equalsIgnoreCase("none")) {
                     continue;
                 }
                 childLayoutParams.measuredWidth = null;
                 childLayoutParams.measuredHeight = null;
                 OnekitCSS.View_H5.initWidth(node);
                 OnekitCSS.View_H5.initHeight(node);
                 Float flexBasis = OnekitCSS.View_H5.getFlexBasis(node);
                 if (flexBasis != null) {
                     childLayoutParams.measuredWidth=flexBasis;
                 }
                 OnekitCSS.View_measure.measure(node);
                 //
                 float w = childLayoutParams.measuredWidth;
                 float h = childLayoutParams.measuredHeight;
                 Float ml = OnekitCSS.View_H5.getSize1("marginLeft", node);
                 Float mr = OnekitCSS.View_H5.getSize1("marginRight", node);
                 float mt = OnekitCSS.View_H5.getSize2("marginTop", node);
                 float mb = OnekitCSS.View_H5.getSize2("marginBottom", node);
                 String position = OnekitCSS.View_H5.getPosition(node);
                 if (position.equalsIgnoreCase("static") || position.equalsIgnoreCase("relative")) {
                     //
                     w2 = (ml != null ? ml : 0) + w + (mr != null ? ml : 0);
                     h2 = mt + h + mb;
                     mm = 0;
                     if (ml == null) {
                         mm++;
                     }
                     if (mr == null) {
                         mm++;
                     }
                     _grow = OnekitCSS.View_H5.getFlexGrow(node);
                     _shrink = OnekitCSS.View_H5.getFlexShrink(node);
                     if (!needAddRow) {
                         if (parentLayoutParams.measuredWidth != null && !OnekitCSS.View_H5.getFlexWrap(parent).equalsIgnoreCase("nowrap")) {
                             needAddRow = ((lw + w2) > parentLayoutParams.measuredWidth- LEFT - RIGHT);
                         }
                     }
                     addLine.invoke();
                     addCell.invoke(i);
                 } else if (position.equalsIgnoreCase("absolute")) {
                     w2 = 0;
                     h2 = 0;
                     addLine.invoke();
                     addCell.invoke(i);
                 } else {
                     Log.e("[position] ", position);
                 }
             }
             aw = Math.max(aw, lw);
             ah += lh;
             lws.add(lw);
             lhs.add(lh);
             grows.add(grow);
             shrinks.add(shrink);
             lmms.add(lmm);
             lines.add(line);

             childrenHeight = ah;
             //
         }
         if (!DOM.isRoot(parent)) {
             OnekitCSS.View_H5.fixWidth(parent, aw);
             OnekitCSS.View_H5.fixHeight(parent, ah);
             aw += LEFT;
             aw += RIGHT;
             ah += TOP;
             ah += BOTTOM;
         }
         if (orders.size() <= 0) {
             return;
         }
         //////////////////////
         float mw = parentLayoutParams.measuredWidth- LEFT - RIGHT;
         float mh = parentLayoutParams.measuredHeight - TOP - BOTTOM;
         //
         String alignContent = OnekitCSS.View_H5.getAlignContent(parent);
         Float[] los;
         float lo, lo_;
         //
         if (lines.size() > 1) {
             los = new Float[lines.size()];
             switch (alignContent) {
                 case "flex-start":
                 case "start":
                     lo = 0;
                     if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                         for (int ln = lines.size() - 1; ln >= 0; ln--) {
                             los[ln] = lo;
                             lo += lhs.get(ln);
                         }
                     } else {
                         for (int ln = 0; ln < lines.size(); ln++) {
                             los[ln] = lo;
                             lo += lhs.get(ln);
                         }
                     }
                     break;
                 case "flex-end":
                 case "end": {
                     lo = mh - childrenHeight;
                     if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                         for (int ln = lines.size() - 1; ln >= 0; ln--) {
                             los[ln] = lo;
                             lo += lhs.get(ln);
                         }
                     } else {
                         for (int ln = 0; ln < lines.size(); ln++) {
                             los[ln] = lo;
                             lo += lhs.get(ln);
                         }
                     }
                     break;
                 }
                 case "center": {
                     lo = (mh - childrenHeight) / 2;
                     if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                         for (int ln = lines.size() - 1; ln >= 0; ln--) {
                             los[ln] = lo;
                             lo += lhs.get(ln);
                         }
                     } else {
                         for (int ln = 0; ln < lines.size(); ln++) {
                             los[ln] = lo;
                             lo += lhs.get(ln);
                         }
                     }
                     break;
                 }
                 case "space-between": {
                     lo_ = (mh - childrenHeight) / (lines.size() - 1);
                     lo = 0;
                     if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                         for (int ln = lines.size() - 1; ln >= 0; ln--) {
                             los[ln] = lo;
                             lo += lhs.get(ln);
                             lo += lo_;
                         }
                     } else {
                         for (int ln = 0; ln < lines.size(); ln++) {
                             los[ln] = lo;
                             lo += lhs.get(ln);
                             lo += lo_;
                         }
                     }
                     break;
                 }
                 case "space-around": {
                     lo_ = (int) ((mh - childrenHeight) * .5 / lines.size());
                     lo = 0;
                     if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                         for (int ln = lines.size() - 1; ln >= 0; ln--) {
                             lo += lo_;
                             los[ln] = lo;
                             lo += lhs.get(ln) + lo_;
                         }
                     } else {
                         for (int ln = 0; ln < lines.size(); ln++) {
                             lo += lo_;
                             los[ln] = lo;
                             lo += lhs.get(ln) + lo_;
                         }
                     }
                     break;
                 }
                 case "normal":
                 case "stretch": {
                     lo = 0;
                     float lh0;
                     if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                         for (int ln = lines.size() - 1; ln >= 0; ln--) {
                             los[ln] = lo;
                             lh0 = mh * lhs.get(ln) / childrenHeight;
                             lo += lh0;
                             lhs.set(ln, lh0);
                         }
                     } else {
                         for (int ln = 0; ln < lines.size(); ln++) {
                             los[ln] = lo;
                             lh0 = mh * lhs.get(ln) / childrenHeight;
                             lo += lh0;
                             lhs.set(ln, lh0);
                         }
                     }
                     break;
                 }
                 default:
                     Log.e("[alignContent]", alignContent);
                     break;
             }
         } else {
             lhs.set(0, mh);
             los = new Float[]{0f};
         }
         //
         String alignItems = OnekitCSS.View_H5.getAlignItems(parent);
         String justifyContent = OnekitCSS.View_H5.getJustifyContent(parent);
         int i = 0;
         for (int ln = 0; ln < lines.size(); ln++) {
             line = lines.get(ln);
             lw = lws.get(ln);
             lh = lhs.get(ln);
             grow = grows.get(ln);
             shrink = shrinks.get(ln);
             lmm = lmms.get(ln);
             float x, _x, x_;
             if (mw <= lw) {
                 if (reverse) {
                     _x = x_ = 0;
                     x = parentLayoutParams.measuredWidth - RIGHT;
                 } else {
                     x = LEFT;
                     _x = x_ = 0;
                 }
             } else {
                 if (reverse) {
                     switch (justifyContent) {
                         case "normal":
                         case "flex-start":
                         case "start":
                             _x = 0;
                             x_ = 0;
                             x = parentLayoutParams.measuredWidth - RIGHT;
                             break;
                         case "flex-end":
                         case "end":
                             _x = 0;
                             x_ = 0;
                             x = LEFT + lw;
                             break;
                         case "center":
                             _x = 0;
                             x_ = 0;
                             x = (parentLayoutParams.measuredWidth- lw) / 2;
                             break;
                         case "space-between":
                             _x = 0;
                             x_ = (mw - lw) / (line.size() - 1);
                             x = parentLayoutParams.measuredWidth - RIGHT;
                             break;
                         case "space-around":
                             _x = x_ = (int) ((mw - lw) * .5 / line.size());
                             x = parentLayoutParams.measuredWidth - RIGHT - x_;
                             break;
                         default:
                             _x = x_ = x = 0;
                             Log.e("[justifyContent]", justifyContent);
                             break;
                     }
                 } else {
                     switch (justifyContent) {
                         case "normal":
                         case "flex-start":
                         case "start":
                             _x = 0;
                             x_ = 0;
                             x = LEFT;
                             break;
                         case "flex-end":
                         case "end":
                             _x = 0;
                             x_ = 0;
                             x = parentLayoutParams.measuredWidth - RIGHT - lw;
                             break;
                         case "center":
                             _x = 0;
                             x_ = 0;
                             x = (parentLayoutParams.measuredWidth - lw) / 2;
                             break;
                         case "space-between":
                             _x = x_ = (int)(((mw - lw)*0.5/ (line.size() - 1)));
                             x = LEFT;
                             break;
                         case "space-around":
                             _x = x_ = (int) ((mw - lw) * .5 / line.size());
                             x = LEFT + x_;
                             break;
                         default:
                             _x = x_ = x = 0;
                             Log.e("[justifyContent]", justifyContent);
                             break;
                     }
                 }
             }
             int ii = i;
             float lww = 0;
             for (int c = 0; c < line.size(); c++) {
                 int index = (int) (float) orders.get(i).get("i");
                 View child = (View) viewGroup.getChildAt(index);
                 CssLayoutParams childLayoutParams = (CssLayoutParams) child.getLayoutParams();
                 i++;
                 float ml, mr;
                 Float mt, mb;
                 if (!(child instanceof LITERAL_)) {
                     String position = OnekitCSS.View_H5.getPosition((View) child);
                     if (!position.equalsIgnoreCase("static") && !position.equalsIgnoreCase("relative")) {
                         continue;
                     }
                     //
                     View node = (View) child;
                     ml = OnekitCSS.View_H5.getSize2("marginLeft", node);
                     mr = OnekitCSS.View_H5.getSize2("marginRight", node);
                     mt = OnekitCSS.View_H5.getSize1("marginTop", node);
                     mb = OnekitCSS.View_H5.getSize1("marginBottom", node);
                     //
                     _grow = OnekitCSS.View_H5.getFlexGrow(node);
                     _shrink = OnekitCSS.View_H5.getFlexShrink(node);
                 } else {
                     mt = mb = 0f;
                     ml = mr = 0;
                 }
                 float h = childLayoutParams.measuredHeight;
                 //
                 h2 = (mt != null ? mt : 0) + h + (mb != null ? mb : 0);
                 float y = childLayoutParams.y;
                 y = TOP + los[ln];
                 if (lh >= h2) {
                     if (mt == null && mb == null) {
                         y += (lh - h2) / 2;
                     } else if (mt == null) {
                         y += lh - h2;
                     } else if (mb == null) {
                         y += mt;
                     } else {
                         String alignSelf = OnekitCSS.View_H5.getAlignSelf((View) child);
                         String align = (!alignSelf.equalsIgnoreCase("auto") ? alignSelf : alignItems);
                         switch (align) {
                             case "flex-start":
                             case "start":
                                 y += mt;
                                 break;
                             case "flex-end":
                             case "end":
                                 y += lh - h2 + mt;
                                 break;
                             case "center":
                                 y += (mh - h2) / 2 + mt;
                                 break;
                             case "baseline":
                                 y += (mh - h2) / 2 + mt;
                                 Log.e("[alignItems]baseline", "");
                                 break;
                             case "stretch":
                             case "normal":
                                 y += mt;
                                 if (!(child instanceof LITERAL_) && null == childLayoutParams.computedStyle.height) {
                                     childLayoutParams.measuredHeight = lh - mt - mb;
                                 }
                                 break;
                             default:
                                 Log.e("[align]", align);
                                 break;
                         }
                     }
                 } else {
                     y += mt;
                 }
                 childLayoutParams.y=y;
                 //
                 float w = childLayoutParams.measuredWidth;
                 float frees = mw - lw;
                 if (frees < 0) {
                     if (shrink > 1) {
                         w += frees * _shrink / shrink;
                     } else {
                         w += frees * _shrink;
                     }
                 } else if (frees > 0) {
                     //if (grow > 1) {
                     w += frees * _grow / grow;
                     // } else {
                     //     w += frees * _grow;
                     // }

                 }
                 lww += ml + w + mr;
                 if (childLayoutParams.measuredWidth== null || Math.abs(childLayoutParams.measuredWidth- w) >= 1) {
                     childLayoutParams.measuredWidth=w;
                     //
                     if (child instanceof View) {
                         View node = (View) child;
                         childLayoutParams.measuredHeight=null;
                         OnekitCSS.View_H5.initWidth(node);
                         OnekitCSS.View_H5.initHeight(node);
                         OnekitCSS. View_measure.measure(node);
                     }
                 }
             }
             float m = (lmm == 0 ? 0 : (mw - lww) / lmm);
             for (int c = 0; c < line.size(); c++) {
                 int index = (int) (float) orders.get(ii).get("i");
                 View child = (View) viewGroup.getChildAt(index);
                 CssLayoutParams childLayoutParams = (CssLayoutParams) child.getLayoutParams();
                 ii++;
                 if (OnekitCSS.View_H5.getDisplay(child).equalsIgnoreCase("none")) {
                     continue;
                 }
                 if (child instanceof View) {
                     Float ml, mr;
                     View node = (View) child;
                     ml = OnekitCSS.View_H5.getSize1("marginLeft", node);
                     mr = OnekitCSS.View_H5.getSize1("marginRight", node);
                     if (ml == null) {
                         ml = m;
                     }
                     if (mr == null) {
                         mr = m;
                     }
                     String position = OnekitCSS.View_H5.getPosition(node);
                     if (!position.equalsIgnoreCase("static")
                             && !position.equalsIgnoreCase("relative")) {
                         OnekitCSS.View_H5.computeAbsoluted(node, reverse, false);
                         continue;
                     }

                     if (reverse) {
                         x -= (mr + childLayoutParams.measuredWidth);
                         childLayoutParams.x=x;
                         x -= (ml + x_ + _x);
                     } else {
                         x += ml;
                         childLayoutParams.x=x;
                         x += childLayoutParams.measuredWidth+ mr + x_ + _x;
                     }

                 }
             }
         }
     }
}
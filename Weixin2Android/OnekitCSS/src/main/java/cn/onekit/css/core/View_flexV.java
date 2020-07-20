package cn.onekit.css.core;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import cn.onekit.ACTION;
import cn.onekit.ACTION1;
import cn.onekit.DOM;
import cn.onekit.LITERAL_;


class View_flexV {
    private OnekitCSS OnekitCSS;
    View_flexV(OnekitCSS OnekitCSS){
        this.OnekitCSS=OnekitCSS;
    }
    private boolean needAddRow;
    private ArrayList line;
    private float grow = 0, shrink = 0;
    private float _grow, _shrink;
    private float aw, ah, lw, lh, w2, h2;
    private float lmm, mm;

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
                ah = Math.max(ah, lh);
                aw += lw;
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
                lh += h2;
                lmm += mm;
                lw = Math.max(lw, w2);
                //
                if (null == line) {
                    line = new ArrayList();
                }
                line.add(index);
            }
        };

        //////////////////////////////
        String flexWrap = OnekitCSS.View_H5.getFlexWrap(parent);
        ArrayList<Map<String, Float>> orders = new ArrayList();
        ViewGroup viewGroup = parent instanceof ViewGroup?(ViewGroup)parent:null;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
           /* if (!(child instanceof CssNODE_)) {
                      continue;

            }*/
            if(child.getVisibility()== View.GONE){
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
        float childrenWidth = 0;
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
                    OnekitCSS.View_measure.literal(parent,literal.view());
                    w2 = childLayoutParams.measuredWidth;
                    h2 = childLayoutParams.measuredHeight;
                    mm = 0;
                    _grow = 0;
                    _shrink = 1;
                    if (!needAddRow) {
                        if (parentLayoutParams.measuredHeight != null && !OnekitCSS.View_H5.getFlexWrap(parent).equalsIgnoreCase("nowrap")) {
                            needAddRow = ((lh + h2) > parentLayoutParams.measuredHeight - TOP - BOTTOM);    }
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

                childLayoutParams.measuredWidth=null;
                childLayoutParams.measuredHeight=null;
                OnekitCSS.View_H5.initWidth(node);
                OnekitCSS.View_H5.initHeight(node);
                Float flexBasis = OnekitCSS.View_H5.getFlexBasis(node);
                if (flexBasis != null) {
                    childLayoutParams.measuredHeight=flexBasis;
                }
                OnekitCSS.View_measure.measure(node);
                //
                float w = childLayoutParams.measuredWidth;
                float h = childLayoutParams.measuredHeight;
                float ml = OnekitCSS.View_H5.getSize2("marginLeft", node);
                float mr = OnekitCSS.View_H5.getSize2("marginRight", node);
                Float mt = OnekitCSS.View_H5.getSize1("marginTop", node);
                Float mb = OnekitCSS.View_H5.getSize1("marginBottom", node);
                String position = OnekitCSS.View_H5.getPosition(node);
                if (position.equalsIgnoreCase("static") || position.equalsIgnoreCase("relative")) {
                    //
                    w2 = ml + w + mr;
                    h2 = (mt!=null?mt:0) + h + (mb!=null?mb:0);
                    mm = 0;
                    if (mt == null) {
                        mm++;
                    }
                    if (mb == null) {
                        mm++;
                    }
                    _grow = OnekitCSS.View_H5.getFlexGrow(node);
                    _shrink = OnekitCSS.View_H5.getFlexShrink(node);
                    if (!needAddRow) {
                        if (parentLayoutParams.measuredHeight != null && !OnekitCSS.View_H5.getFlexWrap(parent).equalsIgnoreCase("nowrap")) {
                           needAddRow = ((lh + h2) > parentLayoutParams.measuredHeight - TOP - BOTTOM);    }
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
            ah = Math.max(ah, lh);
            aw += lw;
            lws.add(lw);
            lhs.add(lh);
            grows.add(grow);
            shrinks.add(shrink);
            lmms.add(lmm);
            lines.add(line);

            childrenWidth = aw;
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
        if(orders.size()<=0){
            return;
        }
        //////////////////////
        float mw = parentLayoutParams.measuredWidth- LEFT - RIGHT;
        float mh = parentLayoutParams.measuredHeight - TOP - BOTTOM;
        //
        String alignContent = OnekitCSS.View_H5.getAlignContent(parent);
        Float[] los;
        float lo,lo_;
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
                            lo += lws.get(ln);
                        }
                    } else {
                        for (int ln = 0; ln < lines.size(); ln++) {
                            los[ln] = lo;
                            lo += lws.get(ln);
                        }
                    }
                    break;
                case "flex-end":
                case "end": {
                    lo = mw - childrenWidth;
                    if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                        for (int ln = lines.size() - 1; ln >= 0; ln--) {
                            los[ln] = lo;
                            lo += lws.get(ln);
                        }
                    } else {
                        for (int ln = 0; ln < lines.size(); ln++) {
                            los[ln] = lo;
                            lo += lws.get(ln);
                        }
                    }
                    break;
                }
                case "center": {
                    lo = (mw - childrenWidth) / 2;
                    if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                        for (int ln = lines.size() - 1; ln >= 0; ln--) {
                            los[ln] = lo;
                            lo += lws.get(ln);
                        }
                    } else {
                        for (int ln = 0; ln < lines.size(); ln++) {
                            los[ln] = lo;
                            lo += lws.get(ln);
                        }
                    }
                    break;
                }
                case "space-between": {
                     lo_ = (mw - childrenWidth) / (lines.size() - 1);
                    lo = 0;
                    if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                        for (int ln = lines.size() - 1; ln >= 0; ln--) {
                            los[ln] = lo;
                            lo += lws.get(ln);
                            lo += lo_;
                        }
                    } else {
                        for (int ln = 0; ln < lines.size(); ln++) {
                            los[ln] = lo;
                            lo += lws.get(ln);
                            lo += lo_;
                        }
                    }
                    break;
                }
                case "space-around": {
                     lo_ = (int) ((mw - childrenWidth) * .5 / lines.size());
                    lo = 0;
                    if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                        for (int ln = lines.size() - 1; ln >= 0; ln--) {
                            lo += lo_;
                            los[ln] = lo;
                            lo += lws.get(ln) + lo_;
                        }
                    } else {
                        for (int ln = 0; ln < lines.size(); ln++) {
                            lo += lo_;
                            los[ln] = lo;
                            lo += lws.get(ln) + lo_;
                        }
                    }
                    break;
                }
                case "normal":
                case "stretch": {
                    lo = 0;
                    float lw0;
                    if (flexWrap.equalsIgnoreCase("wrap-reverse")) {
                        for (int ln = lines.size() - 1; ln >= 0; ln--) {
                            los[ln] = lo;
                            lw0 = mw * lws.get(ln) / childrenWidth;
                            lo += lw0;
                            lhs.set(ln, lw0);
                        }
                    } else {
                        for (int ln = 0; ln < lines.size(); ln++) {
                            los[ln] = lo;
                            lw0 = mh * lws.get(ln) / childrenWidth;
                            lo += lw0;
                            lhs.set(ln, lw0);
                        }
                    }
                    break;
                }
                default:
                    Log.e("[alignContent]", alignContent);
                    break;
            }
        } else {
            lws.set(0, mw);
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
            float y, _y, y_;
            if (mh <= lh) {
                if (reverse) {
                    _y = y_ = 0;
                    y = parentLayoutParams.measuredHeight - BOTTOM;
                } else {
                    y = TOP;
                    _y = y_ = 0;
                }
            } else{
                if (reverse) {
                    switch (justifyContent) {
                        case "normal":
                        case "flex-start":
                        case "start":
                            _y = 0;
                            y_ = 0;
                            y = parentLayoutParams.measuredHeight - BOTTOM;
                            break;
                        case "flex-end":
                        case "end":
                            _y = 0;
                            y_ = 0;
                            y = TOP + lh;
                            break;
                        case "center":
                            _y = 0;
                            y_ = 0;
                            y = (parentLayoutParams.measuredHeight - lh) / 2;
                            break;
                        case "space-between":
                            _y = 0;
                            y_ = (mh - lh) / (line.size() - 1);
                            y = parentLayoutParams.measuredHeight - BOTTOM;
                            break;
                        case "space-around":
                            _y = y_ = (int) ((mh - lh) * .5 / line.size());
                            y = parentLayoutParams.measuredHeight - BOTTOM - y_;
                            break;
                        default:
                            _y = y_ = y = 0;
                            Log.e("[justifyContent]", justifyContent);
                            break;
                    }
                } else {
                    switch (justifyContent) {
                        case "normal":
                        case "flex-start":
                        case "start":
                            _y = 0;
                            y_ = 0;
                            y = TOP;
                            break;
                        case "flex-end":
                        case "end":
                            _y = 0;
                            y_ = 0;
                            y = parentLayoutParams.measuredHeight - BOTTOM - lh;
                            break;
                        case "center":
                            _y = 0;
                            y_ = 0;
                            y = (parentLayoutParams.measuredHeight - lh) / 2;
                            break;
                        case "space-between":
                            _y = 0;
                            y_ = (mh - lh) / (line.size() - 1);
                            y = TOP;
                            break;
                        case "space-around":
                            _y = y_ = (int) ((mh - lh) * .5 / line.size());
                            y = TOP + y_;
                            break;
                        default:
                            _y = y_ = y = 0;
                            Log.e("[justifyContent]", justifyContent);
                            break;
                    }
                }
            }
            int ii = i;
            int lhh = 0;
            for (int c = 0; c < line.size(); c++) {
                int index = (int) (float) orders.get(i).get("i");
                View child =  viewGroup.getChildAt(index);
                CssLayoutParams childLayoutParams = (CssLayoutParams) child.getLayoutParams();
                i++;
                Float ml, mr;
                float mt, mb;
                if (!(child instanceof LITERAL_)) {
                    String position = OnekitCSS.View_H5.getPosition((View) child);
                    if (!position.equalsIgnoreCase("static") && !position.equalsIgnoreCase("relative")) {
                        continue;
                    }
                    //
                    View node = (View) child;
                    ml = OnekitCSS.View_H5.getSize1("marginLeft", node);
                    mr = OnekitCSS.View_H5.getSize1("marginRight", node);
                    mt = OnekitCSS.View_H5.getSize2("marginTop", node);
                    mb = OnekitCSS.View_H5.getSize2("marginBottom", node);
                    //
                    _grow = OnekitCSS.View_H5.getFlexGrow(node);
                    _shrink = OnekitCSS.View_H5.getFlexShrink(node);
                } else {
                    mt = mb = 0;
                    ml = mr = 0f;
                }
                float w = childLayoutParams.measuredWidth;
                //
                w2 = (ml!=null?ml:0) + w + (mr!=null?mr:0);
                float x = childLayoutParams.x;
                x=LEFT + los[ln];
                if (lh >= h2) {
                    if (ml == null && mr == null) {
                        x += (lh - h2) / 2;
                    } else if (ml == null) {
                        y += lh - h2;
                    } else if (mr == null) {
                        y += mt;
                    } else {
                        String alignSelf = OnekitCSS.View_H5.getAlignSelf((View) child);
                        String align = (!alignSelf.equalsIgnoreCase("auto") ? alignSelf : alignItems);
                        switch (align) {
                            case "flex-start":
                            case "start":
                                x += mt;
                                break;
                            case "flex-end":
                            case "end":
                                x += lw - w2 + ml;
                                break;
                            case "center":
                                x+= (mw - w2) / 2 ;
                                break;
                            case "baseline":
                                x += (mw - w2) / 2 ;
                                Log.e("[alignItems]baseline", "");
                                break;
                            case "stretch":
                            case "normal":
                                x += ml;
                                if (!(child instanceof LITERAL_) && null ==  childLayoutParams.computedStyle.width) {
                                    childLayoutParams.measuredWidth=lw - ml - mw;
                                }
                                break;
                            default:
                                Log.e("[align]", align);
                                break;
                        }
                    }
                } else {
                    x += (ml!=null?ml:0);
                }
                childLayoutParams.x=x;
                //
                float h = childLayoutParams.measuredHeight;
                float frees = mh - lh;
                if (frees < 0) {
                    if (shrink > 1) {
                        h += frees * _shrink / shrink;
                    } else {
                        h += frees * _shrink;
                    }
                } else if (frees > 0) {
                 //   if (grow > 1) {
                       h += frees * _grow / grow;
                  //  } else {
                  //     h += frees * _grow;
                 //   }

                }
                lhh += mt + h + mb;
                if(childLayoutParams.measuredHeight==null  || Math.abs(childLayoutParams.measuredWidth- w) >= 1) {
                    childLayoutParams.measuredHeight=h;
                    //
                    if (child instanceof View) {
                        View node= (View)child;
                        childLayoutParams.measuredWidth=null;
                        OnekitCSS.View_H5.initWidth(node);
                        OnekitCSS.View_H5.initHeight(node);
                        OnekitCSS.View_measure.measure( node);
                    }
                }
            }
            float m = (lmm == 0 ? 0 : (mh - lhh) / lmm);
            for (int c = 0; c < line.size(); c++) {
                int index = (int) (float) orders.get(ii).get("i");
                View child = (View) viewGroup.getChildAt(index);
                CssLayoutParams childLayoutParams = (CssLayoutParams) child.getLayoutParams();
                ii++;
                if (OnekitCSS.View_H5.getDisplay(child).equalsIgnoreCase("none")) {
                    continue;
                }
                Float mt, mb;
                if (!(child instanceof LITERAL_)) {
                    View node = (View) child;
                    mt = OnekitCSS.View_H5.getSize1("marginTop", node);
                    mb = OnekitCSS.View_H5.getSize1("marginBottom", node);
                    if (mt == null) {
                        mt = m;
                    }
                    if (mb == null) {
                        mb = m;
                    }
                    String position = OnekitCSS.View_H5.getPosition(node);
                    if (!position.equalsIgnoreCase("static")
                            && !position.equalsIgnoreCase("relative")) {
                        OnekitCSS.View_H5.computeAbsoluted(node, false, reverse);
                        continue;
                    }
                } else {
                    mt = mb = 0f;
                }
                if (reverse) {
                    y -= (mb + childLayoutParams.measuredHeight);
                    childLayoutParams.y=y;
                    y -= (mt + y_ + _y);
                } else {
                    y += mt;
                    childLayoutParams.y=y;
                    y+= childLayoutParams.measuredHeight + mb + y_ + _y;
                }
            }
        }
    }
}
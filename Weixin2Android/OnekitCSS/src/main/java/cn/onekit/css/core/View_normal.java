package cn.onekit.css.core;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import thekit.ACTION;
import thekit.ACTION1;
import cn.onekit.DOM;
import cn.onekit.LITERAL_;

class View_normal {
    private boolean needAddRow;
    private float aw, ah;
    private float lw, lh;
    private float w2, h2;
    private ArrayList line;
    OnekitCSS OnekitCSS;
    View_normal(OnekitCSS OnekitCSS){
        this.OnekitCSS=OnekitCSS;
    }
    void measure(View parent) {
      CssLayoutParams  parentLayoutParams = (CssLayoutParams) parent.getLayoutParams();
        float LEFT = OnekitCSS.View_H5.getSize2("paddingLeft", parent);
        float RIGHT = OnekitCSS.View_H5.getSize2("paddingRight", parent);
        float TOP = OnekitCSS.View_H5.getSize2("paddingTop", parent);
        float BOTTOM = OnekitCSS.View_H5.getSize2("paddingBottom", parent);
        aw = 0;
        ah = 0;
        lw = 0;
        lh = 0;
        float mw = (parentLayoutParams.measuredWidth!= null ? (parentLayoutParams.measuredWidth - LEFT - RIGHT) : 0);
        //
        final ArrayList<ArrayList<Integer>> lines = new ArrayList();
        line = null;
        final ArrayList<Float> lws = new ArrayList();
        final ArrayList<Float> lhs = new ArrayList();

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

                aw = Math.max(aw, lw);
                ah += lh;
                lw = 0;
                lh = 0;
                needAddRow = false;
            }
        };

        ACTION1 addCell = new ACTION1() {

            @Override
            public void invoke(Object index) {
                lw += w2;
                lh = Math.max(lh, h2);
                //
                if ((Integer) index >= 0) {
                    if (null == line) {
                        line = new ArrayList();
                    }
                    line.add((Integer) index);
                }
            }
        };

        //////////////////////////////

        ViewGroup viewGroup = parent instanceof ViewGroup?(ViewGroup)parent:null;
        List<Integer> orders = new ArrayList();
        if (viewGroup != null) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                /*if (!(child instanceof CssNODE_)) {
                    continue;
                }*/
                orders.add(i);
            }
        }
        if (orders.size() > 0) {
            int index;
            for (int i = 0; i < orders.size(); i++) {
                index = orders.get(i);
                View child = viewGroup.getChildAt(index);
                if( !(child.getLayoutParams() instanceof CssLayoutParams)) {
                    continue;
                }
                CssLayoutParams childLayoutParams= (CssLayoutParams) child.getLayoutParams();
                if (child instanceof LITERAL_) {
                    LITERAL_ literal = (LITERAL_) child;
                    OnekitCSS.View_measure.literal(parent, literal.view());
                    w2 = childLayoutParams.measuredWidth;
                    h2 = childLayoutParams.measuredHeight;
                    if (!needAddRow) {
                        if (!OnekitCSS.View_H5.getWhiteSpace(parent).equalsIgnoreCase("nowrap") && parentLayoutParams.measuredWidth!= null) {
                            needAddRow = ((lw + w2) > mw);
                        }
                    }
                    addLine.invoke();
                    addCell.invoke(i);
                        continue;

                }
                View node = (View) child;
                if (OnekitCSS.View_H5.getDisplay(node).equalsIgnoreCase("none")) {
                    continue;
                }

                childLayoutParams.measuredWidth=null;
                childLayoutParams.measuredHeight=null;
                OnekitCSS.View_H5.initWidth(node);
                OnekitCSS.View_H5.initHeight(node);
                //
                OnekitCSS.View_measure.measure(node);
                float w = childLayoutParams.measuredWidth;
                float h = childLayoutParams.measuredHeight;
                float ml = OnekitCSS.View_H5.getSize2("marginLeft", node);
                float mr = OnekitCSS.View_H5.getSize2("marginRight", node);
                float mt = OnekitCSS.View_H5.getSize2("marginTop", node);
                float mb = OnekitCSS.View_H5.getSize2("marginBottom", node);
                //
                String position = OnekitCSS.View_H5.getPosition(node);
                if (position.equalsIgnoreCase("static") || position.equalsIgnoreCase("relative")) {
                    String display = OnekitCSS.View_H5.getDisplay(node);

                    if (display.equalsIgnoreCase("inline")) {
                        w2 = ml + w + mr;
                        h2 = h;
                        if (!needAddRow) {
                            if (!OnekitCSS.View_H5.getWhiteSpace(parent).equalsIgnoreCase("nowrap") && parentLayoutParams.measuredWidth != null) {
                                needAddRow = ((lw + w2) > mw);
                            }
                        }
                        addLine.invoke();
                        addCell.invoke(i);

                    } else if (display.equalsIgnoreCase("inline-block")) {
                        w2 = ml + w + mr;
                        h2 = mt + h + mb;
                        if (!needAddRow) {
                            if (!OnekitCSS.View_H5.getWhiteSpace(parent).equalsIgnoreCase("nowrap") && parentLayoutParams.measuredWidth != null) {
                                needAddRow = ((lw + w2) > mw);
                            }
                        }
                        addLine.invoke();
                        addCell.invoke(i);
                    } else if (display.equalsIgnoreCase("block") || display.equalsIgnoreCase("flex")) {

                        if (i == 0) {
                            mt = OnekitCSS.View_H5.marginTopBlock(node, true);
                        } else {
                            mt = OnekitCSS.View_H5.marginBlockBlock(node, i);
                        }
                        w2 = ml + w + mr;
                        h2 = mt + h + mb;
                        needAddRow = true;
                        addLine.invoke();
                        addCell.invoke(i);
                        //
                        needAddRow = true;
                    } else {
                        Log.e("======", "[display]" + display);
                    }

                } else if (position.equalsIgnoreCase("absolute")
                        || position.equalsIgnoreCase("fixed")) {
                    w2 = 0;
                    h2 = 0;
                    addLine.invoke();
                    addCell.invoke(i);
                } else {
                    Log.e("======", "[position] " + position);
                }
            }
            aw = Math.max(aw, lw);
            ah += lh;
            lws.add(lw);
            lhs.add(lh);
            lines.add(line);
            //
        }
        if (!DOM.isRoot(parent)) {
            OnekitCSS.View_H5.fixWidth(parent, aw);
            OnekitCSS.View_H5.fixHeight(parent, ah);
            //
            aw += LEFT;
            aw += RIGHT;
            ah += TOP;
            ah += BOTTOM;
        }
        //doCssLITERAL_(true);
        if (orders.size() <= 0) {
            return;
        }
        //////////////////////
        int i = 0;
        float y = TOP;
        //var parentNode,mt0;
        for (int ln = 0; ln < lines.size(); ln++) {
            line = lines.get(ln);
            lw = lws.get(ln);
            lh = lhs.get(ln);
            float x = 0;
            float x0;
            String textAlign = OnekitCSS.View_H5.getTextAlign(parent);
            switch (textAlign) {
                case "left":
                case "start":
                    x0 = LEFT;
                    break;
                case "center":
                    x0 = (parentLayoutParams.measuredWidth - lw) / 2;
                    break;
                case "right":
                case "end":
                    x0 = parentLayoutParams.measuredWidth - RIGHT - lw;
                    break;
                default:
                    x0 = LEFT;
                    break;
            }
            if(line!=null) {
                for (int c = 0; c < line.size(); c++) {
                    //if (ln ==0 && c ==0) {
                    //doCssLITERAL_(false, true);
                    //}
                    int index = orders.get(i);
                    float w, h;
                    View child = viewGroup.getChildAt(index);
                    CssLayoutParams childLayoutParams = (CssLayoutParams) child.getLayoutParams();
                    if (child instanceof LITERAL_) {
                        LITERAL_ LITERAL = (LITERAL_) child;
                        w = childLayoutParams.measuredWidth;
                        childLayoutParams.x = x0 + x;
                        childLayoutParams.y = y;
                        x += w;
                        i++;
                        continue;
                    }
                    //
                    View node = child;
                    w = childLayoutParams.measuredWidth;
                    h = childLayoutParams.measuredHeight;
                    //
                    Float ml = OnekitCSS.View_H5.getSize1("marginLeft", node);
                    Float mr = OnekitCSS.View_H5.getSize1("marginRight", node);
                    float mt = OnekitCSS.View_H5.getSize2("marginTop", node);
                    float mb = OnekitCSS.View_H5.getSize2("marginBottom", node);
                    //

                    //
                    String position = OnekitCSS.View_H5.getPosition(node);
                    if (position.equalsIgnoreCase("static") || position.equalsIgnoreCase("relative")) {
                        String display = OnekitCSS.View_H5.getDisplay(node);

                        if (display.equalsIgnoreCase("inline")) {

                            childLayoutParams.y = y;
                        } else if (display.equalsIgnoreCase("inline-block")) {

                            if (lh > (mt + h + mb)) {
                                childLayoutParams.y = y + (lh - h - mb);
                            } else {
                                childLayoutParams.y = mt + y;
                            }
                        } else if (display.equalsIgnoreCase("block") || display.equalsIgnoreCase("flex")) {
                            if (i == 0) {
                                mt = OnekitCSS.View_H5.marginTopBlock(node, false);
                            } else {
                                mt = OnekitCSS.View_H5.marginBlockBlock(node, i);
                            }
                            childLayoutParams.y = y + mt;
                        } else {
                            Log.e("[display]", display);
                        }
                        if (ml == null && mr == null) {
                            childLayoutParams.x = (parentLayoutParams.measuredWidth - lw) / 2;
                        } else if (ml == null) {
                            childLayoutParams.x = parentLayoutParams.measuredWidth - RIGHT - lw;
                        } else {
                            childLayoutParams.x = x0 + x + ml;
                        }

                        x = childLayoutParams.x + w + (mr != null ? mr : 0);
                        if (position.equalsIgnoreCase("relative")) {
                            Float l = OnekitCSS.View_H5.getSize1("left", node);
                            Float t = OnekitCSS.View_H5.getSize1("top", node);
                            if (l != null) {
                                childLayoutParams.x = l;
                            }
                            if (t != null) {
                                childLayoutParams.y = t;
                            }
                        }
                    } else if (position.equalsIgnoreCase("absolute")
                            || position.equalsIgnoreCase("fixed")) {
                        OnekitCSS.View_H5.computeAbsoluted(node, false, false);
                    } else {
                        Log.e("[position]", position);
                    }
                    i++;
                }
                y += lh;
            }
        }
    }
}

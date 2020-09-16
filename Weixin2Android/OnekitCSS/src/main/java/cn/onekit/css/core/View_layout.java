package cn.onekit.css.core;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.Log;
import android.util.SizeF;
import android.view.View;
import java.util.ArrayList;

import cn.onekit.COLOR;
import cn.onekit.LITERAL_;
import thekit.ARRAY;

import static thekit.android.Android.dp2px;

public class View_layout {
    private OnekitCSS OnekitCSS;
    public View_layout(OnekitCSS OnekitCSS){
        this.OnekitCSS=OnekitCSS;
    }

    @SuppressLint("NewApi")
    public  void h5css(View SELF) {
        android.view.View view = SELF;
        if (ARRAY.contains(new String[]{"relative", "absolute"}, OnekitCSS.View_H5.getPosition(SELF))) {
            view.setElevation(1);
        }
        Float zIndex = OnekitCSS.View_H5.getZIndex(SELF);
        if (zIndex != null) {
            view.setElevation(zIndex);
        }
        //Size Size = new Size(view.getMeasuredWidth(), view.getMeasuredHeight());
        //////////////////
        float tl = OnekitCSS.View_H5.getBorderRadius(SELF, "TopLeft");
        float tr = OnekitCSS.View_H5.getBorderRadius(SELF, "TopRight");
        float bl = OnekitCSS.View_H5.getBorderRadius(SELF, "BottomLeft");
        float br = OnekitCSS.View_H5.getBorderRadius(SELF, "BottomRight");
        /////////////////////////////////////////
        ArrayList<Drawable> backgrounds = new ArrayList();

        String backgroundColor = OnekitCSS.View_H5.getBackgroundColor(SELF);
        if (backgroundColor != null) {
            float[] outerR = new float[]{tl, tl, tr, tr, br, br, bl, bl};
            RoundRectShape roundRectShape0 = new RoundRectShape(outerR, null, null);
            ShapeDrawable shapeDrawableBg = new ShapeDrawable();
            shapeDrawableBg.setShape(roundRectShape0);
            shapeDrawableBg.getPaint().setStyle(Paint.Style.FILL);
            shapeDrawableBg.getPaint().setColor(COLOR.parse(backgroundColor));
            backgrounds.add(shapeDrawableBg);
        }
        _h5border(SELF, backgrounds, "Top", new String[]{"TopLeft", "TopRight"});
        _h5border(SELF, backgrounds, "Right", new String[]{"TopRight", "BottomRight"});
        _h5border(SELF, backgrounds, "Bottom", new String[]{"BottomRight", "BottomLeft"});
        _h5border(SELF, backgrounds, "Left", new String[]{"BottomLeft", "TopLeft"});
        //
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{}, new LayerDrawable(backgrounds.toArray(new Drawable[backgrounds.size()])));
        SELF.setBackground(stateListDrawable);
        //
        // Log.e("=============", "==================" + SELF.hashCode());
      /*  if (SELF.animation() != null) {
            VIEW_.animation(SELF, SELF.animation());
        }*/
    }

    public  void onDraw(View SELF, Canvas canvas) {

    }

     void _h5border(View SELF, ArrayList<Drawable> backgrounds, String border, String[] corners) {
        String borderStyle = OnekitCSS.View_H5.getBorderStyle(SELF, border);
        float borderWidth = OnekitCSS.View_H5.getBorderWidth(SELF, border);
        int borderColor = OnekitCSS.View_H5.getBorderColor(SELF, border);
        if (borderWidth <= 0) {
            return;
        }
        int r, g, b, a;
        a = Color.alpha(borderColor);
        r = Color.red(borderColor);
        g = Color.green(borderColor);
        b = Color.blue(borderColor);
        int darkBorderColor = Color.argb(a, r / 2, g / 2, b / 2);
        if (borderStyle.equalsIgnoreCase("dotted")) {
            float lineDash[] = {borderWidth / 2, borderWidth / 2, borderWidth / 2, borderWidth / 2};
            _h5border(SELF, backgrounds,
                    border
                    , corners
                    , borderWidth
                    , new float[]{0, 0}
                    , lineDash
                    , borderColor);
        } else if (borderStyle.equalsIgnoreCase("dashed")) {
            float lineDash[] = {borderWidth * 5 / 2, borderWidth * 5 / 2, borderWidth * 5 / 2, borderWidth * 5 / 2};
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth
                    , new float[]{0, 0}
                    , lineDash
                    , borderColor);
        } else if (borderStyle.equalsIgnoreCase("solid")) {
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth
                    , new float[]{0, 0}
                    , null
                    , borderColor);
        } else if (borderStyle.equalsIgnoreCase("float")) {
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth * 0.3f
                    , new float[]{0, 0}
                    , null
                    , borderColor);
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth * 0.3f
                    , new float[]{borderWidth * 0.7f, borderWidth * 0.7f}
                    , null
                    , borderColor);
        } else if (borderStyle.equalsIgnoreCase("groove")) {
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth
                    , new float[]{0, 0}
                    , null
                    , darkBorderColor);
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth * 0.4f
                    , new float[]{borderWidth * 0.6f, borderWidth * 0.6f}
                    , null
                    , borderColor);
        } else if (borderStyle.equalsIgnoreCase("ridge")) {
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth
                    , new float[]{0, 0}
                    , null
                    , borderColor);
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth * 0.4f
                    , new float[]{borderWidth * 0.6f, borderWidth * 0.6f}
                    , null
                    , darkBorderColor);
        } else if (borderStyle.equalsIgnoreCase("inset")) {
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth
                    , new float[]{0, 0}
                    , null
                    , darkBorderColor);
        } else if (borderStyle.equalsIgnoreCase("outset")) {
            _h5border(SELF, backgrounds
                    , border
                    , corners
                    , borderWidth
                    , new float[]{0, 0, 0, 0}
                    , null
                    , borderColor);
        } else {
            Log.e("[borderStyle]", borderStyle);
        }
    }

    @SuppressLint("NewApi")
     void _h5border(View SELF, ArrayList<Drawable> backgrounds,
                          String border,
                          String[] corners,
                          float width,
                          float[] radiusOffsets,
                          float[] dash,
                          int color) {
        SizeF Size = getSize(SELF);
        float radius1 = OnekitCSS.View_H5.getBorderRadius(SELF, corners[0]);
        float radius2 = OnekitCSS.View_H5.getBorderRadius(SELF, corners[1]);
        PointF cp12, cp23;
        PointF p1, p3;
        float a1, a2, a3;
        float r12, r23;
        float radiusOffset1 = radiusOffsets[0];
        float radiusOffset2 = radiusOffsets[1];
        r12 = radius1 - radiusOffset1;
        r23 = radius2 - radiusOffset2;
        float delta = (float) (radius1 - r12 / 1.414);
        if (border.equalsIgnoreCase("Top")) {
            cp12 = new PointF(radius1, radius1);
            cp23 = new PointF(Size.getWidth() - radius2, radius2);
            p1 = new PointF(delta, delta);
            p3 = new PointF(Size.getWidth() - radius2, radiusOffset2);
            a1 = 225;
            a2 = 270;
            a3 = 315;
        } else if (border.equalsIgnoreCase("Right")) {
            cp12 = new PointF(Size.getWidth() - radius1, radius1);
            cp23 = new PointF(Size.getWidth() - radius2, Size.getHeight() - radius2);
            p1 = new PointF(Size.getWidth() - delta, delta);
            p3 = new PointF(Size.getWidth() - radiusOffset2, Size.getHeight() - radius2);
            a1 = 315;
            a2 = 360;
            a3 = 405;
        } else if (border.equalsIgnoreCase("Bottom")) {
            cp12 = new PointF(Size.getWidth() - radius1, Size.getHeight() - radius1);
            cp23 = new PointF(radius2, Size.getHeight() - radius2);
            p1 = new PointF(Size.getWidth() - delta, Size.getHeight() - delta);
            p3 = new PointF(radius2, Size.getHeight() - radiusOffset2);
            a1 = 45;
            a2 = 90;
            a3 = 135;
        } else if (border.equalsIgnoreCase("Left")) {
            cp12 = new PointF(radius1, Size.getHeight() - radius1);
            cp23 = new PointF(radius2, radius2);
            p1 = new PointF(delta, Size.getHeight() - delta);
            p3 = new PointF(0 + radiusOffset2, radius2);
            a1 = 135;
            a2 = 180;
            a3 = 225;
        } else {
            return;
        }
        int width2 = dp2px(width);
        if (width > 0 && width2 < 1) {
            width2 = 1;
        }
        Path path = new Path();
        path.moveTo(dp2px(p1.x), dp2px(p1.y));
        path.addArc(dp2px(cp12.x - r12), dp2px(cp12.y - r12), dp2px(cp12.x + r12), dp2px(cp12.y + r12), a1, a2 - a1);
        path.lineTo(dp2px(p3.x), dp2px(p3.y));
        path.addArc(dp2px(cp23.x - r23), dp2px(cp23.y - r23), dp2px(cp23.x + r23), dp2px(cp23.y + r23), a2, a3 - a2);
        PathShape pathShape = new PathShape(path, Size.getWidth(), Size.getHeight());
        ShapeDrawable drawable = new ShapeDrawable(pathShape);
        drawable.getPaint().setStyle(Paint.Style.STROKE);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setStrokeWidth(width2);
        if (dash != null) {
            DashPathEffect dashPathEffect = new DashPathEffect(dash, 0);
            drawable.getPaint().setPathEffect(dashPathEffect);
        }
        backgrounds.add(drawable);
    }

    @SuppressLint("NewApi")
    private  SizeF getSize(View SELF) {
        CssLayoutParams layoutParams = (CssLayoutParams) SELF.getLayoutParams();
        float width = layoutParams.measuredWidth != null ? dp2px(layoutParams.measuredWidth) : 0;
        float height = layoutParams.measuredHeight != null ? dp2px(layoutParams.measuredHeight) : 0;
        return new SizeF(width, height);
    }
}

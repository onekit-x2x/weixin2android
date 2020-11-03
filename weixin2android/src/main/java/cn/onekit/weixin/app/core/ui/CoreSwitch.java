package cn.onekit.weixin.app.core.ui;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.storage.StorageManager;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.Switch;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public  class CoreSwitch extends Switch {
    Class<Switch> clazz = Switch.class;
    public CoreSwitch(Context context) {
        super(context);
    }
    public CoreSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private Layout makeLayout(CharSequence text) {
        try {
            Field mSwitchTransformationMethod = clazz.getDeclaredField("mSwitchTransformationMethod");mSwitchTransformationMethod.setAccessible(true);
            Field mTextPaint = clazz.getDeclaredField("mTextPaint");mTextPaint.setAccessible(true);
            Field mUseFallbackLineSpacing = clazz.getDeclaredField("mTextPaint");mTextPaint.setAccessible(true);
            Method getDesiredWidth = Layout.class.getDeclaredMethod("getDesiredWidth");getDesiredWidth.setAccessible(true);
            final CharSequence transformed = text;

            int width = (int) Math.ceil((int)getDesiredWidth.invoke(null,
                    transformed,
                    0,
                    transformed.length(),
                    (TextPaint)mTextPaint.get(this),
                    getTextDirectionHeuristic()));
            return StaticLayout.Builder.obtain(transformed, 0, transformed.length(), (TextPaint) mTextPaint.get(this), width)
                    .setUseLineSpacingFromFallbacks((boolean)mUseFallbackLineSpacing.get(this))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        try{
            Field mOnLayout = clazz.getDeclaredField("mOnLayout");mOnLayout.setAccessible(true);
            Field mOffLayout = clazz.getDeclaredField("mOffLayout");mOffLayout.setAccessible(true);
            Field mThumbWidth = clazz.getDeclaredField("mThumbWidth");mThumbWidth.setAccessible(true);
//            Field mTempRect = clazz.getDeclaredField("mTempRect");mTempRect.setAccessible(true);
            Field mSwitchWidth = clazz.getDeclaredField("mSwitchWidth");mSwitchWidth.setAccessible(true);
            Field mSwitchHeight = clazz.getDeclaredField("mSwitchHeight");mSwitchHeight.setAccessible(true);
        if (getShowText()) {
            if (mOnLayout.get(this) == null) {
                mOnLayout.set(this,makeLayout(getTextOn()));
            }

            if (mOffLayout.get(this) == null) {
                mOffLayout.set(this,makeLayout(getTextOff()));
            }
        }

        final Rect padding = new Rect();//(Rect) mTempRect.get(this);
        final int thumbWidth;
        final int thumbHeight;
        if (getThumbDrawable() != null) {
            // Cached thumb width does not include padding.
            getThumbDrawable() .getPadding(padding);
            thumbWidth = getThumbDrawable() .getIntrinsicWidth() - padding.left - padding.right;
            thumbHeight = getThumbDrawable() .getIntrinsicHeight();
        } else {
            thumbWidth = 0;
            thumbHeight = 0;
        }

        final int maxTextWidth;
        if (getShowText()) {
            maxTextWidth = Math.max(((Layout)mOnLayout.get(this)).getWidth(), ((Layout)mOnLayout.get(this)).getWidth())
                    + getThumbTextPadding() * 2;
        } else {
            maxTextWidth = 0;
        }

        mThumbWidth.set(this,Math.max(maxTextWidth, thumbWidth));

        final int trackHeight;
        if (getTrackDrawable() != null) {
            getTrackDrawable().getPadding(padding);
            trackHeight = getTrackDrawable().getIntrinsicHeight();
        } else {
            padding.setEmpty();
            trackHeight = 0;
        }

        // Adjust left and right padding to ensure there's enough room for the
        // thumb's padding (when present).
        int paddingLeft = padding.left;
        int paddingRight = padding.right;
        if (getTrackDrawable() != null) {
            final Insets inset = getTrackDrawable().getOpticalInsets();
            paddingLeft = Math.max(paddingLeft, inset.left);
            paddingRight = Math.max(paddingRight, inset.right);
        }

        final int switchWidth = Math.max(getSwitchMinWidth(),
                2 * (int)mThumbWidth.get(this) + paddingLeft + paddingRight);
        final int switchHeight = Math.max(trackHeight, thumbHeight);
        mSwitchWidth.set(this, switchWidth);
        mSwitchHeight.set(this,switchHeight);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int measuredHeight = getMeasuredHeight();
        if (measuredHeight < switchHeight) {
            setMeasuredDimension(getMeasuredWidthAndState(), switchHeight);
        }
        }catch (Exception e){
            e.printStackTrace();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
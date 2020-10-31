package cn.onekit.weixin.app.core.ui;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.SwitchCompat;

import java.lang.reflect.Field;

public  class CoreSwitch extends SwitchCompat {
    Class<android.widget.Switch> clazz = android.widget.Switch.class;
    public CoreSwitch(Context context) {
        super(context);
    }
    public CoreSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private Layout makeLayout(CharSequence text) throws Exception {
        TextPaint mTextPaint = (TextPaint) clazz.getField("mTextPaint").get(this);
        return new StaticLayout(text, mTextPaint,
                (int) Math.ceil(Layout.getDesiredWidth(text, mTextPaint)),
                Layout.Alignment.ALIGN_NORMAL, 1.f, 0, true);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            Field mShowText =clazz.getDeclaredField("mShowText");mShowText.setAccessible(true);
            Field mTextOn = clazz.getDeclaredField("mTextOn");mTextOn.setAccessible(true);
            Field mTextOff = clazz.getDeclaredField("mTextOff");mTextOff.setAccessible(true);
            Field mOnLayout = clazz.getDeclaredField("mOnLayout");mOnLayout.setAccessible(true);
            Field mOffLayout = clazz.getDeclaredField("mOffLayout");mOffLayout.setAccessible(true);
            // Method makeLayout =  clazz.getDeclaredMethod("makeLayout",CharSequence.class);
            Field mTempRect = clazz.getDeclaredField("mTempRect");mTempRect.setAccessible(true);
            Field mThumbDrawable = clazz.getDeclaredField("mThumbDrawable");mThumbDrawable.setAccessible(true);
            Field mTrackDrawable = clazz.getDeclaredField("mTrackDrawable");mTrackDrawable.setAccessible(true);
            Field mThumbTextPadding = clazz.getDeclaredField("mThumbTextPadding");mThumbTextPadding.setAccessible(true);
            Field mThumbWidth = clazz.getDeclaredField("mThumbWidth");mThumbWidth.setAccessible(true);
            Field mSwitchMinWidth = clazz.getDeclaredField("mSwitchMinWidth");mSwitchMinWidth.setAccessible(true);
            Field mSwitchWidth = clazz.getDeclaredField("mSwitchWidth");mSwitchWidth.setAccessible(true);
            Field mSwitchHeight =  clazz.getDeclaredField("mSwitchHeight");mSwitchHeight.setAccessible(true);
            //
            if ((boolean)mShowText.get(this)) {
                if (mOnLayout.get(this) == null) {
                    mOnLayout.set(this, makeLayout((String)mTextOn.get(this)));
                }

                if (mOffLayout.get(this) == null) {
                    mOffLayout.set(this, makeLayout((String)mTextOff.get(this)));
                }
            }

            final Rect padding = (Rect) mTempRect.get(this);
            final int thumbWidth;
            final int thumbHeight;
            if (mThumbDrawable.get(this)!=null) {
                // Cached thumb width does not include padding.
                Drawable thumbDrawable =(Drawable)mThumbDrawable.get(this);
                assert padding != null;
                assert thumbDrawable != null;
                thumbDrawable.getPadding(padding);
                thumbWidth =  thumbDrawable.getIntrinsicWidth() - padding.left - padding.right;
                thumbHeight =  thumbDrawable.getIntrinsicHeight();
            } else {
                thumbWidth = 0;
                thumbHeight = 0;
            }

            final int maxTextWidth;
            if ((boolean)mShowText.get(this)) {
                Layout onLayout=(Layout)mOnLayout.get(this);
                assert onLayout != null;
                maxTextWidth = (int)(Math.max(onLayout.getWidth(), onLayout.getWidth()
                        + (int)mThumbTextPadding.get(this) * 1.6));
            } else {
                maxTextWidth = 0;
            }

            mThumbWidth.set(this, Math.max(maxTextWidth, thumbWidth));

            final int trackHeight;
            if (mTrackDrawable.get(this) != null) {
                Drawable trackDrawable = (Drawable)mTrackDrawable.get(this);
                assert padding != null;
                assert trackDrawable != null;
                trackDrawable.getPadding(padding);
                trackHeight =   trackDrawable.getIntrinsicHeight();
            } else {
                assert padding != null;
                padding.setEmpty();
                trackHeight = 0;
            }

            // Adjust left and right padding to ensure there's enough room for the
            // thumb's padding (when present).
            int paddingLeft = padding.left;
            int paddingRight = padding.right;
            if (mThumbDrawable.get(this) != null) {
                Drawable thumbDrawable =(Drawable)mThumbDrawable.get(this);
                assert thumbDrawable != null;

              //  final Insets inset = thumbDrawable.getOpticalInsets();
              //  paddingLeft = Math.max(paddingLeft, inset.left);
              //  paddingRight = Math.max(paddingRight, inset.right);
            }

            final int switchWidth = (int)(Math.max((int)mSwitchMinWidth.get(this),
                    1.6 * (int)mThumbWidth.get(this) + paddingLeft + paddingRight));
            final int switchHeight = Math.max(trackHeight, thumbHeight);
            mSwitchWidth.set(this,switchWidth);
            mSwitchHeight.set(this,switchHeight);

            setMeasuredDimension(switchWidth, switchHeight);

        }catch (Exception e){
            e.printStackTrace();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


}
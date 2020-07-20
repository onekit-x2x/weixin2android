
package cn.onekit.weixin.app.core;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.onekit.Android;
import cn.onekit.Android;
import cn.onekit.weixin.Rotate3dAnimation;

import static cn.onekit.Android.dp2px;
import static cn.onekit.Android.px2dp;

public class View_Animation {
    public static void animation(final View view, final Map animationData) {
        if (animationData == null) {
            return;
        }
        final ArrayList actions = (ArrayList) animationData.get("actions");
        if (actions == null) {
            return;
        }
        final Context context = Android.context;
        view.post(new Runnable() {
            @Override
            public void run() {
                AnimationSet allAnimationSet = new AnimationSet(true);
                for (int s = 0; s < actions.size(); s++) {
                    Map action = (Map) actions.get(s);
                    ArrayList animates = (ArrayList) action.get("animates");
                    Map option = (Map) action.get("option");
                    Map transition = (Map) option.get("transition");
                    long duration = (long) (float) transition.get("duration");
                    //
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(duration);
                    animatorSet.setStartDelay(s * duration);

                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.setDuration(duration);
                    animationSet.setStartOffset(s * duration);
                    for (int i = 0; i < animates.size(); i++) {
                        Map animate = (Map) animates.get(i);
                        String type = (String) animate.get("type");
                        Float[] args = (Float[]) animate.get("args");
                        Animation animation = null;
                        switch (type) {
                            case "width": {
                                float width1 = px2dp(view.getWidth());//((H5View_) view).measuredWidth();
                                float width2 = args[0];
                                float scaleX = width2 / width1;
                                animation = new ScaleAnimation(Animation.RELATIVE_TO_SELF,
                                        scaleX, Animation.RELATIVE_TO_SELF, 1);
                            }
                            break;
                            case "height": {
                                float height1 = px2dp(view.getHeight());//((H5View_) view).measuredHeight();
                                float height2 = args[0];
                                float scaley = height2 / height1;
                                animation = new ScaleAnimation(Animation.RELATIVE_TO_SELF,
                                        1, Animation.RELATIVE_TO_SELF, scaley);
                            }
                            break;
                            case "backgroundColor": {
                                ObjectAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor", (int) (float) args[0]);
                                animator.setEvaluator(new ArgbEvaluator());
                                animatorSet.play(animator);
                                animatorSet.start();
                            }
                            break;
                            case "alpha": {
                                animation = new AlphaAnimation(1f, args[0]);
                            }
                            break;
                            case "scale": {//缩放动画
                                animation = new ScaleAnimation(Animation.RELATIVE_TO_SELF,
                                        args[0], Animation.RELATIVE_TO_SELF, args[1]);
                            }
                            break;

                            case "rotation": {//旋转动画
                                animation = new RotateAnimation(0f, (float) args[0], Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            }
                            break;
                            case "rotateX": {
                                float centerX = view.getWidth() / 2.0f;
                                float centerY = view.getHeight() / 2.0f;
                                float depthZ = 0f;

                                animation = new Rotate3dAnimation(0, args[0], centerX,
                                        centerY, depthZ, Rotate3dAnimation.ROTATE_X_AXIS, true);
                            }
                            break;
                            case "rotateY": {
                                float centerX = view.getWidth() / 2.0f;
                                float centerY = view.getHeight() / 2.0f;
                                float centerZ = 0f;

                                animation = new Rotate3dAnimation(0, args[0],
                                        centerX, centerY, centerZ, Rotate3dAnimation.ROTATE_Y_AXIS, true);
                            }
                            break;
                            case "rotateZ": {
                                float centerX = view.getWidth() / 2.0f;
                                float centerY = view.getHeight() / 2.0f;
                                float centerZ = 0f;
                                // Log.e("======", centerX + "====" + centerY);
                                animation = new Rotate3dAnimation(
                                        -args[0], 0, centerX, centerY, centerZ,
                                        Rotate3dAnimation.ROTATE_Z_AXIS, true);
                            }
                            break;
                            case "translation": { //移动动画
                                animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                                        dp2px( args[0]), Animation.RELATIVE_TO_SELF, dp2px( args[1]));
                            }
                            break;
                            case "bottom": {
                                animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                                        0, Animation.RELATIVE_TO_SELF, getScreenHeight(context) - dp2px( args[0]) - view.getHeight());

                            }
                            break;
                            case "right": {
                                animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                                        getScreenWidth(context) - dp2px( args[0]) - view.getWidth(), Animation.RELATIVE_TO_SELF, 0);
                            }
                            break;
                            case "skew": {//倾斜动画
                                final float v1 = dp2px( args[0]);
                                final float v2 = dp2px( args[0]);
                                animation = new Animation() {
                                    @Override
                                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                                        Matrix matrix = t.getMatrix();
                                        matrix.setSkew(v1 * interpolatedTime / view.getWidth(), v2 * interpolatedTime / view.getHeight());
                                    }
                                };
                                animation.setDuration(duration);
                            }
                            break;
                            case "matrix": {//矩阵动画a, b, c, d, tx, ty
                                final float a = args[0];
                                final float b = args[1];
                                final float c = args[2];
                                final float d = args[3];
                                final float tx = dp2px( args[4]);
                                final float ty = dp2px( args[5]);
                                animation = new Animation() {
                                    @Override
                                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                                        Matrix matrix = t.getMatrix();
                                        float[] values = new float[9];
                                        matrix.getValues(values);
                                        values[0] = 1 + (a - 1) * interpolatedTime;
                                        values[1] = c * interpolatedTime;
                                        values[2] = tx * interpolatedTime;
                                        values[3] = b * interpolatedTime;
                                        values[4] = 1 + (d - 1) * interpolatedTime;
                                        values[5] = ty * interpolatedTime;
                                        Matrix m = new Matrix();
                                        m.setValues(values);
                                        matrix.set(m);
                                    }
                                };
                                animation.setFillAfter(true);
                            }
                            break;
                            case "matrix3d": {
                                final float a = args[0];
                                final float b = args[1];
                                final float c = args[2];
                                final float d = args[3];
                                final float e = args[4];
                                final float f = args[5];
                                final float g = args[6];
                                final float tx = dp2px( args[7]);
                                final float ty = dp2px( args[8]);
                                animation = new Animation() {
                                    @Override
                                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                                        Matrix matrix = t.getMatrix();
                                        float[] values = new float[9];
                                        matrix.getValues(values);
                                        values[0] = 1 + (a - 1) * interpolatedTime;
                                        values[1] = c * interpolatedTime;
                                        values[2] = tx * interpolatedTime;
                                        values[3] = b * interpolatedTime;
                                        values[4] = 1 + (d - 1) * interpolatedTime;
                                        values[5] = ty * interpolatedTime;

                                        values[6] = e * interpolatedTime;
                                        values[7] = f * interpolatedTime;
                                        values[8] = g * interpolatedTime;
                                        Matrix m = new Matrix();
                                        m.setValues(values);
                                        matrix.set(m);
                                    }
                                };
                                animation.setFillAfter(true);
                            }
                            break;

                            default:
                                animation = null;
                                break;
                        }
                        if (animation != null) {
                            animationSet.addAnimation(animation);
                        }
                    }
                    allAnimationSet.addAnimation(animationSet);
                    allAnimationSet.setFillAfter(true); // 设置保持动画最后的状态

                }
                view.startAnimation(allAnimationSet);

//  */
            }

        });
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    /*
    *
    * 滑动ScrollView到指定位置
    *
    * */
    public static void pageScrollTO(HashMap OBJECT) {
        int scrollTop = 0;
        if (OBJECT.containsKey("scrollTop") && (!OBJECT.get("scrollTop").toString().equals(""))) {
            scrollTop = Integer.valueOf((String) OBJECT.get("scrollTop"));
        }
        //cn.onekit.weixin.app.MainActivity.SCROLLVIEW.smoothScrollTo(0,scrollTop);
    }


}

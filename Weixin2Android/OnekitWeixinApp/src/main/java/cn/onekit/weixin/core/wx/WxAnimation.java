package cn.onekit.weixin.core.wx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import thekit.COLOR;
import cn.onekit.js.JsArray;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsString;

public class WxAnimation extends WxAnalytics {
    class AnimationInfo {
        String type;
        JsArray values;

        public AnimationInfo(String type, Float[] values) {
            this.type = type;
            this.values = new JsArray();
            for(Float value:values){
                this.values.add(new JsNumber(value));
            }
        }
    }

    public class AnimationInfoSet {
        List<AnimationInfo> animationInfos=new ArrayList();
    }

    ArrayList<AnimationInfoSet>  _animations = new ArrayList<AnimationInfoSet> ();
    private ArrayList<AnimationInfo> _buffer = new ArrayList<AnimationInfo>();
    float duration = 400;//动画持续时间
    String timingFunction = "linear";//定义动画的效果
    float delay = 0;//动画延迟时间
    String transformOrigin = "50% 50% 0";//效果等同于center center

    WxAnimation() {

    }

    public WxAnimation createAnimation(Map OBJECT) {
        WxAnimation animation = new WxAnimation();
        if (OBJECT.get("duration") != null) {
            animation.duration = (float) OBJECT.get("duration");
        }
        if (OBJECT.get("timingFunction") != null) {
            animation.timingFunction = (String) OBJECT.get("timingFunction");
        }
        if (OBJECT.get("delay") != null) {
            animation.delay = (float) OBJECT.get("delay");
        }
        if (OBJECT.get("transformOrigin") != null) {
            animation.transformOrigin = (String) OBJECT.get("transformOrigin");
        }
        return animation;
    }

    //缩放：scaleX、scaleY
    public WxAnimation scale(Number sx, Number sy) {
        _buffer.add(new AnimationInfo("scale", new Float[]{sx.floatValue(), sy.floatValue()}));
        return this;
    }

    public WxAnimation scaleX(float s) {

        return scale((float) s, 1);
    }

    public WxAnimation scaleY(float s) {

        return scale(1, (float) s);
    }

    //横向拉伸
    public WxAnimation width(float width) {
        _buffer.add(new AnimationInfo("width", new Float[]{(float) width}));
        return this;
    }

    //纵向拉伸
    public WxAnimation height(float height) {
        _buffer.add(new AnimationInfo("height", new Float[]{(float) height}));
        return this;
    }

    //旋转度数：rotation、rotationX、rotationY
    public WxAnimation rotate(Float degree) {
        _buffer.add(new AnimationInfo("rotation", new Float[]{degree.floatValue()}));
        return this;
    }

    public WxAnimation rotateX(Float degree) {
        _buffer.add(new AnimationInfo("rotateX", new Float[]{degree.floatValue()}));
        return this;
    }

    public WxAnimation rotateY(Float degree) {
        _buffer.add(new AnimationInfo("rotateY", new Float[]{degree.floatValue()}));
        return this;
    }

    public WxAnimation rotateZ(Float degree) {
        _buffer.add(new AnimationInfo("rotateZ", new Float[]{degree.floatValue()}));
        return this;
    }

    //透明动画
    public WxAnimation opacity(float alpha) {
        _buffer.add(new AnimationInfo("alpha", new Float[]{(float) alpha}));
        return this;
    }

    //平移：translationX、translationY
    public WxAnimation translate(Float tx, Float ty) {
        if (tx == null) {
            tx = 1.0f;
        } else if (ty == null) {
            ty = 1.0f;
        }
        _buffer.add(new AnimationInfo("translation", new Float[]{(float) ((float) tx), (float) ((float) ty)}));
        return this;
    }

    public WxAnimation translateX(float t) {
        return translate(t, 0.0f);
    }

    public WxAnimation translateY(float t) {
        return translate(0.0f, t);
    }

    //距离
    public WxAnimation top(float top) {

        return translateY(top);
    }

    public WxAnimation left(float left) {

        return translateX(left);
    }

    public WxAnimation bottom(Float bottom) {

        _buffer.add(new AnimationInfo("bottom", new Float[]{(float) (float) bottom}));
        return this;
    }

    public WxAnimation right(Float right) {
        _buffer.add(new AnimationInfo("right", new Float[]{(float) (float) right}));
        return this;
    }

    //背景颜色
    public WxAnimation backgroundColor(String backgroundColor) {
        int color = COLOR.parse(backgroundColor);
        _buffer.add(new AnimationInfo("backgroundColor", new Float[]{(float) color}));
        return this;
    }

    //倾斜
    public WxAnimation skew(Float sx, Float sy) {
        _buffer.add(new AnimationInfo("skew", new Float[]{(float) (float) sx, (float) (float) sy}));
        return this;
    }

    //矩阵
    public WxAnimation matrix(float a, float b, float c, float d, float tx, float ty) {
        _buffer.add(new AnimationInfo("matrix", new Float[]{(float) a, (float) b, (float) c, (float) d, (float) tx, (float) ty}));
        return this;
    }

    public WxAnimation matrix3d(float a, float b, float c, float d, float e, float f, float g, float tx, float ty) {
        _buffer.add(new AnimationInfo("matrix3d", new Float[]{(float) a, (float) b, (float) c, (float) d, (float) e, (float) f, (float) g, (float) tx, (float) ty}));
        return this;
    }

    public void step(Map option) {
        AnimationInfoSet animationInfoSet = new AnimationInfoSet();
        for (AnimationInfo animationInfo : _buffer) {
            animationInfoSet.animationInfos.add(animationInfo);
        }
        _animations.add(animationInfoSet);
        _buffer.clear();
    }

    public JsObject export() {
        final JsArray actions = new JsArray();
        for (AnimationInfoSet animationInfoSet : _animations) {
            final JsObject action = new JsObject();
            //定义集合
            final JsArray animates = new JsArray();
            for (AnimationInfo animationInfo : animationInfoSet.animationInfos) {
                final JsObject animate = new JsObject();
                animate.put("type", new JsString(animationInfo.type));
                animate.put("args",  animationInfo.values);
                animates.add(animate);
            }
            action.put("animates", animates);
            //
            final JsObject option = new JsObject();
            option.put("transformOrigin", new JsString( transformOrigin));
            option.put("transition", new JsObject() {{
                put("duration", new JsNumber(duration));
                put("timingFunction", new JsString(timingFunction));
                put("delay", new JsNumber(delay));
            }});
            action.put("option", option);
            //
            actions.add(action);
        }
        //
        _animations.clear();
        //
        return new JsObject() {{
            put("actions", actions);
        }};

    }

}

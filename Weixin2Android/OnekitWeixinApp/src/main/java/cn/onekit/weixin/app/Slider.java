package cn.onekit.weixin.app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import cn.onekit.js.JsObject;
import cn.onekit.js.JsNumber;
import cn.onekit.w3c.Event;
import cn.onekit.w3c.core.FormItem_;
import cn.onekit.weixin.app.core.WeixinElement;

import static thekit.android.Android.dp2px;

public class Slider extends WeixinElement implements FormItem_<JsNumber> {


    public Slider(Context context) {
        super(context);
        _init();
    }

    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    private SeekBar _slider(){
        return findViewById(R.id.slider);
    }
    private TextView _text(){
        return findViewById(R.id.value);
    }

    private void _init(){
        inflate(getContext(), R.layout.onekit_slider, this);
        //
        setBlockColor(getBlockColor());
        setBlockSize(getBlockSize());
        setShowValue(getShowValue());
        _text().setText(String.valueOf(_min));
        _slider().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                _value = new JsNumber(progress * _step + _min);
                String v = _value + "";
                _text().setText(v);
                Event event = new Event("changing", new JsObject() {{

                    put("value", _value);

                }},Slider.this,Slider.this,0);
                dispatchEvent(event);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Event event = new Event("change", new JsObject() {{

                    put("value", _value);

                }},Slider.this,Slider.this,0);
                dispatchEvent(event);
            }
        });

    }
    ///////////////
    private float _min=0;
    public void setMin(float min){
        _min=min;
        _updateMax();
    }
    public float getMin(){
        return _min;
    }
    //////////////
    private float _max=100;
    public void setMax(float max){
        _max=max;
        _updateMax();
    }
    public float getMax(){
        return _max;
    }
    //////////////
    public float _step=1;
    public void setStep(float step){
        _step=step;
        _updateMax();
    }
    public float getStep(){
        return _step;
    }
    ///////////////
    void _updateMax(){
        _slider().setMax(Math.round((_max-_min)/_step));
    }
    ///
    private boolean _disabled=false;
    public void setDisabled(boolean disabled){
        _disabled=disabled;
        _slider().setEnabled(!_disabled);
    }
    public boolean getDisabled(){
        return _disabled;
    }
    /////////////
    private int _backgroundColor;
    public void setBackgroundColor(int backgroundColor){
        _backgroundColor=backgroundColor;
        ColorStateList backTintList = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.progress}
        },
                new int[]{
                    _backgroundColor
                });
        _slider().setProgressBackgroundTintList(backTintList);
    }
    public int getBackgroundColor(){
        return _backgroundColor;
    }

    private int _activeColor;
    public void setActiveColor(int activeColor){
        _activeColor=activeColor;
        ColorStateList activeTintList = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.progress}
        },
                new int[]{
                        _activeColor
                });
       // _slider().setProgressBackgroundTintList(activeTintList);
        _slider().setProgressTintList(activeTintList);
    }
    public int getActiveColor(){
        return _activeColor;
    }
    /////////////////////
    private int _blockSize=28;

    public void setBlockSize(int blockSize){
        _blockSize=blockSize;

       // Drawable drawable=getResources().getDrawable(R.drawable.slider_ios_thumb,getContext().getTheme());
        StateListDrawable thumbDrawableList= (StateListDrawable)_slider().getThumb();
        GradientDrawable thumbDrawable= (GradientDrawable) thumbDrawableList.getStateDrawable(0);
        //
        int blockSize_ = dp2px(_blockSize);
        assert thumbDrawable != null;
        thumbDrawable.setSize(blockSize_,blockSize_);
        _slider().setThumbOffset(blockSize_/2);
       // _slider().setThumb(drawable);

    }
    public int getBlockSize(){
        return _blockSize;
    }

    /////////////////
    private int _blockColor=Color.parseColor("#FFFFFF");

    public void setBlockColor(int blockColor){
        _blockColor=blockColor;
        StateListDrawable thumbDrawableList= (StateListDrawable)_slider().getThumb();
        GradientDrawable thumbDrawable= (GradientDrawable) thumbDrawableList.getStateDrawable(0);
        assert thumbDrawable != null;
        thumbDrawable.setColor(blockColor);

    }
    public int getBlockColor(){
        return _blockColor;
    }

    /////////////////
    private boolean _showValue=false;
    public void setShowValue(boolean showValue){
        _showValue=showValue;
        if(_showValue) {
            _text().setVisibility(View.VISIBLE);
        }else {
            _text().setVisibility(View.GONE);
        }
    }
    public boolean getShowValue(){
        return _showValue;
    }
    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setValue(JsNumber value) {
        _value = value;
    }

    private JsNumber _value=new JsNumber(0);
    @Override
    public JsNumber getValue() {
        return new JsNumber(_value);
    }

    @Override
    public void reset() {

    }


}

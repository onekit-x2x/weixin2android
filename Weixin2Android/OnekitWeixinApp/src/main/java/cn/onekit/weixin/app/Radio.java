package cn.onekit.weixin.app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import cn.onekit.js.JsBoolean;
import cn.onekit.js.Dict;
import cn.onekit.js.JsObject;
import cn.onekit.w3c.Event;
import cn.onekit.w3c.core.FormItem_;
import cn.onekit.weixin.app.core.WeixinElement;

public class Radio extends WeixinElement implements FormItem_ {

    public Radio(Context context) {
        super(context);
        _init();
    }
    public Radio(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init();
    }
    private RadioButton _radio(){
        return findViewById(R.id.radio);
    }
    private void _init(){
        inflate(getContext(), R.layout.onekit_radio,this);
    _radio().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            Event event = new Event("change",new Dict() {{

                put("value", new JsBoolean(isChecked));

            }},Radio.this,Radio.this,0);
            dispatchEvent(event);

        }
    });
}
    private int _color=Color.parseColor("#00FF00");
    public void setColor(int color){
        _color=color;
        ColorStateList tintList = new ColorStateList(new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked},
        },
                new int[]{
                        _color,
                        Color.parseColor("#565656")

                });
        _radio().setButtonTintList(tintList);
    }
    public int getColor(){
        return _color;
    }

    private boolean _checked=false;
    public void setChecked(boolean checked){
        _checked=checked;
        _radio().setChecked(true);
    }
    public boolean getChecked(){
        return _checked;
    }
    private boolean _disabled=false;
    public void setDisabled(boolean disabled){
        _disabled=disabled;
        _radio().setEnabled(!disabled);
    }
    public boolean getDisabled(){
        return _disabled;
    }
    String _name;
    @Override
    public void setName(String name) {
_name = name;
    }
    @Override
    public String getName() {
        return _name;
    }


    private String _value="";
    @Override
    public void setValue(JsObject value) {
        _value=value.toString();
        _radio().setText(_value);
    }

    @Override
    public JsObject getValue() {
        return null;
    }

    @Override
    public void reset() {

    }

}

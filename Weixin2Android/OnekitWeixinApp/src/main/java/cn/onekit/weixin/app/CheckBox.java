package cn.onekit.weixin.app;

import android.content.Context;
import android.util.AttributeSet;

import cn.onekit.js.JsBoolean;
import cn.onekit.js.Dict;
import cn.onekit.w3c.Event;
import cn.onekit.w3c.core.FormItem_;
import cn.onekit.weixin.app.core.WeixinElement;
import cn.onekit.weixin.app.core.ui.CoreCheckBox;

public class CheckBox extends WeixinElement implements FormItem_<JsBoolean> {

    public CheckBox(Context context) {
        super(context);
    }
    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void _init() {
        inflate(getContext(), R.layout.onekit_switch, this);
        setColor(getColor());
        _checkbox()._checkbox().setOnCheckedChangeListener((sender, isChecked) -> {

            Event event = new Event("change",new Dict() {{

                put("value",new JsBoolean( isChecked));

            }},this,this,0);
            dispatchEvent(event);
        });
//

    }
    private CoreCheckBox _checkbox() {
        return  this.findViewById(R.id.checkbox);
    }
private String _name;
    @Override
    public void setName(String name) {
_name=name;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public void setValue(JsBoolean value) {
_value=value;
    }

    JsBoolean _value;
    @Override
    public JsBoolean getValue() {

        return _checkbox().getChecked()?_value:null;
    }

    @Override
    public void reset() {
        _checkbox().setChecked(false);
    }

    int _color;

    public void setColor(int color) {
        _color = color;

        _checkbox().setColor(_color);

    }

    public int getColor() {
        return _color;
    }
    private JsBoolean _checked;
    public void setChecked(JsBoolean checked){
        _checked=checked;
        _checkbox().setChecked(checked.THIS);
    }
    public JsBoolean getChecked(){
        return _checked;
    }



}

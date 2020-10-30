package cn.onekit.weixin.app;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import cn.onekit.js.JsBoolean;
import cn.onekit.js.JsObject;
import cn.onekit.w3c.Event;
import cn.onekit.w3c.core.FormItem_;
import cn.onekit.weixin.app.core.WeixinElement;
import cn.onekit.weixin.app.core.ui.CoreCheckBox;
import cn.onekit.weixin.app.core.ui.CoreSwitch;


public class Switch extends WeixinElement implements FormItem_<JsBoolean> {
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

    }
    @Override
    public JsBoolean getValue() {
        switch (_type) {
            case "checkbox":
                return new JsBoolean(_mainCheckbox().getChecked());

            case "switch":
                return new JsBoolean( _mainSwitch().isChecked());
            default:
                return null;
        }
    }

    @Override
    public void reset() {
        _mainSwitch().setChecked(false);
        _mainCheckbox().setChecked(false);
    }

    ////////////////////////////////////////



    public Switch(Context context) {
        super(context);
        _init();
    }
    /////////////////////////////////////
    private boolean _disabled;

    public void setDisabled(Boolean disabled){
            _disabled=disabled;
            _mainSwitch().setEnabled(!disabled);
            _mainCheckbox().setDisabled(disabled);
    }
    public boolean getDisabled(){
        return _disabled;
    }
    private boolean _checked;
    public void setChecked(Boolean checked){
            _checked=checked;
            _mainSwitch().setChecked(checked);
            _mainCheckbox().setChecked(checked);
    }
    public boolean getChecked(){
        return _checked;
    }


    private String _type = "switch";
    public void setType(String type) {
        switch (type) {
            case "checkbox":
                _mainSwitch().setVisibility(View.GONE);
                _mainCheckbox().setVisibility(View.VISIBLE);
                break;
            case "switch":
                _mainSwitch().setVisibility(View.VISIBLE);
                _mainCheckbox().setVisibility(View.GONE);
            default:
                return;
        }
        _type = type;
    }

    public String getType() {
        return _type;
    }

    //
    private CoreSwitch _mainSwitch() {
        return  this.findViewById(R.id.mainSwitch);
    }

    private CoreCheckBox _mainCheckbox() {
        return  this.findViewById(R.id.mainCheckbox);
    }


    private void _init() {
        inflate(getContext(), R.layout.onekit_switch, this);
        setColor(getColor());
        _mainSwitch().setOnCheckedChangeListener((buttonView, isChecked) -> {


            Event event = new Event("change",new JsObject() {{

                put("value", new JsBoolean(isChecked));

            }},this,this,0);
            dispatchEvent(event);
        });
        _mainCheckbox()._checkbox().setOnCheckedChangeListener((sender, isChecked) -> {

            Event event = new Event("change",new JsObject() {{

                put("value", new JsBoolean(isChecked));

            }},this,this,0);
            dispatchEvent(event);
        });
//

    }

   private int _color=getResources().getColor(R.color.weixin,getContext().getTheme());


    public void setColor(int color) {
        _color = color;
        //
        switch (_type) {
            case "checkbox":
                _mainCheckbox().setColor(_color);
                break;
            case "switch":
                StateListDrawable trackDrawableList =      (StateListDrawable) _mainSwitch().getTrackDrawable();
                GradientDrawable trackDrawable= (GradientDrawable) trackDrawableList.getStateDrawable(1);
                assert trackDrawable != null;
                trackDrawable.setColor(_color);
                //
                StateListDrawable thumbDrawableList =      (StateListDrawable) _mainSwitch().getThumbDrawable();
                GradientDrawable thumbDrawable= (GradientDrawable) thumbDrawableList.getStateDrawable(1);
                assert thumbDrawable != null;
                thumbDrawable.setStroke(1,_color);
                break;
            default:
                break;
        }
    }

    public int getColor() {
        return _color;
    }


}
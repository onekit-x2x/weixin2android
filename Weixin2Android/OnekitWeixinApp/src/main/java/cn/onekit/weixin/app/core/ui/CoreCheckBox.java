package cn.onekit.weixin.app.core.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import cn.onekit.weixin.app.R;

//import cn.onekit.R;

public class CoreCheckBox extends RelativeLayout {
    /* public interface OnCheckedChangeListener {
        void onChange(CoreCheckBox sender, boolean isChecked);
    }

     public OnCheckedChangeListener _changeListener;
     public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
        _changeListener=listener;
    }*/

    public CoreCheckBox(Context context) {
        super(context);
        _init();
    }
    public CoreCheckBox(Context context,  AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    public android.widget.CheckBox _checkbox() {
        return  this.findViewById(R.id.checkbox);
    }
    private View _mask() {
        return  this.findViewById(R.id.mask);
    }
    private void _init(){
        View view=inflate(getContext(), R.layout.onekit_checkbox, this);
        view.setScaleX(1.37f);
        view.setScaleY(1.37f);
        setColor(_color);
       /* _checkbox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if(_changeListener!=null){
                    _changeListener.onChange(CoreCheckBox.this,isChecked);
                }

            }
        });*/
        //_checkbox().setPadding(-8,0,0,0);

    }
    //
    private boolean _disabled=true;
    public void setDisabled(boolean disabled){
        _disabled=disabled;
        _checkbox().setEnabled(!disabled);
        _mask().setVisibility(disabled && !_checked?View.VISIBLE:View.GONE);
    }
    public boolean getDisabled(){
        return _disabled;
    }
    //
    private boolean _checked=false;
    public void setChecked(boolean checked){
        _checked=checked;
        _checkbox().setChecked(checked);
    }
    public boolean getChecked(){
        return _checked;
    }
    //
    private int _color = Color.parseColor("#04BE02");
    public void setColor( int color){
        _color=color;
        @SuppressLint("ResourceType")
                int[] attr = new int[]{android.R.attr.colorControlNormal};
        TypedArray array = getContext().getTheme().obtainStyledAttributes(R.style.onekit_checkbox, attr);
        int defaultColor =array.getColor(0  , 0 );
        ColorStateList buttonTintList = new ColorStateList(new int[][]{
                //  new int[]{android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled,android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_enabled}
        },
                new int[]{
                        _color,
                        defaultColor,
                        defaultColor
                });
        _checkbox().setButtonTintList(buttonTintList);

    }
    public int getColor(){return _color;}
}

package cn.onekit.weixin.app;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.onekit.css.CSSStyleDeclaration;
import cn.onekit.weixin.app.core.WeixinElement;

public class RadioGroup extends WeixinElement {

    public RadioGroup(@NonNull Context context) {
        super(context);
        _init();
    }
    public RadioGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        Log.d("TAG", "setOnClickListener: "+111111111);
        for (int i=0;i<this.getChildCount();i++){
            View childView=this.getChildAt(i);
            if(childView instanceof Radio && childView==((Activity) getContext()).getCurrentFocus()){
                ((Radio)childView).setChecked(true);
            }else if(childView instanceof Radio && childView!=((Activity) getContext()).getCurrentFocus()){
                ((Radio)childView).setChecked(false);
            }
        }
    }

    private void _init(){

//        for(int i=0;i<this.getChildCount();i++){
//            View childView=this.getChildAt(i);
//            if (childView instanceof Radio){
//                if(((Radio) childView).getChecked()==true){
//                    List<View> views=getAllChildView(this);
//                    for()
//                }
//            }
//        }

        this.setOnClickListener(view -> {
            for (int i=0;i<this.getChildCount();i++){
                View childView=this.getChildAt(i);
                if(childView instanceof Radio && childView==((Activity) getContext()).getCurrentFocus()){
                    ((Radio)childView).setChecked(true);
                }else if(childView instanceof Radio && childView!=((Activity) getContext()).getCurrentFocus()){
                    ((Radio)childView).setChecked(false);
                }
            }
        });


    }

//    private List<View> getAllChildView(ViewGroup v){
//        for (int i=0;i<v.getChildCount();i++){
//            View childView=v.getChildAt(i);
//            if(childView instanceof Radio && childView==((Activity) getContext()).getCurrentFocus()){
//                ((Radio)childView).setChecked(true);
//            }else if(childView instanceof Radio && childView!=((Activity) getContext()).getCurrentFocus()){
//                ((Radio)childView).setChecked(false);
//            }
//        }
//        return childList;
//    }


}

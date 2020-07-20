package demo.weixin.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import cn.onekit.w3c.Event;
import cn.onekit.w3c.EventListener;
import cn.onekit.weixin.app.SLIDER;

public class SliderDemo extends Activity {
    ViewGroup root(){
        return this.findViewById(R.id.root);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_checkbox);
        //
        SLIDER slider = new SLIDER(this);
        root().addView(slider);
        slider.addEventListener("change", new EventListener() {
            @Override
            public boolean handleEvent(Event event) {
                Log.e("========",event+"");
                return false;
            }
        },false);
    }
}

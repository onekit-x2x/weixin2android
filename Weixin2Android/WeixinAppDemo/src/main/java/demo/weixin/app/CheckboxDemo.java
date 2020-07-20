package demo.weixin.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import cn.onekit.w3c.Event;
import cn.onekit.w3c.EventListener;
import cn.onekit.weixin.app.CHECKBOX2;

public class CheckboxDemo extends Activity {
    ViewGroup root(){
        return this.findViewById(R.id.root);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_checkbox);
        //
        CHECKBOX2 checkbox = new CHECKBOX2(this);
        checkbox.setChecked(true);
        // switcha.setDisabled(true);
        checkbox.setColor(Color.RED);
        root().addView(checkbox);
        checkbox.addEventListener("change", new EventListener() {
            @Override
            public boolean handleEvent(Event event) {
                Log.e("========",event+"");
                return false;
            }
        },false);
    }
}

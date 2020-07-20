package demo.weixin.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import cn.onekit.w3c.Event;
import cn.onekit.w3c.EventListener;
import cn.onekit.weixin.app.RADIO;

public class RadioDemo extends Activity {
    ViewGroup root(){
        return this.findViewById(R.id.root);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_checkbox);
        //
        RADIO radio = new RADIO(this);
       // radio.setChecked(true);
        // switcha.setDisabled(true);
        //radio.setColor(Color.RED);
        root().addView(radio);
        radio.addEventListener("change", new EventListener() {
            @Override
            public boolean handleEvent(Event event) {
                Log.e("========",event+"");
                return false;
            }
        },false);
    }
}

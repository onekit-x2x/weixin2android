package demo.weixin.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import cn.onekit.w3c.Event;
import cn.onekit.w3c.EventListener;
import cn.onekit.weixin.app.*;

public class SwitchDemo extends Activity {
    ViewGroup root(){
        return this.findViewById(R.id.root);
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_switch);
        //
        SWITCH switcha = new SWITCH(this);
        switcha.setChecked(true);
       // switcha.setDisabled(true);
     //   switcha.setType("checkbox");
        switcha.setColor(Color.RED);
        root().addView(switcha);
        switcha.addEventListener("change", new EventListener() {
            @Override
            public boolean handleEvent(Event event) {
                Log.e("========",event+"");
                return false;
            }
        },false);
    }
}

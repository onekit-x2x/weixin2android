package demo.js;

import android.app.Activity;
import android.os.Bundle;

import cn.onekit.js.JsArray;
import cn.onekit.js.Float64Array;
import cn.onekit.js.core.JsFile;

public class MainActivity extends Activity implements JsFile {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //java demo
        Float64Array float64 = new Float64Array(new JsArray(){{add(1);add(2);add(3);}});
        console.log(float64.includes(2)); //false
    }
}

package cn.onekit.js;

import android.util.Log;

import java.util.Random;

import cn.onekit.js.core.Onekit_JS;

public class Console implements JsObject {
    String _run(JsObject... data) {
        StringBuilder str = new StringBuilder(" \r\n");
        for (int i=0;i<data.length;i++) {
            JsObject item = data[i];
            str.append(String.format("%s\t", Onekit_JS.toString(item)));
        }
        return str.toString();
    }

    public void asset(JsObject... assets) {

        Log.v("[OneKit]====================="+ new Random().nextInt(), _run(assets));
    }

    public void error(JsObject... errors) {

        Log.e("[OneKit]====================="+ new Random().nextInt(), _run(errors));
    }

    public void info(JsObject... infos) {

        Log.i("[OneKit]====================="+new Random().nextInt(), _run(infos));
    }

    public void log(JsObject... logs) {

        Log.d("[OneKit]====================="+ new Random().nextInt(), _run(logs));
    }

    public void warn(JsObject... warns) {

        Log.w("[OneKit]====================="+ new Random().nextInt(), _run(warns));
    }

    @Override
    public JsString ToString() {
        return null;
    }
}

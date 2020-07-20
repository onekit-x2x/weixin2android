package cn.onekit.weixin.app;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import cn.onekit.js.Dict;
import cn.onekit.js.core.JsString;
import cn.onekit.w3c.Event;
import cn.onekit.weixin.app.core.WeixinElement;

public class RichText extends WeixinElement {

    private TextView richText(){
        return findViewById(R.id.rich_text);
    }
    public RichText(Context context) {
        super(context);
        _init();
    }
    public RichText(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init();
    }
    private void _init(){
        inflate(getContext(),R.layout.onekit_richtext,this);
        richText().setOnClickListener(view -> {
            Log.d("~~~~~~~~~~~~", "1111111111111");
            Event event = new Event("tap",new Dict() {{
                put("tap", new JsString("tap"));
            }},this,this,0);
            dispatchEvent(event);
        });
        richText().setTextColor(Color.parseColor("#000000"));
    }
    private Object _node;
    public void setNode(Object node){
        _node=node;
        if (_node instanceof String){
            richText().setText((String)_node);
        }else {

        }
    }
    public Object getNode(){
        return _node;
    }



}

package demo.weixin.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import cn.onekit.weixin.app.RICHTEXT;

public class RichtextDemo extends Activity {
    ViewGroup root(){
        return this.findViewById(R.id.root);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_richtext);
        RICHTEXT richText=new RICHTEXT(this);
        root().addView(richText);
        richText.setNode("我爱你");
//        richText.addEventListener("tap", new EventListener() {
//            @Override
//            public boolean handleEvent(Event event) {
//                //Log.e("========",event+"");
//                Log.d("~~~~~~~~~~~~", event.detail.toString());
//                Log.d("~~~~~~~~~~~~", "1111111111111");
//                return true;
//            }
//        },true);
//        try{
//            JSONObject jsonObject=new JSONObject();
//            jsonObject.put("node",new JSONArray(){{
//
//                put(new JSONObject(){{
//                    put("name","a");
//                    put()
//                }});
//            }});
//            jsonObject.put("space","ss");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        HashMap hashMap=new HashMap();
        hashMap.put("node",new ArrayList(){{
            add(new HashMap(){{
                put("name","a");
                put("attrs",new HashMap(){{put("class","div_class");put("style","line-height: 100px; color: red;");}});
                put("children",new ArrayList(){{
                    add(new HashMap(){{
                    put("type","text");
                    put("text","Hello&nbsp;World!");
                }});}});
            }});

        }});

    }
}
